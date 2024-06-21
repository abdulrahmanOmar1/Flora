package org.example.flora.service;

import org.example.flora.DTO.PlantDto;
import org.example.flora.entity.Family;
import org.example.flora.entity.Plant;
import org.example.flora.exception.PlantNotFoundException;
import org.example.flora.repo.FamilyRepository;
import org.example.flora.repo.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Override
    public void createNewPlant(PlantDto plantDto) {
        Family existingFamily = familyRepository.findByName(plantDto.getFamily());
        if (existingFamily == null) {
            throw new IllegalArgumentException("Family not found");
        }
        Plant plant = new Plant();
        plant.setScientificName(plantDto.getScientificName());
        plant.setNormalName(plantDto.getNormalName());
        plant.setFamily(existingFamily); // استخدم العائلة الموجودة في قاعدة البيانات
        plant.setImageUrl(plantDto.getImageUrl());
        plant.setDescription(plantDto.getDescription());
        plant.setPlantUsage(plantDto.getPlantUsage());
        plantRepository.save(plant);
    }

    @Override
    public void updatePlant(int id, PlantDto dto) throws PlantNotFoundException {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        plant.setScientificName(dto.getScientificName());
        plant.setNormalName(dto.getNormalName());
        Family family = familyRepository.findByName(dto.getFamily());
        if (family == null) {
            throw new IllegalArgumentException("Family not found");
        }
        plant.setFamily(family);
        plant.setImageUrl(dto.getImageUrl());
        plant.setDescription(dto.getDescription());
        plant.setPlantUsage(dto.getPlantUsage());
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
                .map(this::convertToDto)
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
        Page<Plant> plantPage = plantRepository.findByFamily_Name(family, pageable);
        return plantPage.map(this::convertToDto);
    }

    @Override
    public Page<PlantDto> searchPlantsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plantPage = plantRepository.findByNormalNameContainingIgnoreCase(name, pageable);
        return plantPage.map(this::convertToDto);
    }

    @Override
    public List<String> getAllDistinctFamilies() {
        return plantRepository.findAllDistinctFamilies();
    }

    public PlantDto getPlantById(int id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        return convertToDto(plant);
    }

    @Override
    public List<PlantDto> getAllPlantsNoPaginationn() {
        List<Plant> plants = plantRepository.findAll();
        return plants.stream().map(this::convertToDto).collect(Collectors.toList());    }

    private PlantDto convertToDto(Plant plant) {
        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId());
        plantDto.setScientificName(plant.getScientificName());
        plantDto.setNormalName(plant.getNormalName());
        plantDto.setFamily(plant.getFamily().getName());
        plantDto.setImageUrl(plant.getImageUrl());
        plantDto.setDescription(plant.getDescription());
        plantDto.setPlantUsage(plant.getPlantUsage());
        return plantDto;
    }
}
