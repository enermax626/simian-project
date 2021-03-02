package com.simian.project.simianproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Animal animalOne = new Animal();
    private Animal animalTwo = new Animal();
    private String[] startDNA = new String[] {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"};
    private Long initialId = 1L;
    @BeforeEach
    void setUp(){
        animalOne.setDNA(startDNA);
        animalTwo.setDNA(startDNA);
        animalOne.setAnimalType(AnimalOrder.SIMIAN);
        animalTwo.setAnimalType(AnimalOrder.SIMIAN);
        animalOne.setId(initialId);
        animalTwo.setId(initialId);
    }

    @Test
    @DisplayName("getId from animal")
    void getId() {
        Assertions.assertNotNull(animalOne.getId());
        Assertions.assertEquals(initialId,animalOne.getId());

    }

    @Test
    @DisplayName("setId to animal")
    void setId() {
        Long newId = 2L;
        animalOne.setId(newId);
        Assertions.assertNotNull(animalOne.getId());
        Assertions.assertEquals(newId,animalOne.getId());

    }


    @Test
    @DisplayName("getDNA from animal")
    void getDNA() {
        Assertions.assertNotNull(animalOne.getDNA());
        Assertions.assertEquals(startDNA,animalOne.getDNA());
    }

    @Test
    @DisplayName("setDNA to animal")
    void setDNA() {
        String[] newDNA = new String[] {"CCGGTA", "CCCCAC", "CCCCTC", "ATTTTA", "CTACGC", "TCCGCA"};
        animalOne.setDNA(newDNA);
        Assertions.assertNotNull(animalOne.getDNA());
        Assertions.assertEquals(newDNA,animalOne.getDNA());
    }


    @Test
    @DisplayName("getAnimalType from animal")
    void getAnimalType() {
        Assertions.assertNotNull(animalOne.getAnimalType());
        Assertions.assertEquals(AnimalOrder.SIMIAN,animalOne.getAnimalType());
    }

    @Test
    @DisplayName("setAnimalType to animal")
    void setAnimalType() {
        AnimalOrder newType = AnimalOrder.HUMAN;
        animalOne.setAnimalType(newType);
        Assertions.assertNotNull(animalOne.getAnimalType());
        Assertions.assertEquals(AnimalOrder.HUMAN,animalOne.getAnimalType());
    }

    @Test
    void equals_True() {
        Assertions.assertEquals(animalOne,animalTwo);
    }

    @Test
    void testEqualHashCode() {
        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setAnimalType(AnimalOrder.SIMIAN);
        animal1.setDNA(new String[] {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        Animal animal2 = new Animal();
        animal2.setDNA(new String[] {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        animal1.setId(5L);
        animal1.setAnimalType(AnimalOrder.HUMAN);
        Assertions.assertEquals(animal1,animal2);

    }

    @Test
    void testNotEqualHashCode() {
        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setAnimalType(AnimalOrder.SIMIAN);
        animal1.setDNA(new String[] {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        Animal animal2 = new Animal();
        animal2.setDNA(new String[] {"CCGGTA", "CCCCCC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        animal1.setId(5L);
        animal1.setAnimalType(AnimalOrder.HUMAN);

        Assertions.assertNotEquals(animal1,animal2);
    }
}