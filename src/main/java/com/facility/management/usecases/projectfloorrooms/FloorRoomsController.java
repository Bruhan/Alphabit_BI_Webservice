package com.facility.management.usecases.projectfloorrooms;


import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.ProjectFloorRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${spring.base.path}")
public class FloorRoomsController {
    @Autowired
    FloorRoomsService floorRoomsService;
    @Autowired
    ClaimsSet claimsSet;

    @PostMapping("Create/ProjectFloorRoom")
    public ResponseEntity<?> CreateProjectFloorRoom(HttpServletRequest request,
                                                    @RequestBody ProjectFloorRoom projectFloorRoom) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String crAt = new DateTimeCalc().getUcloTodayDate();
        String crBy = claimsDao.getEid();
        String plant = claimsDao.getPlt();

        projectFloorRoom.setPlant(plant);
        projectFloorRoom.setCrat(crAt);
        projectFloorRoom.setCrby(crBy);

        floorRoomsService.createProjectFloorRoom(projectFloorRoom,crAt,crBy,plant);

        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    @GetMapping("GetAll/ProjectFloorRoom")
    public ResponseEntity<?> GetAllProjectFloorRoom (HttpServletRequest request) throws Exception {

        List<ProjectFloorRoom> getProjectFloorRoomAll = floorRoomsService.getAllProjectFloorRoom();
        return new ResponseEntity<>(getProjectFloorRoomAll, HttpStatus.OK);
    }


    @GetMapping("GetById/ProjectFloorRoom")
    public ResponseEntity<?> GetByIdProjectFloorRoom(HttpServletRequest request,
                                                 @RequestParam int id) throws Exception {

        Optional<ProjectFloorRoom> projectFloorRoomById = floorRoomsService.projectFloorRoomGetById(id);
        return new ResponseEntity<>(projectFloorRoomById, HttpStatus.OK);
    }


    @PutMapping("UpdateById/ProjectFloorRoom")
    public ResponseEntity<?> UpdateProjectFloorRoomById(HttpServletRequest request,
                                                    @RequestBody ProjectFloorRoom projectFloorRoom) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String upAt = new DateTimeCalc().getUcloTodayDate();
        String upBy = claimsDao.getEid();
        String plant = claimsDao.getPlt();

        floorRoomsService.updateByIdProjectFloorRoom(projectFloorRoom,upAt,upBy,plant);
        return new ResponseEntity<>("Update Success", HttpStatus.OK);
    }


    @DeleteMapping("DeleteById/ProjectFloorRoom")
    public ResponseEntity<?> DeleteProjectFloorRoomById(HttpServletRequest request,
                                                    @RequestParam int id) throws Exception {
        floorRoomsService.projectFloorRoomDeleteById(id);
        return new ResponseEntity<>("Delete", HttpStatus.OK);
    }

}
