package com.ethereal.witch.web.exception;

import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.service.exception.PasswordInvalidException;
import com.ethereal.witch.service.exception.UniqueViolationExeception;
import com.ethereal.witch.service.exception.UnnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExeptionHandler {

    @ExceptionHandler(UniqueViolationExeception.class)
    public ResponseEntity<ErrorMessage> usernameUniqueViolationExeception (RuntimeException ex, HttpServletRequest request){
        log.error("api error", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }
    @ExceptionHandler(EntityNotfoundException.class)
    public ResponseEntity<ErrorMessage> entityNotfoundException(RuntimeException ex, HttpServletRequest request){
        log.error("api error",ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));
    }
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException (RuntimeException ex, HttpServletRequest request){
        log.error("api error", ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,ex.getMessage()));
    }
    @ExceptionHandler(UnnauthorizedException.class)
    public ResponseEntity<ErrorMessage> unnautorizedException  (RuntimeException ex, HttpServletRequest request){
        log.error("api error", ex);
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED,ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException (MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result){
        log.error("api error",ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.UNPROCESSABLE_ENTITY,"Invalid Fields.", result));
    }

}
