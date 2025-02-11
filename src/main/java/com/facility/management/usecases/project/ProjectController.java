package com.facility.management.usecases.project;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.project.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class ProjectController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    ProjectService projectService;

    @GetMapping("/executive/open-projects/{executiveNo}")
    public ResponseEntity<Object> getAllOpenExecutiveProjects(HttpServletRequest request, @PathVariable("executiveNo") String executiveNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProjectDTO> projectDTOList = projectService.getAllOpenExecutiveProjects(plant, executiveNo, null);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/executive/open-onsite-projects/{executiveNo}")
    public ResponseEntity<Object> getAllOpenExecutiveOnSiteProjects(HttpServletRequest request, @PathVariable("executiveNo") String executiveNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProjectDTO> projectDTOList = projectService.getAllOpenExecutiveProjects(plant, executiveNo, "on-site");

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/executive/open-offsite-projects/{executiveNo}")
    public ResponseEntity<Object> getAllOpenExecutiveOffSiteProjects(HttpServletRequest request, @PathVariable("executiveNo") String executiveNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProjectDTO> projectDTOList = projectService.getAllOpenExecutiveProjects(plant, executiveNo, "off-site");

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }


    @GetMapping("/supervisor/open-onsite-projects/{supervisorNo}")
    public ResponseEntity<Object> getAllOpenSupervisorOnSiteProjects(HttpServletRequest request, @PathVariable("supervisorNo") String supervisorNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProjectDTO> projectDTOList = projectService.getAllOpenSupervisorProjects(plant, supervisorNo, "on-site");

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/supervisor/open-offsite-projects/{supervisorNo}")
    public ResponseEntity<Object> getAllOpenSupervisorOffSiteProjects(HttpServletRequest request, @PathVariable("supervisorNo") String supervisorNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProjectDTO> projectDTOList = projectService.getAllOpenSupervisorProjects(plant, supervisorNo, "off-site");

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

}
