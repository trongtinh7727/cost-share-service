package com.iiex.cost_share_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiex.cost_share_service.dto.CreateUserRequest;
import com.iiex.cost_share_service.dto.CreateUserResponse;
import com.iiex.cost_share_service.dto.LoginRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeResponse;
import com.iiex.cost_share_service.dto.VerifyOTPRequest;
import com.iiex.cost_share_service.dto.VerifyOTPResponse;
import com.iiex.cost_share_service.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // registry
    @PostMapping("/registry/send-otp")
    public ResponseEntity<SendVerifyCodeResponse> sendVerifyOTP(@Valid @RequestBody SendVerifyCodeRequest codeRequest) {
        SendVerifyCodeResponse response = authService.createOtp(codeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registry/verify-otp")
    public ResponseEntity<VerifyOTPResponse> verifyOTP(@Valid @RequestBody VerifyOTPRequest request) {
        VerifyOTPResponse response = authService.validateOtp(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/registry/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = authService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CreateUserResponse> login(@Valid @RequestBody LoginRequest request) {
        CreateUserResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
