package com.simian.project.simianproject.repository;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStat;
import com.simian.project.simianproject.utilityTest.AnimalCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Stream;

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
        Assertions.assertEquals(animalToPersist.getDNA(),persistedAnimal.getDNA());
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
    @DisplayName("Get at Least human statistic count")
    void getAnimalStatistic_Human() {
        Animal animalToPersist = AnimalCreator.createHumanAnimal();
        animalRepository.save(animalToPersist);
        Stream<AnimalStat> count = animalRepository.getAnimalStatistic();
        Assertions.assertTrue(count.toArray().length > 0);
    }

    @Test
    @DisplayName("Get both animals statistic count")
    void getAnimalStatistic_Simian() {
        Animal simianAnimalanimalToPersist = AnimalCreator.createSimianAnimal();
        Animal humanAnimalToPersist = AnimalCreator.createHumanAnimal();
        animalRepository.save(humanAnimalToPersist);
        animalRepository.save(simianAnimalanimalToPersist);
        Stream<AnimalStat> count = animalRepository.getAnimalStatistic();
        Assertions.assertTrue(count.toArray().length > 1);
    }




}
