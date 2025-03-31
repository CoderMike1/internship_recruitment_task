package com.example.remitlyrecruitmenttask.controller;


import com.example.remitlyrecruitmenttask.dto.MessageResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new MessageResponse(error));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid request payload";

        if (ex.getCause() instanceof InvalidFormatException cause) {
            errorMessage = "Invalid value for field '" + cause.getPath().get(0).getFieldName() + "': expected " + cause.getTargetType().getSimpleName();
        }

        return ResponseEntity.badRequest().body(new MessageResponse(errorMessage));
    }

}

