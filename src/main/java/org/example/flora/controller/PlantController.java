package org.example.flora.controller;

import org.example.flora.DTO.PlantDto;
import org.example.flora.exception.PlantNotFoundException;
import org.example.flora.service.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;


    @PostMapping("/add")
    public ResponseEntity<String> addPlant(@RequestBody PlantDto plantDto) {
        plantService.createNewPlant(plantDto);
        return new ResponseEntity<>("Plant added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlant(@PathVariable int id, @RequestBody PlantDto plantDto) {
        try {
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable int id) {
        try {
            plantService.deletePlantById(id);
            return new ResponseEntity<>("Plant deleted successfully", HttpStatus.NO_CONTENT);
        } catch (PlantNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/families")
    public ResponseEntity<List<String>> getAllDistinctFamilies() {
        List<String> families = plantService.getAllDistinctFamilies();
        return new ResponseEntity<>(families, HttpStatus.OK);
    }
//    @GetMapping("/all")
//    public ResponseEntity<List<PlantDto>> getAllPlants() {
//        List<PlantDto> plants = plantService.getAllPlants();
//        return new ResponseEntity<>(plants, HttpStatus.OK);
//    }
@GetMapping("/all")
public ResponseEntity<Page<PlantDto>> getAllPlants(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "9") int size
) {
    Page<PlantDto> plants = plantService.getPlants(page, size);
    return new ResponseEntity<>(plants, HttpStatus.OK);
}
//    @GetMapping("/by-family/{family}")
//    public ResponseEntity<List<PlantDto>> getPlantsByFamily(@PathVariable String family) {
//        List<PlantDto> plants = plantService.findByFamily(family);
//        return ResponseEntity.ok(plants);
//    }

@GetMapping("/by-family")
public ResponseEntity<List<PlantDto>> getPlantsByFamily(@RequestParam(name = "family") String family) {
    List<PlantDto> plants = plantService.findByFamily(family);
    return ResponseEntity.ok(plants);
}
    @GetMapping("/search")
    public ResponseEntity<List<PlantDto>> searchPlants(@RequestParam(name ="name") String name) {
        List<PlantDto> plants = plantService.searchPlantsByName(name);
        return ResponseEntity.ok(plants);
    }
}
