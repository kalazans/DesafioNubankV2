package com.desafio.nubank.desafio.infra.advice;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ApiError {
    private HttpStatus httpStatus;
    private  String message;
    private List<String> erros;
    private LocalDateTime timeStamp;

    public ApiError(){}

    public ApiError(HttpStatus httpStatus, String message, List<String> erros, LocalDateTime timeStamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.erros = erros;
        this.timeStamp = timeStamp;
    }
    public ApiError(HttpStatus httpStatus, String message, String erros, LocalDateTime timeStamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.erros = Arrays.asList(erros);
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErros() {
        return erros;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
