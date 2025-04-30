package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.UserDTO;
import com.ro77en.blog_pessoal.dto.UserResponseDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createNewUser(@RequestBody UserDTO data) {
        User newUser = userService.createNewUser(data);
        UserResponseDTO responseDTO = new UserResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<User> users = userService.getUsers();

        List<UserResponseDTO> responseDTOList = users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getProfilePicUrl()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserResponseDTO> editUser(@PathVariable String id, @RequestBody UserDTO data) {
        User user = userService.editUser(id, data);
        UserResponseDTO responseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
