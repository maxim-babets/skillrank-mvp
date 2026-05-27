package com.cyberolymp.skillrank.mvp.controller;

import com.cyberolymp.skillrank.mvp.dto.user.CreateUserRequest;
import com.cyberolymp.skillrank.mvp.dto.user.UserResponse;
import com.cyberolymp.skillrank.mvp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/users/")
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }
}
