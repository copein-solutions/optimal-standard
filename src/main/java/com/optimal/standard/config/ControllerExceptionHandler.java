package com.optimal.standard.config;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
    BindingResult result = e.getBindingResult();
    List<String> details = new ArrayList<>();
    ErrorResponse errorResponse = new ErrorResponse();
    for (FieldError fieldError : result.getFieldErrors()) {
      details.add(fieldError.getField() + " - " + fieldError.getDefaultMessage());
    }
    errorResponse.setError(HttpStatus.BAD_REQUEST.name());
    errorResponse.setMessage(e
        .getBody()
        .getDetail());
    errorResponse.setDetails(details);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.name(), e.getMessage(), HttpStatus.NOT_FOUND.value()),
        HttpStatus.NOT_FOUND);
  }

}
