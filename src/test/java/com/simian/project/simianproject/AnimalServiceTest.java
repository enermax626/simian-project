package com.simian.project.simianproject;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStatistic;
import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.service.AnimalService;
import com.simian.project.simianproject.util.AnimalCreator;
import com.simian.project.simianproject.util.StringPatternFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class AnimalServiceTest {

    private final static char[][] SIMIANPATTERNS = {{'A', 'A', 'A', 'A'}, {'T', 'T', 'T', 'T'}, {'C', 'C', 'C', 'C'},
            {'G', 'G', 'G', 'G'}};

    @InjectMocks
    private AnimalService animalService;

    @Mock
    AnimalRepository animalRepositoryMock;

    @Mock
    StringPatternFinder stringPatternFinder;

    @BeforeEach
    void setUp(){
        BDDMockito.when(animalRepositoryMock.save(ArgumentMatchers.eq(AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidHumanAnimalRegisteredFromDTO());
        BDDMockito.when(animalRepositoryMock.save(ArgumentMatchers.eq(AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO())))
                .thenReturn(AnimalCreator.createValidSimianAnimalRegisteredFromDTO());

        String[] humanDNA = AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO().getDNA();
        BDDMockito.when(stringPatternFinder.isAnyPatternPresentInStringArray(ArgumentMatchers.eq(humanDNA),ArgumentMatchers.eq(SIMIANPATTERNS)))
                .thenReturn(false);

        String[] simianDNA = AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO().getDNA();
        BDDMockito.when(stringPatternFinder.isAnyPatternPresentInStringArray(ArgumentMatchers.eq(simianDNA),ArgumentMatchers.eq(SIMIANPATTERNS)))
                .thenReturn(true);

        BDDMockito.when(animalRepositoryMock.getAnimalStatistic(AnimalOrder.HUMAN))
                .thenReturn(Integer.toUnsignedLong(8));
        BDDMockito.when(animalRepositoryMock.getAnimalStatistic(AnimalOrder.SIMIAN))
                .thenReturn(Integer.toUnsignedLong(2));
    }


    @Test
    void registerAnimal_HumanSuccess(){
        Animal humanToBeRegistered = AnimalCreator.createValidHumanAnimalToBeRegisteredFromDTO();
        Animal humanRegistered = animalService.registerAnimal(humanToBeRegistered);

        Assertions.assertNotNull(humanRegistered);
        Assertions.assertNotNull(humanRegistered.getId());
        Assertions.assertEquals(humanRegistered,humanToBeRegistered);
        Assertions.assertEquals(humanRegistered.getAnimalType(), AnimalOrder.HUMAN);
    }

    @Test
    void registerAnimal_SimianSuccess(){
        Animal simianToBeRegistered = AnimalCreator.createValidSimianAnimalToBeRegisteredFromDTO();
        Animal simianRegistered = animalService.registerAnimal(simianToBeRegistered);

        Assertions.assertNotNull(simianRegistered);
        Assertions.assertNotNull(simianRegistered.getId());
        Assertions.assertEquals(simianRegistered,simianToBeRegistered);
        Assertions.assertEquals(simianRegistered.getAnimalType(), AnimalOrder.SIMIAN);
    }

    @Test
    void registerAnimal_WithoutDNAFailure(){
        Animal humanToBeRegistered = AnimalCreator.createEmptyDNAHumanAnimal();

        Exception exception = assertThrows(DNANotFoundException.class, () -> animalService.registerAnimal(humanToBeRegistered));

        String expectedMessage = "DNA sequence not found";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getAnimalStatistic_Success(){
        AnimalStatistic returnedStatistic = animalService.getAnimalStatistic();
        Assertions.assertNotNull(returnedStatistic);
        Assertions.assertEquals(returnedStatistic.getCount_mutant_dna(),2);
        Assertions.assertEquals(returnedStatistic.getCount_human_dna(),8);
        Assertions.assertEquals(returnedStatistic.getRatio(),0.2);
    }
    @Test
    void isSimian_True(){
        String[] dnaInput = new String[]{"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"};



    }


//  try(MockedStatic<StringPatternFinder> mockedStatic = Mockito.mockStatic(StringPatternFinder.class)){
//
//        mockedStatic.when(StringPatternFinder.isAnyPatternPresentInStringArray(anyInt())).thenReturn(false);
//    }

}
