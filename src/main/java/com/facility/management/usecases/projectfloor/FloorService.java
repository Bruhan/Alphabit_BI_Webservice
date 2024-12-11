package com.facility.management.usecases.projectfloor;


import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.ProjectFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FloorService {


    @Autowired
    FloorRepository floorRepository;

    public void createProjectFloor(ProjectFloor projectFloor, String createdAt, String createdBy, String plant) throws Exception {
        // Set plant
        projectFloor.setPlant(plant);
        projectFloor.setFloorName(projectFloor.getFloorName());
        // Set other fields
        projectFloor.setId(0);
        projectFloor.setCrat(createdAt);
        // Set createdBy
        projectFloor.setCrby(createdBy);
        floorRepository.save(projectFloor);
    }


    public List<ProjectFloor> getAllProjectFloor() throws Exception {
        List<ProjectFloor> projectFloor;
        try {
            projectFloor = floorRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return projectFloor;
    }

    public Optional<ProjectFloor> projectFloorGetById(int id) throws Exception {
        Optional<ProjectFloor> val;
        try {
            val = floorRepository.findById(id);
        } catch (Exception e) {
            LoggerConfig.logger.info("Error currencyUseQt " + e);
            throw new Exception("SQL Error!!!");
        }
        return val;
    }


    public ProjectFloor updateByIdProjectFloor(ProjectFloor projectFloor, String updatedAt, String updatedBy, String plant){
        ProjectFloor existingFloor=floorRepository.findById(projectFloor.getId()).orElse(null);
        assert existingFloor!=null;
        existingFloor.setPlant(plant);
        existingFloor.setFloorName(projectFloor.getFloorName());

        existingFloor.setUpat(updatedAt);
        existingFloor.setUpby(updatedBy);
        return floorRepository.save(existingFloor);
    }

    public void projectFloorDeleteById(int id) throws Exception {

        try {
            floorRepository.deleteById(id);
        } catch (Exception e) {
            LoggerConfig.logger.info("Error currencyUseQt " + e);
            throw new Exception("SQL Error!!!");
        }

    }


}
