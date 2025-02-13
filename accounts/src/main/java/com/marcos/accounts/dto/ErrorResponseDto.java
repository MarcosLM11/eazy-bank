package com.marcos.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorResponseDto {
    private HttpStatus errorCode;
    private String apiPath;
    private String message;
    private LocalDateTime timestamp;
}
