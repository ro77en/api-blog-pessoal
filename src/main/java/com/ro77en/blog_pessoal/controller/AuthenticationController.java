package com.ro77en.blog_pessoal.controller;

import com.ro77en.blog_pessoal.dto.LoginDTO;
import com.ro77en.blog_pessoal.dto.TokenDTO;
import com.ro77en.blog_pessoal.security.JwtService;
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
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDTO) {
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
}
