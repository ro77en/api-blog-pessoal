package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.PostDTO;
import com.ro77en.blog_pessoal.model.Post;
import com.ro77en.blog_pessoal.repository.CategoryRepository;
import com.ro77en.blog_pessoal.repository.PostRepository;
import com.ro77en.blog_pessoal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
