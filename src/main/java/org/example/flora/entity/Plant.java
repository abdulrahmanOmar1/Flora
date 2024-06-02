package org.example.flora.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "scientific_name", nullable = false)
    private String scientificName;

    @Column(name = "normal_name", nullable = false)
    private String normalName;

    @Column(nullable = false)
    private String family;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "used_in")
    private String PlantUsage;


}
