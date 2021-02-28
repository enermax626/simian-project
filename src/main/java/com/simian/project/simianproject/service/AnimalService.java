package com.simian.project.simianproject.service;

import org.springframework.stereotype.Service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStatistic;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.util.StringPatternFinder;

@Service
public class AnimalService {
	
	private char[][] simianPatterns= { {'A','A','A','A'},{'T','T','T','T'},{'C','C','C','C'},{'G','G','G','G'}
			
	};
	
	public AnimalService(AnimalRepository animalRepository) {
		this.animalRepository = animalRepository;
	}

	private AnimalRepository animalRepository;
	
	public Animal registerAnimal(Animal animal) {

		if(isSimian(animal.getDNA()))
			animal.setAnimalType(AnimalOrder.SIMIAN);
		else
			animal.setAnimalType(AnimalOrder.HUMAN);
		
		return animalRepository.save(animal);
		
	}

	public AnimalStatistic getAnimalStatistic(String animalType) {
		Long humanQuantity = animalRepository.getAnimalStatistic(AnimalOrder.HUMAN);
		Long simianQuantity = animalRepository.getAnimalStatistic(AnimalOrder.SIMIAN);
		double result=simianQuantity;
		
			if(result > 0)
				 result = result/(humanQuantity+simianQuantity);
			
		return new AnimalStatistic(simianQuantity,humanQuantity, result);
		
	}
	
	private boolean isSimian(String[] dna) {
		for(char[] pattern : simianPatterns) {
			if(StringPatternFinder.isPatternPresentInStringArray(dna, pattern))
				 return true;
		
		}
		return false;
	}
	
}
