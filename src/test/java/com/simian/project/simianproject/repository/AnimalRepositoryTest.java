package com.simian.project.simianproject.repository;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.util.AnimalCreator;
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
    void save_Success() {
        Animal animalToPersist = AnimalCreator.createHumanAnimal();
        Animal persistedAnimal = animalRepository.save(animalToPersist);
        Assertions.assertNotNull(persistedAnimal);
        Assertions.assertNotNull(persistedAnimal.getId());
        Assertions.assertEquals(persistedAnimal.getDNA(), animalToPersist.getDNA());
    }


    @Test
    @DisplayName("Persist animal without DNA in database")
    void save_Failure() {
        Animal animalToPersist = AnimalCreator.createEmptyDNAHumanAnimal();
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> animalRepository.save(animalToPersist));
        String expectedMessage = "not-null property references a null or transient value";
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));

    }

    @Test
    @DisplayName("Get human statistic count")
    void getAnimalStatistic_Human() {
        Animal animalToPersist = AnimalCreator.createHumanAnimal();
        animalRepository.save(animalToPersist);
        long humanCount = animalRepository.getAnimalStatistic(AnimalOrder.HUMAN);
        Assertions.assertTrue(humanCount > 0);
    }

    @Test
    @DisplayName("Get simian statistic count")
    void getAnimalStatistic_Simian() {
        Animal animalToPersist = AnimalCreator.createSimianAnimal();
        animalRepository.save(animalToPersist);
        long simianCount = animalRepository.getAnimalStatistic(AnimalOrder.SIMIAN);
        Assertions.assertTrue(simianCount > 0);
    }




}
