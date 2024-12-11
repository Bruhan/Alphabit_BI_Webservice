package com.facility.management.usecases.auth.dao;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.persistence.models.FinProjectMultiSupervisor;
import com.facility.management.persistence.models.FinRecycleProject;
import com.facility.management.persistence.models.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository("AuthDao")
public class AuthDaoImpl implements AuthDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public UserInfo findByUserId(String username) {
        Session session = sessionFactory.openSession();
        UserInfo userInfo = null;
        try {
            String sql = "SELECT TOP 1 [UI_PKEY],[DEPT],[USER_ID],[PASSWORD],[USER_NAME],[ACCESS_COUNTER]" +
                    ",[RANK],[DESGINATION],[TELNO],[HPNO],[FAX],[EMAIL],[REMARKS],[USER_LEVEL],[USER_STATUS]" +
                    ",[EFFECTIVE_DATE],[ENROLLED_BY],[ENROLLED_ON],[UPDATED_BY],[UPDATED_ON],[AUTHORISE_BY],[AUTHORISE_ON]" +
                    ",[ISAPIUSER],[IMAGEPATH],[LAST_NOTFICATION_CHK],[USER_LEVEL_ACCOUNTING],[USER_LEVEL_PAYROLL]" +
                    ",[WEB_ACCESS],[ISACCESSOWNERAPP],[MANAGER_APP_ACCESS],[ISACCESS_STOREAPP],[RIDER_APP_ACCESS]" +
                    ",[APP_NOTIFICATION_KEY],[ISADMIN],[ISPURCHASEAPPROVAL],[ISSALESAPPROVAL],[ISPURCHASERETAPPROVAL]" +
                    ",[ISSALESRETAPPROVAL],[PRD_DEPT_ID],[ISACCESSSUPERVISORAPP],[ISACCESSPROJECTMANAGERAPP] FROM USER_INFO WHERE USER_ID = :username";
            Query query = session.createSQLQuery(sql);

            query.setParameter("username", username);

            Object[] row = (Object[]) query.uniqueResult();
            if(row != null) {
                userInfo = UserInfo.builder()
                        .uiPKey((int) row[0])
                        .dept((String) row[1])
                        .userId((String) row[2])
                        .password((String) row[3])
                        .userName((String) row[4])
                        .accessCounter((Character) row[5])
                        .rank((String) row[6])
                        .designation((String) row[7])
                        .telNo((String) row[8])
                        .hpNo((String) row[9])
                        .fax((String) row[10])
                        .email((String) row[11])
                        .remarks((String) row[12])
                        .userLevel((String) row[13])
                        .userStatus((Character) row[14])
                        .effectiveDate((String) row[15])
                        .enrolledBy((String) row[16])
                        .enrolledOn((String) row[17])
                        .updatedBy((String) row[18])
                        .updatedOn((String) row[19])
                        .authoriseBy((String) row[20])
                        .authoriseOn((String) row[21])
                        .isApiUser((String) row[22])
                        .imagePath((String) row[23])
                        .lastNotificationCheck((String) row[24])
                        .userLevelAccounting((String) row[25])
                        .userLevelPayroll((String) row[26])
                        .webAccess((Byte) row[27])
                        .isAccessOwnerApp((Byte) row[28])
                        .managerAppAccess((Byte) row[29])
                        .isAccessStoreApp((Byte) row[30])
                        .riderAppAccess((Byte) row[31])
                        .appNotificationKey((String) row[32])
                        .isAdmin((Byte) row[33])
                        .isPurchaseApproval((Byte) row[34])
                        .isSalesApproval((Byte) row[35])
                        .isPurchaseRetApproval((Byte) row[36])
                        .isSalesRetApproval((Byte) row[37])
                        .prdDeptId((String) row[38])
                        .isAccesssupervisorApp((Byte) row[39])
                        .isAccessProjectManagerApp((Byte) row[40])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return userInfo;
    }

    @Override
    public UserInfo findByUserIdAndDept(String username, String dept) {
        return null;
    }

    @Override
    public List<UserInfo> findByStore(String dept) {
        return Collections.emptyList();
    }

    @Override
    public List<UserInfo> findByDelivery(String dept) {
        return Collections.emptyList();
    }

    @Override
    public EmployeeMaster findByEmployeeLoginId(String username, String plant) {
        Session session = sessionFactory.openSession();
        EmployeeMaster employeeMaster = null;
        try {
            String sql = "SELECT TOP 1 [PLANT], [ID], [EMPNO], [FNAME], [LNAME], [GENDER], [DATEOFBIRTH], [DEPTARTMENT], [DESGINATION], [DATEOFJOINING], " +
                    "[DATEOFLEAVING], [NATIONALITY], [TELNO], [HPNO], [EMAIL], [SKYPEID], [FACEBOOKID], [TWITTERID], [LINKEDINID], [PASSPORTNUMBER], " +
                    "[COUNTRYOFISSUE], [PASSPORTEXPIRYDATE], [UNITNO], [BUILDING], [STREET], [CITY], [STATE], [COUNTRY], [ZIP], [EMIRATESID], [EMIRATESIDEXPIRY], " +
                    "[VISANUMBER], [VISAEXPIRYDATE], [LABOURCARDNUMBER], [WORKPERMITNUMBER], [CONTRACTSTARTDATE], [CONTRACTENDDATE], [BANKNAME], [IBAN], " +
                    "[BANKROUTINGCODE], [BASICSALARY], [HOUSERENTALLOWANCE], [TRANSPORTALLOWANCE], [COMMUNICATIONALLOWANCE], [OTHERALLOWANCE], [BONUS], " +
                    "[COMMISSION], [REMARKS], [CATLOGPATH], [IsActive], [CRAT], [CRBY], [UPAT], [UPBY], [NUMBEROFMANDAYS], [EMPLOYEETYPEID], [REPORTING_INCHARGE], " +
                    "[EMPUSERID], [PASSWORD], [GRATUITY], [AIRTICKET], [LEAVESALARY], [ISAUTOEMAIL], [ISPOSCUSTOMER], [ISEDITPOSPRODUCTPRICE], [OUTLET], [ISCASHIER], " +
                    "[ISSALESMAN], [ISTEMPORARY_EMP], [ISSALARYBY], [EMPLOYEESUBTYPEID] FROM " + plant + "_EMP_MST WHERE EMPUSERID = :username";

            Query query = session.createSQLQuery(sql);
            query.setParameter("username", username);
            Object[] row = (Object[]) query.uniqueResult();
            if(row != null) {
                employeeMaster = EmployeeMaster.builder()
                        .PLANT((String) row[0])
                        .ID((int) row[1])
                        .EMPNO((String) row[2])
                        .FNAME((String) row[3])
                        .LNAME((String) row[4])
                        .GENDER((Character) row[5])
                        .DATEOFBIRTH((String) row[6])
                        .DEPTARTMENT((String) row[7])
                        .DESGINATION((String) row[8])
                        .DATEOFJOINING((String) row[9])
                        .DATEOFLEAVING((String) row[10])
                        .NATIONALITY((String) row[11])
                        .TELNO((String) row[12])
                        .HPNO((String) row[13])
                        .EMAIL((String) row[14])
                        .SKYPEID((String) row[15])
                        .FACEBOOKID((String) row[16])
                        .TWITTERID((String) row[17])
                        .LINKEDINID((String) row[18])
                        .PASSPORTNUMBER((String) row[19])
                        .COUNTRYOFISSUE((String) row[20])
                        .PASSPORTEXPIRYDATE((String) row[21])
                        .UNITNO((String) row[22])
                        .BUILDING((String) row[23])
                        .STREET((String) row[24])
                        .CITY((String) row[25])
                        .STATE((String) row[26])
                        .COUNTRY((String) row[27])
                        .ZIP((String) row[28])
                        .EMIRATESID((String) row[29])
                        .EMIRATESIDEXPIRY((String) row[30])
                        .VISANUMBER((String) row[31])
                        .VISAEXPIRYDATE((String) row[32])
                        .LABOURCARDNUMBER((String) row[33])
                        .WORKPERMITNUMBER((String) row[34])
                        .CONTRACTSTARTDATE((String) row[35])
                        .CONTRACTENDDATE((String) row[36])
                        .BANKNAME((String) row[37])
                        .IBAN((String) row[38])
                        .BANKROUTINGCODE((String) row[39])
                        .BASICSALARY(row[40] != null ? ((double) row[40]) : 0.0)
                        .HOUSERENTALLOWANCE(row[41] != null ? ((double) row[41]) : 0.0)
                        .TRANSPORTALLOWANCE(row[42] != null ? ((double) row[42]) : 0.0)
                        .COMMUNICATIONALLOWANCE(row[43] != null ? ((double) row[43]) : 0.0)
                        .OTHERALLOWANCE(row[44] != null ? ((double) row[44]) : 0.0)
                        .BONUS(row[45] != null ? ((double) row[45]) : 0.0)
                        .COMMISSION(row[46] != null ? ((double) row[46]) : 0.0)
                        .REMARKS((String) row[47])
                        .CATLOGPATH((String) row[48])
                        .IsActive((String) row[49])
                        .CRAT((String) row[50])
                        .CRBY((String) row[51])
                        .UPAT((String) row[52])
                        .UPBY((String) row[53])
                        .NUMBEROFMANDAYS(row[54] != null ? ((double) row[54]) : 0.0)
                        .EMPLOYEETYPEID((int) row[55])
                        .REPORTING_INCHARGE((int) row[56])
                        .EMPUSERID((String) row[57])
                        .PASSWORD((String) row[58])
                        .GRATUITY(row[59] != null ? ((double) row[59]) : 0.0)
                        .AIRTICKET(row[60] != null ? ((double) row[60]) : 0.0)
                        .LEAVESALARY(row[61] != null ? ((double) row[61]) : 0.0)
                        .ISAUTOEMAIL((Byte) row[62])
                        .ISPOSCUSTOMER((Byte) row[63])
                        .ISEDITPOSPRODUCTPRICE((Byte) row[64])
                        .OUTLET((String) row[65])
                        .isCashier((Byte) row[66])
                        .isSalesman((Byte) row[67])
                        .isTemporaryEmp((Byte) row[68])
                        .isSalaryBy((Byte) row[69])
                        .employeeSubtypeId((int) row[70])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return employeeMaster;
    }

    @Override
    public FinRecycleProject findProjectBySupervisor(String empNo, String plant) {
        Session session = sessionFactory.openSession();
        FinRecycleProject finRecycleProject = null;
        try {
            String sql = "SELECT TOP 1 [PLANT],[ID],[CUSTNO],[PROJECT],[PROJECT_NAME],[PROJECT_DATE],[PROJECT_STATUS],[REFERENCE]," +
                    "[ESTIMATE_COST],[ESTIMATE_TIME],[BILLING_OPTION],[ISPERHOURWAGES],[PERHOURWAGESCOST],[NOTE],[CRAT],[CRBY]," +
                    "[UPAT],[UPBY],[MANDAY_HOUR],[ISMANDAY_HOUR],[PROJECTTYPE_ID],[PROJECTCLASSIFICATION_ID],[EXPIRY_DATE]," +
                    "[QUOTATION],[PREPARATION],[MANAGERENG],[SUPERVISOR],[SAFETYSUPERVISOR],[QUOTATION_DATE],[CURRENCYID]," +
                    "[CURRENCYUSEQT],[TOTAL_AMOUNT],[LOC],[TOTAL_WORKAMOUNT],[SCOPE],[BYDATE],[PAYTERMS],[SCOPEQTY],[SCOPEAMOUNT]," +
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT] FROM " + plant + "_FINRECYCLEPROJECT WHERE SUPERVISOR = :supervisor AND " +
                    "PROJECT_STATUS = :projectStatus AND PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("supervisor", empNo);
            query.setParameter("projectStatus", "Open");
            query.setParameter("plant", plant);
            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                finRecycleProject = FinRecycleProject.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .custNo((String) row[2])
                        .project((String) row[3])
                        .projectName((String) row[4])
                        .projectDate((String) row[5])
                        .projectStatus((String) row[6])
                        .reference((String) row[7])
                        .estimateCost((String) row[8])
                        .estimateTime((String) row[9])
                        .billingOption((String) row[10])
                        .isPerHourWages((Byte) row[11])
                        .perHourWagesCost(row[12] != null ? ((double) row[12]) : 0.0)
                        .note((String) row[13])
                        .CRAT((String) row[14])
                        .CRBY((String) row[15])
                        .UPAT((String) row[16])
                        .UPBY((String) row[17])
                        .mandayHour(row[18] != null ? ((double) row[18]) : 0.0)
                        .isMandayHour((Byte) row[19])
                        .projectTypeId((BigInteger) row[20])
                        .projectClassificationId((BigInteger) row[21])
                        .expiryDate((String) row[22])
                        .quotation((String) row[23])
                        .preparation((String) row[24])
                        .managerEng((String) row[25])
                        .supervisor((String) row[26])
                        .safetySupervisor((String) row[27])
                        .quotationDate((String) row[28])
                        .currencyId((String) row[29])
                        .currencyUseQt(row[30] != null ? ((double) row[30]) : 0.0)
                        .totalAmount(row[31] != null ? ((double) row[31]) : 0.0)
                        .loc((String) row[32])
                        .totalWorkAmount(row[33] != null ? ((double) row[33]) : 0.0)
                        .scope((String) row[34])
                        .byDate((Byte) row[35])
                        .payTerms((String) row[36])
                        .scopeQty((String) row[37])
                        .scopeAmount(row[38] != null ? ((double) row[38]) : 0.0)
                        .scopeDiscount(row[39] != null ? ((double) row[39]) : 0.0)
                        .totalWorkQuotAmount(row[40] != null ? ((double) row[40]) : 0.0)
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return finRecycleProject;
    }

    @Override
    public boolean checkSupervisorHasMultipleProjects(String empno, String plant) {
        boolean hasMultipleProjects = false;
        Session session = sessionFactory.openSession();
        List<Integer> projectHdrIdList;
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_FINRECYCLEPROJECT WHERE SUPERVISOR = :supervisor AND " +
                    "PROJECT_STATUS = :projectStatus AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("supervisor", empno);
            query.setParameter("plant", plant);
            query.setParameter("projectStatus", "Open");

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 1) {
                hasMultipleProjects = true;
                return hasMultipleProjects;
            }

            String sql2_1 = "SELECT PROJECTHDRID FROM " + plant + "_FINPROJECT_MULTISUPERVISOR WHERE PLANT = :plant " +
                    "AND SUPERVISOR = :supervisor";

            Query query2_1 = session.createSQLQuery(sql2_1);

            query2_1.setParameter("supervisor", empno);
            query2_1.setParameter("plant", plant);

            List<Integer> rows = query2_1.list();
            projectHdrIdList = new ArrayList<>();

            for(Integer row: rows) {
//                System.out.println(row[0]);
                projectHdrIdList.add(row);
            }

            String sql2_2 = "SELECT COUNT(*) FROM " + plant + "_FINRECYCLEPROJECT WHERE ID IN :projectHdrIdList AND " +
                    "PROJECT_STATUS = :projectStatus AND PLANT = :plant";

            Query query2_2 = session.createSQLQuery(sql2_2);

            query2_2.setParameter("projectHdrIdList", projectHdrIdList);
            query2_2.setParameter("projectStatus", "Open");
            query2_2.setParameter("plant", plant);

            Long count2 = ((Number) query2_2.uniqueResult()).longValue();

            if (count2 > 1) {
                hasMultipleProjects = true;
                return hasMultipleProjects;
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return hasMultipleProjects;
    }

    @Override
    public List<FinProjectMultiSupervisor> findMultiSupervisorsById(String empuserid, String plant) {
        Session session = sessionFactory.openSession();
        List<FinProjectMultiSupervisor> finProjectMultiSupervisorList = null;
        try {
            String sql = "SELECT [PLANT],[ID],[PROJECTHDRID],[SUPERVISORID],[SUPERVISOR],[ISADMIN],[CRAT],[CRBY],[UPAT],[UPBY] " +
                    "FROM " + plant + "_FINPROJECT_MULTISUPERVISOR WHERE SUPERVISOR = :supervisor AND PLANT = :plant";

            Query query = session.createSQLQuery(sql);

            query.setParameter("supervisor", empuserid);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            finProjectMultiSupervisorList = new ArrayList<>();

            for(Object[] row: rows) {
                FinProjectMultiSupervisor finProjectMultiSupervisor = FinProjectMultiSupervisor.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectHdrId((int) row[2])
                        .supervisorId((String) row[3])
                        .supervisor((String) row[4])
                        .isAdmin((Byte) row[5])
                        .CRAT((String) row[6])
                        .CRBY((String) row[7])
                        .UPAT((String) row[8])
                        .UPBY((String) row[9])
                        .build();

                finProjectMultiSupervisorList.add(finProjectMultiSupervisor);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return finProjectMultiSupervisorList;
    }

    @Override
    public FinRecycleProject findProjectByIds(List<Integer> projectHDRIdList, String plant) {
        Session session = sessionFactory.openSession();
        FinRecycleProject finRecycleProject = null;
        try {
            String sql = "SELECT TOP 1 [PLANT],[ID],[CUSTNO],[PROJECT],[PROJECT_NAME],[PROJECT_DATE],[PROJECT_STATUS],[REFERENCE]," +
                    "[ESTIMATE_COST],[ESTIMATE_TIME],[BILLING_OPTION],[ISPERHOURWAGES],[PERHOURWAGESCOST],[NOTE],[CRAT],[CRBY]," +
                    "[UPAT],[UPBY],[MANDAY_HOUR],[ISMANDAY_HOUR],[PROJECTTYPE_ID],[PROJECTCLASSIFICATION_ID],[EXPIRY_DATE]," +
                    "[QUOTATION],[PREPARATION],[MANAGERENG],[SUPERVISOR],[SAFETYSUPERVISOR],[QUOTATION_DATE],[CURRENCYID]," +
                    "[CURRENCYUSEQT],[TOTAL_AMOUNT],[LOC],[TOTAL_WORKAMOUNT],[SCOPE],[BYDATE],[PAYTERMS],[SCOPEQTY],[SCOPEAMOUNT]," +
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT] FROM " + plant + "_FINRECYCLEPROJECT WHERE ID IN :idList AND PLANT = :plant AND " +
                    "PROJECT_STATUS = :projectStatus";

            Query query = session.createSQLQuery(sql);
            query.setParameter("idList", projectHDRIdList);
            query.setParameter("projectStatus", "Open");
            query.setParameter("plant", plant);
            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                finRecycleProject = FinRecycleProject.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .custNo((String) row[2])
                        .project((String) row[3])
                        .projectName((String) row[4])
                        .projectDate((String) row[5])
                        .projectStatus((String) row[6])
                        .reference((String) row[7])
                        .estimateCost((String) row[8])
                        .estimateTime((String) row[9])
                        .billingOption((String) row[10])
                        .isPerHourWages((Byte) row[11])
                        .perHourWagesCost(row[12] != null ? ((double) row[12]) : 0.0)
                        .note((String) row[13])
                        .CRAT((String) row[14])
                        .CRBY((String) row[15])
                        .UPAT((String) row[16])
                        .UPBY((String) row[17])
                        .mandayHour(row[18] != null ? ((double) row[18]) : 0.0)
                        .isMandayHour((Byte) row[19])
                        .projectTypeId((BigInteger) row[20])
                        .projectClassificationId((BigInteger) row[21])
                        .expiryDate((String) row[22])
                        .quotation((String) row[23])
                        .preparation((String) row[24])
                        .managerEng((String) row[25])
                        .supervisor((String) row[26])
                        .safetySupervisor((String) row[27])
                        .quotationDate((String) row[28])
                        .currencyId((String) row[29])
                        .currencyUseQt(row[30] != null ? ((double) row[30]) : 0.0)
                        .totalAmount(row[31] != null ? ((double) row[31]) : 0.0)
                        .loc((String) row[32])
                        .totalWorkAmount(row[33] != null ? ((double) row[33]) : 0.0)
                        .scope((String) row[34])
                        .byDate((Byte) row[35])
                        .payTerms((String) row[36])
                        .scopeQty((String) row[37])
                        .scopeAmount(row[38] != null ? ((double) row[38]) : 0.0)
                        .scopeDiscount(row[39] != null ? ((double) row[39]) : 0.0)
                        .totalWorkQuotAmount(row[40] != null ? ((double) row[40]) : 0.0)
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return finRecycleProject;
    }

    @Override
    public boolean checkEmployeeType(String plant, String employeeNo, String type) {
        Session session = sessionFactory.openSession();
        try {
            String sql1 = "SELECT EMPLOYEETYPEID FROM " + plant + "_EMP_MST WHERE EMPNO = :empNo AND PLANT = :plant";

            Query query1 = session.createSQLQuery(sql1);

            query1.setParameter("empNo", employeeNo);
            query1.setParameter("plant", plant);

            Integer employeeId = ((Number) query1.uniqueResult()).intValue();

            String sql2 = "SELECT EMPLOYEETYPE FROM " + plant + "_HREMPTYPE WHERE ID = :employeeId AND PLANT = :plant";

            Query query2 = session.createSQLQuery(sql2);

            query2.setParameter("employeeId", employeeId);
            query2.setParameter("plant", plant);

            String employeeType = (String) query2.uniqueResult();

            return Objects.equals(employeeType, type);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }
}
