package com.asciugano.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.asciugano.blog.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
    log.error("Caught exception", e);
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("An unexpected error occurred")
        .build();

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorResponse> handleIlligalArgumentException(IllegalArgumentException e) {
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiErrorResponse> handleIlligalStateException(IllegalStateException e) {
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.CONFLICT.value())
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentialException(BadCredentialsException e) {
    ApiErrorResponse error = ApiErrorResponse.builder()
        .status(HttpStatus.UNAUTHORIZED.value())
        .message("Incorrect email or password")
        .build();

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

}
