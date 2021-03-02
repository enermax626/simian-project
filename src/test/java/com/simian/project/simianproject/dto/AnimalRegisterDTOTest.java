package com.simian.project.simianproject.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class AnimalRegisterDTOTest {

    private AnimalRegisterDTO animalOne = new AnimalRegisterDTO();
    private AnimalRegisterDTO animalTwo = new AnimalRegisterDTO();
    private String[] startDna = new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"};


    @BeforeEach
    void setUp(){
        animalOne.setDNA(startDna);
        animalTwo.setDNA(startDna);
    }

    @Test
    @DisplayName("AnimalRegisterDTO getDNA")
    void getDNA() {
        Assertions.assertEquals(startDna,animalOne.getDNA());
    }

    @Test
    @DisplayName("AnimalRegisterDTO setDNA")
    void setDNA() {
        String[] newDNA = new String[]{"TTTTTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"};
        animalOne.setDNA(newDNA);
        Assertions.assertEquals(newDNA,animalOne.getDNA());
    }

    @Test
    void testEquals() {
        Assertions.assertTrue(animalOne.equals(animalTwo));
    }

    @Test
    void testHashCode_equals() {
        Assertions.assertEquals(animalOne,animalTwo);

    }
}