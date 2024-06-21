package org.example.flora.service;

import org.example.flora.DTO.PlantDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlantService {
    void createNewPlant(PlantDto plantDto);
    void updatePlant(int id, PlantDto dto);
    void deletePlantById(int id);
    List<PlantDto> getAllPlants();
    Page<PlantDto> findByFamily(String family, int page, int size);
    Page<PlantDto> searchPlantsByName(String name, int page, int size);
    List<String> getAllDistinctFamilies();
    Page<PlantDto> getPlants(int page, int size);
    PlantDto getPlantById(int id);
    List<PlantDto> getAllPlantsNoPaginationn();

}
