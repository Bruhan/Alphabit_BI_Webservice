package com.facility.management.usecases.projectfloor;


import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.ProjectFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${spring.base.path}")
public class FloorController {


    @Autowired
    FloorService floorService;
    @Autowired
    ClaimsSet claimsSet;

    @PostMapping("Create/ProjectFloor")
    public ResponseEntity<?> CreateProjectFloor(HttpServletRequest request, @RequestBody ProjectFloor projectfloor) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String crAt = new DateTimeCalc().getUcloTodayDate();
        String crBy = claimsDao.getEid();
        String plant = claimsDao.getPlt();

        projectfloor.setPlant(plant);
        projectfloor.setCrat(crAt);
        projectfloor.setCrby(crBy);

        floorService.createProjectFloor(projectfloor,crAt,crBy,plant);

        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    @GetMapping("GetAll/ProjectFloor")
    public ResponseEntity<?> GetAllProjectFloor (HttpServletRequest request) throws Exception {

        List<ProjectFloor> getProjectFloorAll = floorService.getAllProjectFloor();
        return new ResponseEntity<>(getProjectFloorAll, HttpStatus.OK);
    }


    @GetMapping("GetById/ProjectFloor")
    public ResponseEntity<?> GetByIdProjectFloor(HttpServletRequest request,
                                               @RequestParam int id) throws Exception {

        Optional<ProjectFloor> projectFloorById = floorService.projectFloorGetById(id);
        return new ResponseEntity<>(projectFloorById, HttpStatus.OK);
    }


    @PutMapping("UpdateById/ProjectFloor")
    public ResponseEntity<?> UpdateProjectFloorById(HttpServletRequest request,
                                                  @RequestBody ProjectFloor projectFloor) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String upAt = new DateTimeCalc().getUcloTodayDate();
        String upBy = claimsDao.getEid();
        String plant = claimsDao.getPlt();

        floorService.updateByIdProjectFloor(projectFloor,upAt,upBy,plant);
        return new ResponseEntity<>("Update Success", HttpStatus.OK);
    }


    @DeleteMapping("DeleteById/ProjectFloor")
    public ResponseEntity<?> DeleteProjectFloorById(HttpServletRequest request,
                                                  @RequestParam int id) throws Exception {
        floorService.projectFloorDeleteById(id);
        return new ResponseEntity<>("Delete", HttpStatus.OK);
    }

}
