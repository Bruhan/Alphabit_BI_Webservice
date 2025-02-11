package com.facility.management.usecases.employee_master.dao;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("EmployeeMasterDao")
public class EmployeeMasterDaoImpl implements EmployeeMasterDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<WorkerDTO> getAllWorkers(String plant) {
        Session session = sessionFactory.openSession();
        List<WorkerDTO> workerDTOList = null;
        String sql = null;
        try {
            sql = "SELECT PLANT, ID, EMPNO, FNAME, LNAME FROM " + plant + "_EMP_MST WHERE PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            List<Object[]> rows = query.list();
            workerDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                WorkerDTO workerDTO = new WorkerDTO();
                workerDTO.setPlant((String) row[0]);
                workerDTO.setId((int) row[1]);
                workerDTO.setEmpNo((String) row[2]);
                workerDTO.setEmpName((String) row[3] + " " + (String) row[4]);

                workerDTOList.add(workerDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return workerDTOList;
    }

    @Override
    public WorkerDTO getWorkerByEmpNo(String plant, String empNo) {
        Session session = sessionFactory.openSession();
        WorkerDTO workerDTO = null;
        String sql = null;
        try {
            sql = "SELECT TOP (1) PLANT, ID, EMPNO, FNAME, LNAME FROM " + plant + "_EMP_MST WHERE PLANT = :plant AND EMPNO = :empNo";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("empNo", empNo);
            Object[] row = (Object[]) query.uniqueResult();
            workerDTO = new WorkerDTO();

            workerDTO.setPlant((String) row[0]);
            workerDTO.setId((int) row[1]);
            workerDTO.setEmpNo((String) row[2]);
            workerDTO.setEmpName((String) row[3] + " " + (String) row[4]);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return workerDTO;
    }

    @Override
    public List<WorkerDTO> getWorkersByProjectNo(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<WorkerDTO> workerDTOList = null;
        String sql = null;
        try {
            sql = "SELECT PLANT, ID, EMPCODE, EMPNAME FROM " + plant + "_PROJECT_WORKERLIST WHERE PLANT = :plant AND PROJECTNO = :projectNo AND STATUS = :status";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);
            query.setParameter("status", 1);
            List<Object[]> rows = query.list();
            workerDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                WorkerDTO workerDTO = new WorkerDTO();
                workerDTO.setPlant((String) row[0]);
                workerDTO.setId((int) row[1]);
                workerDTO.setEmpNo((String) row[2]);
                workerDTO.setEmpName((String) row[3]);

                workerDTOList.add(workerDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return workerDTOList;
    }


}
