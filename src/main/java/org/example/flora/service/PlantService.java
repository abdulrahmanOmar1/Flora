package org.example.flora.service;

import org.example.flora.DTO.PlantDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlantService {
    void createNewPlant(PlantDto plantDto);
    void updatePlant(int id, PlantDto dto);
    void deletePlantById(int id);
    List<PlantDto> getAllPlants();
    List<PlantDto> findByFamily(String family);
    List<String> getAllDistinctFamilies();
    List<PlantDto> searchPlantsByName(String name);
    Page<PlantDto> getPlants(int page, int size);
    PlantDto getPlantById(int id);

}
