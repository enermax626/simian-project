package com.simian.project.simianproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
public class Animal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ANIMAL")
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ANIMAL")
	private Long id;
	

	@Column(name = "DNA", nullable = false, unique = true, length = 10000)
	private String[] DNA;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ANIMALTYPE")
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
