package com.owner.process.usecases.visitors_dashboard;

import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.PosSalesReport;
import com.owner.process.usecases.visitors_dashboard.dto.SaveVisitorsDTO;
import com.owner.process.usecases.visitors_dashboard.dto.VisitorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${spring.base.path}")
public class VisitorsDashboardController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    VisitorsDashboardService visitorsDashboardService;

    @GetMapping("/visitors/getVisitors")
    public ResponseEntity<?> getVisitors(HttpServletRequest request,
                                         @RequestParam String plant,
                                         @RequestParam(required = false) String visitorType,
                                         @RequestParam(required = false) String fromDate,
                                         @RequestParam(required = false) String toDate) {
//        if(Objects.equals(plant, "C2443679950S2T")) {
//            plant = "C2620614616S2T";
//        }
        List<VisitorsDTO> visitorsDTOList = new ArrayList<VisitorsDTO>();
        try {
            visitorsDTOList = visitorsDashboardService.getVisitors(plant, visitorType, fromDate, toDate);

        }catch (Exception e){
            System.out.println("/visitors/getVisitors");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(visitorsDTOList)
                .totalCount((long) visitorsDTOList.size())
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @PostMapping("/visitors/save")
    public ResponseEntity<?> saveVisitors(HttpServletRequest request, @RequestBody SaveVisitorsDTO saveVisitorsDTO) {
        Integer result = 0;
        try {
            result = visitorsDashboardService.saveVisitors(saveVisitorsDTO);
        } catch (Exception e){
            System.out.println("/visitors/save");
            System.out.println(e.getMessage());
        }
        ResultsDTO resultsDTO = ResultsDTO.builder()
                .results(result)
                .totalCount(0L)
                .message("Success")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }
}
