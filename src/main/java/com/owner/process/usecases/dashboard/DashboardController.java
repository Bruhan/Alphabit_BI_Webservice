package com.owner.process.usecases.dashboard;


import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.auth.project.AuthProjectDao;
import com.owner.process.usecases.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("${spring.base.path}")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @Autowired
    ClaimsSet claimsSet;
    @Autowired
    AuthProjectDao authProjectDao;
    @Autowired
    CompanyService companyService;

    @Value("${MLoggerConstant.file.path}")
    private String filePath;

    @GetMapping("/filter/getcustomer")
    public ResponseEntity<?> getCustomer(HttpServletRequest request,@RequestParam(required=false) String search ) throws Exception {
        List<CustomerMst> customerMaster = new ArrayList<CustomerMst>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            customerMaster = dashboardService.getallCustmer(plant, search);
        }catch (Exception e){
            System.out.println("/filter/getcustomer");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(customerMaster, HttpStatus.OK);
    }

    @GetMapping("/filter/getlocation")
    public ResponseEntity<?> getlocation(HttpServletRequest request,@RequestParam(required=false) String search ) throws Exception {
        List<LocationMst> locationMst = new ArrayList<LocationMst>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            locationMst = dashboardService.getallLocation(plant, search);
        }catch (Exception e){
            System.out.println("/filter/getlocation");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(locationMst, HttpStatus.OK);
    }

    @GetMapping("/filter/getexecutves")
    public ResponseEntity<?> getexecutves(HttpServletRequest request,@RequestParam(required=false) String search ,@RequestParam(required=false) String projectsitetype ) throws Exception {
        List<EmpExecPojo> empExecPojo = new ArrayList<EmpExecPojo>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            empExecPojo = dashboardService.getallemployee(plant, search,projectsitetype);
        }catch (Exception e){
            System.out.println("/filter/getexecutves");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(empExecPojo, HttpStatus.OK);
    }

    @GetMapping("/filter/getproject")
    public ResponseEntity<?> getproject(HttpServletRequest request,
                                        @RequestParam(required=false) String custno,
                                        @RequestParam(required=false) String location,
                                        @RequestParam(required=false) String executivecode,
                                        @RequestParam(required=false) String projectType,
                                        @RequestParam(required=false) String search ) throws Exception {
        List<RecycleProject> recycleProject = new ArrayList<RecycleProject>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            recycleProject = dashboardService.getallproject(plant,custno,location,executivecode,projectType,search);
        }catch (Exception e){
            System.out.println("/filter/getproject");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(recycleProject, HttpStatus.OK);
    }

    @GetMapping("/filter/getSubCategory")
    public ResponseEntity<?> getSubCategory(HttpServletRequest request,@RequestParam(required=false) String search ) throws Exception {
        List<PrdTypeMaster> prdTypeMaster = new ArrayList<PrdTypeMaster>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            prdTypeMaster = dashboardService.getallSubcategory(plant, search);
        }catch (Exception e){
            System.out.println("/filter/getlocation");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(prdTypeMaster, HttpStatus.OK);
    }

    @GetMapping("/getIncommingWasteByDate")
    public ResponseEntity<?> getIncommingWasteByDate(HttpServletRequest request,
                                                     @RequestParam(required=false) String fromdate,
                                                     @RequestParam(required=false) String todate,
                                                     @RequestParam(required=false) String custno,
                                                     @RequestParam(required=false) String location,
                                                     @RequestParam(required=false) String executivecode,
                                                     @RequestParam(required=false) String projectsitetype,
                                                     @RequestParam(required=false) String projectno ) throws Exception {
        List<WastageDateWeight> wastageDateWeight = new ArrayList<WastageDateWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            wastageDateWeight = dashboardService.getIncommingWaste(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
        }catch (Exception e){
            System.out.println("/getIncommingWasteByDate");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(wastageDateWeight, HttpStatus.OK);
    }

    @GetMapping("/getIncommingWasteByType")
    public ResponseEntity<?> getIncommingWasteByType(HttpServletRequest request,
                                                     @RequestParam(required=false) String fromdate,
                                                     @RequestParam(required=false) String todate,
                                                     @RequestParam(required=false) String custno,
                                                     @RequestParam(required=false) String location,
                                                     @RequestParam(required=false) String executivecode,
                                                     @RequestParam(required=false) String projectsitetype,
                                                     @RequestParam(required=false) String projectno ) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight = new ArrayList<WastageTypeWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            wastageTypeWeight = dashboardService.getIncommingWasteBytype(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
        }catch (Exception e){
            System.out.println("/getIncommingWasteByType");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(wastageTypeWeight, HttpStatus.OK);
    }

    @GetMapping("/getSegregateWaste")
    public ResponseEntity<?> getSegregateWaste(HttpServletRequest request,
                                                     @RequestParam(required=false) String fromdate,
                                                     @RequestParam(required=false) String todate,
                                                     @RequestParam(required=false) String custno,
                                                     @RequestParam(required=false) String location,
                                                     @RequestParam(required=false) String executivecode,
                                                     @RequestParam(required=false) String projectsitetype,
                                                     @RequestParam(required=false) String projectno ) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight = new ArrayList<WastageTypeWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            double organicwaste = dashboardService.getOrganicWaste(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
            double inorganicwaste = dashboardService.getInOrganicWaste(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);

            WastageTypeWeight wastageTypeWeight1 = new WastageTypeWeight();
            wastageTypeWeight1.setWastageType("ORGANIC WASTE");
            wastageTypeWeight1.setTotalqty(organicwaste);
            wastageTypeWeight1.setUom("KGS");
            wastageTypeWeight.add(wastageTypeWeight1);

            WastageTypeWeight wastageTypeWeight2 = new WastageTypeWeight();
            wastageTypeWeight2.setWastageType("INORGANIC WASTE");
            wastageTypeWeight2.setTotalqty(inorganicwaste);
            wastageTypeWeight2.setUom("KGS");
            wastageTypeWeight.add(wastageTypeWeight2);

        }catch (Exception e){
            System.out.println("/getSegregateWaste");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(wastageTypeWeight, HttpStatus.OK);
    }

    @GetMapping("/getSegregateInorganicWaste")
    public ResponseEntity<?> getSegregateInorganicWaste(HttpServletRequest request,
                                                     @RequestParam(required=false) String fromdate,
                                                     @RequestParam(required=false) String todate,
                                                     @RequestParam(required=false) String custno,
                                                     @RequestParam(required=false) String location,
                                                     @RequestParam(required=false) String executivecode, @RequestParam(required=false) String projectsitetype,
                                                     @RequestParam(required=false) String projectno,@RequestParam(required=false) String subCategory ) throws Exception {
        List<ProductWastageWeight> productWastageWeight = new ArrayList<ProductWastageWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            productWastageWeight = dashboardService.getSgtInorganicWastePro(plant,fromdate,todate,custno,location,executivecode,projectno,subCategory,projectsitetype);
        }catch (Exception e){
            System.out.println("/getSegregateInorganicWaste");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(productWastageWeight, HttpStatus.OK);
    }

    @GetMapping("/getSegregateOrganicWaste")
    public ResponseEntity<?> getSegregateOrganicWaste(HttpServletRequest request,
                                               @RequestParam(required=false) String fromdate,
                                               @RequestParam(required=false) String todate,
                                               @RequestParam(required=false) String custno,
                                               @RequestParam(required=false) String location,
                                               @RequestParam(required=false) String executivecode, @RequestParam(required=false) String projectsitetype,
                                               @RequestParam(required=false) String projectno ) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight = new ArrayList<WastageTypeWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            double biogas = dashboardService.getBiogasData(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
            double owc = dashboardService.getOWCdata(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
            double cattlefeed = dashboardService.getCattlefeeddata(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);

            WastageTypeWeight wastageTypeWeight1 = new WastageTypeWeight();
            wastageTypeWeight1.setWastageType("BIOGAS");
            wastageTypeWeight1.setTotalqty(biogas);
            wastageTypeWeight1.setUom("KGS");
            wastageTypeWeight.add(wastageTypeWeight1);

            if(projectsitetype == null) {
                WastageTypeWeight wastageTypeWeight2 = new WastageTypeWeight();
                wastageTypeWeight2.setWastageType("OWC");
                wastageTypeWeight2.setTotalqty(owc);
                wastageTypeWeight2.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight2);

                WastageTypeWeight wastageTypeWeight3 = new WastageTypeWeight();
                wastageTypeWeight3.setWastageType("CATTLE FEED");
                wastageTypeWeight3.setTotalqty(cattlefeed);
                wastageTypeWeight3.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight3);
            }else if(projectsitetype.equalsIgnoreCase("on-site")){
                WastageTypeWeight wastageTypeWeight2 = new WastageTypeWeight();
                wastageTypeWeight2.setWastageType("OWC");
                wastageTypeWeight2.setTotalqty(owc);
                wastageTypeWeight2.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight2);
            }else if(projectsitetype.equalsIgnoreCase("off-site")){
                WastageTypeWeight wastageTypeWeight3 = new WastageTypeWeight();
                wastageTypeWeight3.setWastageType("CATTLE FEED");
                wastageTypeWeight3.setTotalqty(cattlefeed);
                wastageTypeWeight3.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight3);
            }else{
                WastageTypeWeight wastageTypeWeight2 = new WastageTypeWeight();
                wastageTypeWeight2.setWastageType("OWC");
                wastageTypeWeight2.setTotalqty(owc);
                wastageTypeWeight2.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight2);

                WastageTypeWeight wastageTypeWeight3 = new WastageTypeWeight();
                wastageTypeWeight3.setWastageType("CATTLE FEED");
                wastageTypeWeight3.setTotalqty(cattlefeed);
                wastageTypeWeight3.setUom("KGS");
                wastageTypeWeight.add(wastageTypeWeight3);
            }

        }catch (Exception e){
            System.out.println("/getSegregateOrganicWaste");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(wastageTypeWeight, HttpStatus.OK);
    }

    @GetMapping("/getRejectedWasteByType")
    public ResponseEntity<?> getRejectedWasteByType(HttpServletRequest request,
                                                     @RequestParam(required=false) String fromdate,
                                                     @RequestParam(required=false) String todate,
                                                     @RequestParam(required=false) String custno,
                                                     @RequestParam(required=false) String location,
                                                     @RequestParam(required=false) String executivecode,
                                                     @RequestParam(required=false) String projectsitetype,
                                                     @RequestParam(required=false) String projectno ) throws Exception {
        List<WastageTypeWeight> wastageTypeWeight = new ArrayList<WastageTypeWeight>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            wastageTypeWeight = dashboardService.getRejectedWasteBytype(plant,fromdate,todate,custno,location,executivecode,projectno,projectsitetype);
        }catch (Exception e){
            System.out.println("/getRejectedWasteByType");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(wastageTypeWeight, HttpStatus.OK);
    }

    @GetMapping("/getAttendanceData")
    public ResponseEntity<?> getAttendanceData(HttpServletRequest request,
                                                    @RequestParam String fromdate,
                                                    @RequestParam String todate,
                                                    @RequestParam(required=false) String projectno,
                                                    @RequestParam(required=false) String executivecode,
                                                    @RequestParam(required=false) String custno,
                                                    @RequestParam(required=false) String projectsitetype) throws Exception {
        List<AttDateValue> AttDateValueList = new ArrayList<AttDateValue>();
        List<AttDayValue> AttDayValueList = new ArrayList<AttDayValue>();
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            if(projectno == null && projectsitetype == null && executivecode == null && custno == null) {
                AttDateValueList = dashboardService.getAttendenceByDate(plant, fromdate, todate);
            }else{
                AttDateValueList = dashboardService.getAttendenceByProject(plant,fromdate,todate,projectno,projectsitetype,executivecode,custno);
            }

            List<Date> dates = new ArrayList<Date>();

            String str_date =fromdate;
            String end_date =todate;

            DateFormat formatter ;

            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date  startDate = (Date)formatter.parse(str_date);
            Date  endDate = (Date)formatter.parse(end_date);
            long interval = 24*1000 * 60 * 60; // 1 hour in millis
            long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for(int i=0;i<dates.size();i++){
                Date lDate =(Date)dates.get(i);
                String ds = formatter.format(lDate);
                String[] splitdtae = ds.split("-");
                //System.out.println(" Date is ..." + ds);
                //System.out.println(" Date is ..." + splitdtae[2]);
                boolean status = true;
                AttDayValue attDayValue = new AttDayValue();
                for (AttDateValue attDateValue:AttDateValueList) {
                    if(!attDateValue.getAttDate().after(lDate) && !attDateValue.getAttDate().before(lDate)) {
                        status = false;
                        attDayValue.setValue(attDateValue.getValue());
                        attDayValue.setAttDate(splitdtae[2]);
                    }
                }
                if(status){
                    attDayValue.setValue(0);
                    attDayValue.setAttDate(splitdtae[2]);
                }
                AttDayValueList.add(attDayValue);
            }

        }catch (Exception e){
            System.out.println("/getAttendanceData");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(AttDayValueList, HttpStatus.OK);
    }

    @GetMapping("/getsalesData")
    public ResponseEntity<?> getsalesData(HttpServletRequest request,
                                               @RequestParam(required=false) String fromdate,
                                               @RequestParam(required=false) String todate ) throws Exception {
        List<SalesOverAllData> SalesOverAllDataList = new ArrayList<SalesOverAllData>();

        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();

               int leadgeneration = dashboardService.GetleadGenerationCount(plant, fromdate, todate);
               int quotation = dashboardService.GetQuatationCount(plant, fromdate, todate);
               int project = dashboardService.GetProjectCount(plant, fromdate, todate);
               int notsecured = dashboardService.GetQuotationOpenCount(plant, fromdate, todate);

            SalesOverAllData salesOverAllData1 = new SalesOverAllData();
            salesOverAllData1.setProcessType("LEAD GENERATION");
            salesOverAllData1.setCount(leadgeneration);
            SalesOverAllDataList.add(salesOverAllData1);

            SalesOverAllData salesOverAllData2 = new SalesOverAllData();
            salesOverAllData2.setProcessType("QUOTATION");
            salesOverAllData2.setCount(quotation);
            SalesOverAllDataList.add(salesOverAllData2);

            SalesOverAllData salesOverAllData3 = new SalesOverAllData();
            salesOverAllData3.setProcessType("SECURED");
            salesOverAllData3.setCount(quotation);
            SalesOverAllDataList.add(salesOverAllData3);

            SalesOverAllData salesOverAllData4 = new SalesOverAllData();
            salesOverAllData4.setProcessType("NONSECURED");
            salesOverAllData4.setCount(quotation);
            SalesOverAllDataList.add(salesOverAllData4);



        }catch (Exception e){
            System.out.println("/getsalesData");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(SalesOverAllDataList, HttpStatus.OK);
    }

    @GetMapping("/Status/getprojectSiteType")
    public ResponseEntity<?> getprojectSiteType(HttpServletRequest request,@RequestParam String projecttype ) throws Exception {
        String sitetype = "";
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            FinRecycleProject recycleProject = authProjectDao.findProjectByProjectType(projecttype,plant);
            sitetype = recycleProject.getProjectSiteType();
        }catch (Exception e){
            System.out.println("/filter/getproject");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(sitetype, HttpStatus.OK);
    }

    @GetMapping("/Status/getprojectByNo")
    public ResponseEntity<?> getprojectByNo(HttpServletRequest request,@RequestParam String projectno ) throws Exception {
        FinRecycleProject recycleProject = null;
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            recycleProject = authProjectDao.findProjectByProjectNo(projectno,plant);
        }catch (Exception e){
            System.out.println("/filter/getproject");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(recycleProject, HttpStatus.OK);
    }


    @GetMapping("/data/getCompanyName")
    public ResponseEntity<?> getCompanyName(HttpServletRequest request) throws Exception {
        String companyname = "";
        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();

            companyname = companyService.getCompanyNmae(plant);
        }catch (Exception e){
            System.out.println("/filter/getproject");
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(companyname, HttpStatus.OK);
    }

    @GetMapping(value = "/data/getCompanyLogo", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getCompanyLogo(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String filepath = filePath+"/track/Logos/"+plant+"Logo.GIF";
        File f = new File(filepath);
        if(f.exists() && !f.isDirectory()) {
            filepath = filePath+"/track/Logos/"+plant+"Logo.GIF";
        }else{
            filepath= filePath+"/track/Logos/NoLogo.JPG";
        }

        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                filepath
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }


}
