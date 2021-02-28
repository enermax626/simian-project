package com.simian.project.simianproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStatistic;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;
import com.simian.project.simianproject.mapper.AnimalMapper;
import com.simian.project.simianproject.service.AnimalService;


@RestController()
@RequestMapping("/")

public class AnimalController {

	private AnimalService animalService;
	private AnimalMapper animalMapper;
	
	public AnimalController(AnimalMapper animalMapper, AnimalService animalService) {
		this.animalMapper = animalMapper;
		this.animalService = animalService;
	}
	
	@GetMapping(path = "/stats")
	@ResponseStatus(code = HttpStatus.OK)
	public AnimalStatistic getDNAStatistic() {
		return animalService.getAnimalStatistic("HUMAN");
	}	
		
	@GetMapping(path = "/stats/quantity")
	@ResponseStatus(code = HttpStatus.OK)
	public int getAnimalQuantity() {
		return animalService.getAnimalQuantity();
	}	
	
	@GetMapping(path = "/simian")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Animal> getAllAnimals() {
		return animalService.getAllAnimals();
	}	
		
	@PostMapping(path = "/simian")
	public ResponseEntity<Animal> registerAnimal(@RequestBody AnimalRegisterDTO animalRegisterDTO) {
		Animal registeredAnimal = animalService.registerAnimal(animalMapper.toAnimal(animalRegisterDTO));
		if(registeredAnimal.getAnimalType().equals(AnimalOrder.SIMIAN))
			return new ResponseEntity<Animal>(registeredAnimal,HttpStatus.OK);
		return new ResponseEntity<Animal>(registeredAnimal,HttpStatus.FORBIDDEN);
	}
}
