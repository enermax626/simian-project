package com.simian.project.simianproject.dto;

import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnimalStatisticDTOTest {

    @Test
    void getCount_mutant_dna() {
        AnimalStatisticDTO animalStatisticDTO = new AnimalStatisticDTO(1L,1L);
        Assertions.assertEquals(1L, animalStatisticDTO.getCount_mutant_dna());
    }



    @Test
    void getCount_human_dna() {
        AnimalStatisticDTO animalStatisticDTO = new AnimalStatisticDTO(1L,1L);
        Assertions.assertEquals(1L, animalStatisticDTO.getCount_human_dna());
    }


    @Test
    void getRatio() {
        AnimalStatisticDTO animalStatisticDTO = new AnimalStatisticDTO(1L,1L);
        Assertions.assertEquals(0.5, animalStatisticDTO.getRatio());
    }
}