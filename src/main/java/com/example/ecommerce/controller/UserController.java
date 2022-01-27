package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.TokenService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    TokenService authenticationService;
    // two apis

    //sign up
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    //sign in
    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }
}
