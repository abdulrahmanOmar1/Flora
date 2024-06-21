package org.example.flora.controller;

import org.example.flora.DTO.PlantDto;
import org.example.flora.exception.PlantNotFoundException;
import org.example.flora.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @PostMapping("/add")
    public ResponseEntity<String> addPlant(
            @RequestParam("normalName") String normalName,
            @RequestParam("scientificName") String scientificName,
            @RequestParam("family") String family,
            @RequestParam("description") String description,
            @RequestParam("plantUsage") String plantUsage,
            @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl) {
        try {
            PlantDto plantDto = new PlantDto();
            plantDto.setNormalName(normalName);
            plantDto.setScientificName(scientificName);
            plantDto.setFamily(family);
            plantDto.setDescription(description);
            plantDto.setPlantUsage(plantUsage);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                // معالجة الصورة هنا (رفعها إلى السيرفر أو تخزينها في مكان مناسب)
                // مثال: plantDto.setImageUrl(imageUrl.getOriginalFilename());
            }

            plantService.createNewPlant(plantDto);
            return new ResponseEntity<>("Plant added successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return new ResponseEntity<>("Failed to add plant", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlant(
            @PathVariable int id,
            @RequestParam("normalName") String normalName,
            @RequestParam("scientificName") String scientificName,
            @RequestParam("family") String family,
            @RequestParam("description") String description,
            @RequestParam("plantUsage") String plantUsage,
            @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl) {
        try {
            PlantDto plantDto = new PlantDto();
            plantDto.setNormalName(normalName);
            plantDto.setScientificName(scientificName);
            plantDto.setFamily(family);
            plantDto.setDescription(description);
            plantDto.setPlantUsage(plantUsage);

            if (imageUrl != null && !imageUrl.isEmpty()) {


            }

            plantService.updatePlant(id, plantDto);
            return new ResponseEntity<>("Plant updated successfully", HttpStatus.OK);
        } catch (PlantNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable int id) {
        PlantDto plant = plantService.getPlantById(id);
        return ResponseEntity.ok(plant);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PlantDto>> getAllPlants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Page<PlantDto> plants = plantService.getPlants(page, size);
        return new ResponseEntity<>(plants, HttpStatus.OK);
    }
    @GetMapping("/allWithoutPagination")
    public ResponseEntity<List<PlantDto>> getAllPlantsNoPagination() {
        List<PlantDto> plants = plantService.getAllPlantsNoPaginationn();
        return ResponseEntity.ok(plants);
    }
    @GetMapping("/by-family")
    public ResponseEntity<Page<PlantDto>> getPlantsByFamily(
            @RequestParam(name = "family") String family,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Page<PlantDto> plants = plantService.findByFamily(family, page, size);
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlantDto>> searchPlants(
            @RequestParam(name ="name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Page<PlantDto> plants = plantService.searchPlantsByName(name, page, size);
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/families")
    public ResponseEntity<List<String>> getAllDistinctFamilies() {
        List<String> families = plantService.getAllDistinctFamilies();
        return new ResponseEntity<>(families, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable int id) {
        try {
            plantService.deletePlantById(id);
            return new ResponseEntity<>("Plant deleted successfully", HttpStatus.NO_CONTENT);
        } catch (PlantNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
