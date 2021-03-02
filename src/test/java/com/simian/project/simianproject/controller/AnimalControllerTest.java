package com.simian.project.simianproject.controller;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import com.simian.project.simianproject.dto.AnimalRegisterDTO;
import com.simian.project.simianproject.mapper.AnimalMapper;
import com.simian.project.simianproject.serviceImpl.AnimalServiceImpl;
import com.simian.project.simianproject.utilityTest.AnimalCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AnimalControllerTest {

    @InjectMocks
    private AnimalController animalController;

    @Mock
    private AnimalServiceImpl animalServiceImplMock;
    @Mock
    private AnimalMapper animalMapperMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(animalServiceImplMock.getAnimalStatistic())
                            .thenReturn(AnimalCreator.createAnimalStatistic());

        BDDMockito.when(animalServiceImplMock.registerAnimal(ArgumentMatchers.eq(AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidHumanAnimalRegisteredFromDTO());

        BDDMockito.when(animalMapperMock.toAnimal(ArgumentMatchers.eq(AnimalCreator.createValidHumanAnimalRegisterDTO())))
                .thenReturn(AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO());

        BDDMockito.when(animalServiceImplMock.registerAnimal(ArgumentMatchers.eq(AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidSimianAnimalRegisteredFromDTO());

        BDDMockito.when(animalMapperMock.toAnimal(ArgumentMatchers.eq(AnimalCreator.createValidSimianAnimalRegisterDTO())))
                .thenReturn(AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO());


    }


    @Test
    @DisplayName("Returns the database statistic about the persisted animals")
    void getAnimalStatistic_Success(){
        AnimalStatisticDTO animalStat = AnimalCreator.createAnimalStatistic();

        ResponseEntity<AnimalStatisticDTO> returnedStatistic = animalController.getAnimalStatistic();

        Assertions.assertNotNull(returnedStatistic);
        Assertions.assertNotNull(returnedStatistic.getBody().getCount_human_dna());
        Assertions.assertNotNull(returnedStatistic.getBody().getCount_mutant_dna());

        Assertions.assertEquals(animalStat.getCount_human_dna(), returnedStatistic.getBody().getCount_human_dna());
        Assertions.assertEquals(animalStat.getCount_mutant_dna(), returnedStatistic.getBody().getCount_mutant_dna());
        Assertions.assertEquals(animalStat.getRatio(), returnedStatistic.getBody().getRatio());
    }

    @Test
    @DisplayName("Persist a human animal and returns it")
    void registerHumanAnimal_Success(){
        AnimalRegisterDTO animalRegisterDTO = AnimalCreator.createValidHumanAnimalRegisterDTO();
        ResponseEntity<Animal> response = animalController.registerAnimal(animalRegisterDTO);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody(), AnimalCreator.createValidHumanAnimalRegisteredFromDTO());
        Assertions.assertEquals(response.getBody().getAnimalType(),AnimalOrder.HUMAN);
    }

    @Test
    @DisplayName("Persist a simian animal and returns it")
    void registerSimianAnimal_Success(){
        AnimalRegisterDTO animalRegisterDTO = AnimalCreator.createValidSimianAnimalRegisterDTO();
        ResponseEntity<Animal> response = animalController.registerAnimal(animalRegisterDTO);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody(), AnimalCreator.createValidSimianAnimalRegisteredFromDTO());
        Assertions.assertEquals(response.getBody().getAnimalType(),AnimalOrder.SIMIAN);
    }



}