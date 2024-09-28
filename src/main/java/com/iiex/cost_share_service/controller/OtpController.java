package com.iiex.cost_share_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiex.cost_share_service.dto.SendVerifyCodeRequest;
import com.iiex.cost_share_service.dto.SendVerifyCodeResponse;
import com.iiex.cost_share_service.service.OtpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<SendVerifyCodeResponse> sendVerifyOTP(@Valid @RequestBody SendVerifyCodeRequest codeRequest) {
        SendVerifyCodeResponse response = otpService.createOtp(codeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
