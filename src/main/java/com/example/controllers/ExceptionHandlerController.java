package com.example.controllers;

import com.example.exceptions.ResourceNotFoundException;
import com.example.exceptions.UniqueConstraintException;
import com.example.responses.DefaultErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//globalny generyczny handler dla wyjatku unique costraint
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<DefaultErrorResponse> handleUniqueConstraintException(UniqueConstraintException exception) {
        //tworzymy customowa odpowiedz symulujaca slownik by zwrocic odpowiednia odpowiedz(JSON)
        DefaultErrorResponse errors = new DefaultErrorResponse();
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    //Obsluga wyjatku dla podania blednego adresu (brak zasobu)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        DefaultErrorResponse errors = new DefaultErrorResponse();
        errors.setError(String.format("%s not found with %s : '%s'", ex.getResourceName(), ex.getFieldName(), ex.getFieldValue()));
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Obsluga wyjatku gdy wpiszemy /films/rt zamiast id, TODO: poprawic
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        System.out.println("TUTAJ JESTEM");
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

//    In other words, a MethodArgumentNotValidException is thrown when the validation fails.
//    It is handled by the handleMethodArgumentNotValid() method provided by the
//    ResponseEntityExceptionHandler, that needs to be overridden if you would like a custom implementation.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    //Obsluga bledu metody
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, headers, status);
    }

    //Obsluga gdy uzytkownik poda bledny typ
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, headers, status);
    }
}

