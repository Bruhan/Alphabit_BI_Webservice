package com.facility.management.usecases.project.dao;

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
    public List<ProjectDTO> getAllOpenProjects(String plant, String executiveNo) {
        Session session = sessionFactory.openSession();
        List<ProjectDTO> projectDTOList = null;
        try {
            String sql = "SELECT prj.PROJECT, prj.PROJECT_NAME, prj.PROJECT_DATE, prj.PROJECT_STATUS, prj.SUPERVISOR, " +
                    "CONCAT(emp.FNAME, emp.LNAME), prj.LOC FROM " + plant + "_FINRECYCLEPROJECT prj LEFT JOIN " + plant +
                    "_EMP_MST emp ON prj.SUPERVISOR = emp.EMPNO WHERE prj.PLANT = :plant AND prj.PROJECT_STATUS = :projectStatus AND SAFETYSUPERVISOR = :executiveNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectStatus", "Open");
            query.setParameter("executiveNo", executiveNo);

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

                projectDTOList.add(projectDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return projectDTOList;
    }
}
