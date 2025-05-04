package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.PostDTO;
import com.ro77en.blog_pessoal.model.Category;
import com.ro77en.blog_pessoal.model.Post;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.repository.CategoryRepository;
import com.ro77en.blog_pessoal.repository.PostRepository;
import com.ro77en.blog_pessoal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Post> getPosts(Integer authorId, Integer categoryId) {
        if (authorId != null) {
            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            return author.getPosts();
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            return category.getPosts();
        }

        return postRepository.findAll();
    }

    @Transactional
    public Post createPost(PostDTO postDTO) {
        var user = userRepository.findById(postDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var category = categoryRepository.findById(postDTO.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Post post = new Post(postDTO.title(), postDTO.content());

        post.setUser(user);
        post.setCategory(category);
        user.getPosts().add(post);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Integer postId, PostDTO postDTO) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        var category = categoryRepository.findById(postDTO.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        post.setCategory(category);
        post.setContent(postDTO.content());
        post.setTitle(postDTO.title());
        post.setTimestamp(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) throw new EntityNotFoundException("Post not found");
        postRepository.deleteById(postId);
    }
}
