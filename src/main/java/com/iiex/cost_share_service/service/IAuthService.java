package com.iiex.cost_share_service.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.iiex.cost_share_service.dto.request.CreateUserRequest;
import com.iiex.cost_share_service.dto.request.LoginRequest;
import com.iiex.cost_share_service.dto.request.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.request.VerifyOTPRequest;
import com.iiex.cost_share_service.dto.response.CreateUserResponse;
import com.iiex.cost_share_service.dto.response.ResponseVO;

@Service
public interface IAuthService {
    public ResponseVO<?> createOtp(SendVerifyCodeRequest request);
    public ResponseVO<?> validateOtp(VerifyOTPRequest verifyOTPRequest);
    ResponseVO<?> createUser(CreateUserRequest request);
    CreateUserResponse login(LoginRequest request);
    CreateUserResponse login(OAuth2AuthenticationToken authentication);
}
