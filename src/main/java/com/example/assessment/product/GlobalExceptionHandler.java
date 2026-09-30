package com.example.assessment.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    }

    /*
     * TODO (candidate): map a duplicate-id create to 409 Conflict.
     *
     * When addProduct is called with an id that already exists, the repository
     * throws your duplicate-key exception. Add an @ExceptionHandler for it here
     * that returns a ProblemDetail with HttpStatus.CONFLICT — mirroring the
     * validation handler above. (Why 409 and not 400 or 500? The request is
     * well-formed; it conflicts with existing state.)
     */
}
