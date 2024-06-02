package org.example.flora.repo;

import org.example.flora.DTO.PlantDto;
import org.example.flora.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByFamily(String family);
    @Query("SELECT DISTINCT p.family FROM Plant p")
    List<String> findAllDistinctFamilies();
    List<Plant> findByNormalNameContainingIgnoreCase(String normalName);
}
