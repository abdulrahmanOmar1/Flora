package org.example.flora.controller;

import org.example.flora.DTO.FamilyDto;
import org.example.flora.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/families")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @PostMapping
    public ResponseEntity<String> addFamily(@RequestBody FamilyDto familyDto) {
        familyService.addFamily(familyDto);
        return new ResponseEntity<>("Family added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FamilyDto>> getAllFamilies() {
        List<FamilyDto> families = familyService.getAllFamilies();
        return new ResponseEntity<>(families, HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateFamily(@PathVariable String name, @RequestBody FamilyDto familyDto) {
        familyService.updateFamily(name, familyDto);
        return new ResponseEntity<>("Family updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteFamily(@PathVariable String name) {
        familyService.deleteFamily(name);
        return new ResponseEntity<>("Family deleted successfully", HttpStatus.NO_CONTENT);
    }
}
