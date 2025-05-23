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

        String profilePic = userDTO.profilePicUrl() == null || userDTO.profilePicUrl().isBlank()
                ? "https://cdn-icons-png.flaticon.com/512/9187/9187604.png"
                : userDTO.profilePicUrl();

        String encodedPassword = passwordEncoder.encode(userDTO.password());

        User newUser = new User(userDTO.username(), encodedPassword, profilePic);

        return userRepository.save(newUser);
    }

    @Transactional
    public User editUser(Integer id, UserDTO userDTO) {
        var user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setUsername(userDTO.username());
        if (userDTO.profilePicUrl() != null) {
            user.setProfilePicUrl(userDTO.profilePicUrl());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println(user.getId());
        userRepository.delete(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }
}
