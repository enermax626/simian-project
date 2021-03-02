package com.simian.project.simianproject.service;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStat;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.serviceImpl.AnimalServiceImpl;
import com.simian.project.simianproject.utilityTest.AnimalCreator;
import com.simian.project.simianproject.utilImpl.StringPatternFinderImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class AnimalServiceImplTest {

    private final static char[][] SIMIANPATTERNS = {{'A', 'A', 'A', 'A'}, {'T', 'T', 'T', 'T'}, {'C', 'C', 'C', 'C'},
            {'G', 'G', 'G', 'G'}};

    @InjectMocks
    private AnimalServiceImpl animalServiceImpl;

    @Mock
    AnimalRepository animalRepositoryMock;

    @Mock
    StringPatternFinderImpl stringPatternFinderImpl;

    @BeforeEach
    void setUp(){
        BDDMockito.when(animalRepositoryMock.save(ArgumentMatchers.eq(AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidHumanAnimalRegisteredFromDTO());
        BDDMockito.when(animalRepositoryMock.save(ArgumentMatchers.eq(AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidSimianAnimalRegisteredFromDTO());

        String[] humanDNA = AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO().getDNA();
        BDDMockito.when(stringPatternFinderImpl.isAnyPatternPresentInStringArray(ArgumentMatchers.eq(humanDNA),ArgumentMatchers.eq(SIMIANPATTERNS)))
                .thenReturn(false);
        String[] simianDNA = AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO().getDNA();
        BDDMockito.when(stringPatternFinderImpl.isAnyPatternPresentInStringArray(ArgumentMatchers.eq(simianDNA),ArgumentMatchers.eq(SIMIANPATTERNS)))
                .thenReturn(true);




    }


    @Test
    void registerAnimal_HumanSuccess(){
        Animal humanToBeRegistered = AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO();
        Animal humanRegistered = animalServiceImpl.registerAnimal(humanToBeRegistered);

        Assertions.assertNotNull(humanRegistered);
        Assertions.assertNotNull(humanRegistered.getId());
        Assertions.assertEquals(humanRegistered,humanToBeRegistered);
        Assertions.assertEquals(humanRegistered.getAnimalType(), AnimalOrder.HUMAN);
    }

    @Test
    void registerAnimal_SimianSuccess(){
        Animal simianToBeRegistered = AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO();
        Animal simianRegistered = animalServiceImpl.registerAnimal(simianToBeRegistered);

        Assertions.assertNotNull(simianRegistered);
        Assertions.assertNotNull(simianRegistered.getId());
        Assertions.assertEquals(simianRegistered,simianToBeRegistered);
        Assertions.assertEquals(simianRegistered.getAnimalType(), AnimalOrder.SIMIAN);
    }

    @Test
    void registerAnimal_WithoutDNAFailure(){
        Animal humanToBeRegistered = AnimalCreator.createEmptyDNAHumanAnimal();

        Exception exception = assertThrows(DNANotFoundException.class, () -> animalServiceImpl.registerAnimal(humanToBeRegistered));

        String expectedMessage = "DNA sequence not found";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
    }


    void statSetup(){

    }
    @Test
    void getAnimalStatistic_Success(){

        AnimalStat stat1 = new AnimalStat(AnimalOrder.HUMAN,2l);
        AnimalStat stat2 = new AnimalStat(AnimalOrder.SIMIAN,8l);
        List<AnimalStat> returnStat= new ArrayList<>();
        returnStat.add(stat1);
        returnStat.add(stat2);
        //If we got more tests on getAnimalStatistics, maybe it's a good idea to have an
        //inner class with some of these configurations setted up.
        BDDMockito.when(animalRepositoryMock.getAnimalStatistic())
                .thenReturn(returnStat.stream());

        AnimalStatisticDTO returnedStatistic = animalServiceImpl.getAnimalStatistic();
        Assertions.assertNotNull(returnedStatistic);
        Assertions.assertEquals(returnedStatistic.getCount_mutant_dna(),8);
        Assertions.assertEquals(returnedStatistic.getCount_human_dna(),2);
        Assertions.assertEquals(returnedStatistic.getRatio(),0.8);
    }

}
