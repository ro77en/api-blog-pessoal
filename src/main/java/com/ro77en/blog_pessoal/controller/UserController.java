package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.UserRegisterDTO;
import com.ro77en.blog_pessoal.dto.UserResponseDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createNewUser(@RequestBody UserRegisterDTO data) {
        User newUser = userService.createNewUser(data);
        UserResponseDTO response = new UserResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
