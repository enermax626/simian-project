package com.simian.project.simianproject.exception;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionDetailsTest {

    private ExceptionDetails exceptionDetails;
    private LocalDateTime fixedTimestamp = LocalDateTime.now();
    @BeforeEach
    void setUp(){
        exceptionDetails = new ExceptionDetails("Title", HttpStatus.OK,"Details string", fixedTimestamp);

    }
    @Test
    @DisplayName("exceptionDetails getTitle")
    void getTitle() {
           Assertions.assertEquals("Title",exceptionDetails.getTitle());
    }

    @Test
    @DisplayName("exceptionDetails setTitle")
    void setTitle() {
        exceptionDetails.setTitle("Title changed");
        Assertions.assertEquals("Title changed",exceptionDetails.getTitle());
    }

    @Test
    @DisplayName("exceptionDetails getStatus")
    void getStatus() {
        Assertions.assertEquals(HttpStatus.OK,exceptionDetails.getStatus());
    }

    @Test
    @DisplayName("exceptionDetails setStatus")
    void setStatus() {
        exceptionDetails.setStatus(HttpStatus.NOT_FOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,exceptionDetails.getStatus());
    }

    @Test
    @DisplayName("exceptionDetails getDetails")
    void getDetails() {
        Assertions.assertEquals("Details string",exceptionDetails.getDetails());
    }

    @Test
    @DisplayName("exceptionDetails setDetails")
    void setDetails() {
        exceptionDetails.setDetails("New Details");
        Assertions.assertEquals("New Details",exceptionDetails.getDetails());
    }

    @Test
    @DisplayName("exceptionDetails getTimestamp")
    void getTimestamp() {
        Assertions.assertEquals(fixedTimestamp,exceptionDetails.getTimestamp());
    }

    @Test
    @DisplayName("exceptionDetails setTimestamp")
    void setTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.now();
        exceptionDetails.setTimestamp(newTimestamp);
        Assertions.assertEquals(newTimestamp,exceptionDetails.getTimestamp());
    }
}