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
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("VisitorsDashboardDao")
public class VisitorsDashboardDaoImpl implements VisitorsDashboardDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<VisitorsDTO> getVisitors(String plant, String visitorType, String fromDate, String toDate) {
        Session session = sessionFactory.openSession();
        List<VisitorsDTO> visitorsDTOList = new ArrayList<>();

        try {
            // Use current date as default if not provided
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            String currentDate = sdf.format(new Date() );

            if (fromDate == null || fromDate.trim().isEmpty()) {
                fromDate = currentDate;
            }
            if (toDate == null || toDate.trim().isEmpty()) {
                toDate = currentDate;
            }

            StringBuilder sql = new StringBuilder(
                    "SELECT TOTAL_VISITORS, VISITORS_TYPE, VISITED_TIME, OUTLET, CAMERA_NO, CONVERSION_RATE, EMOTION, EMOTIONIMGPATH " +
                            "FROM " + plant + "_VISITORS_DET WHERE PLANT = :plant"
            );

            // Add visitor type filter
            if (visitorType != null && !visitorType.trim().isEmpty()) {
                if (visitorType.equalsIgnoreCase("unknown")) {
                    sql.append(" AND LOWER(VISITORS_TYPE) NOT IN ('male','female','children')");
                } else {
                    sql.append(" AND LOWER(VISITORS_TYPE) = LOWER(:visitorType)");
                }
            }

            // Always add date filters (since defaults are guaranteed)
            sql.append(" AND CAST(VISITED_TIME AS date) BETWEEN CAST(:fromDate AS date) AND CAST(:toDate AS date)");
            sql.append(" ORDER BY VISITED_TIME DESC");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("plant", plant);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

            if (visitorType != null && !visitorType.trim().isEmpty() && !visitorType.equalsIgnoreCase("unknown")) {
                query.setParameter("visitorType", visitorType.toLowerCase());
            }

            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                VisitorsDTO visitorsDTO = new VisitorsDTO();
                visitorsDTO.setVisitorsCount(((BigInteger) row[0]).longValue());
                visitorsDTO.setVisitorsType((String) row[1]);
                visitorsDTO.setVisitedTime((Date) row[2]);
                visitorsDTO.setOutlet((String) row[3]);
                visitorsDTO.setCameraNo((String) row[4]);
                visitorsDTO.setConversionRate(((BigInteger) row[5]).longValue());
                visitorsDTO.setEmotion((String) row[6]);

                visitorsDTOList.add(visitorsDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return visitorsDTOList;
    }



    @Override
    public Integer saveVisitors(SaveVisitorsDTO saveVisitorsDTO) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + saveVisitorsDTO.getPlant() + "_VISITORS_DET (PLANT, TOTAL_VISITORS, VISITORS_TYPE, EMOTION, EMOTIONIMGPATH, VISITED_TIME, CONVERSION_RATE, OUTLET, CAMERA_NO, CRAT, CRBY, UPAT) " +
                    "VALUES (:plant, :totalVisitors, :visitorsType, :emotion, :emotionImgPath, :visitedTime, :conversionRate, :outlet, :cameraNo, :crAt, :crBy, :upAt)";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", saveVisitorsDTO.getPlant());
            query.setParameter("totalVisitors", saveVisitorsDTO.getVisitorsCount());
            query.setParameter("visitedTime", saveVisitorsDTO.getVisitedTime());
            query.setParameter("visitorsType", saveVisitorsDTO.getVisitorsType());
            query.setParameter("emotion", saveVisitorsDTO.getEmotion());
            query.setParameter("emotionImgPath", saveVisitorsDTO.getEmotionImgPath());
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
