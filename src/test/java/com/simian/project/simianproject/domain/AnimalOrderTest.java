package com.simian.project.simianproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalOrderTest {

    @Test
    @DisplayName("Validate AnimalOrder Values")
    void values() {
        Assertions.assertEquals(AnimalOrder.values().length,2);
        Assertions.assertEquals(AnimalOrder.values()[0],AnimalOrder.SIMIAN);
        Assertions.assertEquals(AnimalOrder.values()[1],AnimalOrder.HUMAN);
    }

    @Test
    @DisplayName("Validate AnimalOrder String values")
    void valueOf() {
        Assertions.assertEquals(AnimalOrder.valueOf("SIMIAN"),AnimalOrder.SIMIAN);
        Assertions.assertEquals(AnimalOrder.valueOf("HUMAN"),AnimalOrder.HUMAN);
    }
}