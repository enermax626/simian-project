package com.simian.project.simianproject.handler;

import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.exception.ExceptionDetails;
import com.simian.project.simianproject.exception.WrongStringFormatException;
import com.simian.project.simianproject.serviceImpl.AnimalServiceImpl;
import com.simian.project.simianproject.utilImpl.StringPatternFinderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class SimianExceptionHandlerTest {


    @InjectMocks
    private SimianExceptionHandler simianExceptionHandlerMock;


    @Test
    @DisplayName("Receiving a WrongStringFormatException")
    void handlerWrongStringFormatException() {
        WrongStringFormatException e = new WrongStringFormatException("String format wrong");

        ResponseEntity<ExceptionDetails> response = simianExceptionHandlerMock.handlerWrongStringFormatException(e);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(response.getBody().getDetails(),"The size of your input or the content is not valid");

    }

    @Test
    @DisplayName("Receiving a DNANotFoundException")
    void handlerDNANotFoundException() {
        DNANotFoundException e = new DNANotFoundException("DNA sequence not found");

        ResponseEntity<ExceptionDetails> response = simianExceptionHandlerMock.handlerDNANotFoundException(e);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(response.getBody().getDetails(),"DNA sequence not found");
    }

    @Test
    @DisplayName("Receiving a DataIntegrityViolationException")
    void handlerDataIntegrityViolationException() {
        DataIntegrityViolationException e = new DataIntegrityViolationException("DuplicatedData in database");

        ResponseEntity<ExceptionDetails> response = simianExceptionHandlerMock.handlerDataIntegrityViolationException(e);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus(), HttpStatus.BAD_REQUEST);
        Assertions.assertTrue(response.getBody().getDetails().contains("this exception probably was caused because you are trying to persist"));



    }

    @Test
    @DisplayName("Receiving a not already filtered exception")
    void handlerGenericException() {
        RuntimeException e = new RuntimeException("Generic exception");

        ResponseEntity<ExceptionDetails> response = simianExceptionHandlerMock.handlerGenericException(e);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertEquals(response.getBody().getDetails(),e.getMessage());


    }
}