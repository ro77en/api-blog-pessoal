package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.UserDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createNewUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            throw new EntityExistsException("Username already exists");
        }

        String profilePic = userDTO.profilePicUrl() == null
                ? "https://cdn-icons-png.flaticon.com/512/9187/9187604.png"
                : userDTO.profilePicUrl();

        String encodedPassword = passwordEncoder.encode(userDTO.password());

        User newUser = new User(userDTO.username(), encodedPassword, profilePic);

        return userRepository.save(newUser);
    }

    @Transactional
    public User editUser(String id, UserDTO userDTO) {
        var user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setUsername(userDTO.username());
        if (userDTO.profilePicUrl() != null) {
            user.setProfilePicUrl(userDTO.profilePicUrl());
        }

        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
