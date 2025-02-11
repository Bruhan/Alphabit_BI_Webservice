package com.facility.management.usecases.product_request.dao;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.ProductRequestReceiveDET;
import com.facility.management.persistence.models.ProductRequestReceiveHDR;
import com.facility.management.usecases.product_request.dto.*;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.LNStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("ProductRequestDao")
public class ProductRequestDaoImpl implements ProductRequestDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<ProductRequestHdrDTO> getProductRequestByCriteria(String plant, String projectNo, String requestorId, RequestStatus requestStatus, ApprovalStatus approvalStatus) {

        Session session = sessionFactory.openSession();
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        String hdrSql = null;
        String detSql = null;

        try {
            hdrSql = "SELECT PLANT AS plant, ID AS projectRequestHdrId, PROJECTID AS projectId, PROJECTNO AS projectNo, REQUESTEDDATE AS requestedDate, REQUESTOR_ID AS requesterId, \n" +
                    "  REQUESTSTATUS AS requestStatus, REQUESTER_REMARKS AS requesterRemarks, APPROVER_CODE AS approverCode, APPROVAL_DATE AS approvalDate, APPROVAL_STATUS AS approvalStatus, APPROVER_REMARKS AS approverRemarks\n" +
                    "  FROM " + plant + "_PROJECTSTOCKREQUESTHDR WHERE (PROJECTNO = :projectNo OR :projectNo IS NULL) AND (REQUESTOR_ID = :requestorId OR :requestorId IS NULL) AND (REQUESTSTATUS = :requestStatus OR :requestStatus IS NULL) AND " +
                    "  (APPROVAL_STATUS = :approvalStatus OR :approvalStatus IS NULL) AND PLANT = :plant";

            Query query = session.createSQLQuery(hdrSql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("requestStatus", requestStatus != null ? requestStatus.name() : null);
            query.setParameter("approvalStatus", approvalStatus != null ? approvalStatus.name() : null);
            query.setParameter("requestorId", requestorId);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            productRequestHdrDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProductRequestHdrDTO productRequestHdrDTO = new ProductRequestHdrDTO();

                productRequestHdrDTO.setPlant((String) row[0]);
                productRequestHdrDTO.setProductRequestHdrId((int) row[1]);
                productRequestHdrDTO.setProjectId((int) row[2]);
                productRequestHdrDTO.setProjectNo((String) row[3]);
                productRequestHdrDTO.setRequestedDate((String) row[4]);
                productRequestHdrDTO.setRequesterId((String) row[5]);
                productRequestHdrDTO.setRequestStatus(row[6] != null ? RequestStatus.valueOf((String) row[6]) : null);
                productRequestHdrDTO.setRequesterRemarks((String) row[7]);
                productRequestHdrDTO.setApproverCode((String) row[8]);
                productRequestHdrDTO.setApprovalDate((String) row[9]);
                productRequestHdrDTO.setApprovalStatus(row[10] != null ? ApprovalStatus.valueOf((String) row[10]) : null);
                productRequestHdrDTO.setApproverRemarks((String) row[11]);

                productRequestHdrDTOList.add(productRequestHdrDTO);
            }

            for(ProductRequestHdrDTO productRequestHdrDTO : productRequestHdrDTOList) {
                detSql = "  SELECT pr.PLANT AS plant, pr.ID AS productRequestDetId, pr.ITEM AS item, i.ITEMDESC AS productName, (SELECT string_agg(ITEMDETAILDESC, '. ') FROM "+ plant +"_ITEMDET_DESC idd " +
                        "WHERE idd.ITEM = i.item) AS productDescription, pr.LNSTATUS AS lnStatus, pr.LNNO AS lnNo, pr.UOM AS uom, pr.QTY AS quantity, pr.PROCESSEDQTY AS processedQty " +
                        ", pr.BALANCEQTY AS balanceQty, pr.RECEIVED_QTY, pr.NONRECEIVED_QTY FROM " + plant + "_PROJECTSTOCKREQUESTDET pr LEFT JOIN " + plant + "_ITEMMST i ON pr.ITEM = i.ITEM WHERE pr.HDRID = :hdrId AND pr.PLANT = :plant";

                Query query1 = session.createSQLQuery(detSql);
                query1.setParameter("hdrId", productRequestHdrDTO.getProductRequestHdrId());
                query1.setParameter("plant", plant);

                List<Object[]> rows1 = query1.list();
                List<ProductRequestDetDTO> productRequestDetDTOList = new ArrayList<>();
                for(Object[] row1: rows1) {
                    ProductRequestDetDTO productRequestDetDTO = new ProductRequestDetDTO();

                    productRequestDetDTO.setPlant((String) row1[0]);
                    productRequestDetDTO.setProductRequestDetId((int) row1[1]);
                    productRequestDetDTO.setItem((String) row1[2]);
                    productRequestDetDTO.setProductName((String) row1[3]);
                    productRequestDetDTO.setProductDescription((String) row1[4]);
                    productRequestDetDTO.setLnStatus(row1[5] != null ? LNStatus.valueOf((String) row1[5]) : null);
                    productRequestDetDTO.setLnNo((int) row1[6]);
                    productRequestDetDTO.setUom((String) row1[7]);
                    productRequestDetDTO.setQuantity((double) row1[8]);
                    productRequestDetDTO.setProcessedQty((double) row1[9]);
                    productRequestDetDTO.setBalanceQty((double) row1[10]);
                    productRequestDetDTO.setReceivedQty((double) row1[11]);
                    productRequestDetDTO.setNonReceivedQty((double) row1[12]);

                    productRequestDetDTOList.add(productRequestDetDTO);
                }

                productRequestHdrDTO.setProductRequestDetDTOList(productRequestDetDTOList);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return productRequestHdrDTOList;
    }

    @Override
    public ProductRequestHdrDTO getProductRequestById(String plant, int requestId) {
        Session session = sessionFactory.openSession();
        ProductRequestHdrDTO productRequestHdrDTO = null;
        String hdrSql = null;
        String detSql = null;
        try {
            hdrSql =  "SELECT PLANT AS plant, ID AS projectRequestHdrId, PROJECTID AS projectId, PROJECTNO AS projectNo, REQUESTEDDATE AS requestedDate, REQUESTOR_ID AS requesterId, " +
                    "  REQUESTSTATUS AS requestStatus, REQUESTER_REMARKS AS requesterRemarks, APPROVER_CODE AS approverCode, APPROVAL_DATE AS approvalDate, APPROVAL_STATUS AS approvalStatus, APPROVER_REMARKS AS approverRemarks" +
                    "  FROM " + plant + "_PROJECTSTOCKREQUESTHDR WHERE ID = :hdrId AND PLANT = :plant";
            Query query = session.createSQLQuery(hdrSql);
            query.setParameter("hdrId", requestId);
            query.setParameter("plant", plant);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                productRequestHdrDTO = new ProductRequestHdrDTO();
                productRequestHdrDTO.setPlant((String) row[0]);
                productRequestHdrDTO.setProductRequestHdrId((int) row[1]);
                productRequestHdrDTO.setProjectId((int) row[2]);
                productRequestHdrDTO.setProjectNo((String) row[3]);
                productRequestHdrDTO.setRequestedDate((String) row[4]);
                productRequestHdrDTO.setRequesterId((String) row[5]);
                productRequestHdrDTO.setRequestStatus((RequestStatus) row[6]);
                productRequestHdrDTO.setRequesterRemarks((String) row[7]);
                productRequestHdrDTO.setApproverCode((String) row[8]);
                productRequestHdrDTO.setApprovalDate((String) row[9]);
                productRequestHdrDTO.setApprovalStatus((ApprovalStatus) row[10]);
                productRequestHdrDTO.setApproverRemarks((String) row[11]);

                detSql = "  SELECT pr.PLANT AS plant, pr.ID AS productRequestDetId, pr.ITEM AS ITEM, i.ITEMDESC AS productName, (SELECT string_agg(ITEMDETAILDESC, '. ') FROM "+ plant +"_ITEMDET_DESC idd " +
                        "WHERE idd.ITEM = i.item) AS productDescription, pr.LNSTATUS AS lnStatus, pr.LNNO AS lnNo, pr.UOM AS uom, pr.QTY AS quantity, pr.PROCESSEDQTY AS processedQty " +
                        ", pr.BALANCEQTY AS balanceQty FROM " + plant + "_PROJECTSTOCKREQUESTDET pr LEFT JOIN " + plant + "_ITEMMST i ON pr.ITEM = i.ITEM WHERE pr.HDRID = :hdrId AND PLANT = :plant";

                Query query1 = session.createSQLQuery(detSql);
                query1.setParameter("hdrId", productRequestHdrDTO.getProjectId());
                query1.setParameter("plant", plant);

                List<Object[]> rows1 = query1.list();
                List<ProductRequestDetDTO> productRequestDetDTOList = new ArrayList<>();
                for(Object[] row1: rows1) {
                    ProductRequestDetDTO productRequestDetDTO = new ProductRequestDetDTO();

                    productRequestDetDTO.setPlant((String) row1[0]);
                    productRequestDetDTO.setProductRequestDetId((int) row1[1]);
                    productRequestDetDTO.setItem((String) row1[2]);
                    productRequestDetDTO.setProductName((String) row1[3]);
                    productRequestDetDTO.setProductDescription((String) row1[4]);
                    productRequestDetDTO.setLnStatus(row1[5] != null ? LNStatus.valueOf((String) row1[5]) : null);
                    productRequestDetDTO.setLnNo((int) row1[6]);
                    productRequestDetDTO.setUom((String) row1[7]);
                    productRequestDetDTO.setQuantity((double) row1[8]);
                    productRequestDetDTO.setProcessedQty((double) row1[9]);
                    productRequestDetDTO.setBalanceQty((double) row1[10]);

                    productRequestDetDTOList.add(productRequestDetDTO);
                }

                productRequestHdrDTO.setProductRequestDetDTOList(productRequestDetDTOList);
            }



        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }


        return productRequestHdrDTO;
    }

    @Override
    public Integer saveProductRequest(String plant, PRHdrRequestDTO productRequestHdrDTO) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        String hdrsql = null;
        try {
            hdrsql = "INSERT INTO " + plant + "_PROJECTSTOCKREQUESTHDR " +
                "  (PLANT, PROJECTID, PROJECTNO, REQUESTSTATUS, REQUESTEDDATE, REQUESTOR_ID, REQUESTER_REMARKS, APPROVAL_STATUS, APPROVER_CODE, CRAT, CRBY, UPAT, UPBY)" +
                "  VALUES (:plant, :projectId, :projectNo, :requestStatus, :requestedDate, :requestorId, :requesterRemarks, :approvalStatus, :approverCode, :crAt, :crBy, :upAt, :upBy); " +
                    "SELECT SCOPE_IDENTITY();";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(hdrsql);
            query.setParameter("plant", plant);
            query.setParameter("projectId", productRequestHdrDTO.getProjectId());
            query.setParameter("projectNo", productRequestHdrDTO.getProjectNo());
            query.setParameter("requestStatus", productRequestHdrDTO.getRequestStatus().toString());
            query.setParameter("requestedDate", productRequestHdrDTO.getRequestedDate());
            query.setParameter("requestorId", productRequestHdrDTO.getRequesterId());
            query.setParameter("approvalStatus", productRequestHdrDTO.getApprovalStatus().toString());
            query.setParameter("approverCode", productRequestHdrDTO.getApproverCode());
            query.setParameter("requesterRemarks", productRequestHdrDTO.getRequesterRemarks());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", productRequestHdrDTO.getRequesterId());
            query.setParameter("upAt", null);
            query.setParameter("upBy", null);

            Integer newProductRequestId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            for(PRDetRequestDTO requestDetDTO: productRequestHdrDTO.getProductRequestDetDTOList()) {
                String detsql = "INSERT INTO " + plant + "_PROJECTSTOCKREQUESTDET (PLANT, HDRID, LNSTATUS, LNNO, ITEM, UOM, QTY, PROCESSEDQTY, BALANCEQTY, CRAT, CRBY, RECEIVED_QTY, NONRECEIVED_QTY) " +
                        "  VALUES (:plant, :hdrId, :lnStatus, :lnNo, :item, :uom, :qty, :processedQty, :balanceQty, :crAt, :crBy, :receivedQty, :nonReceivedQty)";

                session.beginTransaction();
                Query query1 = session.createSQLQuery(detsql);
                query1.setParameter("plant", plant);
                query1.setParameter("hdrId", newProductRequestId);
                query1.setParameter("lnStatus", requestDetDTO.getLnStatus().toString());
                query1.setParameter("lnNo", requestDetDTO.getLnNo());
                query1.setParameter("item", requestDetDTO.getProductName());
                query1.setParameter("uom", requestDetDTO.getUom());
                query1.setParameter("qty", requestDetDTO.getQuantity());
                query1.setParameter("processedQty", requestDetDTO.getProcessedQty());
                query1.setParameter("balanceQty", requestDetDTO.getBalanceQty());
                query1.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                query1.setParameter("crBy", null);
                query1.setParameter("receivedQty", requestDetDTO.getReceivedQty());
                query1.setParameter("nonReceivedQty", requestDetDTO.getNonReceivedQty());

                result = query1.executeUpdate();
                session.getTransaction().commit();

            }

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<ProductRequestHdrDTO> getTodayProductRequest(String plant) {
        Session session = sessionFactory.openSession();
        String sql = null;
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        try {
            sql = "SELECT PLANT AS plant, ID AS projectRequestHdrId, PROJECTID AS projectId, PROJECTNO AS projectNo, REQUESTEDDATE AS requestedDate, REQUESTOR_ID AS requesterId, \n" +
                    "  REQUESTSTATUS AS requestStatus, REQUESTER_REMARKS AS requesterRemarks, APPROVER_CODE AS approverCode, APPROVAL_DATE AS approvalDate, APPROVAL_STATUS AS approvalStatus, APPROVER_REMARKS AS approverRemarks\n" +
                    "  FROM " + plant + "_PROJECTSTOCKREQUESTHDR WHERE REQUESTEDDATE = :requestedDate AND PLANT = :plant";

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);

            query.setParameter("requestedDate", dateTimeCalc.getTodayDateTime());
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            productRequestHdrDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProductRequestHdrDTO productRequestHdrDTO = new ProductRequestHdrDTO();

                productRequestHdrDTO.setPlant((String) row[0]);
                productRequestHdrDTO.setProductRequestHdrId((int) row[1]);
                productRequestHdrDTO.setProjectId((int) row[2]);
                productRequestHdrDTO.setProjectNo((String) row[3]);
                productRequestHdrDTO.setRequestedDate((String) row[4]);
                productRequestHdrDTO.setRequesterId((String) row[5]);
                productRequestHdrDTO.setRequestStatus((RequestStatus) row[6]);
                productRequestHdrDTO.setRequesterRemarks((String) row[7]);
                productRequestHdrDTO.setApproverCode((String) row[8]);
                productRequestHdrDTO.setApprovalDate((String) row[9]);
                productRequestHdrDTO.setApprovalStatus((ApprovalStatus) row[10]);
                productRequestHdrDTO.setApproverRemarks((String) row[11]);

                productRequestHdrDTOList.add(productRequestHdrDTO);
            }

            for(ProductRequestHdrDTO productRequestHdrDTO : productRequestHdrDTOList) {
                String detSql = "  SELECT pr.PLANT AS plant, pr.ID AS productRequestDetId, pr.ITEM as item, i.ITEMDESC AS productName, (SELECT string_agg(ITEMDETAILDESC, '. ') FROM "+ plant +"_ITEMDET_DESC idd " +
                        "WHERE idd.ITEM = i.item) AS productDescription, pr.LNSTATUS AS lnStatus, pr.LNNO AS lnNo, pr.UOM AS uom, pr.QTY AS quantity, pr.PROCESSEDQTY AS processedQty " +
                        ", pr.BALANCEQTY AS balanceQty, pr.RECEIVED_QTY, pr.NONRECEIVED_QTY FROM " + plant + "_PROJECTSTOCKREQUESTDET pr LEFT JOIN " + plant + "_ITEMMST i ON pr.ITEM = i.ITEM WHERE pr.HDRID = :hdrId AND PLANT = :plant";

                Query query1 = session.createSQLQuery(detSql);
                query1.setParameter("hdrId", productRequestHdrDTO.getProjectId());
                query1.setParameter("plant", plant);

                List<Object[]> rows1 = query1.list();
                List<ProductRequestDetDTO> productRequestDetDTOList = new ArrayList<>();
                for(Object[] row1: rows1) {
                    ProductRequestDetDTO productRequestDetDTO = new ProductRequestDetDTO();

                    productRequestDetDTO.setPlant((String) row1[0]);
                    productRequestDetDTO.setProductRequestDetId((int) row1[1]);
                    productRequestDetDTO.setItem((String) row1[2]);
                    productRequestDetDTO.setProductName((String) row1[3]);
                    productRequestDetDTO.setProductDescription((String) row1[4]);
                    productRequestDetDTO.setLnStatus(row1[5] != null ? LNStatus.valueOf((String) row1[5]) : null);
                    productRequestDetDTO.setLnNo((int) row1[6]);
                    productRequestDetDTO.setUom((String) row1[7]);
                    productRequestDetDTO.setQuantity((double) row1[8]);
                    productRequestDetDTO.setProcessedQty((double) row1[9]);
                    productRequestDetDTO.setBalanceQty((double) row1[10]);
                    productRequestDetDTO.setReceivedQty((double) row1[11]);
                    productRequestDetDTO.setNonReceivedQty((double) row1[12]);

                    productRequestDetDTOList.add(productRequestDetDTO);
                }

                productRequestHdrDTO.setProductRequestDetDTOList(productRequestDetDTOList);
            }


        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return productRequestHdrDTOList;
    }

    @Override
    public ProductRequestDetDTO getProductRequestDetByItem(String plant, String item) {
        ProductRequestDetDTO productRequestDetDTO = null;
        Session session = sessionFactory.openSession();
        try {
            String detSql = "  SELECT TOP 1 pr.PLANT AS plant, pr.ID AS productRequestDetId, pr.ITEM as item, i.ITEMDESC AS productName, (SELECT string_agg(ITEMDETAILDESC, '. ') FROM "+ plant +"_ITEMDET_DESC idd " +
                    "WHERE idd.ITEM = i.item) AS productDescription, pr.LNSTATUS AS lnStatus, pr.LNNO AS lnNo, pr.UOM AS uom, pr.QTY AS quantity, pr.PROCESSEDQTY AS processedQty " +
                    ", pr.BALANCEQTY AS balanceQty, pr.RECEIVED_QTY, pr.NONRECEIVED_QTY FROM " + plant + "_PROJECTSTOCKREQUESTDET pr LEFT JOIN " + plant + "_ITEMMST i ON pr.ITEM = i.ITEM WHERE pr.ITEM = :item AND pr.PLANT = :plant";

            Query query1 = session.createSQLQuery(detSql);
            query1.setParameter("item", item);
            query1.setParameter("plant", plant);

            Object[] row = (Object[]) query1.uniqueResult();
            productRequestDetDTO = new ProductRequestDetDTO();

            productRequestDetDTO.setPlant((String) row[0]);
            productRequestDetDTO.setProductRequestDetId((int) row[1]);
            productRequestDetDTO.setItem((String) row[2]);
            productRequestDetDTO.setProductName((String) row[3]);
            productRequestDetDTO.setProductDescription((String) row[4]);
            productRequestDetDTO.setLnStatus(row[5] != null ? LNStatus.valueOf((String) row[5]) : null);
            productRequestDetDTO.setLnNo((int) row[6]);
            productRequestDetDTO.setUom((String) row[7]);
            productRequestDetDTO.setQuantity((double) row[8]);
            productRequestDetDTO.setProcessedQty((double) row[9]);
            productRequestDetDTO.setBalanceQty((double) row[10]);
            productRequestDetDTO.setReceivedQty((double) row[11]);
            productRequestDetDTO.setNonReceivedQty((double) row[12]);


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return productRequestDetDTO;
    }

    @Override
    public Integer updateProductRequest(String plant, Integer requestId, ProductRequestHdrDTO productRequestHdrDTO) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        String sql = null;
        try {
            sql = "UPDATE " + plant + "_PROJECTSTOCKREQUESTHDR SET REQUESTSTATUS = :requestStatus, APPROVAL_STATUS = :approvalStatus, UPAT = :upAt WHERE ID = :requestId AND PLANT = :plant";
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("requestStatus", productRequestHdrDTO.getRequestStatus());
            query.setParameter("approvalStatus", productRequestHdrDTO.getApprovalStatus());
            query.setParameter("requestId", requestId);
            query.setParameter("plant", plant);
            query.setParameter("upAt", dateTimeCalc.getTodayDMYDate());

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

    @Override
    public Integer updateProductRequestDET(String plant, ProductRequestDetDTO productRequestDetDTO) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        String sql = null;
        try {
            sql = "UPDATE " + plant + "_PROJECTSTOCKREQUESTDET SET PROCESSEDQTY = :processedQty, BALANCEQTY = :balanceQty, RECEIVED_QTY = :receivedQty, " +
                    "NONRECEIVED_QTY = :nonReceivedQty, UPAT = :upAt WHERE ID = :detId AND PLANT = :plant";
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("processedQty", productRequestDetDTO.getProcessedQty());
            query.setParameter("balanceQty", productRequestDetDTO.getBalanceQty());
            query.setParameter("receivedQty", productRequestDetDTO.getReceivedQty());
            query.setParameter("nonReceivedQty", productRequestDetDTO.getNonReceivedQty());
            query.setParameter("detId", productRequestDetDTO.getProductRequestDetId());
            query.setParameter("plant", plant);
            query.setParameter("upAt", dateTimeCalc.getTodayDMYDate());

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

    @Override
    public Integer receiveProductRequest(String plant, ProductRequestReceiveDTO productRequestReceiveDTO) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        String hdrsql = null;
        try {
            hdrsql = "INSERT INTO " + plant + "_PRODUCTREQUESTRECEIVEHDR " +
                    "  (PLANT, PROJECTNO, RECEIVED_DATE, RECEIVER_ID, RECEIVER_REMARKS, DRIVER_NAME, VEHICLE_NUMBER, CRAT, CRBY, UPAT, UPBY)" +
                    "  VALUES (:plant, :projectNo, :receivedDate, :receiverId, :receiverRemarks, :driverName, :vehicleNo, :crAt, :crBy, :upAt, :upBy); " +
                    "SELECT SCOPE_IDENTITY();";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(hdrsql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", productRequestReceiveDTO.getProjectNo());
            query.setParameter("receivedDate", dateTimeCalc.getTodayDMYDate());
            query.setParameter("receiverId", productRequestReceiveDTO.getReceiverId());
            query.setParameter("receiverRemarks", productRequestReceiveDTO.getReceiverRemarks());
            query.setParameter("driverName", productRequestReceiveDTO.getDriverName());
            query.setParameter("vehicleNo", productRequestReceiveDTO.getVehicleNo());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", productRequestReceiveDTO.getReceiverId());
            query.setParameter("upAt", null);
            query.setParameter("upBy", null);

            Integer newProductRequestId = ((Number) query.uniqueResult()).intValue();
            session.getTransaction().commit();

            for(ProductRequestReceiveProductDTO receiveProductDTO: productRequestReceiveDTO.getProductRequestReceiveProductDTOList()) {
                String detsql = "INSERT INTO " + plant + "_PRODUCTREQUESTRECEIVEDET (PLANT, PROJECTNO, HDR_ID, ITEM, UOM, QTY, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                        "  VALUES (:plant, :projectNo, :hdrId, :item, :uom, :qty, :processedQty, :pendingQty, :crAt, :crBy)";

                session.beginTransaction();
                Query query1 = session.createSQLQuery(detsql);
                query1.setParameter("plant", plant);
                query1.setParameter("projectNo", productRequestReceiveDTO.getProjectNo());
                query1.setParameter("hdrId", newProductRequestId);
                query1.setParameter("item", receiveProductDTO.getItem());
                query1.setParameter("uom", receiveProductDTO.getUom());
                query1.setParameter("qty", receiveProductDTO.getQty());
                query1.setParameter("processedQty", 0);
                query1.setParameter("pendingQty", receiveProductDTO.getQty());
                query1.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                query1.setParameter("crBy", productRequestReceiveDTO.getReceiverId());

                result = query1.executeUpdate();
                session.getTransaction().commit();

            }

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<ProductRequestReceiveDTO> getProductRequestReceiveByCriteria(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<ProductRequestReceiveDTO> productRequestReceiveDTOList = null;
        String hdrSql = null;
        String detSql = null;

        try {
            hdrSql = "SELECT ID AS hdrId, PROJECTNO AS projectNo, RECEIVED_DATE AS receivedDate, RECEIVER_ID AS receiverId, \n" +
                    "  RECEIVER_REMARKS AS receiverRemarks, DRIVER_NAME AS driverName, VEHICLE_NUMBER AS vehicleNumber \n" +
                    "  FROM " + plant + "_PRODUCTREQUESTRECEIVEHDR WHERE PROJECTNO = :projectNo AND PLANT = :plant";

            Query query = session.createSQLQuery(hdrSql);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            productRequestReceiveDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProductRequestReceiveDTO productRequestReceiveDTO = new ProductRequestReceiveDTO();


                productRequestReceiveDTO.setId((int) row[0]);
                productRequestReceiveDTO.setProjectNo((String) row[1]);
                productRequestReceiveDTO.setReceivedDate(row[2] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[2]) : "");
                productRequestReceiveDTO.setReceiverId((String) row[3]);
                productRequestReceiveDTO.setReceiverRemarks((String) row[4]);
                productRequestReceiveDTO.setDriverName((String) row[5]);
                productRequestReceiveDTO.setVehicleNo((String) row[6]);

                productRequestReceiveDTOList.add(productRequestReceiveDTO);
            }

            for(ProductRequestReceiveDTO productRequestReceiveDTO : productRequestReceiveDTOList) {
                detSql = "  SELECT pr.ID, pr.ITEM, i.ITEMDESC AS productName, pr.PENDING_QTY, pr.UOM FROM " + plant + "_PRODUCTREQUESTRECEIVEDET pr " +
                        "LEFT JOIN " + plant + "_ITEMMST i ON pr.ITEM = i.ITEM WHERE pr.HDR_ID = :hdrId AND pr.PLANT = :plant";

                Query query1 = session.createSQLQuery(detSql);
                query1.setParameter("hdrId", productRequestReceiveDTO.getId());
                query1.setParameter("plant", plant);

                List<Object[]> rows1 = query1.list();
                List<ProductRequestReceiveProductDTO> productRequestReceiveProductDTOList = new ArrayList<>();
                for(Object[] row1: rows1) {
                    ProductRequestReceiveProductDTO productRequestReceiveProductDTO = new ProductRequestReceiveProductDTO();

                    productRequestReceiveProductDTO.setId((int) row1[0]);
                    productRequestReceiveProductDTO.setItem((String) row1[1]);
                    productRequestReceiveProductDTO.setItemDesc((String) row1[2]);
                    productRequestReceiveProductDTO.setQty((double) row1[3]);
                    productRequestReceiveProductDTO.setUom((String) row1[4]);

                    productRequestReceiveProductDTOList.add(productRequestReceiveProductDTO);
                }

                productRequestReceiveDTO.setProductRequestReceiveProductDTOList(productRequestReceiveProductDTOList);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return productRequestReceiveDTOList;
    }

    @Override
    public Integer approveProductRequests(String plant, ApprovePRDTO approvePRDTO) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_PROJECTSTOCKREQUESTHDR SET REQUESTSTATUS = :requestStatus, APPROVAL_STATUS = :approvalStatus, " +
                    "APPROVAL_DATE = :approvalDate , APPROVER_REMARKS = :approverRemarks , APPROVER_CODE = :approverCode " +
                    "WHERE ID = :id AND PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            Query query = session.createSQLQuery(sql);

            query.setParameter("requestStatus", approvePRDTO.getIsApproved() == 1 ? RequestStatus.OPEN.name() : RequestStatus.DRAFT.name());
            query.setParameter("approvalStatus", approvePRDTO.getIsApproved() == 1 ? ApprovalStatus.APPROVED.name() : ApprovalStatus.REJECTED.name());
            query.setParameter("approvalDate", dateTimeCalc.getTodayDMYDate());
            query.setParameter("approverRemarks", approvePRDTO.getApproverRemarks());
            query.setParameter("approverCode", approvePRDTO.getApproverCode());
            query.setParameter("id", approvePRDTO.getProductRequestHdrId());
            query.setParameter("plant", plant);
            query.setParameter("projectNo", approvePRDTO.getProjectNo());

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

    @Override
    public List<PRNonReceivedDTO> getProductCurrentStock(String plant, String projectNo) {
        List<PRNonReceivedDTO> prNonReceivedDTOList = null;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT ITEM, QTY, UOM, PROCESSEDQTY, BALANCEQTY, RECEIVED_QTY, NONRECEIVED_QTY FROM " + plant + "_PROJECTSTOCKREQUESTDET WHERE PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            prNonReceivedDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                PRNonReceivedDTO prNonReceivedDTO = new PRNonReceivedDTO();
                prNonReceivedDTO.setItem((String) row[0]);
                prNonReceivedDTO.setQty(row[1] != null ? (double) row[1] : 0.0);
                prNonReceivedDTO.setUom((String) row[2]);
                prNonReceivedDTO.setProcessedQty(row[3] != null ? (double) row[3] : 0.0);
                prNonReceivedDTO.setBalanceQty(row[4] != null ? (double) row[4] : 0.0);
                prNonReceivedDTO.setReceivedQty(row[5] != null ? (double) row[5] : 0.0);
                prNonReceivedDTO.setNonReceivedQty(row[6] != null ? (double) row[6] : 0.0);
                prNonReceivedDTO.setProjectNo(projectNo);

                prNonReceivedDTOList.add(prNonReceivedDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return prNonReceivedDTOList;
    }
}
