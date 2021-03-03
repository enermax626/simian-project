package com.simian.project.simianproject.domain;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANIMAL")
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ANIMAL")
    private Long id;


    @Column(name = "DNA", nullable = false, length = 8000)
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Arrays.equals(DNA, animal.DNA);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(DNA);
    }
}
