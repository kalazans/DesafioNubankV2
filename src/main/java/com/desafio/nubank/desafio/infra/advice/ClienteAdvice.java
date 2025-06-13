package com.desafio.nubank.desafio.infra.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ClienteAdvice  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> erros =new ArrayList<>();
        ex.getFieldErrors().forEach(e->erros.add(e.getField()+" : "+e.getDefaultMessage()));
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "campos invalidos no json",erros, LocalDateTime.now());
        return ResponseEntity.badRequest().body(apiError);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> tratarMissingServletRequestParameter(MissingServletRequestParameterException ex){
        String error = ex.getParameterName()+": parameter is missing";
        ApiError apiError =  new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),error,LocalDateTime.now());
        return ResponseEntity.badRequest().body(apiError);
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiError> tratarMissingServletRequestParameter(MissingServletRequestPartException ex){
        String error = ex.getRequestPartName()+": parameter is missing";
        ApiError apiError =  new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),error,LocalDateTime.now());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> tratarNoResourceFoundException(NoResourceFoundException ex,HttpServletRequest request){
        String error = request.getRequestURI()+": não existe este endpoint;";
        ApiError apiError =  new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage(),error,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> tratarMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        String error = ex.getName() + ": shoulbe be type "+ex.getRequiredType().getName();
        ApiError apiError =  new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),error,LocalDateTime.now());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> tratarHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        StringBuilder error = new StringBuilder();
        error.append(ex.getMethod());
        error.append(": O método não é aceito para essa requisição. Métodos aceitos são: ");
        ex.getSupportedHttpMethods().forEach(m->error.append(m+", "));
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,ex.getLocalizedMessage(),error.toString(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> tratarHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex){
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" tipo de media nãp aceito. Os tipos de media aceitos são: ");
        ex.getSupportedMediaTypes().forEach(m->builder.append(m+", "));
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,ex.getLocalizedMessage(),builder.toString(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> tratarHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String error = "json com formato invalido";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),error,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);


    }

}
