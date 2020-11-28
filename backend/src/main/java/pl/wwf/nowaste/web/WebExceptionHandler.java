package pl.wwf.nowaste.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error("Exception: {}", e.getMessage());
        return new ErrorResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("Exception: {}", e.getMessage());
        return new ErrorResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(I_AM_A_TEAPOT)
    public ErrorResponse exceptionHandler(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return new ErrorResponse(I_AM_A_TEAPOT, e.getMessage());
    }

    @Data
    @AllArgsConstructor
    private static class ErrorResponse {
        private HttpStatus status;
        private String reason;
    }

}
