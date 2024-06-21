package org.example.flora.repo;

import org.example.flora.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
    Family findByName(String name);

}
