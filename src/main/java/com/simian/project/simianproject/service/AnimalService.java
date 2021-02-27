package com.simian.project.simianproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.repository.AnimalRepository;

@Service
public class AnimalService {

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
		return animalRepository.save(animal);
		
	}
	
	
	
}
