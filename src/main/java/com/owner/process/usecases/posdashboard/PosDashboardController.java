package com.owner.process.usecases.posdashboard;

import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.posdashboard.dto.InventoryDTO;
import com.owner.process.usecases.posdashboard.dto.InventoryQuantityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${spring.base.path}")
public class PosDashboardController {
    @Autowired
    PosDashboardService posDashboardService;

    @Autowired
    ClaimsSet claimsSet;

    @GetMapping("/pos/getsalesreport")
    public ResponseEntity<?> getsalesreport(HttpServletRequest request,
                                        @RequestParam String plant,
                                        @RequestParam(required=false) String fromdate,
                                        @RequestParam(required=false) String todate) throws Exception {
        List<PosSalesReport> PosSalesReport = new ArrayList<PosSalesReport>();
        try {
            //ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            //String plant = claimsDao.getPlt();
            PosSalesReport = posDashboardService.getPosSalesReport(plant,fromdate,todate);
        }catch (Exception e){
            System.out.println("/pos/getsalesreport");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(PosSalesReport)
                .totalCount((long) PosSalesReport.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pos/getsalesreportByoutlet")
    public ResponseEntity<?> getsalesreportByoutlet(HttpServletRequest request,
                                            @RequestParam String plant,
                                            @RequestParam(required=false) String fromdate,
                                            @RequestParam(required=false) String todate,
                                            @RequestParam(required=false) String outlet,
                                            @RequestParam(required=false) String terminal) throws Exception {
        List<PosSalesReportOutTerm> PosSalesReport = new ArrayList<PosSalesReportOutTerm>();
        try {
            //ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            //String plant = claimsDao.getPlt();
            PosSalesReport = posDashboardService.getPosSalesReportOutTerm(plant,fromdate,todate,outlet,terminal);
        }catch (Exception e){
            System.out.println("/pos/getsalesreportByoutlet");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(PosSalesReport)
                .totalCount((long) PosSalesReport.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pos/getsalesreportWithDate")
    public ResponseEntity<?> getsalesreportwithdate(HttpServletRequest request,
                                            @RequestParam String plant,
                                            @RequestParam(required=false) String fromdate,
                                            @RequestParam(required=false) String todate) throws Exception {
        List<PosSalesReportWithDate> PosSalesReport = new ArrayList<PosSalesReportWithDate>();
        try {
            //ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            //String plant = claimsDao.getPlt();
            PosSalesReport = posDashboardService.getPosSalesReportwithdate(plant,fromdate,todate);
        }catch (Exception e){
            System.out.println("/pos/getsalesreport");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(PosSalesReport)
                .totalCount((long) PosSalesReport.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pos/getsalesreportByoutletWithDate")
    public ResponseEntity<?> getsalesreportByoutletwithdate(HttpServletRequest request,
                                                    @RequestParam String plant,
                                                    @RequestParam(required=false) String fromdate,
                                                    @RequestParam(required=false) String todate,
                                                    @RequestParam(required=false) String outlet,
                                                    @RequestParam(required=false) String terminal) throws Exception {
        List<PosSalesReportOutTermWithDate> PosSalesReport = new ArrayList<PosSalesReportOutTermWithDate>();
        try {
            //ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            //String plant = claimsDao.getPlt();
            PosSalesReport = posDashboardService.getPosSalesReportOutTermwithdate(plant,fromdate,todate,outlet,terminal);
        }catch (Exception e){
            System.out.println("/pos/getsalesreportByoutlet");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(PosSalesReport)
                .totalCount((long) PosSalesReport.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pos/getInventory")
    public ResponseEntity<?> getInventory(HttpServletRequest request, @RequestParam String plant,
                                          @RequestParam(required = false) String item,
                                          @RequestParam(required = false) String loc,
                                          @RequestParam Integer page,
                                          @RequestParam Integer productsCount ) {

        List<InventoryDTO> inventoryDTOList = posDashboardService.getInventory(plant, item, loc, page, productsCount);
        Long totalCount = posDashboardService.getInventoryTotalCount(plant, item, loc);

        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(inventoryDTOList)
                .totalCount(totalCount)
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pos/getInventoryQuantities")
    public ResponseEntity<?> getInventoryQuantities(HttpServletRequest request, @RequestParam String plant,
                                                    @RequestParam(required = false) String item,
                                                    @RequestParam(required = false) String loc) {
        List<InventoryQuantityDTO> inventoryQuantityDTOList = posDashboardService.getInventoryQuantities(plant, item, loc);

        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(inventoryQuantityDTOList)
                .totalCount((long)inventoryQuantityDTOList.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }
 }
