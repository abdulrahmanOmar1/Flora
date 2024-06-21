package org.example.flora.repo;

import jakarta.transaction.Transactional;
import org.example.flora.entity.Family;
import org.example.flora.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    Page<Plant> findByFamily_Name(String familyName, Pageable pageable);

    @Query("SELECT DISTINCT p.family.name FROM Plant p")
    List<String> findAllDistinctFamilies();

    Page<Plant> findByNormalNameContainingIgnoreCase(String normalName, Pageable pageable);
    List<Plant> findAll();
    @Transactional
    void deleteByFamily(Family family);

}
