package com.chotchip.processing.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountErrorResponse {
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

}
