package com.simian.project.simianproject.mapper;

import org.mapstruct.Mapper;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;

@Mapper(componentModel = "spring")
public abstract class AnimalMapper {
	
	public abstract Animal toAnimal(AnimalRegisterDTO animalRegisterDTO);
	
	
	

}
