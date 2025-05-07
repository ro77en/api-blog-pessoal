package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.LoginDTO;
import com.ro77en.blog_pessoal.dto.TokenDTO;
import com.ro77en.blog_pessoal.dto.UserDTO;
import com.ro77en.blog_pessoal.dto.UserResponseDTO;
import com.ro77en.blog_pessoal.exceptions.ApiError;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.security.JwtService;
import com.ro77en.blog_pessoal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.username().isEmpty() || loginDTO.password().isEmpty()) {
            var apiError = new ApiError(HttpStatus.BAD_REQUEST, "All fields must be filled");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO data) {
        if (data.username().isEmpty() || data.password().isEmpty()) {
            var apiError = new ApiError(HttpStatus.BAD_REQUEST, "All fields must be filled");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }
        User newUser = userService.createNewUser(data);
        UserResponseDTO responseDTO = new UserResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getProfilePicUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
