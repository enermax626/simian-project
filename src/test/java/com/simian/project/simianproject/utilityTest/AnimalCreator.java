package com.simian.project.simianproject.utilityTest;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;

public class AnimalCreator {

    public static Animal createSimianAnimal() {
        Animal animal = new Animal();
        String[] animalDNA = {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"};
        animal.setDNA(animalDNA);
        animal.setAnimalType(AnimalOrder.SIMIAN);
        return animal;
    }

    public static Animal createHumanAnimal() {
        Animal animal = new Animal();
        String[] animalDNA = {"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"};
        animal.setDNA(animalDNA);
        animal.setAnimalType(AnimalOrder.HUMAN);
        return animal;
    }

    public static Animal createEmptyDNAHumanAnimal() {
        Animal animal = new Animal();
        animal.setAnimalType(AnimalOrder.HUMAN);
        return animal;
    }

    public static AnimalStatisticDTO createAnimalStatistic() {
        AnimalStatisticDTO animalStatisticDTO = new AnimalStatisticDTO(2L,8L);
        return animalStatisticDTO;
    }

    public static AnimalRegisterDTO createValidHumanAnimalRegisterDTO() {
        AnimalRegisterDTO animalRegisterDTO = new AnimalRegisterDTO();
        animalRegisterDTO.setDNA(new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"});
        return animalRegisterDTO;
    }

    public static Animal createValidHumanAnimalToBeRegisteredFromDTO() {
        Animal animalToBeRegistered = new Animal();
        animalToBeRegistered.setDNA(new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"});
        return animalToBeRegistered;
    }

    public static Animal createValidHumanAnimalRegisteredFromDTO() {
        Animal animalRegistered = new Animal();
        animalRegistered.setDNA(new String[]{"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"});
        animalRegistered.setAnimalType(AnimalOrder.HUMAN);
        animalRegistered.setId(100L);
        return animalRegistered;
    }

    public static AnimalRegisterDTO createValidSimianAnimalRegisterDTO() {
        AnimalRegisterDTO animalRegisterDTO = new AnimalRegisterDTO();
        animalRegisterDTO.setDNA(new String[]{"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        return animalRegisterDTO;
    }

    public static Animal createValidSimianAnimalToBeRegisteredFromDTO() {
        Animal animalToBeRegistered = new Animal();
        animalToBeRegistered.setDNA(new String[]{"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        return animalToBeRegistered;
    }

    public static Animal createValidSimianAnimalRegisteredFromDTO() {
        Animal animalRegistered = new Animal();
        animalRegistered.setDNA(new String[]{"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"});
        animalRegistered.setAnimalType(AnimalOrder.SIMIAN);
        animalRegistered.setId(101L);
        return animalRegistered;
    }



}
