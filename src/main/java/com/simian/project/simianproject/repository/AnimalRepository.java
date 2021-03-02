package com.simian.project.simianproject.repository;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(value = "SELECT new com.simian.project.simianproject.domain.AnimalStat(animalType, count(1)) FROM Animal a group by animaltype order by animalType")
    Stream<AnimalStat> getAnimalStatistic();

}
