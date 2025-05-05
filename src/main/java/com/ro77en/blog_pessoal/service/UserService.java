package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.UserDTO;
import com.ro77en.blog_pessoal.model.User;
import com.ro77en.blog_pessoal.repository.UserRepository;
import com.ro77en.blog_pessoal.security.UserAuthenticated;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

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

        String profilePic = userDTO.profilePicUrl() == null ? "https://cdn-icons-png.flaticon.com/512/9187/9187604.png" : userDTO.profilePicUrl();

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
