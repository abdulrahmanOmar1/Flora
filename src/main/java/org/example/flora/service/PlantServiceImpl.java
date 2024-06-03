package org.example.flora.service;

import org.example.flora.DTO.PlantDto;
import org.example.flora.entity.Plant;
import org.example.flora.exception.PlantNotFoundException;
import org.example.flora.repo.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public void createNewPlant(PlantDto plantDto) {
        Plant plant = mapToEntity(new Plant(), plantDto);
        plantRepository.save(plant);
    }

    @Override
    public void updatePlant(int id, PlantDto dto) throws PlantNotFoundException {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        mapToEntity(plant, dto);
        plantRepository.save(plant);
    }

    @Override
    public void deletePlantById(int id) throws PlantNotFoundException {
        if (!plantRepository.existsById(id)) {
            throw new PlantNotFoundException("Plant not found with id: " + id);
        }
        plantRepository.deleteById(id);
    }

    @Override
    public List<PlantDto> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PlantDto> getPlants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plantPage = plantRepository.findAll(pageable);
        return plantPage.map(this::convertToDto);
    }

    @Override
    public Page<PlantDto> findByFamily(String family, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plantPage = plantRepository.findByFamily(family, pageable);
        return plantPage.map(this::convertToDto);
    }

    @Override
    public Page<PlantDto> searchPlantsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plantPage = plantRepository.findByNormalNameContainingIgnoreCase(name, pageable);
        return plantPage.map(this::convertToDto);
    }

    public PlantDto getPlantById(int id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        return convertToDto(plant);
    }

    private PlantDto convertToDto(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setScientificName(plant.getScientificName());
        plantDto.setNormalName(plant.getNormalName());
        plantDto.setFamily(plant.getFamily());
        plantDto.setImageUrl(plant.getImageUrl());
        plantDto.setDescription(plant.getDescription());
        plantDto.setPlantUsage(plant.getPlantUsage());
        return plantDto;
    }

    @Override
    public List<String> getAllDistinctFamilies() {
        return plantRepository.findAllDistinctFamilies();
    }

    private Plant mapToEntity(Plant plant, PlantDto dto) {
        plant.setScientificName(dto.getScientificName());
        plant.setNormalName(dto.getNormalName());
        plant.setFamily(dto.getFamily());
        plant.setImageUrl(dto.getImageUrl());
        plant.setDescription(dto.getDescription());
        plant.setPlantUsage(dto.getPlantUsage());
        return plant;
    }

    private PlantDto mapToDto(Plant plant) {
        return new PlantDto(
                plant.getId(),
                plant.getScientificName(),
                plant.getNormalName(),
                plant.getFamily(),
                plant.getImageUrl(),
                plant.getDescription(),
                plant.getPlantUsage());
    }
}
