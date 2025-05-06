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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<User> users = userService.getUsers();

        List<UserResponseDTO> responseDTOList = users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getProfilePicUrl()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserResponseDTO responseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserResponseDTO> editUser(@PathVariable Integer id, @RequestBody UserDTO data) {
        User user = userService.editUser(id, data);
        UserResponseDTO responseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
