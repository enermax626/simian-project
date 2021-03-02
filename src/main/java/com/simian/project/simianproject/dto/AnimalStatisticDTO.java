package com.simian.project.simianproject.dto;

public class AnimalStatisticDTO {

    private Long count_mutant_dna;

    private Long count_human_dna;

    private double ratio;

    public AnimalStatisticDTO(Long simianQuantity, Long humanQuantity) {
        this.count_mutant_dna = simianQuantity;
        this.count_human_dna = humanQuantity;
        this.ratio = simianQuantity;
        if(simianQuantity>0)
            this.ratio = this.ratio/(simianQuantity+humanQuantity);
    }

    public Long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public Long getCount_human_dna() {
        return count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }

}
