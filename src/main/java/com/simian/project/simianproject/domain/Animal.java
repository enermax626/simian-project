package com.simian.project.simianproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Animal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ANIMAL")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ANIMAL")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String[] DNA;
	
	private AnimalOrder animalType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String[] getDNA() {
		return DNA;
	}

	public void setDNA(String[] dNA) {
		DNA = dNA;
	}

	public AnimalOrder getAnimalType() {
		return animalType;
	}

	public void setAnimalType(AnimalOrder animalType) {
		this.animalType = animalType;
	}

	
	
	
	
}
