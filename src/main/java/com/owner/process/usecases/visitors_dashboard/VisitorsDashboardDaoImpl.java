package com.owner.process.usecases.visitors_dashboard;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.persistence.models.PosSalesReport;
import com.owner.process.usecases.visitors_dashboard.dto.SaveVisitorsDTO;
import com.owner.process.usecases.visitors_dashboard.dto.VisitorsDTO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository("VisitorsDashboardDao")
public class VisitorsDashboardDaoImpl implements VisitorsDashboardDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<VisitorsDTO> getVisitors(String plant) {
        Session session = sessionFactory.openSession();
        List<VisitorsDTO> visitorsDTOList = new ArrayList<>();
        try {
            String sql = "SELECT TOTAL_VISITORS, VISITORS_TYPE, VISITED_TIME, OUTLET, CAMERA_NO, CONVERSION_RATE FROM " + plant + "_VISITORS_DET WHERE PLANT = :plant";
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                VisitorsDTO visitorsDTO = new VisitorsDTO();
                visitorsDTO.setVisitorsCount(((BigInteger) row[0]).longValue());
                visitorsDTO.setVisitorsType((String) row[1]);
                visitorsDTO.setVisitedTime((Date) row[2]);
                visitorsDTO.setOutlet((String) row[3]);
                visitorsDTO.setCameraNo((String) row[4]);
                visitorsDTO.setConversionRate(((BigInteger) row[5]).longValue());

                visitorsDTOList.add(visitorsDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return visitorsDTOList;
    }

    @Override
    public Integer saveVisitors(SaveVisitorsDTO saveVisitorsDTO) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + saveVisitorsDTO.getPlant() + "_VISITORS_DET (PLANT, TOTAL_VISITORS, VISITORS_TYPE, CONVERSION_RATE, OUTLET, CAMERA_NO, CRAT, CRBY, UPAT) " +
                    "VALUES (:plant, :totalVisitors, :visitorsType, :conversionRate, :outlet, :cameraNo, :crAt, :crBy, :upAt)";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", saveVisitorsDTO.getPlant());
            query.setParameter("totalVisitors", saveVisitorsDTO.getVisitorsCount());
            query.setParameter("visitorsType", saveVisitorsDTO.getVisitorsType());
            query.setParameter("conversionRate", saveVisitorsDTO.getConversionRate());
            query.setParameter("outlet", saveVisitorsDTO.getOutlet());
            query.setParameter("cameraNo", saveVisitorsDTO.getCameraNo());
            query.setParameter("crAt", new Date());
            query.setParameter("crBy", saveVisitorsDTO.getPlant());
            query.setParameter("upAt", new Date());

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
