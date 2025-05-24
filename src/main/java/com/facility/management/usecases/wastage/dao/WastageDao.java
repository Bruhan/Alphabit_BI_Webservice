package com.facility.management.usecases.wastage.dao;

import com.facility.management.persistence.models.*;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.wastage.dto.*;
import com.facility.management.usecases.wastage.enums.WastageType;

import java.text.ParseException;
import java.util.List;

public interface WastageDao {
    List<DailyWastageDetailsDET> saveDailyWastageDet(String plant, List<AddWastageRequestDTO> wastageRequestDTOList);

    DailyWastageDetailsHDR getDailyWastageDetailsHDR(String plant, String totalWastageType, String projectNo);

    DailyWastageDetailsDET getDailyWastageDetailsDETById(String plant, String projectNo, int id);

    Integer saveDailyWastageHdr(String plant, DailyWastageDetailsHDR dailyWastageDetailsHDR);

    Integer updateDailyWastageHdr(String plant, DailyWastageDetailsHDR dailyWastageDetailsHDR);

    Integer updateDailyWastageDet(String plant, DailyWastageDetailsDET oldDailyWastageDetails);

    List<WastageDetailsDTO> getWastageDetails(String plant, String projectNo, WastageType wastageType);

    Integer saveOrganicWastageDet(String plant, OrganicWasteDET organicWasteDET);

    Integer saveOrganicWastageHdr(String plant, OrganicWasteHDR organicWasteHDR);

    OrganicWasteHDR getOrganicWasteHDR(String plant, String projectNo);

    Integer updateOrganicWasteHdr(String plant, OrganicWasteHDR organicWasteHDR);

    Integer saveBioGasDet(String plant, BioGasDET bioGasDET);

    BioGasHDR getBioGasHDR(String plant, String projectNo);

    Integer updateBioGasHdr(String plant, BioGasHDR bioGasHDR);

    Integer saveBioGasHdr(String plant, BioGasHDR bioGasHDR);

    Integer saveComposeCattleFeedDet(String plant, ComposeCattleFeedDET composeCattleFeedDET);

    boolean checkComposeCattleFeedHDR(String plant, String projectNo);

    ComposeCattleFeedHDR getComposeCattleFeedHDR(String plant, String projectNo);

    Integer updateComposeCattleFeedHdr(String plant, ComposeCattleFeedHDR existingComposeCattleFeedHdr);

    Integer saveComposeCattleFeedHdr(String plant, ComposeCattleFeedHDR composeCattleFeedHDR);

    Integer saveOWCDet(String plant, OWCDET owcDet);

    OWCHDR getOWCHDR(String plant, String projectNo);

    Integer updateOWCHdr(String plant, OWCHDR owcHdr);

    Integer saveOWCHdr(String plant, OWCHDR owcHdr);

    Integer saveOWCOutcomeDet(String plant, OWCOutcomeDET owcOutcomeDET);

    OWCOutcomeHDR getOWCOutcomeHDR(String plant, String projectNo);

    Integer updateOWCOutcomeHdr(String plant, OWCOutcomeHDR owcOutcomeHDR);

    Integer saveOWCOutcomeHdr(String plant, OWCOutcomeHDR owcOutcomeHDR);

    OWCOutcomeProductDET getOWCOutcomeProductDET(String plant, String projectNo, String product);

    Integer updateOWCOutcomeProductDet(String plant, OWCOutcomeProductDET existingOWCOutcomeProductDET);

    Integer saveOWCOutcomeProductDet(String plant, OWCOutcomeProductDET owcOutcomeProductDET);

    Integer saveInorganicWastageDet(String plant, InorganicWasteDET inorganicWasteDET);

    InorganicWasteProductDET getInorganicWasteProductDET(String plant, String projectNo, String itemName);

    Integer updateInorganicWasteProductDet(String plant, InorganicWasteProductDET inorganicWasteProductDET);

    Integer saveInorganicWasteProductDet(String plant, InorganicWasteProductDET inorganicWasteProductDET);

    InorganicWasteHDR getInorganicWasteHDR(String plant, String projectNo);

    Integer updateInorganicWasteHdr(String plant, InorganicWasteHDR inorganicWasteHDR);

    Integer saveInorganicWasteHdr(String plant, InorganicWasteHDR inorganicWasteHDR);

    Integer saveRejectedWasteDet(String plant, RejectedWasteDET rejectedWasteDET);

    RejectedWasteHDR getRejectedWasteHDR(String plant, String projectNo);

    Integer updateRejectedWasteHdr(String plant, RejectedWasteHDR rejectedWasteHDR);

    Integer saveRejectedWasteHdr(String plant, RejectedWasteHDR rejectedWasteHDR);

    boolean checkTotalWastageType(String plant, String wastageType, String ProjectNo);

    boolean checkOrganicWasteHDR(String plant, String projectNo);

    boolean checkRejectedWasteHDR(String plant, String projectNo);

    boolean checkInorganicWasteProduct(String plant, String projectNo, String itemName);

    boolean checkInorganicWasteHDR(String plant, String projectNo);

    boolean checkBioGasHDR(String plant, String projectNo);

    boolean checkOWCHDR(String plant, String projectNo);

    boolean checkOWCOutcomeHDR(String plant, String projectNo);

    boolean checkProjectInventory(String plant, String projectNo, String product);

    boolean checkOWCOutcomeProductDET(String plant, String projectNo, String product);

    List<BioGasDTO> getBioGasSummary(String plant, String projectNo, String date);

    List<ComposeCattleFeedDTO> getComposeCattleFeedSummary(String plant, String projectNo, String date);

    List<OWCMachineDTO> getOWCSummary(String plant, String projectNo, String date);

    List<OWCMachineProductDTO> getOWCMachineProducts(String plant, String projectNo, int detId);

    List<OWCOutcomeDTO> getReceivedOWCOutcomeSummary(String plant, String projectNo, String date);

    List<MovedOWCOutcomeDTO> getMovedOWCOutcomeSummary(String plant, String projectNo, String date);

    List<WastageDTO> getWastageSummary(String plant, String projectNo, String date);

    List<OrganicWastageSummaryDTO> getOrganicWastageSummary(String plant, String projectNo, String date);

    List<RejectedWastageSummaryDTO> getRejectedWastageSummary(String plant, String projectNo, String date);

    List<InorganicWastageSummaryDTO> getInorganicWastageSummary(String plant, String projectNo ,String date);

    List<OWCMachineMstDTO> getOWCMachines(String plant, String projectNo);

    ProjectInventory getProjectInventory(String plant, String projectNo, String product);

    Integer updateProjectInventory(String plant, ProjectInventory projectInventory, String product);

    Integer saveProjectInventory(String plant, ProjectInventory projectInventory, String product);

    Integer saveProjectExternalInventory(String plant, ProjectExternalInventory projectExternalInventory, String product);


    List<OWCOutcomeProductDTO> getOWCOutcomeProducts(String plant, String projectNo);

    Integer saveOWCProductDET(String plant, OWCProductDET owcProductDET);


    List<ProjectInventoryDTO> getAllProjectInventory(String plant, String projectNo);


    Integer saveMovedOWCOutcomeDet(String plant, MoveOWCOutcomeRequest moveOWCOutcomeRequest);

    List<WastageCalendarResponseDTO> hasWastage(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasOrganicWastage(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasInorganicWastage(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasRejectedWastage(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasBiogas(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasOwcMachineSum(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasComposeCattleFeet(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasReceivedOwcOutcome(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<WastageCalendarResponseDTO> hasMovedOwcOutcome(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;
}
