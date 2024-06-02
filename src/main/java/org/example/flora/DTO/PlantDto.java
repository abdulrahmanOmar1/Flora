package org.example.flora.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantDto {
    private int id;
    private String scientificName;
    private String normalName;
    private String family;
    private String imageUrl;
    private String description;
    private String plantUsage;
}
