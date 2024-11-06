package com.iiex.cost_share_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iiex.cost_share_service.dto.request.CreateUserRequest;
import com.iiex.cost_share_service.dto.request.LoginRequest;
import com.iiex.cost_share_service.dto.request.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.request.VerifyOTPRequest;
import com.iiex.cost_share_service.dto.response.ApiResponse;
import com.iiex.cost_share_service.dto.response.CreateUserResponse;
import com.iiex.cost_share_service.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // registry
    @PostMapping("/registry/send-otp")
    public ResponseEntity<ApiResponse<?>> sendVerifyOTP(@Valid @RequestBody SendVerifyCodeRequest codeRequest) {
        ApiResponse<?> response = authService.createOtp(codeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registry/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOTP(@Valid @RequestBody VerifyOTPRequest request) {
        ApiResponse<?> response = authService.validateOtp(request);
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

    @GetMapping("/login/oauth2 ")
    public ResponseEntity<?> login() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/oauth2/authorize/google")
                .build();
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<?> callback(OAuth2AuthenticationToken authentication) {
        CreateUserResponse response = authService.login(authentication);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
