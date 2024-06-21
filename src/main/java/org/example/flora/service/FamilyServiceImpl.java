package org.example.flora.service;

import org.example.flora.DTO.FamilyDto;
import org.example.flora.entity.Family;
import org.example.flora.repo.FamilyRepository;
import org.example.flora.repo.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public void addFamily(FamilyDto familyDto) {
        Family family = new Family();
        family.setName(familyDto.getName());
        familyRepository.save(family);
    }

    @Override
    public List<FamilyDto> getAllFamilies() {
        return familyRepository.findAll().stream()
                .map(family -> new FamilyDto(family.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateFamily(String name, FamilyDto familyDto) {
        Family family = familyRepository.findByName(name);
        if (family != null) {
            family.setName(familyDto.getName());
            familyRepository.save(family);
        }
    }

    @Override
    @Transactional
    public void deleteFamily(String name) {
        Family family = familyRepository.findByName(name);
        if (family != null) {
            // حذف جميع النباتات المرتبطة بالعائلة
            plantRepository.deleteByFamily(family);
            familyRepository.delete(family);
        }
    }

    @Override
    public FamilyDto getFamilyByName(String name) {
        Family family = familyRepository.findByName(name);
        if (family != null) {
            return new FamilyDto(family.getName());
        }
        return null;
    }
}
