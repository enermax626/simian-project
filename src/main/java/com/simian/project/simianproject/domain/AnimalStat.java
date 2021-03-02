package com.simian.project.simianproject.domain;

public class AnimalStat {

    private AnimalOrder animalOrder;

    private Long count;

    public AnimalOrder getAnimalOrder() {
        return animalOrder;
    }

    public void setAnimalOrder(AnimalOrder animalOrder) {
        this.animalOrder = animalOrder;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public AnimalStat(AnimalOrder animalOrder, Long count) {
        this.count = count;
        this.animalOrder = animalOrder;
    }
}
