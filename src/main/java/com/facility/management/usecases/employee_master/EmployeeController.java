package com.facility.management.usecases.employee_master;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
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
}
