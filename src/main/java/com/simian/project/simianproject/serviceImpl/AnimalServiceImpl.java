package com.simian.project.simianproject.serviceImpl;

import com.simian.project.simianproject.domain.Animal;
import com.simian.project.simianproject.domain.AnimalOrder;
import com.simian.project.simianproject.domain.AnimalStat;
import com.simian.project.simianproject.dto.AnimalStatisticDTO;
import com.simian.project.simianproject.exception.DNANotFoundException;
import com.simian.project.simianproject.repository.AnimalRepository;
import com.simian.project.simianproject.service.AnimalService;
import com.simian.project.simianproject.util.StringPatternFinder;
import com.simian.project.simianproject.utilImpl.StringPatternFinderImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnimalServiceImpl implements AnimalService {

    //This could be persisted in a table, so we would be able do add or remove new patterns that could
    //detect a Simian DNA sequence without needing to recompile this class.
    private final static char[][] SIMIANPATTERNS = {{'A', 'A', 'A', 'A'}, {'T', 'T', 'T', 'T'}, {'C', 'C', 'C', 'C'},
            {'G', 'G', 'G', 'G'}

    };
///
    private StringPatternFinder stringPatternFinder;
    private AnimalRepository animalRepository;


    public AnimalServiceImpl(AnimalRepository animalRepository, StringPatternFinderImpl stringPatternFinderImpl) {
        this.stringPatternFinder = stringPatternFinderImpl;
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal registerAnimal(Animal animal) {

        if (animal.getDNA() == null)
            throw new DNANotFoundException("DNA sequence not found");

        if (isSimian(animal.getDNA()))
            animal.setAnimalType(AnimalOrder.SIMIAN);
        else
            animal.setAnimalType(AnimalOrder.HUMAN);

        return animalRepository.save(animal);
    }
    
    @Override
    @Transactional
    public AnimalStatisticDTO getAnimalStatistic() {

     return populateAnimalStatisticsDTO(animalRepository.getAnimalStatistic());

    }

    @Override
    public boolean isSimian(String[] dna) {
        return stringPatternFinder.isAnyPatternPresentInStringArray(dna, AnimalServiceImpl.SIMIANPATTERNS);
    }

    //Made like this to avoid 2 queries on DB
    private AnimalStatisticDTO populateAnimalStatisticsDTO(Stream<AnimalStat> singleStatistics){
        Map<AnimalOrder,Long> statisticsMap= singleStatistics.collect(Collectors.toMap(entry-> entry.getAnimalOrder(),entry->entry.getCount()));

        return new AnimalStatisticDTO((statisticsMap.get(AnimalOrder.SIMIAN)!= null ? statisticsMap.get(AnimalOrder.SIMIAN):0l),
                (statisticsMap.get(AnimalOrder.HUMAN)!= null ? statisticsMap.get(AnimalOrder.HUMAN):0l));

    }


}
