package com.facility.management.usecases.employee_master;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.persistence.models.ProjectWorkAllocationHDR;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("${spring.base.path}")
public class EmployeeController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    EmployeeMasterService employeeMasterService;

    @GetMapping("/getAllWorkers")
    public ResponseEntity<Object> getAllWorkers(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<WorkerDTO> workerDTOList = employeeMasterService.getAllWorkers(plant);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(workerDTOList);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/getWorkers/{projectNo}")
    public ResponseEntity<Object> getWorkersByProjectNo(HttpServletRequest request, @PathVariable String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<WorkerDTO> workerDTOList = employeeMasterService.getWorkersByProjectNo(plant, projectNo);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(workerDTOList);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/employee/{empNo}")
    public ResponseEntity<Object> getEmployeeByEmpNo(HttpServletRequest request, @PathVariable String empNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        EmployeeMaster employeeMaster = employeeMasterService.findByEmpCode(empNo, plant);
        ResultDao resultDao = new ResultDao();

        if(employeeMaster == null) {
            resultDao.setMessage("FAILED");
            resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
            resultDao.setResults(null);
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
        } else {
            resultDao.setMessage("SUCCESS");
            resultDao.setStatusCode(HttpStatus.OK.value());
            resultDao.setResults(employeeMaster);
            return new ResponseEntity<>(resultDao, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getEmpImage")
    public ResponseEntity<Object> getWorkImage(HttpServletRequest request, @RequestParam String empNo) throws Exception {
         ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

         EmployeeMaster employeeMaster = employeeMasterService.findByEmpCode(empNo, plant);

         if(employeeMaster == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } else if(employeeMaster.getCATLOGPATH() == null || Objects.equals(employeeMaster.getCATLOGPATH(), "")) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } else {

             byte[] imageBytes = null;
             if(employeeMaster.getCATLOGPATH() != null) {
                 Resource resource = new FileSystemResource(employeeMaster.getCATLOGPATH());
                 if(resource.exists() & !resource.getFile().isDirectory()) {
                     imageBytes = Files.readAllBytes(resource.getFile().toPath());
                 } else {
                     imageBytes = new byte[0];
                 }
             }

             ResultDao resultDao = new ResultDao();
             resultDao.setMessage("SUCCESS");
             resultDao.setStatusCode(HttpStatus.OK.value());
             resultDao.setResults(imageBytes);

             return new ResponseEntity<>(resultDao, HttpStatus.OK);
         }
    }
}
