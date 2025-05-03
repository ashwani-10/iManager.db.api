package com.iManager.im.db.api.controller;

import com.iManager.im.db.api.model.Comment;
import com.iManager.im.db.api.model.Tasks;
import com.iManager.im.db.api.model.User;
import com.iManager.im.db.api.repository.CommentRepository;
import com.iManager.im.db.api.repository.TaskRepository;
import com.iManager.im.db.api.repository.UserRepository;
import com.iManager.im.db.api.requestDTO.CommentRequestDTO;
import com.iManager.im.db.api.responseDTO.CommentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/db/api/comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create/{task_id}")
    public ResponseEntity createComment(@PathVariable UUID task_id,
                                        @RequestBody CommentRequestDTO requestDTO){
        try {
            Tasks task = taskRepository.findById(task_id).orElseThrow();
            User user = userRepository.findById(requestDTO.getUser_id()).orElseThrow();

            Comment comment = new Comment();
            comment.setMessage(requestDTO.getMessage());
            comment.setTask(task);
            comment.setUser(user);
            comment.setCreatedAt(Instant.now());
            commentRepository.save(comment);
            return new ResponseEntity<>("Comment created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Failed creating comment",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{task_id}")
    public ResponseEntity getComments(@PathVariable UUID task_id){
        try {
            List<Comment> comments = commentRepository.findCommentsByTaskId(task_id);
            List<CommentResponseDTO> responseDTO = new ArrayList<>();
            for(Comment c : comments){
                CommentResponseDTO comment = new CommentResponseDTO();
                comment.setId(c.getId());
                comment.setMessage(c.getMessage());
                comment.setUserName(c.getUser().getName());
                comment.setCreatedAt(c.getCreatedAt());
                responseDTO.add(comment);
            }
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity("Failed fetching comments",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
