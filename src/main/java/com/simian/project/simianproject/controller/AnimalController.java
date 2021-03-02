package com.simian.project.simianproject.controller;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;
import com.simian.project.simianproject.mapper.AnimalMapper;
import com.simian.project.simianproject.service.AnimalService;
import com.simian.project.simianproject.serviceImpl.AnimalServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/")
public class AnimalController {

    private AnimalService animalService;
    private AnimalMapper animalMapper;

    public AnimalController(AnimalMapper animalMapper, AnimalServiceImpl animalServiceImpl) {
        this.animalMapper = animalMapper;
        this.animalService = animalServiceImpl;
    }

    @GetMapping(path = "/stats")

    public ResponseEntity<AnimalStatisticDTO> getAnimalStatistic() {
        return new ResponseEntity<>(animalService.getAnimalStatistic(),HttpStatus.OK);
    }



    @PostMapping(path = "/simian")
    public ResponseEntity<Animal> registerAnimal(@RequestBody AnimalRegisterDTO animalRegisterDTO) {
        Animal registeredAnimal = animalService.registerAnimal(animalMapper.toAnimal(animalRegisterDTO));

        if (registeredAnimal.getAnimalType().equals(AnimalOrder.SIMIAN))
            return new ResponseEntity<>(registeredAnimal, HttpStatus.OK);
        return new ResponseEntity<>(registeredAnimal, HttpStatus.FORBIDDEN);
    }

}
