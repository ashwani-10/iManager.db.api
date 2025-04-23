package com.iManager.im.db.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class GithubAuthService {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.webhook}")
    private String webhook;

    @Autowired
    RestTemplate restTemplate;

    public String getAccessToken(String authCode){
        String tokenUrl = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Get response in JSON

        // Use MultiValueMap instead of raw string
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", authCode);
        body.add("redirect_uri", redirectUri);  // e.g. http://localhost:5173/dashboard

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        // Cast the response
        Map<String, String> responseMap = response.getBody();
        return responseMap.get("access_token");
    }

    public List<Map<String, Object>> getRepos(String accessToken) {
        String url = "https://api.github.com/user/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // "Authorization: Bearer <token>"
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }

    public void addWebhookToRepo(String repoFullName,String accessToken){
        String url = "https://api.github.com/repos/" + repoFullName+"/hooks";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/vnd.github+json");

        Map<String, Object> config = new HashMap<>();
        config.put("url", webhook);
        config.put("content_type", "json");
        config.put("insecure_ssl", "0");
        config.put("secret", clientSecret);

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "web");
        payload.put("active", true);
        payload.put("events", List.of("push", "pull_request"));
        payload.put("config", config);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
