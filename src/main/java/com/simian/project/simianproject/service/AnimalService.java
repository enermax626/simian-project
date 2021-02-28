package com.simian.project.simianproject.service;

import java.util.List;

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
		super();
		this.animalRepository = animalRepository;
	}


	private AnimalRepository animalRepository;
	
	public List<Animal> getAllAnimals(){
		
		return animalRepository.findAll();
	}
	
	public Animal registerAnimal(Animal animal) {

		
		if(isSimian(animal.getDNA()))
			animal.setAnimalType(AnimalOrder.SIMIAN);
		else
			animal.setAnimalType(AnimalOrder.HUMAN);
		
		
		return animalRepository.save(animal);
		
	}
	
	private boolean isSimian(String[] animalDNA) {
		for(char[] pattern : simianPatterns) {
			if(StringPatternFinder.isPatternPresentInStringArray(animalDNA, pattern))
				 return true;
		
		}
		return false;
	}


	public AnimalStatistic getAnimalStatistic(String animalType) {
		Long humanQuantity = animalRepository.getAnimalStatistic(AnimalOrder.HUMAN);
		Long simianQuantity = animalRepository.getAnimalStatistic(AnimalOrder.SIMIAN);
		double total= humanQuantity+simianQuantity;
		
		double result=0;
			if(simianQuantity+humanQuantity > 0)
				 result = simianQuantity/total;
			
		return new AnimalStatistic(simianQuantity,humanQuantity, result);
		
	}
	
	
	public int getAnimalQuantity() {
		return animalRepository.getAnimalQuantity();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
