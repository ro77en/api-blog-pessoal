package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.PostDTO;
import com.ro77en.blog_pessoal.model.Post;
import com.ro77en.blog_pessoal.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO data) {
        Post newPost = postService.createPost(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }
}
