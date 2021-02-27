package com.simian.project.simianproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.util.StringPatternFinder;

@Service
public class AnimalService {
	
	private char[][] simianPatterns= { {'A','A','A','A'},{'T','T','T','T'},{'C','C','C','C'},{'G','G','G','G'}
			
	};
	
	private AnimalRepository animalRepository;
	
	AnimalService(AnimalRepository animalRepository){
		this.animalRepository = animalRepository;
	}
	
	
	public List<Animal> getAllAnimals(){
		
		return animalRepository.findAll();
	}
	
	public Animal registerAnimal(String[] animalDNA) {
		Animal animal = new Animal();
		animal.setDNA(animalDNA);
		
		if(isSimianPatternPresent(animalDNA))
			animal.setAnimalType(AnimalOrder.SIMIAN);
		else
			animal.setAnimalType(AnimalOrder.HUMAN);
		
		
		return animalRepository.save(animal);
		
	}
	
	private boolean isSimianPatternPresent(String[] animalDNA) {
		for(char[] pattern : simianPatterns) {
			if(StringPatternFinder.isPatternPresentInStringArray(animalDNA, pattern))
				 return true;
		
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
