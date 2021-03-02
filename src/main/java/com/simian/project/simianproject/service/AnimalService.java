package com.simian.project.simianproject.service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;

public interface AnimalService {
    Animal registerAnimal(Animal animal);

    AnimalStatisticDTO getAnimalStatistic();

    boolean isSimian(String[] dna);

}
