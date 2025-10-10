package com.owner.process.usecases.outlets_and_terminals;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.outlets_and_terminals.POSOutlets.dao.POSOutletsDao;
import com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals.POSOutletsTerminalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("${spring.base.path}")
public class OutletsAndTerminalsController {
    @Autowired
    POSOutletsTerminalsService pOSOutletsTerminalsService;
    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    POSOutletsDao pOSOutletsDao;

    @GetMapping("/outlet/get-all")
    public ResponseEntity<?> getalloutlets(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        List<POSOutlets> outletlist = pOSOutletsDao.findAll(plant);
        ResultsDTO result = new ResultsDTO();
        result.setResults(outletlist);
        result.setMessage("Outlet List");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/terminals/get-by-outlet")
    public ResponseEntity<?> getTerminalByOutlet(HttpServletRequest request, @RequestParam String outlet) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        List<POSOutletsTerminals> terminalsList = pOSOutletsTerminalsService.getByoutlets(plant,outlet);
        ResultsDTO result = new ResultsDTO();
        result.setResults(terminalsList);
        result.setMessage("Terminal List By Outlet");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/terminals/get-by-outletwithsatus")
    public ResponseEntity<?> getNonactiveTerminalByOutlet(HttpServletRequest request, @RequestParam String outlet) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        List<POSOutletsTerminals> terminalsList = pOSOutletsTerminalsService.getByoutletsAndsatus(plant,outlet, (short) 0);
        ResultsDTO result = new ResultsDTO();
        result.setResults(terminalsList);
        result.setMessage("Terminal List By Outlet");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/terminals/get-by-terminal")
    public ResponseEntity<?> getTerminalByTerminal(HttpServletRequest request, @RequestParam String outlet, @RequestParam String terminalcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        POSOutletsTerminals terminal = pOSOutletsTerminalsService.getByoutletsAndTerminal(plant,outlet,terminalcode);
        ResultsDTO result = new ResultsDTO();
        result.setResults(terminal);
        result.setMessage("Terminal By Terminal Code");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/terminals/get-by-device")
    public ResponseEntity<?> getTerminalBydevicename(HttpServletRequest request, @RequestParam String devicename) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        List<POSOutletsTerminals> terminal = pOSOutletsTerminalsService.getByDeviceName(plant,(short) 1,devicename);
        boolean status = false;
        if(terminal != null){
            if(terminal.size() > 0){
                status = true;
            }
        }
        return  new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/terminals/get-by-device-terminal")
    public ResponseEntity<?> getTerminalBydnameterminal(HttpServletRequest request, @RequestParam String devicename, @RequestParam String terminalcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String plant = claimsDao.getPlt();
        List<POSOutletsTerminals> terminal = pOSOutletsTerminalsService.getByDeviceNameTerminal(plant,(short) 1,devicename,terminalcode);
        boolean status = false;
        if(terminal != null){
            if(terminal.size() > 0){
                status = true;
            }
        }
        return  new ResponseEntity<>(status, HttpStatus.OK);
    }

    @RequestMapping(value = "/terminals/save", method = RequestMethod.POST)
    public ResponseEntity<?> seterminals (HttpServletRequest request, @RequestBody POSOutletsTerminals terminal) throws Exception {
        ResultsDTO resultsDTO = new ResultsDTO();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String loc = request.getHeader("Location");
            String createdAt = new DateTimeCalc().getUcloTodayDateTime();
            String createdBy = claimsDao.getEid();

            terminal.setUPAT(createdAt);
            terminal.setUPBY(createdBy);
            pOSOutletsTerminalsService.saveterminal(terminal);

            resultsDTO.setResults("ok");
            resultsDTO.setMessage("terminals saved Successfully");
            resultsDTO.setPageNumber(1);
            resultsDTO.setPageSize(1);

        }catch (Exception e){
            System.out.println(e.getMessage());
            resultsDTO.setResults("not ok");
            resultsDTO.setMessage(e.getMessage());
            resultsDTO.setPageNumber(1);
            resultsDTO.setPageSize(1);

        }


        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

}
