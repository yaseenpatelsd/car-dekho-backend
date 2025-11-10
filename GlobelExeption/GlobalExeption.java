package com.car.dekho.car.dekho.GlobelExeption;

import com.car.dekho.car.dekho.Dto.ApiResponseDto;
import com.car.dekho.car.dekho.Exeptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExeption {
@ExceptionHandler(SomethingIsWrongExeption.class)
public ResponseEntity<ApiResponseDto> handleError(SomethingIsWrongExeption e,HttpServletRequest request){
    return build(HttpStatus.INTERNAL_SERVER_ERROR,"Something is wrong", e.getMessage(), request);
}

    @ExceptionHandler(ResourceNotFoundExeption.class)
    public ResponseEntity<ApiResponseDto> handleError(ResourceNotFoundExeption e,HttpServletRequest request){
        return build(HttpStatus.NOT_FOUND,"Resource not found", e.getMessage(), request);
    }
    @ExceptionHandler(UserAlreadyExistExeption.class)
    public ResponseEntity<ApiResponseDto> handleError(UserAlreadyExistExeption e,HttpServletRequest request){
        return build(HttpStatus.ALREADY_REPORTED,"Something is wrong", e.getMessage(), request);
    }
    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<ApiResponseDto> handleError(UserNotFoundExeption e,HttpServletRequest request){
        return build(HttpStatus.NOT_FOUND,"User not register", e.getMessage(), request);
    }
    @ExceptionHandler(OtpExeption.class)
    public ResponseEntity<ApiResponseDto> handleError(OtpExeption e,HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"Otp related error", e.getMessage(), request);
    }
    @ExceptionHandler(NotAuthorizedExeptions.class)
    public ResponseEntity<ApiResponseDto> handleError(NotAuthorizedExeptions e,HttpServletRequest request){
        return build(HttpStatus.UNAUTHORIZED,"UnAuthorized", e.getMessage(), request);
    }


    public ResponseEntity<ApiResponseDto> build(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ){
        ApiResponseDto apiResponseDto=new ApiResponseDto(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(apiResponseDto);
    }
}
