package com.simian.project.simianproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{

	@Query(value = "SELECT count(1) FROM Animal a where a.animalType=:animalType")
	public long getAnimalStatistic(AnimalOrder animalType);
	
}
