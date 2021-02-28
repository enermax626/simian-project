package com.simian.project.simianproject.repository;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("Tests for AnimalRepository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;


    @Test
    @DisplayName("Persist animal in database")
    void save_Sucess() {
        Animal animalToPersist = createHumanAnimal();
        Animal persistedAnimal = animalRepository.save(animalToPersist);
        Assertions.assertNotNull(persistedAnimal);
        Assertions.assertNotNull(persistedAnimal.getId());
        Assertions.assertEquals(persistedAnimal.getDNA(), animalToPersist.getDNA());
    }


    @Test
    @DisplayName("Persist animal without DNA in database")
    void save_Failure() {
        Animal animalToPersist = createEmptyDNAHumanAnimal();
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            animalRepository.save(animalToPersist);
        });
        String expectedMessage = "not-null property references a null or transient value";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Get human statistic count")
    void getAnimalStatistic_Human() {
        Animal animalToPersist = createHumanAnimal();
        animalRepository.save(animalToPersist);
        long humanCount = animalRepository.getAnimalStatistic(AnimalOrder.HUMAN);
        Assertions.assertNotNull(humanCount);
        Assertions.assertTrue(humanCount > 0);
    }

    @Test
    @DisplayName("Get simian statistic count")
    void getAnimalStatistic_Simian() {
        Animal animalToPersist = createSimianAnimal();
        animalRepository.save(animalToPersist);
        long simianCount = animalRepository.getAnimalStatistic(AnimalOrder.SIMIAN);
        Assertions.assertNotNull(simianCount);
        Assertions.assertTrue(simianCount > 0);
    }


    private Animal createSimianAnimal() {
        Animal animal = new Animal();
        String[] animalDNA = {"CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CTACGC", "TCCGCA"};
        animal.setDNA(animalDNA);
        animal.setAnimalType(AnimalOrder.SIMIAN);
        return animal;
    }

//	private Animal createDuplicatedSimianAnimal() {
//		Animal animal = new Animal();
//		String[] animalDNA = { "CCGGTA", "GACGAC", "CCCCTC", "ACATGA", "CCCCCC", "TCCGCA"};
//		animal.setDNA(animalDNA);
//		animal.setAnimalType(AnimalOrder.SIMIAN);
//		return animal;
//	}

    private Animal createHumanAnimal() {
        Animal animal = new Animal();
        String[] animalDNA = {"CCGGTA", "GACGAC", "CGCGTC", "ACATGA", "CTACGC", "TCCGCA"};
        animal.setDNA(animalDNA);
        animal.setAnimalType(AnimalOrder.HUMAN);
        return animal;
    }

    private Animal createEmptyDNAHumanAnimal() {
        Animal animal = new Animal();
        animal.setAnimalType(AnimalOrder.HUMAN);
        return animal;
    }

}
