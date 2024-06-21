package org.example.flora.service;

import org.example.flora.DTO.FamilyDto;
import java.util.List;

public interface FamilyService {
    void addFamily(FamilyDto familyDto);
    List<FamilyDto> getAllFamilies();
    void updateFamily(String name, FamilyDto familyDto);
    void deleteFamily(String name);
    public FamilyDto getFamilyByName(String name);
}
