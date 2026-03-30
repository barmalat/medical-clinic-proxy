package com.barmalat.medicalclinic_proxy.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessageDto> handleFeignException(FeignException e) {
        int status = e.status() > 0 ? e.status() : 503;
        String message = e.contentUTF8();
        if (message != null && !message.isBlank()) {
            try {
                JsonNode json = new ObjectMapper().readTree(message);
                message = json.get("message").asText();
            } catch (Exception ex) {
                message = e.getMessage();
            }
        } else {
            message = e.getMessage();
        }
        log.error(e.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ErrorMessageDto(message, status));
    }

    @ExceptionHandler(MedicalclinnicException.class)
    public ResponseEntity<ErrorMessageDto> handleGithubException(MedicalclinnicException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorMessageDto(e.getMessage(), e.getStatus()));
    }
}