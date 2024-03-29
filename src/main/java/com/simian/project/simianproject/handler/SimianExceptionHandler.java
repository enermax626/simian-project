package com.simian.project.simianproject.handler;

import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.exception.ExceptionDetails;
import com.simian.project.simianproject.exception.WrongStringFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class SimianExceptionHandler {

    @ExceptionHandler(WrongStringFormatException.class)
    public ResponseEntity<ExceptionDetails> handlerWrongStringFormatException(WrongStringFormatException e) {

        return new ResponseEntity<>(
                new ExceptionDetails("WrongStringArrayFormat",
                        HttpStatus.BAD_REQUEST,
                        "The size of your input or the content is not valid",
                        LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DNANotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerDNANotFoundException(DNANotFoundException e) {

        return new ResponseEntity<>(
                new ExceptionDetails("DNA not found",
                        HttpStatus.NOT_FOUND,
                        "DNA sequence not found",
                        LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {

        return new ResponseEntity<>(
                new ExceptionDetails("DataIntegrityViolationException",
                        HttpStatus.BAD_REQUEST,
                        "In the Simian Project Scenario, this exception probably was caused because you are trying to persist an already persisted DNA Sequence",
                        LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handlerGenericException(HttpMessageNotReadableException e) {

        return new ResponseEntity<>(
                new ExceptionDetails("Wrong request",
                        HttpStatus.BAD_REQUEST,
                        "Wrong request parameters",
                        LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handlerGenericException(Exception e) {

        return new ResponseEntity<>(
                new ExceptionDetails("Ops.. Something went wrong",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
