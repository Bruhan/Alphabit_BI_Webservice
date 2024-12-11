package com.facility.management.usecases.plant.dao;


import com.facility.management.persistence.models.PlantMst;
import com.facility.management.usecases.plant.dto.PlantDTO;

public interface PlantDao {
    public PlantDTO getPlantByPlantId(String plant);
}
