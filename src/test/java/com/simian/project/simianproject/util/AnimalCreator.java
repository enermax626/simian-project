package com.simian.project.simianproject.util;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStatistic;
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

    public static AnimalStatistic createAnimalStatistic() {
        AnimalStatistic animalStatistic = new AnimalStatistic(Integer.toUnsignedLong(2),Integer.toUnsignedLong(8),0.2);
        return animalStatistic;
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
        animalRegistered.setId(Integer.toUnsignedLong(100));
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
        animalRegistered.setId(Integer.toUnsignedLong(101));
        return animalRegistered;
    }



}
