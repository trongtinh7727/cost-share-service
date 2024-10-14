package com.iiex.cost_share_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    // Constructor with status, message, and data
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Constructor with status and message (data defaults to null)
    public ApiResponse(int status, String message) {
        this(status, message, null);
    }

    // Constructor with message (status defaults to 200, data defaults to null)
    public ApiResponse(String message) {
        this(200, message, null);
    }

    // Constructor with message and data (status defaults to 200)
    public ApiResponse(String message, T data) {
        this(200, message, data);
    }
}
