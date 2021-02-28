package com.simian.project.simianproject.mapper;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AnimalMapper {

    public abstract Animal toAnimal(AnimalRegisterDTO animalRegisterDTO);


}
