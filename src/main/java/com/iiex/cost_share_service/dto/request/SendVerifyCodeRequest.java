package com.iiex.cost_share_service.dto.request;

import com.iiex.cost_share_service.utils.enums.VerifyType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendVerifyCodeRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "VerifyType cannot be null")
    @Enumerated(EnumType.STRING)
    private VerifyType verifyType;
}