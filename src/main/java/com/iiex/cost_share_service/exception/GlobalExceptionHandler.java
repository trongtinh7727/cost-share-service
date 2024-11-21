package com.iiex.cost_share_service.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.iiex.cost_share_service.dto.response.ResponseVO;

import io.jsonwebtoken.JwtException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.environment}")
    private String environment;

    private boolean isDevelopment() {
        return "development".equalsIgnoreCase(environment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseVO<?>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(
                        error -> ((FieldError) error).getField(),
                        Collectors.mapping(t -> t.getDefaultMessage(), Collectors.toList())

                ));
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors,
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseVO<?>> handleDisabledUserException(DisabledException ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.FORBIDDEN.value(),
                "User account is disabled. Please check your email!",
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseVO<?>> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid email or password",
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseVO<?>> handleJwtException(JwtException ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized: " + ex.getMessage() + " (Invalid or expired token)",
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseVO<?>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO<?>> handleGenericException(Exception ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred",
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseVO<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        ResponseVO<?> response = ResponseVO.error(
                HttpStatus.NOT_FOUND.value(),
                "page not found",
                isDevelopment() ? getStackTraceAsString(ex) : null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public String getStackTraceAsString(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
