package com.owner.process.usecases.wastage_movement.dao;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.usecases.wastage_movement.dto.*;
import com.owner.process.usecases.wastage_movement.enums.WastageType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository("WasteMovementDao")
public class WasteMovementDaoImpl implements WasteMovementDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Integer saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO) {
        Session session = sessionFactory.openSession();
        String hdrsql = null;
        String detsql = null;
        String detInorganicsql = null;
        String detOWCsql = null;
        try {
            hdrsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_HDR (PLANT, PROJECTNO, DATE, VEHICLEID, DRIVERNO, FINAL_DESTINATION, REMARKS, CRAT, CRBY) " +
                    "VALUES(:plant, :projectNo, :date, :vehicleId, :driverNo, :finalDestination, :remarks, :crAt, :crBy); SELECT SCOPE_IDENTITY();";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(hdrsql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", wasteMovementRequestDTO.getProjectNo());
            query.setParameter("date", dateTimeCalc.getTodayDMYDate());
            query.setParameter("vehicleId", wasteMovementRequestDTO.getVehicleNo());
            query.setParameter("driverNo", wasteMovementRequestDTO.getDriverNo());
            query.setParameter("finalDestination", wasteMovementRequestDTO.getDestination());
            query.setParameter("remarks", wasteMovementRequestDTO.getRemarks());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            Integer hdrId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            for(WasteMovementDETDTO wasteMovementDETDTO: wasteMovementRequestDTO.getWasteMovementDETList()) {
                detsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_DET (PLANT, PROJECTNO, CUSTOMERID, CUSTOMERNAME, HDRID, DESTINATION, DESTINATION_TYPE, WASTAGE_TYPE, QTY, UOM, CRAT, CRBY) " +
                        "VALUES (:plant, :projectNo, :customerId, :customerName, :hdrId, :destination, :destinationType, :wastageType, :qty, :uom, :crAt, :crBy); SELECT SCOPE_IDENTITY();";
                session.beginTransaction();
                Query query1 = session.createSQLQuery(detsql);
                query1.setParameter("plant", plant);
                query1.setParameter("projectNo", wasteMovementRequestDTO.getProjectNo());
                query1.setParameter("customerId", wasteMovementDETDTO.getCustomerId());
                query1.setParameter("customerName", wasteMovementDETDTO.getCustomerName());
                query1.setParameter("hdrId", hdrId);
                query1.setParameter("destination", wasteMovementDETDTO.getDestination());
                query1.setParameter("destinationType", wasteMovementDETDTO.getDestinationType());
                query1.setParameter("wastageType", wasteMovementDETDTO.getWastageType().toString());
                query1.setParameter("qty", wasteMovementDETDTO.getQty());
                query1.setParameter("uom", wasteMovementDETDTO.getUom());
                query1.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                query1.setParameter("crBy", null);

                Integer detId = ((Number) query1.uniqueResult()).intValue();
                session.getTransaction().commit();

                if(wasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                    for(WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO: wasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                        detInorganicsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_INORGANIC_PRODUCT (PLANT, PROJECTNO, DETID, HDRID, ITEM, QTY, UOM, CRAT, CRBY) " +
                                "VALUES (:plant, :projectNo, :detId, :hdrId, :item, :qty, :uom, :crAt, :crBy)";
                        session.beginTransaction();
                        Query query2 = session.createSQLQuery(detInorganicsql);
                        query2.setParameter("plant", plant);
                        query2.setParameter("projectNo", wasteMovementRequestDTO.getProjectNo());
                        query2.setParameter("detId", detId);
                        query2.setParameter("hdrId", hdrId);
                        query2.setParameter("item", wasteMovementInorganicProductDTO.getItem());
                        query2.setParameter("qty", wasteMovementInorganicProductDTO.getQty());
                        query2.setParameter("uom", wasteMovementInorganicProductDTO.getUom());
                        query2.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                        query2.setParameter("crBy", null);

                        query2.executeUpdate();
                        session.getTransaction().commit();
                    }
                }

                if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                    for(WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO: wasteMovementDETDTO.getWasteMovementOWCOutcomeList()) {
                        detOWCsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT (PLANT, PROJECTNO, DETID, HDRID, ITEM, QTY, UOM, CRAT, CRBY) " +
                                "VALUES (:plant, :projectNo, :detId, :hdrId, :item, :qty, :uom, :crAt, :crBy)";
                        session.beginTransaction();
                        Query query2 = session.createSQLQuery(detOWCsql);
                        query2.setParameter("plant", plant);
                        query2.setParameter("projectNo", wasteMovementRequestDTO.getProjectNo());
                        query2.setParameter("detId", detId);
                        query2.setParameter("hdrId", hdrId);
                        query2.setParameter("item", wasteMovementOWCOutcomeDTO.getItem());
                        query2.setParameter("qty", wasteMovementOWCOutcomeDTO.getQty());
                        query2.setParameter("uom", wasteMovementOWCOutcomeDTO.getUom());
                        query2.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                        query2.setParameter("crBy", null);

                        query2.executeUpdate();
                        session.getTransaction().commit();
                    }
                }
            }

        } catch(Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return 1;
    }

    @Override
    public List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<WasteMovementDTO> wasteMovementDTOList = null;
        try {
            String sql = "SELECT DATE, VEHICLEID, DRIVERNO, FINAL_DESTINATION FROM " + plant + "_WASTE_MOVEMENT_HDR" +
                    "  WHERE PROJECTNO = :projectNo AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();

            wasteMovementDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                WasteMovementDTO wasteMovementDTO = new WasteMovementDTO();
                wasteMovementDTO.setDate(row[0] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[0]) : "");
                wasteMovementDTO.setVehicleNo((String) row[1]);
                wasteMovementDTO.setDriverNo((String) row[2]);
                wasteMovementDTO.setFinalDestination((String) row[3]);

                wasteMovementDTOList.add(wasteMovementDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wasteMovementDTOList;
    }
}
