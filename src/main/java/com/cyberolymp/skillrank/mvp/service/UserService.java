package com.cyberolymp.skillrank.mvp.service;

import com.cyberolymp.skillrank.mvp.domain.User;
import com.cyberolymp.skillrank.mvp.dto.user.CreateUserRequest;
import com.cyberolymp.skillrank.mvp.dto.user.UserResponse;
import com.cyberolymp.skillrank.mvp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest createUserRequest){
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }
}
