package com.example.restclientpocproductservice.exception;

import com.example.restclientpocproductservice.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> notFound(ProductNotFoundException notFoundException) {
        return ResponseEntity.status(404).body(ApiResponse.<String>builder().data(notFoundException.getMessage()).build());
    }
}
