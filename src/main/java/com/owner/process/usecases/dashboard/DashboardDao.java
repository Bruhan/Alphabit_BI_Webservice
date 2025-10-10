package com.owner.process.usecases.dashboard;

import com.owner.process.persistence.models.*;

import java.text.ParseException;
import java.util.List;

public interface DashboardDao {
    List<CustomerMst> getallCustmer(String plant, String search);
    List<LocationMst> getallLocation(String plant, String search);
    List<PrdTypeMaster> getallSubcategory(String plant,String search);
    List<EmpExecPojo> getallemployee(String plant, String search,String projecttype);
    List<RecycleProject> getallproject(String plant, String custno, String location, String executive,String projectType, String search);
    List<WastageDateWeight> getIncommingWaste(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws ParseException;
    List<WastageTypeWeight> getIncommingWasteBytype(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    double getOrganicWaste(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    double getInOrganicWaste(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    List<ProductWastageWeight> getSgtInorganicWastePro(String plant, String fromdate, String todate, String custno, String location,String executive, String project,String subcategory, String projecttype) throws ParseException;
    double getBiogasData(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    double getOWCdata(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    double getCattlefeeddata(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    List<WastageTypeWeight> getRejectedWasteBytype(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException;
    List<AttDateValue> getAttendenceByProject(String plant, String fromdate, String todate, String project,String projectsit,String executive,String custno) throws ParseException;
    List<AttDateValue> getAttendenceByDate(String plant, String fromdate, String todate) throws ParseException;
    Integer GetleadGenerationCount(String plant, String fromdate, String todate) throws ParseException;
    Integer GetProjectCount(String plant, String fromdate, String todate) throws ParseException;
    Integer GetQuatationCount(String plant, String fromdate, String todate) throws ParseException;
    Integer GetQuotationOpenCount(String plant, String fromdate, String todate) throws ParseException;
}
