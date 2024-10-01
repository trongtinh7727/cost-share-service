package com.iiex.cost_share_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendVerifyCodeResponse extends BaseResponse {
    public SendVerifyCodeResponse(String message) {
        super(message);
    }
}
