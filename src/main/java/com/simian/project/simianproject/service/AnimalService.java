package com.simian.project.simianproject.service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStatistic;
import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.util.StringPatternFinder;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    //This could be persisted in a table, so we would be able do add or remove new patterns that could
    //detect a Simian DNA sequence.
    private final static char[][] SIMIANPATTERNS = {{'A', 'A', 'A', 'A'}, {'T', 'T', 'T', 'T'}, {'C', 'C', 'C', 'C'},
            {'G', 'G', 'G', 'G'}

    };

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    private AnimalRepository animalRepository;

    public Animal registerAnimal(Animal animal) {

        if (animal.getDNA() == null)
            throw new DNANotFoundException("DNA sequence not found");

        if (isSimian(animal.getDNA()))
            animal.setAnimalType(AnimalOrder.SIMIAN);
        else
            animal.setAnimalType(AnimalOrder.HUMAN);

        return animalRepository.save(animal);

    }


    public AnimalStatistic getAnimalStatistic(String animalType) {
        Long humanQuantity = animalRepository.getAnimalStatistic(AnimalOrder.HUMAN);
        Long simianQuantity = animalRepository.getAnimalStatistic(AnimalOrder.SIMIAN);
        double result = simianQuantity;

        if (result > 0)
            result = result / (humanQuantity + simianQuantity);

        return new AnimalStatistic(simianQuantity, humanQuantity, result);

    }

    private boolean isSimian(String[] dna) {
        if (StringPatternFinder.isAnyPatternPresentInStringArray(dna, SIMIANPATTERNS))
            return true;

        return false;
    }

}
