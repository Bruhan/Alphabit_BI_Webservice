package com.owner.process.usecases.dashboard;

import com.owner.process.persistence.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DashboardDao dashboardDao;

    public List<CustomerMst> getallCustmer(String plant, String search) throws Exception {
        List<CustomerMst> customerMst;
        try {
            if(search == null){
                search = "";
            }
            customerMst = dashboardDao.getallCustmer(plant, search);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return customerMst;
    }
    public List<LocationMst> getallLocation(String plant, String search) throws Exception {
        List<LocationMst> locationMst;
        try {
            if(search == null){
                search = "";
            }
            locationMst = dashboardDao.getallLocation(plant, search);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return locationMst;
    }
    public List<PrdTypeMaster> getallSubcategory(String plant, String search) throws Exception {
        List<PrdTypeMaster> prdTypeMaster;
        try {
            if(search == null){
                search = "";
            }
            prdTypeMaster = dashboardDao.getallSubcategory(plant, search);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return prdTypeMaster;
    }
    public List<EmpExecPojo> getallemployee(String plant, String search,String projecttype) throws Exception {
        List<EmpExecPojo> empExecPojo;
        try {
            if(search == null){
                search = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            empExecPojo = dashboardDao.getallemployee(plant, search,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return empExecPojo;
    }

    public List<RecycleProject> getallproject(String plant, String custno, String location, String executive,String projectType, String search) throws Exception {
        List<RecycleProject> recycleProject;
        try {
            if(search == null){
                search = "";
            }
            if(custno == null){
                custno = "";
            }
            if(projectType == null){
                projectType = "";
            }
            if(executive == null){
                executive = "";
            }
            if(location == null){
                location = "";
            }
            recycleProject = dashboardDao.getallproject(plant,custno,location,executive,projectType, search);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return recycleProject;
    }

    public List<WastageDateWeight> getIncommingWaste(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        List<WastageDateWeight> wastageDateWeight;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            wastageDateWeight = dashboardDao.getIncommingWaste(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return wastageDateWeight;
    }

    public List<WastageTypeWeight> getIncommingWasteBytype(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            wastageTypeWeight = dashboardDao.getIncommingWasteBytype(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return wastageTypeWeight;
    }

    public double getOrganicWaste(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        double tqty=0.0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            tqty = dashboardDao.getOrganicWaste(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public double getInOrganicWaste(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        double tqty=0.0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            tqty = dashboardDao.getInOrganicWaste(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public List<ProductWastageWeight> getSgtInorganicWastePro(String plant, String fromdate, String todate, String custno, String location, String executive, String project,String subcategory, String projecttype) throws Exception {
        List<ProductWastageWeight> productWastageWeight;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(subcategory == null){
                subcategory = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            productWastageWeight = dashboardDao.getSgtInorganicWastePro(plant,fromdate,todate,custno,location,executive,project,subcategory,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return productWastageWeight;
    }

    public double getBiogasData(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        double tqty=0.0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            tqty = dashboardDao.getBiogasData(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public double getOWCdata(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        double tqty=0.0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            tqty = dashboardDao.getOWCdata(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }
    public double getCattlefeeddata(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        double tqty=0.0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            tqty = dashboardDao.getCattlefeeddata(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }
    public List<WastageTypeWeight> getRejectedWasteBytype(String plant, String fromdate, String todate, String custno, String location, String executive, String project, String projecttype) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(custno == null){
                custno = "";
            }
            if(location == null){
                location = "";
            }
            if(executive == null){
                executive = "";
            }
            if(project == null){
                project = "";
            }
            if(projecttype == null){
                projecttype = "";
            }
            wastageTypeWeight = dashboardDao.getRejectedWasteBytype(plant,fromdate,todate,custno,location,executive,project,projecttype);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return wastageTypeWeight;
    }

    public List<AttDateValue> getAttendenceByProject(String plant, String fromdate, String todate, String project,String projectsite,String executive,String custno) throws Exception {
        List<AttDateValue> attDateValue;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(project == null){
                project = "";
            }
            if(projectsite == null){
                projectsite = "";
            }
            if(executive == null){
                executive = "";
            }
            if(custno == null){
                custno = "";
            }
            attDateValue = dashboardDao.getAttendenceByProject(plant,fromdate,todate,project,projectsite,executive,custno);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return attDateValue;
    }

    public List<AttDateValue> getAttendenceByDate(String plant, String fromdate, String todate) throws Exception {
        List<AttDateValue> attDateValue;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            attDateValue = dashboardDao.getAttendenceByDate(plant,fromdate,todate);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return attDateValue;
    }

    public Integer GetleadGenerationCount(String plant, String fromdate, String todate) throws Exception {
        Integer tqty=0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            tqty = dashboardDao.GetleadGenerationCount(plant,fromdate,todate);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public Integer GetProjectCount(String plant, String fromdate, String todate) throws Exception {
        Integer tqty=0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            tqty = dashboardDao.GetProjectCount(plant,fromdate,todate);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public Integer GetQuatationCount(String plant, String fromdate, String todate) throws Exception {
        Integer tqty=0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            tqty = dashboardDao.GetQuatationCount(plant,fromdate,todate);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }

    public Integer GetQuotationOpenCount(String plant, String fromdate, String todate) throws Exception {
        Integer tqty=0;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            tqty = dashboardDao.GetQuotationOpenCount(plant,fromdate,todate);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tqty;
    }


}
