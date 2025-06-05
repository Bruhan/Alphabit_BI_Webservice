package com.facility.management.usecases.wastage_movement.dao;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.WasteMovementDET;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.product_request.dto.PRCalendarResponseDTO;
import com.facility.management.usecases.product_request.enums.LNStatus;
import com.facility.management.usecases.wastage.dto.WastageCalendarResponseDTO;
import com.facility.management.usecases.wastage_movement.dto.*;
import com.facility.management.usecases.wastage_movement.enums.WastageType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
            hdrsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_HDR (PLANT, PROJECTNO, DATE, VEHICLEID, DRIVERNO, FINAL_DESTINATION, REMARKS, DC_NO, VEHICLE_TYPE, CRAT, CRBY) " +
                    "VALUES(:plant, :projectNo, :date, :vehicleId, :driverNo, :finalDestination, :remarks, :dcNo, :vehicleType, :crAt, :crBy); SELECT SCOPE_IDENTITY();";
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
            query.setParameter("dcNo", wasteMovementRequestDTO.getGatepassNo());
            query.setParameter("vehicleType", wasteMovementRequestDTO.getVehicleType());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            Integer hdrId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            for(WasteMovementDETDTO wasteMovementDETDTO: wasteMovementRequestDTO.getWasteMovementDETList()) {
                detsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_DET (PLANT, PROJECTNO, CUSTOMERID, CUSTOMERNAME, HDRID, DCNO ,DESTINATION, DESTINATION_TYPE, WASTAGE_TYPE, QTY, UOM, CRAT, CRBY) " +
                        "VALUES (:plant, :projectNo, :customerId, :customerName, :hdrId, :dcNo, :destination, :destinationType, :wastageType, :qty, :uom, :crAt, :crBy); SELECT SCOPE_IDENTITY();";
                session.beginTransaction();
                Query query1 = session.createSQLQuery(detsql);
                query1.setParameter("plant", plant);
                query1.setParameter("projectNo", wasteMovementRequestDTO.getProjectNo());
                query1.setParameter("customerId", wasteMovementDETDTO.getCustomerId());
                query1.setParameter("customerName", wasteMovementDETDTO.getCustomerName());
                query1.setParameter("hdrId", hdrId);
                query1.setParameter("dcNo", wasteMovementDETDTO.getDeliveryChallanNo());
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
    public Integer saveWasteMovementDET(String plant, String projectNo, Integer hdrId, WasteMovementDETDTO wasteMovementDETDTO) {
        Session session = sessionFactory.openSession();
        Integer detId;
        try {
            String detsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_DET (PLANT, PROJECTNO, CUSTOMERID, CUSTOMERNAME, HDRID, DESTINATION, DESTINATION_TYPE, WASTAGE_TYPE, QTY, UOM, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :customerId, :customerName, :hdrId, :destination, :destinationType, :wastageType, :qty, :uom, :crAt, :crBy); SELECT SCOPE_IDENTITY();";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query1 = session.createSQLQuery(detsql);
            query1.setParameter("plant", plant);
            query1.setParameter("projectNo", projectNo);
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

            detId = ((Number) query1.uniqueResult()).intValue();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return detId;
    }

    @Override
    public Integer saveWasteMovementInorganicProductDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String detInorganicsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_INORGANIC_PRODUCT (PLANT, PROJECTNO, DETID, HDRID, ITEM, QTY, UOM, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :detId, :hdrId, :item, :qty, :uom, :crAt, :crBy)";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query2 = session.createSQLQuery(detInorganicsql);
            query2.setParameter("plant", plant);
            query2.setParameter("projectNo", projectNo);
            query2.setParameter("detId", detId);
            query2.setParameter("hdrId", hdrId);
            query2.setParameter("item", wasteMovementInorganicProductDTO.getItem());
            query2.setParameter("qty", wasteMovementInorganicProductDTO.getQty());
            query2.setParameter("uom", wasteMovementInorganicProductDTO.getUom());
            query2.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query2.setParameter("crBy", null);

            result = query2.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        }  finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer saveWasteMovementOWCOutcomeDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String detOWCsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT (PLANT, PROJECTNO, DETID, HDRID, ITEM, QTY, UOM, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :detId, :hdrId, :item, :qty, :uom, :crAt, :crBy)";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query2 = session.createSQLQuery(detOWCsql);
            query2.setParameter("plant", plant);
            query2.setParameter("projectNo", projectNo);
            query2.setParameter("detId", detId);
            query2.setParameter("hdrId", hdrId);
            query2.setParameter("item", wasteMovementOWCOutcomeDTO.getItem());
            query2.setParameter("qty", wasteMovementOWCOutcomeDTO.getQty());
            query2.setParameter("uom", wasteMovementOWCOutcomeDTO.getUom());
            query2.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query2.setParameter("crBy", null);

            result = query2.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        }  finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer deleteWasteMovementInorganicProductDET(String plant, String projectNo, Integer hdrId, Integer detId) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "DELETE FROM " + plant + "_WASTE_MOVEMENT_INORGANIC_PRODUCT WHERE HDRID = :hdrId AND DETID = :detId AND PROJECTNO = :projectNo AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("hdrId", hdrId);
            query.setParameter("detId", detId);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            result = query.executeUpdate();
            session.getTransaction().commit();

        }  catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        }  finally {
            session.close();
        }

        return result;
    }

    @Override
    public Integer deleteWasteMovementOWCOutcomeDET(String plant, String projectNo, Integer hdrId, Integer detId) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "DELETE FROM " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT WHERE HDRID = :hdrId AND DETID = :detId AND PROJECTNO = :projectNo AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("hdrId", hdrId);
            query.setParameter("detId", detId);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            result = query.executeUpdate();
            session.getTransaction().commit();

        }  catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        }  finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo, String date) {
        Session session = sessionFactory.openSession();
        List<WasteMovementDTO> wasteMovementDTOList = null;
        try {
            String sql = "SELECT DATE, VEHICLEID, DRIVERNO, FINAL_DESTINATION, REMARKS, DC_NO, VEHICLE_TYPE, ID, PLANT,IS_GP_SIGNED,INSPECTING_PERSON_SIGN FROM " + plant + "_WASTE_MOVEMENT_HDR" +
                    "  WHERE PROJECTNO = :projectNo AND PLANT = :plant AND (DATE = :date OR :date IS NULL)";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            if (date != null) {
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                query.setParameter("date", sqlDate);
            } else {
                query.setParameter("date", null,  org.hibernate.type.DateType.INSTANCE);
            }

            List<Object[]> rows = query.list();

            wasteMovementDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                WasteMovementDTO wasteMovementDTO = new WasteMovementDTO();
                wasteMovementDTO.setDate(row[0] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[0]) : "");
                wasteMovementDTO.setVehicleNo((String) row[1]);
                wasteMovementDTO.setDriverNo((String) row[2]);
                wasteMovementDTO.setFinalDestination((String) row[3]);
                wasteMovementDTO.setRemarks((String) row[4]);
                wasteMovementDTO.setGatepassNo((String) row[5]);
                wasteMovementDTO.setVehicleType((String) row[6]);
                wasteMovementDTO.setId((Integer) row[7]);
                wasteMovementDTO.setPlant((String) row[8]);
                wasteMovementDTO.setIsGpSigned(row[9] != null ? ((Short) row[9]) : 0);
                wasteMovementDTO.setInspectingPersonSign((String) row[10]);

                wasteMovementDTOList.add(wasteMovementDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wasteMovementDTOList;
    }

    @Override
    public WasteMovementDTO getWastageMovementSummaryById(String plant, String projectNo, Integer id) {
        Session session = sessionFactory.openSession();
        WasteMovementDTO wasteMovementDTO = null;
        try {
            String sql = "SELECT DATE, VEHICLEID, DRIVERNO, FINAL_DESTINATION, REMARKS, DC_NO, VEHICLE_TYPE, ID FROM " + plant + "_WASTE_MOVEMENT_HDR" +
                    " WHERE PROJECTNO = :projectNo AND PLANT = :plant AND ID = :id";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("id", id);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                wasteMovementDTO = new WasteMovementDTO();
                wasteMovementDTO.setDate(row[0] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[0]) : "");
                wasteMovementDTO.setVehicleNo((String) row[1]);
                wasteMovementDTO.setDriverNo((String) row[2]);
                wasteMovementDTO.setFinalDestination((String) row[3]);
                wasteMovementDTO.setRemarks((String) row[4]);
                wasteMovementDTO.setGatepassNo((String) row[5]);
                wasteMovementDTO.setVehicleType((String) row[6]);
                wasteMovementDTO.setId((Integer) row[7]);
            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wasteMovementDTO;
    }

    @Override
    public List<WasteMovementDETOutDTO> getWasteMovementDET(String plant, String projectNo, Integer hdrId) {
        Session session = sessionFactory.openSession();
        List<WasteMovementDETOutDTO> wasteMovementDETList = null;
        try {
            String sql = "SELECT CUSTOMERID, CUSTOMERNAME, DESTINATION, DESTINATION_TYPE, WASTAGE_TYPE, QTY, UOM, ID, DCNO, IS_DC_SIGNED,AUTHORISED_SIGN FROM " + plant + "_WASTE_MOVEMENT_DET" +
                    "  WHERE PROJECTNO = :projectNo AND HDRID = :hdrId AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("hdrId", hdrId);

            List<Object[]> rows = query.list();

            wasteMovementDETList = new ArrayList<>();
            for(Object[] row: rows) {
                WasteMovementDETOutDTO wasteMovementDET = new WasteMovementDETOutDTO();
                wasteMovementDET.setCustomerId((String) row[0]);
                wasteMovementDET.setCustomerName((String) row[1]);
                wasteMovementDET.setDestination((String) row[2]);
                wasteMovementDET.setDestinationType((String) row[3]);
                wasteMovementDET.setWastageType((row[4] != null ? WastageType.valueOf((String) row[4]) : null));
                wasteMovementDET.setQty((double) row[5]);
                wasteMovementDET.setUom((String) row[6]);
                wasteMovementDET.setId((Integer) row[7]);
                wasteMovementDET.setDeliveryChallanNo((String) row[8]);
                wasteMovementDET.setIsDcSigned(row[9] != null ? ((Short) row[9]) : 0);
                wasteMovementDET.setAuthorizedSign((String) row[10]);

                wasteMovementDETList.add(wasteMovementDET);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return wasteMovementDETList;
    }

    @Override
    public List<WasteMovementInorganicProductOutDTO> getWasteMovementInorganicProducts(String plant, String projectNo, Integer hdrId, Integer detId) {
        Session session = sessionFactory.openSession();
        List<WasteMovementInorganicProductOutDTO> wasteMovementInorganicProductOutDTOList = null;
        try {
            String sql = "SELECT IT.ITEMDESC, WM.ITEM, WM.QTY, WM.UOM, WM.ID FROM " + plant + "_WASTE_MOVEMENT_INORGANIC_PRODUCT WM LEFT JOIN " + plant + "_ITEMMST IT " +
                    "ON IT.ITEM = WM.ITEM WHERE WM.PROJECTNO = :projectNo AND WM.PLANT = :plant AND WM.HDRID = :hdrId AND WM.DETID = :detId";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("hdrId", hdrId);
            query.setParameter("detId", detId);

            List<Object[]> rows = query.list();

            wasteMovementInorganicProductOutDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                WasteMovementInorganicProductOutDTO wasteMovementInorganicProductOutDTO = new WasteMovementInorganicProductOutDTO();
                wasteMovementInorganicProductOutDTO.setProductName((String) row[0]);
                wasteMovementInorganicProductOutDTO.setItem((String) row[1]);
                wasteMovementInorganicProductOutDTO.setQty((double) row[2]);
                wasteMovementInorganicProductOutDTO.setUom((String) row[3]);
                wasteMovementInorganicProductOutDTO.setId((Integer) row[4]);

                wasteMovementInorganicProductOutDTOList.add(wasteMovementInorganicProductOutDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wasteMovementInorganicProductOutDTOList;
    }

    @Override
    public List<WasteMovementOWCOutcomeOutDTO> getWasteMovementOWCOutcomeProducts(String plant, String projectNo, Integer hdrId, Integer detId) {
        Session session = sessionFactory.openSession();
        List<WasteMovementOWCOutcomeOutDTO> wasteMovementOWCOutcomeOutDTOList = null;
        try {
            String sql = "SELECT IT.ITEMDESC, WM.ITEM, WM.QTY, WM.UOM, WM.ID FROM " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT WM LEFT JOIN " + plant + "_ITEMMST IT " +
                    "ON IT.ITEM = WM.ITEM WHERE WM.PROJECTNO = :projectNo AND WM.PLANT = :plant AND WM.HDRID = :hdrId AND WM.DETID = :detId";

            Query query = session.createSQLQuery(sql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("hdrId", hdrId);
            query.setParameter("detId", detId);

            List<Object[]> rows = query.list();

            wasteMovementOWCOutcomeOutDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                WasteMovementOWCOutcomeOutDTO wasteMovementOWCOutcomeOutDTO = new WasteMovementOWCOutcomeOutDTO();
                wasteMovementOWCOutcomeOutDTO.setProductName((String) row[0]);
                wasteMovementOWCOutcomeOutDTO.setItem((String) row[1]);
                wasteMovementOWCOutcomeOutDTO.setQty((double) row[2]);
                wasteMovementOWCOutcomeOutDTO.setUom((String) row[3]);
                wasteMovementOWCOutcomeOutDTO.setId((Integer) row[4]);

                wasteMovementOWCOutcomeOutDTOList.add(wasteMovementOWCOutcomeOutDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wasteMovementOWCOutcomeOutDTOList;
    }

    @Override
    public Integer updateWasteMovement(String plant, Integer id, UpdateWasteMovementRequestDTO updateWasteMovementRequestDTO) {
        Session session = sessionFactory.openSession();
        String hdrsql = null;
        String detsql = null;
        String detInorganicsql = null;
        String detOWCsql = null;
        try {
           hdrsql = "UPDATE " + plant + "_WASTE_MOVEMENT_HDR SET DATE = :date, VEHICLEID = :vehicleId, DRIVERNO = :driverNo, FINAL_DESTINATION = :finalDestination, " +
                    "REMARKS = :remarks, DC_NO = :dcNo,IS_GP_SIGNED = :isGpSigned,INSPECTING_PERSON_SIGN = :inspectingPersonSign, VEHICLE_TYPE = :vehicleType, UPAT = :upAt, UPBY = :upBy OUTPUT INSERTED.ID WHERE ID = :hdrId AND PLANT = :plant";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(hdrsql);
            query.setParameter("plant", plant);
            query.setParameter("date", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(updateWasteMovementRequestDTO.getDate())));
            query.setParameter("vehicleId", updateWasteMovementRequestDTO.getVehicleNo());
            query.setParameter("driverNo", updateWasteMovementRequestDTO.getDriverNo());
            query.setParameter("finalDestination", updateWasteMovementRequestDTO.getDestination());
            query.setParameter("remarks", updateWasteMovementRequestDTO.getRemarks());
            query.setParameter("dcNo", updateWasteMovementRequestDTO.getGatepassNo());
            query.setParameter("vehicleType", updateWasteMovementRequestDTO.getVehicleType());
            query.setParameter("isGpSigned", updateWasteMovementRequestDTO.getIsGpSigned());
            query.setParameter("inspectingPersonSign", updateWasteMovementRequestDTO.getInspectingPersonSign());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("hdrId", updateWasteMovementRequestDTO.getId());

            Integer hdrId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            for(WasteMovementDETDTO wasteMovementDETDTO: updateWasteMovementRequestDTO.getWasteMovementDETList()) {

                detsql = "UPDATE " + plant + "_WASTE_MOVEMENT_DET SET CUSTOMERID = :customerId, CUSTOMERNAME = :customerName, DESTINATION = :destination, DESTINATION_TYPE = :destinationType, " +
                        "WASTAGE_TYPE = :wastageType,DCNO = :dcNo,IS_DC_SIGNED = :isDcSigned, AUTHORISED_SIGN = :authSign, QTY = :qty, UOM = :uom, UPAT = :upAt, UPBY = :upBy OUTPUT INSERTED.ID WHERE HDRID = :hdrId AND ID = :id AND PLANT = :plant";
                session.beginTransaction();
                Query query1 = session.createSQLQuery(detsql);
                query1.setParameter("plant", plant);
                query1.setParameter("customerId", wasteMovementDETDTO.getCustomerId());
                query1.setParameter("customerName", wasteMovementDETDTO.getCustomerName());
                query1.setParameter("hdrId", hdrId);
                query1.setParameter("dcNo", wasteMovementDETDTO.getDeliveryChallanNo());
                query1.setParameter("isDcSigned", wasteMovementDETDTO.getIsDcSigned());
                query1.setParameter("authSign", wasteMovementDETDTO.getAuthorizedSign());
                query1.setParameter("id", wasteMovementDETDTO.getId());
                query1.setParameter("destination", wasteMovementDETDTO.getDestination());
                query1.setParameter("destinationType", wasteMovementDETDTO.getDestinationType());
                query1.setParameter("wastageType", wasteMovementDETDTO.getWastageType().toString());
                query1.setParameter("qty", wasteMovementDETDTO.getQty());
                query1.setParameter("uom", wasteMovementDETDTO.getUom());
                query1.setParameter("upAt", dateTimeCalc.getTodayDateTime());
                query1.setParameter("upBy", null);

                Integer detId = ((Number) query1.uniqueResult()).intValue();
                session.getTransaction().commit();

                if(wasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                    for(WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO: wasteMovementDETDTO.getWasteMovementInorganicProductList()) {

                        detInorganicsql = "UPDATE " + plant + "_WASTE_MOVEMENT_INORGANIC_PRODUCT SET ITEM = :item, QTY = :qty, UOM = :uom, UPAT = :upAt, UPBY = :upBy " +
                                "WHERE ID = :id AND DETID = :detId AND HDRID = :hdrId AND PLANT = :plant";
                        session.beginTransaction();
                        Query query2 = session.createSQLQuery(detInorganicsql);
                        query2.setParameter("plant", plant);
                        query2.setParameter("id", wasteMovementInorganicProductDTO.getId());
                        query2.setParameter("detId", detId);
                        query2.setParameter("hdrId", hdrId);
                        query2.setParameter("item", wasteMovementInorganicProductDTO.getItem());
                        query2.setParameter("qty", wasteMovementInorganicProductDTO.getQty());
                        query2.setParameter("uom", wasteMovementInorganicProductDTO.getUom());
                        query2.setParameter("upAt", dateTimeCalc.getTodayDateTime());
                        query2.setParameter("upBy", null);

                        query2.executeUpdate();
                        session.getTransaction().commit();
                    }
                }

                if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                    for(WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO: wasteMovementDETDTO.getWasteMovementOWCOutcomeList()) {
//                        detOWCsql = "INSERT INTO " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT (PLANT, PROJECTNO, DETID, HDRID, ITEM, QTY, UOM, CRAT, CRBY) " +
//                                "VALUES (:plant, :projectNo, :detId, :hdrId, :item, :qty, :uom, :crAt, :crBy)";
                        detOWCsql = "UPDATE " + plant + "_WASTE_MOVEMENT_OWC_PRODUCT SET ITEM = :item, QTY = :qty, UOM = :uom, UPAT = :upAt, UPBY = :upBy " +
                                "WHERE ID = :id AND DETID = :detId AND HDRID = :hdrId AND PLANT = :plant";
                        session.beginTransaction();
                        Query query2 = session.createSQLQuery(detOWCsql);
                        query2.setParameter("plant", plant);
                        query2.setParameter("id", wasteMovementOWCOutcomeDTO.getId());
                        query2.setParameter("detId", detId);
                        query2.setParameter("hdrId", hdrId);
                        query2.setParameter("item", wasteMovementOWCOutcomeDTO.getItem());
                        query2.setParameter("qty", wasteMovementOWCOutcomeDTO.getQty());
                        query2.setParameter("uom", wasteMovementOWCOutcomeDTO.getUom());
                        query2.setParameter("upAt", dateTimeCalc.getTodayDateTime());
                        query2.setParameter("upBy", null);

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
    public List<TransportCalendarResponseDTO> hasTransport(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        List<TransportCalendarResponseDTO> result = new ArrayList<>();
        try {

            for(String date : calendarRequestDTO.getDateList()) {
                String sql = "SELECT COUNT(*) AS RESULT FROM " + plant + "_WASTE_MOVEMENT_HDR " +
                        "WHERE DATE = :date AND PROJECTNO = :projectNo";

                Query query = session.createSQLQuery(sql);
                query.setParameter("projectNo", projectNo);

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = inputFormat.parse(date);
                query.setParameter("date", parsedDate);

                Number row = (Number) query.uniqueResult();

                TransportCalendarResponseDTO transportCalendarResponseDTO = new TransportCalendarResponseDTO();
                transportCalendarResponseDTO.setDate(date);
                transportCalendarResponseDTO.setHasTransport(row.intValue());

                result.add(transportCalendarResponseDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return result;
    }


    @Override
    public Integer updateGatePassSign(String plant, Integer id, String gatePassSignpath) {
        Session session = sessionFactory.openSession();
        String hdrsql = null;
        try {
            hdrsql = "UPDATE " + plant + "_WASTE_MOVEMENT_HDR SET IS_GP_SIGNED = :isGpSigned,INSPECTING_PERSON_SIGN = :inspectingPersonSign, " +
                    "UPAT = :upAt, UPBY = :upBy OUTPUT INSERTED.ID WHERE ID = :hdrId AND PLANT = :plant";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(hdrsql);
            query.setParameter("plant", plant);
            query.setParameter("isGpSigned", 1);
            query.setParameter("inspectingPersonSign", gatePassSignpath);
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("hdrId", id);

            Integer hdrId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            } catch(Exception ex) {
                session.getTransaction().rollback();
                throw new RuntimeException(ex);
            } finally {
                session.close();
            }
            return 1;
        }

        @Override
        public Integer updateDCSign(String plant, Integer id, String customerId,String dcSignPath) {
            Session session = sessionFactory.openSession();
            String detsql = null;
            try {
                    DateTimeCalc dateTimeCalc = new DateTimeCalc();
                    detsql = "UPDATE " + plant + "_WASTE_MOVEMENT_DET SET IS_DC_SIGNED = :isDcSigned, AUTHORISED_SIGN = :authSign, " +
                            "UPAT = :upAt, UPBY = :upBy WHERE HDRID = :hdrId AND CUSTOMERID = :customerId AND PLANT = :plant";
                    session.beginTransaction();
                    Query query1 = session.createSQLQuery(detsql);
                    query1.setParameter("plant", plant);
                    query1.setParameter("customerId", customerId);
                    query1.setParameter("hdrId", id);
                    query1.setParameter("isDcSigned", 1);
                    query1.setParameter("authSign", dcSignPath);

                    query1.setParameter("upAt", dateTimeCalc.getTodayDateTime());
                    query1.setParameter("upBy", null);

                    Integer detId = ((Number) query1.uniqueResult()).intValue();
                    session.getTransaction().commit();

                } catch(Exception ex) {
                    session.getTransaction().rollback();
                    throw new RuntimeException(ex);
                } finally {
                    session.close();
                }
                return 1;
            }
}
