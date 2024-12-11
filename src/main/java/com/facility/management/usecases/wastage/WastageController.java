package com.facility.management.usecases.wastage;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.wastage.dto.*;
import com.facility.management.usecases.wastage.enums.WastageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class WastageController {
    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    WastageService wastageService;

    @GetMapping("/getWastageTypes")
    public ResponseEntity<Object> getWastageTypes(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(WastageType.values());
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/wastage")
    public ResponseEntity<Object> saveDailyWastage(HttpServletRequest request, @RequestBody List<AddWastageRequestDTO> wastageRequestDTOList) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        Integer result = wastageService.saveDailyWastage(plant, wastageRequestDTOList);

        ResultDao resultDao = new ResultDao();
        if(result > 0) {
            resultDao.setMessage("SUCCESS");
            resultDao.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(resultDao, HttpStatus.OK);
        }
        else {
            resultDao.setMessage("FAILED");
            resultDao.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping("/wastage/details/{projectNo}")
    public ResponseEntity<Object> getWastageDetails(HttpServletRequest request, @PathVariable("projectNo") String projectNo, @RequestParam(required = false) WastageType wastageType) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<WastageDetailsDTO> wastageDetailsDTOList = wastageService.getWastageDetails(plant, projectNo, wastageType);

        ResultDao resultDao = new ResultDao();

        resultDao.setResults(wastageDetailsDTOList);
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }


    @PostMapping("/organic-wastage")
    public ResponseEntity<Object> saveProcessedOrganicWastage(HttpServletRequest request, @RequestBody OrganicWastageRequestDTO organicWastageRequestDTO) throws Exception {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wastageService.saveProcessedOrganicWastage(plant, organicWastageRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/inorganic-wastage")
    public ResponseEntity<Object> saveInorganicWastage(HttpServletRequest request, @RequestBody InorganicWastageRequestDTO inorganicWastageRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wastageService.saveInorganicWastage(plant, inorganicWastageRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/biogas")
    public ResponseEntity<Object> sendToBioGas(HttpServletRequest request, @RequestBody BioGasRequestDTO bioGasRequestDTO) throws Exception {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wastageService.sendToBioGas(plant, bioGasRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/owc-machine")
    public ResponseEntity<Object> sendToOWCMachine(HttpServletRequest request, @RequestBody OWCMachineRequestDTO owcMachineRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wastageService.sendToOWCMachine(plant, owcMachineRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/owc-outcome-receive")
    public ResponseEntity<Object> receiveOWCOutcome(HttpServletRequest request, @RequestBody ReceivedOWCOutcomeRequest receivedOWCOutcomeRequest) throws Exception {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wastageService.receiveOWCOutcome(plant, receivedOWCOutcomeRequest);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/owc-outcome-move")
    public ResponseEntity<Object> moveOWCOutcome(HttpServletRequest request, @RequestBody MoveOWCOutcomeRequest moveOWCOutcomeRequest) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        HashMap<String, Integer> result = wastageService.moveOWCOutcome(plant, moveOWCOutcomeRequest);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/biogas-summary/{projectNo}")
    public ResponseEntity<Object> getBioGasSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
//        String plant = "test";
            List<BioGasDTO> bioGasDTOList = wastageService.getBioGasSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(bioGasDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/owc-summary/{projectNo}")
    public ResponseEntity<Object> getOWCSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";
        List<OWCMachineDTO> owcMachineDTOList = wastageService.getOWCSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcMachineDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/receive-owc-outcome-summary/{projectNo}")
    public ResponseEntity<Object> getReceivedOWCOutcomeSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<OWCOutcomeDTO> owcOutcomeDTOList = wastageService.getReceivedOWCOutcomeSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcOutcomeDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

//    @GetMapping("/move-owc-outcome-summary/{projectNo}")
//    public ResponseEntity<Object> getMovedOWCOutcomeSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
//        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
//        String plant = claimsDao.getPlt();
//
//    }

    @GetMapping("/organic-wastage-summary/{projectNo}")
    public ResponseEntity<Object> getOrganicWastageSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<OrganicWastageSummaryDTO> organicWastageSummaryDTOList = wastageService.getOrganicWastageSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(organicWastageSummaryDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/rejected-wastage-summary/{projectNo}")
    public ResponseEntity<Object> getRejectedWastageSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<RejectedWastageSummaryDTO> rejectedWastageSummaryDTOList = wastageService.getRejectedWastageSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(rejectedWastageSummaryDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/inorganic-wastage-summary/{projectNo}")
    public ResponseEntity<Object> getInorganicWastageSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<InorganicWastageSummaryDTO> inorganicWastageSummaryDTOList = wastageService.getInorganicWastageSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(inorganicWastageSummaryDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/owc-outcome-products/{projectNo}")
    public ResponseEntity<Object> getOWCOutcomeProducts(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<OWCOutcomeProductDTO> owcOutcomeProductDTOList = wastageService.getOWCOutcomeProducts(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcOutcomeProductDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);

    }

    @GetMapping("/wastage-summary/{projectNo}")
    public ResponseEntity<Object> getWastageSummary(HttpServletRequest request,
                                                    @PathVariable("projectNo") String projectNo,
                                                    @RequestParam(required = false) String date) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<WastageDTO> wastageDTOList = wastageService.getWastageSummary(plant, projectNo, date);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(wastageDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/getOWCMachines/{projectNo}")
    public ResponseEntity<Object> getOWCMachines(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";
        List<OWCMachineMstDTO> owcMachineMstDTOList = wastageService.getOWCMachines(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcMachineMstDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/getAllProjectInventory/{projectNo}")
    public ResponseEntity<Object> getAllProjectInventory(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";
        List<ProjectInventoryDTO> owcMachineMstDTOList = wastageService.getAllProjectInventory(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcMachineMstDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }


}
