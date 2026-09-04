package dev.nidhi.productservice.advices;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public String handleException(Exception e) {
        return "Something went wrong: " + e.getMessage();
    }

    public String handleRuntimeException(RuntimeException e) {
        return "Runtime exception occurred: " + e.getMessage();
    }
}
