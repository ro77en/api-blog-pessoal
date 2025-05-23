package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.PostDTO;
import com.ro77en.blog_pessoal.model.Post;
import com.ro77en.blog_pessoal.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) Integer categoryId) {
        List<Post> posts = postService.getPosts(authorId, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO data) {
        Post newPost = postService.createPost(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody PostDTO data) {
        Post updatedPost = postService.updatePost(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
