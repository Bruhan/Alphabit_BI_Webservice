package com.facility.management.usecases.project.dao;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.products.dto.ProductDTO;
import com.facility.management.usecases.project.dto.ProjectDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("ProjectDao")
public class ProjectDaoImpl implements ProjectDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<ProjectDTO> getAllOpenExecutiveProjects(String plant, String executiveNo, String siteType) {
        Session session = sessionFactory.openSession();
        List<ProjectDTO> projectDTOList = null;
        try {
            String sql = "SELECT prj.PROJECT, prj.PROJECT_NAME, prj.PROJECT_DATE, prj.PROJECT_STATUS, prj.SUPERVISOR, " +
                    "CONCAT(emp.FNAME, emp.LNAME), prj.LOC, prj.PROJECT_SITETYPE FROM " + plant + "_FINRECYCLEPROJECT prj LEFT JOIN " + plant +
                    "_EMP_MST emp ON prj.SUPERVISOR = emp.EMPNO WHERE prj.PLANT = :plant AND prj.PROJECT_STATUS = :projectStatus AND SAFETYSUPERVISOR = :executiveNo" +
                    " AND (PROJECT_SITETYPE = :type OR :type IS NULL)";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectStatus", "Open");
            query.setParameter("executiveNo", executiveNo);
            query.setParameter("type", siteType);

            List<Object[]> rows = query.list();

            projectDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProjectCode((String) row[0]);
                projectDTO.setProjectName((String) row[1]);
                projectDTO.setProjectDate((String) row[2]);
                projectDTO.setProjectStatus((String) row[3]);
                projectDTO.setSupervisorCode((String) row[4]);
                projectDTO.setSupervisorName((String) row[5]);
                projectDTO.setLocation((String) row[6]);
                projectDTO.setProjectType((String) row[7]);

                projectDTOList.add(projectDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return projectDTOList;
    }

    @Override
    public List<ProjectDTO> getAllOpenSupervisorProjects(String plant, String supervisorNo, String siteType) {
        Session session = sessionFactory.openSession();
        List<ProjectDTO> projectDTOList = null;
        List<Integer> projectHdrIdList;
        try {
            String sql_1 = "SELECT prj.PROJECT, prj.PROJECT_NAME, prj.PROJECT_DATE, prj.PROJECT_STATUS, prj.SUPERVISOR, " +
                    "CONCAT(emp.FNAME, emp.LNAME), prj.LOC, prj.PROJECT_SITETYPE FROM " + plant + "_FINRECYCLEPROJECT prj LEFT JOIN " + plant +
                    "_EMP_MST emp ON prj.SUPERVISOR = emp.EMPNO WHERE prj.PLANT = :plant AND prj.PROJECT_STATUS = :projectStatus AND SUPERVISOR = :supervisorNo" +
                    " AND (PROJECT_SITETYPE = :type OR :type IS NULL)";

            Query query_1 = session.createSQLQuery(sql_1);

            query_1.setParameter("plant", plant);
            query_1.setParameter("projectStatus", "Open");
            query_1.setParameter("supervisorNo", supervisorNo);
            query_1.setParameter("type", siteType);

            List<Object[]> rows_1 = query_1.list();

            projectDTOList = new ArrayList<>();
            for(Object[] row: rows_1) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProjectCode((String) row[0]);
                projectDTO.setProjectName((String) row[1]);
                projectDTO.setProjectDate((String) row[2]);
                projectDTO.setProjectStatus((String) row[3]);
                projectDTO.setSupervisorCode((String) row[4]);
                projectDTO.setSupervisorName((String) row[5]);
                projectDTO.setLocation((String) row[6]);
                projectDTO.setProjectType((String) row[7]);

                projectDTOList.add(projectDTO);
            }

            String sql_2 = "SELECT PROJECTHDRID FROM " + plant + "_FINPROJECT_MULTISUPERVISOR WHERE PLANT = :plant " +
                    "AND SUPERVISOR = :supervisor";

            Query query_2 = session.createSQLQuery(sql_2);

            query_2.setParameter("supervisor", supervisorNo);
            query_2.setParameter("plant", plant);

            List<Integer> rows_2 = query_2.list();
            projectHdrIdList = new ArrayList<>();

            for(Integer row: rows_2) {
                projectHdrIdList.add(row);
            }

            String sql_3 = "SELECT prj.PROJECT, prj.PROJECT_NAME, prj.PROJECT_DATE, prj.PROJECT_STATUS, prj.SUPERVISOR," +
                    " CONCAT(emp.FNAME, emp.LNAME), prj.LOC, prj.PROJECT_SITETYPE FROM " + plant + "_FINRECYCLEPROJECT prj LEFT JOIN " + plant +
                    "_EMP_MST emp ON prj.SUPERVISOR = emp.EMPNO WHERE prj.ID IN :projectHdrIdList AND prj.PROJECT_STATUS = :projectStatus " +
                    "AND prj.PLANT = :plant AND (PROJECT_SITETYPE = :type OR :type IS NULL)";

            Query query_3 = session.createSQLQuery(sql_3);

            query_3.setParameter("projectHdrIdList", projectHdrIdList);
            query_3.setParameter("projectStatus", "Open");
            query_3.setParameter("plant", plant);
            query_3.setParameter("type", siteType);

            List<Object[]> rows_3 = query_3.list();

            for(Object[] row: rows_3) {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setProjectCode((String) row[0]);
                projectDTO.setProjectName((String) row[1]);
                projectDTO.setProjectDate((String) row[2]);
                projectDTO.setProjectStatus((String) row[3]);
                projectDTO.setSupervisorCode((String) row[4]);
                projectDTO.setSupervisorName((String) row[5]);
                projectDTO.setLocation((String) row[6]);
                projectDTO.setProjectType((String) row[7]);

                projectDTOList.add(projectDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return projectDTOList;
    }

    @Override
    public String getExecutiveByProjectNo(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        String executiveNo = null;
        try {
            String sql = "SELECT TOP 1 SAFETYSUPERVISOR FROM " + plant + "_FINRECYCLEPROJECT WHERE PLANT = :plant " +
                    " AND PROJECT = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            executiveNo = (String) query.uniqueResult();

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return executiveNo;
    }
}
