package com.facility.management.usecases.dashboard.dao;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.DailyWastageDetailsHDR;
import com.facility.management.usecases.dashboard.dto.*;
import com.facility.management.usecases.dashboard.enums.OWCMachineStatus;
import com.facility.management.usecases.wastage.enums.WastageType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("DashboardDao")
public class DashboardDaoImpl implements DashboardDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public EmployeeDetailDTO getEmployeeDetails(String plant, String employeeId) {
        Session session = sessionFactory.openSession();
        EmployeeDetailDTO employeeDetailDTO = null;
        try {
            String sql = "SELECT CONCAT(FNAME, ' ', LNAME), EMPNO, CATLOGPATH FROM " + plant + "_EMP_MST WHERE EMPNO = :empNo AND" +
                    " PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("empNo", employeeId);
            query.setParameter("plant", plant);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                employeeDetailDTO = new EmployeeDetailDTO();
                employeeDetailDTO.setEmployeeName((String) row[0]);
                employeeDetailDTO.setEmployeeId((String) row[1]);
                employeeDetailDTO.setEmployeeImagePath((String) row[2]);
            }



        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return employeeDetailDTO;
    }

    @Override
    public ProjectDetailDTO getProjectDetails(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        ProjectDetailDTO projectDetailDTO = null;
        try {
            String sql = "SELECT PROJ.PROJECT, PROJ.CUSTPRNO, PROJ.PROJECT_NAME, LOC.LOCDESC FROM " + plant + "_FINRECYCLEPROJECT PROJ LEFT JOIN " + plant + "_LOCMST LOC ON PROJ.LOC = LOC.LOC WHERE PROJ.PROJECT = :projectNo AND " +
                    "PROJ.PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                projectDetailDTO = new ProjectDetailDTO();
                projectDetailDTO.setProjectNo((String) row[0]);
                projectDetailDTO.setCustomerProjectNo((String) row[1]);
                projectDetailDTO.setProjectName((String) row[2]);
                projectDetailDTO.setLocation((String) row[3]);
            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return projectDetailDTO;
    }

    @Override
    public TotalWastageDTO getTotalWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalOrganicWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant AND WASTAGE_TYPE = :wastageType GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("wastageType", WastageType.ORGANIC_WASTE.name());

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalInorganicWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant AND WASTAGE_TYPE = :wastageType GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("wastageType", WastageType.INORGANIC_WASTE.name());

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalRejectedWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant AND WASTAGE_TYPE = :wastageType GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("wastageType", WastageType.REJECTED_WASTE.name());

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalDebrisWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant AND WASTAGE_TYPE = :wastageType GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("wastageType", WastageType.DEBRIS_WASTE.name());

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalGardenWastage(String plant, String projectNo, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO = :projectNo AND PLANT = :plant AND WASTAGE_TYPE = :wastageType GROUP BY " +
                    "WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("wastageType", WastageType.GARDEN_WASTE.name());

            Object[] row = (Object[]) query.uniqueResult();


            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TotalWastageDTO getTotalExecutiveWastage(String plant, String employeeId, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        TotalWastageDTO totalWastageDTO = null;
        try {
            String sql = "SELECT TOP 1 SUM(WASTAGE_QTY), WASTAGE_UOM FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET WHERE " +
                    "(DATE BETWEEN :fromDate AND :toDate) AND PROJECTNO IN (SELECT PROJECT FROM " + plant + "_FINRECYCLEPROJECT " +
                    "WHERE PLANT = :plant AND PROJECT_STATUS = :projectStatus AND SAFETYSUPERVISOR = :executiveNo) AND PLANT = :plant " +
                    "GROUP BY WASTAGE_UOM";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("projectStatus", "Open");
            query.setParameter("executiveNo", employeeId);

            Object[] row = (Object[]) query.uniqueResult();

            totalWastageDTO = new TotalWastageDTO();
            totalWastageDTO.setQty(row != null ? (double) row[0] : 0.0);
            totalWastageDTO.setUom(row != null ? (String) row[1] : "KGS");


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalWastageDTO;
    }

    @Override
    public TodayWorkerDTO getTodayWorkers(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        TodayWorkerDTO todayWorkerDTO = null;
        try {
            String sql = "SELECT COUNT(DISTINCT EMPID) FROM " + plant + "_Staffattendance WHERE CAST(Att_Date AS DATE) = " +
                    "CAST(GETDATE() AS DATE) AND EMPID IN (SELECT EMPID FROM " + plant + "_PROJECT_WORKERLIST WHERE PROJECTNO " +
                    " = :projectNo AND PLANT = :plant)";
            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            todayWorkerDTO = new TodayWorkerDTO();

            todayWorkerDTO.setTodayWorkersCount(((Number) query.uniqueResult()).intValue());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return todayWorkerDTO;
    }

    @Override
    public TotalProductRequestDTO getTotalProductRequests(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        TotalProductRequestDTO totalProductRequestDTO = null;
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_PROJECTSTOCKREQUESTHDR WHERE PROJECTNO = :projectNo AND" +
                    " PLANT = :plant AND CONVERT(DATE, REQUESTEDDATE, 105) = CONVERT(DATE, GETDATE())";
            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            totalProductRequestDTO = new TotalProductRequestDTO();

            totalProductRequestDTO.setTotalProductRequests(((Number) query.uniqueResult()).intValue());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return totalProductRequestDTO;
    }

    @Override
    public List<OWCMachineDetailDTO> getOWCMachines(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OWCMachineDetailDTO> owcMachineDetailDTOList = null;
        try {
            String sql = "SELECT MACHINE_ID, DESCRIPTION, STATUS FROM " + plant + "_OWC_MACHINE_MST WHERE PROJECTNO = :projectNo " +
                    "AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            owcMachineDetailDTOList = new ArrayList<>();
            List<Object[]> rows = query.list();

            for(Object[] row: rows) {
                OWCMachineDetailDTO owcMachineDetailDTO = new OWCMachineDetailDTO();
                owcMachineDetailDTO.setMachineNo((String) row[0]);
                owcMachineDetailDTO.setMachineName((String) row[1]);
                owcMachineDetailDTO.setStatus((String) row[2]);

                owcMachineDetailDTOList.add(owcMachineDetailDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return owcMachineDetailDTOList;
    }

    @Override
    public Integer changeOWCMachineStatus(String plant, String projectNo, ChangeOWCMachineStatus changeOWCMachineStatus) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_OWC_MACHINE_MST SET STATUS = :status, UPAT = :upAt WHERE PROJECTNO = :projectNo AND MACHINE_ID = :machineId";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            Query query = session.createSQLQuery(sql);

            query.setParameter("status", changeOWCMachineStatus.getOwcMachineStatus().name());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("projectNo", projectNo);
            query.setParameter("machineId", changeOWCMachineStatus.getMachineId());

            result = query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return result;
    }

}
