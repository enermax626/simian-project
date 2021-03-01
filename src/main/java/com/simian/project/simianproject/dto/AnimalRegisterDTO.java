package com.simian.project.simianproject.dto;

import java.util.Arrays;

public class AnimalRegisterDTO {

    private String[] DNA;

    public String[] getDNA() {
        return DNA;
    }

    public void setDNA(String[] dNA) {
        DNA = dNA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalRegisterDTO that = (AnimalRegisterDTO) o;
        return Arrays.equals(DNA, that.DNA);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(DNA);
    }
}
