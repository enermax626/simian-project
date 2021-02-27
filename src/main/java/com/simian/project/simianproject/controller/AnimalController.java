package com.simian.project.simianproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.service.AnimalService;

@RestController()
@RequestMapping("/simian")
public class AnimalController {

	private AnimalService animalService;
	
	AnimalController(AnimalService animalService){
		this.animalService = animalService;
	}
	
	@GetMapping
	public List<Animal> getAllAnimals() {
		return animalService.getAllAnimals();
	}
		
	@PostMapping
	public Animal registerAnimal(@RequestBody Animal animal) {
		return animalService.registerAnimal(animal.getDNA());
	}
}
