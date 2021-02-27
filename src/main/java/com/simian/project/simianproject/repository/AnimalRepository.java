package com.simian.project.simianproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simian.project.simianproject.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{

}
