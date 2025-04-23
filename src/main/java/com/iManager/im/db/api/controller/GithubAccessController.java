package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.PullRequest;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.PullRequestRepo;
import com.iManager.im.db.api.responseDTO.PullResponseDTO;
import com.iManager.im.db.api.service.GithubAuthService;
import com.iManager.im.db.api.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/github")
public class GithubAccessController {
    @Autowired
    GithubAuthService githubAuthService;
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    PullRequestRepo pullRequestRepo;
    @Autowired
    Mapper mapper;

    @PostMapping("/exchange")
    public ResponseEntity getAccessToken(@RequestParam String authCode,
                                         @RequestParam UUID loggedId){
        String token = githubAuthService.getAccessToken(authCode);
        System.out.println(token);
        try {
            Optional<Organization> organization = orgRepository.findById(loggedId);
            if (organization.isPresent()) {
                Organization org = organization.get();
                org.setGithubToken(token);
                orgRepository.save(org);
                return ResponseEntity.ok(token);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return new ResponseEntity("Failed authorizing github connect",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get/repos/{loggedEmail}")
    public ResponseEntity getRepos(@PathVariable String loggedEmail)
    {
        try{
            Optional<Organization> organization = orgRepository.findByEmail(loggedEmail);
            if(organization.isPresent()){
                String accessToken = organization.get().getGithubToken();
                List<Map<String,Object>> responseBody = githubAuthService.getRepos(accessToken);
                List<String> repoFullNames = responseBody.stream()
                        .map(repo -> (String) repo.get("full_name"))
                        .toList();
                return ResponseEntity.ok(repoFullNames);
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/webhook")
    public ResponseEntity githubWebhook(@RequestBody Map<String, Object> payload){
        Map<String, Object> pullRequest = (Map<String, Object>) payload.get("pull_request");

        if(pullRequest != null) {
            Map<String, Object> user = (Map<String, Object>) pullRequest.get("user");

            String prTitle = (String) pullRequest.get("title");
            String prState = (String) pullRequest.get("state");
            if((boolean) pullRequest.get("merged")){
                prState = "merged";
            }
            String url = (String) pullRequest.get("html_url");
            Map<String,Object> base = (Map<String, Object>) pullRequest.get("base");
            Map<String,Object> head = (Map<String, Object>) pullRequest.get("head");
            String baseBranch = (String) base.get("ref");
            String headBranch = (String) head.get("ref");
            String author = (String) user.get("login");

            PullRequest pr = new PullRequest();
            pr.setAuthor(author);
            pr.setTicketId(headBranch.substring(4));
            pr.setPrTitle(prTitle);
            pr.setPrUrl(url);
            pr.setState(prState);
            pr.setHeadBranch(headBranch);
            pr.setBaseBranch(baseBranch);

            pullRequestRepo.save(pr);

            System.out.println("ticket-id: "+headBranch.substring(4));
            System.out.println("username: "+author);
            System.out.println("📌 Title: " + prTitle);
            System.out.println("📌 Url: " + url);
            System.out.println("📌 baseBranch: " + baseBranch);
            System.out.println("📌 headBranch: " + headBranch);
            System.out.println("📂 State: " + prState);

            return ResponseEntity.ok("Webhook received");
        }

        return ResponseEntity.ok("not pull req");
    }

    @GetMapping("/get/pr/{ticketId}")
    public ResponseEntity getGithubPr(@PathVariable String ticketId){
        try {
            List<PullRequest> prList = pullRequestRepo.findByTicketId(ticketId);
            List<PullResponseDTO> pullResponseDTOList = mapper.pullResponseDTOS(prList);
            return ResponseEntity.ok(pullResponseDTOList);
        }catch (Exception e){
            return new ResponseEntity("failed fetchign pr",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
