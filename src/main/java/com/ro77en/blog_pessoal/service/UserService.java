package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.UserRegisterDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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
    public User createNewUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.username())) {
            throw new EntityExistsException("Username already exists");
        }

        String profilePic = userRegisterDTO.profilePicUrl() == null
                ? "https://cdn-icons-png.flaticon.com/512/9187/9187604.png"
                : userRegisterDTO.profilePicUrl();

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.password());

        User newUser = new User(userRegisterDTO.username(), encodedPassword, profilePic);

        return userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
