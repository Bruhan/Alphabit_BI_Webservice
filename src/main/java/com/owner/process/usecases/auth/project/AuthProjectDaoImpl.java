package com.owner.process.usecases.auth.project;

import com.owner.process.persistence.models.FinProjectMultiSupervisor;
import com.owner.process.persistence.models.FinRecycleProject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository("AuthProjectDao")
public class AuthProjectDaoImpl implements AuthProjectDao{
    @Autowired
    SessionFactory sessionFactory;
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
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT],ISNULL(PROJECT_SITETYPE,'off-site') AS PROJECT_SITETYPE  FROM " + plant + "_FINRECYCLEPROJECT WHERE SUPERVISOR = :supervisor AND " +
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
                        .projectSiteType((String) row[41])
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
    public FinRecycleProject findProjectByCustomer(String CustNo, String plant) {
        Session session = sessionFactory.openSession();
        FinRecycleProject finRecycleProject = null;
        try {
            String sql = "SELECT TOP 1 [PLANT],[ID],[CUSTNO],[PROJECT],[PROJECT_NAME],[PROJECT_DATE],[PROJECT_STATUS],[REFERENCE]," +
                    "[ESTIMATE_COST],[ESTIMATE_TIME],[BILLING_OPTION],[ISPERHOURWAGES],[PERHOURWAGESCOST],[NOTE],[CRAT],[CRBY]," +
                    "[UPAT],[UPBY],[MANDAY_HOUR],[ISMANDAY_HOUR],[PROJECTTYPE_ID],[PROJECTCLASSIFICATION_ID],[EXPIRY_DATE]," +
                    "[QUOTATION],[PREPARATION],[MANAGERENG],[SUPERVISOR],[SAFETYSUPERVISOR],[QUOTATION_DATE],[CURRENCYID]," +
                    "[CURRENCYUSEQT],[TOTAL_AMOUNT],[LOC],[TOTAL_WORKAMOUNT],[SCOPE],[BYDATE],[PAYTERMS],[SCOPEQTY],[SCOPEAMOUNT]," +
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT],ISNULL(PROJECT_SITETYPE,'off-site') AS PROJECT_SITETYPE  FROM " + plant + "_FINRECYCLEPROJECT WHERE CUSTNO = :custno AND " +
                    "PROJECT_STATUS = :projectStatus AND PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("custno", CustNo);
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
                        .projectSiteType((String) row[41])
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
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT],ISNULL(PROJECT_SITETYPE,'off-site') AS PROJECT_SITETYPE  FROM " + plant + "_FINRECYCLEPROJECT WHERE ID IN :idList AND PLANT = :plant AND " +
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
                        .projectSiteType((String) row[41])
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
    public FinRecycleProject findProjectByProjectNo(String ProjectNo, String plant) {
        Session session = sessionFactory.openSession();
        FinRecycleProject finRecycleProject = null;
        try {
            String sql = "SELECT TOP 1 [PLANT],[ID],[CUSTNO],[PROJECT],[PROJECT_NAME],[PROJECT_DATE],[PROJECT_STATUS],[REFERENCE]," +
                    "[ESTIMATE_COST],[ESTIMATE_TIME],[BILLING_OPTION],[ISPERHOURWAGES],[PERHOURWAGESCOST],[NOTE],[CRAT],[CRBY]," +
                    "[UPAT],[UPBY],[MANDAY_HOUR],[ISMANDAY_HOUR],[PROJECTTYPE_ID],[PROJECTCLASSIFICATION_ID],[EXPIRY_DATE]," +
                    "[QUOTATION],[PREPARATION],[MANAGERENG],[SUPERVISOR],[SAFETYSUPERVISOR],[QUOTATION_DATE],[CURRENCYID]," +
                    "[CURRENCYUSEQT],[TOTAL_AMOUNT],[LOC],[TOTAL_WORKAMOUNT],[SCOPE],[BYDATE],[PAYTERMS],[SCOPEQTY],[SCOPEAMOUNT]," +
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT],ISNULL(PROJECT_SITETYPE,'off-site') AS PROJECT_SITETYPE FROM " + plant + "_FINRECYCLEPROJECT WHERE PROJECT = :projectno AND PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectno", ProjectNo);
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
                        .projectSiteType((String) row[41])
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
    public FinRecycleProject findProjectByProjectType(String projectType, String plant) {
        Session session = sessionFactory.openSession();
        FinRecycleProject finRecycleProject = null;
        try {
            String sql = "SELECT TOP 1 [PLANT],[ID],[CUSTNO],[PROJECT],[PROJECT_NAME],[PROJECT_DATE],[PROJECT_STATUS],[REFERENCE]," +
                    "[ESTIMATE_COST],[ESTIMATE_TIME],[BILLING_OPTION],[ISPERHOURWAGES],[PERHOURWAGESCOST],[NOTE],[CRAT],[CRBY]," +
                    "[UPAT],[UPBY],[MANDAY_HOUR],[ISMANDAY_HOUR],[PROJECTTYPE_ID],[PROJECTCLASSIFICATION_ID],[EXPIRY_DATE]," +
                    "[QUOTATION],[PREPARATION],[MANAGERENG],[SUPERVISOR],[SAFETYSUPERVISOR],[QUOTATION_DATE],[CURRENCYID]," +
                    "[CURRENCYUSEQT],[TOTAL_AMOUNT],[LOC],[TOTAL_WORKAMOUNT],[SCOPE],[BYDATE],[PAYTERMS],[SCOPEQTY],[SCOPEAMOUNT]," +
                    "[SCOPEDISCOUNT],[TOTAL_WORKQUOTAMOUNT],ISNULL(PROJECT_SITETYPE,'off-site') AS PROJECT_SITETYPE FROM " + plant + "_FINRECYCLEPROJECT WHERE ISNULL(PROJECT_SITETYPE,'') = :projecttype AND PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projecttype", projectType);
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
                        .projectSiteType((String) row[41])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return finRecycleProject;
    }
}
