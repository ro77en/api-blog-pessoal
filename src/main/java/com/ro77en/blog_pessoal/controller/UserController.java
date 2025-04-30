package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.UserRegisterDTO;
import com.ro77en.blog_pessoal.dto.UserResponseDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<User> users = userService.getUsers();

        List<UserResponseDTO> response = users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getPassword()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
