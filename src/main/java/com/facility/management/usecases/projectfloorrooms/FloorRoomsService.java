package com.facility.management.usecases.projectfloorrooms;


import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.ProjectFloorRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FloorRoomsService {
    @Autowired
    FloorRoomsRepository floorRoomsRepository;

    public void createProjectFloorRoom(ProjectFloorRoom projectFloorRoom, String createdAt, String createdBy, String plant) throws Exception {
        // Set plant
        projectFloorRoom.setPlant(plant);
        projectFloorRoom.setFloorId(projectFloorRoom.getFloorId());
        projectFloorRoom.setRoomName(projectFloorRoom.getRoomName());
        // Set other fields
        projectFloorRoom.setId(0);
        projectFloorRoom.setCrat(createdAt);
        // Set createdBy
        projectFloorRoom.setCrby(createdBy);
        floorRoomsRepository.save(projectFloorRoom);
    }


    public List<ProjectFloorRoom> getAllProjectFloorRoom() throws Exception {
        List<ProjectFloorRoom> projectFloorRoom;
        try {
            projectFloorRoom = floorRoomsRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return projectFloorRoom;
    }

    public Optional<ProjectFloorRoom> projectFloorRoomGetById(int id) throws Exception {
        Optional<ProjectFloorRoom> val;
        try {
            val = floorRoomsRepository.findById(id);
        } catch (Exception e) {
            LoggerConfig.logger.info("Error currencyUseQt " + e);
            throw new Exception("SQL Error!!!");
        }
        return val;
    }


    public ProjectFloorRoom updateByIdProjectFloorRoom(ProjectFloorRoom projectFloorRoom, String updatedAt, String updatedBy, String plant){
        ProjectFloorRoom existingFloor=floorRoomsRepository.findById(projectFloorRoom.getId()).orElse(null);
        assert existingFloor!=null;
        existingFloor.setPlant(plant);
        existingFloor.setRoomName(projectFloorRoom.getRoomName());
        existingFloor.setFloorId(projectFloorRoom.getFloorId());

        existingFloor.setUpat(updatedAt);
        existingFloor.setUpby(updatedBy);
        return floorRoomsRepository.save(existingFloor);
    }

    public void projectFloorRoomDeleteById(int id) throws Exception {

        try {
            floorRoomsRepository.deleteById(id);
        } catch (Exception e) {
            LoggerConfig.logger.info("Error currencyUseQt " + e);
            throw new Exception("SQL Error!!!");
        }

    }

}
