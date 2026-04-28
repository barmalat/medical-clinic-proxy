package com.barmalat.config;

import com.barmalat.ErrorMessageDto;
import com.barmalat.MedicalclinnicException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessageDto> handleFeignException(FeignException e) {
        int status = e.status() > 0 ? e.status() : 503;
        String message = extractMessage(e);
        log.error(message);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessageDto(message, status));
    }

    private String extractMessage(FeignException e) {
        String body = e.contentUTF8();
        if (body == null || body.isBlank()) {
            return e.getMessage();
        }
        try {
            return objectMapper.readTree(body).get("message").asText();
        } catch (Exception ex) {
            return e.getMessage();
        }
    }

    @ExceptionHandler(MedicalclinnicException.class)
    public ResponseEntity<ErrorMessageDto> handleGithubException(MedicalclinnicException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorMessageDto(e.getMessage(), e.getStatus()));
    }
}