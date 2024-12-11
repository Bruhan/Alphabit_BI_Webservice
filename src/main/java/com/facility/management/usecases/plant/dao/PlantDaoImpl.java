package com.facility.management.usecases.plant.dao;

import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import com.facility.management.usecases.plant.dto.PlantDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository("PlantDao")
public class PlantDaoImpl implements PlantDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public PlantDTO getPlantByPlantId(String plant) {
        Session session = sessionFactory.openSession();
        PlantDTO plantDTO = null;
        try {
            String sql = "SELECT TOP (1) PLANT, ISAUTO_SUPERVISOR_LOGIN, ISAUTO_SUPERVISOR_LOGOUT FROM PLNTMST WHERE PLANT = :plant";
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            Object[] row = (Object[]) query.uniqueResult();
            plantDTO = new PlantDTO();

            plantDTO.setPlant((String) row[0]);
            plantDTO.setIsAutoSupervisorLogin(row[1] != null ? (Byte) row[1] : 0);
            plantDTO.setIsAutoSupervisorLogout(row[2] != null ? (Byte) row[2] : 0);


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return plantDTO;
    }
}
