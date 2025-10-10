package com.owner.process.usecases.wastage.dao;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.wastage.dto.*;
import com.owner.process.usecases.wastage.enums.WastageType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository("WastageDao")
public class WastageDaoImpl implements WastageDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<DailyWastageDetailsDET> saveDailyWastageDet(String plant, List<AddWastageRequestDTO> wastageRequestDTOList) {
        Session session = sessionFactory.openSession();

        List<DailyWastageDetailsDET> dailyWastageDetailsDETList = new ArrayList<>();
        try {
            for(AddWastageRequestDTO wastageRequestDTO: wastageRequestDTOList) {
                DailyWastageDetailsDET dailyWastageDetailsDET = new DailyWastageDetailsDET();
                String sql = "  INSERT INTO "+ plant +"_DAILY_WASTAGE_DETAILS_DET (PLANT, PROJECTNO, WASTAGE_TYPE, WASTAGE_QTY, WASTAGE_UOM, SUPERVISOR_CODE, DATE, CRAT, CRBY) " +
                        " OUTPUT INSERTED.PLANT, INSERTED.ID, INSERTED.PROJECTNO, INSERTED.WASTAGE_TYPE, INSERTED.WASTAGE_QTY, INSERTED.WASTAGE_UOM, INSERTED.SUPERVISOR_CODE, INSERTED.DATE, INSERTED.CRAT, INSERTED.CRBY" +
                        "  VALUES (:plant, :projectNo, :wastageType, :wastageQty, :wastageUom, :supervisorCode, :date, :crAt, :crBy)";
                session.beginTransaction();
                DateTimeCalc dateTimeCalc = new DateTimeCalc();
                Query query = session.createSQLQuery(sql);

                query.setParameter("plant", plant);
                query.setParameter("projectNo", wastageRequestDTO.getProjectNo());
                query.setParameter("wastageType", wastageRequestDTO.getWastageType().toString());
                query.setParameter("wastageQty", wastageRequestDTO.getWastageQty());
                query.setParameter("wastageUom", wastageRequestDTO.getWastageUOM());
                query.setParameter("supervisorCode", wastageRequestDTO.getSupervisorCode());
                query.setParameter("date", dateTimeCalc.getTodayDMYDate());
                query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
                query.setParameter("crBy", null);

                Object[] record =  (Object[]) query.uniqueResult();

                if(record != null) {
                    dailyWastageDetailsDET = new DailyWastageDetailsDET();
                    dailyWastageDetailsDET.setPlant((String) record[0]);
                    dailyWastageDetailsDET.setId((int) record[1]);
                    dailyWastageDetailsDET.setProjectNo((String) record[2]);
                    dailyWastageDetailsDET.setWastageType((String) record[3]);
                    dailyWastageDetailsDET.setWastageQty((double) record[4]);
                    dailyWastageDetailsDET.setWastageUOM((String) record[5]);
                    dailyWastageDetailsDET.setSupervisorCode((String) record[6]);
                    dailyWastageDetailsDET.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) record[7]));
                    dailyWastageDetailsDET.setCrAt((String) record[8]);
                    dailyWastageDetailsDET.setCrBy((String) record[9]);
                }

                session.getTransaction().commit();

                dailyWastageDetailsDETList.add(dailyWastageDetailsDET);
            }


        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return dailyWastageDetailsDETList;
    }

    @Override
    public DailyWastageDetailsHDR getDailyWastageDetailsHDR(String plant, String totalWastageType, String projectNo) {
        Session session = sessionFactory.openSession();
        DailyWastageDetailsHDR dailyWastageDetailsHDR = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, TOTAL_WASTAGE_TYPE, TOTAL_WASTAGE_QTY, TOTAL_WASTAGE_UOM, PROJECTNO, PROCESSED_QTY, PENDING_QTY, " +
                    "CRAT, CRBY, UPAT, UPBY FROM " + plant + "_DAILY_WASTAGE_DETAILS_HDR WHERE PLANT = :plant AND TOTAL_WASTAGE_TYPE = :totalWastageType AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("totalWastageType", totalWastageType);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                 dailyWastageDetailsHDR = DailyWastageDetailsHDR.builder()
                         .plant((String) row[0])
                         .id((int) row[1])
                         .totalWastageType((String) row[2])
                         .totalWastageQty((double) row[3])
                         .totalWastageUOM((String) row[4])
                         .projectNo((String) row[5])
                         .processedQty((double) row[6])
                         .pendingQty((double) row[7])
                         .build();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return dailyWastageDetailsHDR;
    }

    @Override
    public Integer saveDailyWastageHdr(String plant, DailyWastageDetailsHDR dailyWastageDetailsHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant + "_DAILY_WASTAGE_DETAILS_HDR (PLANT, TOTAL_WASTAGE_TYPE, TOTAL_WASTAGE_QTY, TOTAL_WASTAGE_UOM, PROJECTNO, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    "VALUES (:plant, :totalWastageType, :totalWastageQty, :totalWastageUOM, :projectNo, :processedQty, :pendingQty, :crAt, :crBy)";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("totalWastageType", dailyWastageDetailsHDR.getTotalWastageType().toString());
            query.setParameter("totalWastageQty", dailyWastageDetailsHDR.getTotalWastageQty());
            query.setParameter("totalWastageUOM", dailyWastageDetailsHDR.getTotalWastageUOM());
            query.setParameter("projectNo", dailyWastageDetailsHDR.getProjectNo());
            query.setParameter("processedQty", dailyWastageDetailsHDR.getProcessedQty());
            query.setParameter("pendingQty", dailyWastageDetailsHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public Integer updateDailyWastageHdr(String plant, DailyWastageDetailsHDR dailyWastageDetailsHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_DAILY_WASTAGE_DETAILS_HDR SET TOTAL_WASTAGE_TYPE = :totalWastageType, TOTAL_WASTAGE_QTY = :totalWastageQty, " +
                    "TOTAL_WASTAGE_UOM = :totalWastageUom, PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT= :upAt, UPBY = :upBy WHERE PLANT = :plant AND ID = :id";
            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);

            query.setParameter("totalWastageType", dailyWastageDetailsHDR.getTotalWastageType());
            query.setParameter("totalWastageQty", dailyWastageDetailsHDR.getTotalWastageQty());
            query.setParameter("totalWastageUom", dailyWastageDetailsHDR.getTotalWastageUOM());
            query.setParameter("processedQty", dailyWastageDetailsHDR.getProcessedQty());
            query.setParameter("pendingQty", dailyWastageDetailsHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("plant", plant);
            query.setParameter("id", dailyWastageDetailsHDR.getId());

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
    public List<WastageDetailsDTO> getWastageDetails(String plant, String projectNo, WastageType wastageType) {
        Session session = sessionFactory.openSession();
        List<WastageDetailsDTO> wastageDetailsDTOList = null;
        try {
            String sql = "SELECT PLANT, ID, TOTAL_WASTAGE_TYPE, TOTAL_WASTAGE_QTY, TOTAL_WASTAGE_UOM, PROJECTNO, PROCESSED_QTY, PENDING_QTY " +
                    "FROM " + plant + "_DAILY_WASTAGE_DETAILS_HDR WHERE PLANT = :plant AND (PROJECTNO = :projectNo OR :projectNo IS NULL) AND (TOTAL_WASTAGE_TYPE = :wastageType OR :wastageType IS NULL)";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);
            query.setParameter("wastageType", wastageType != null ? wastageType.toString() : null);

            wastageDetailsDTOList = new ArrayList<>();

            List<Object[]> rows = query.list();

            for(Object[] row: rows) {
                WastageDetailsDTO wastageDetailsDTO = new WastageDetailsDTO();
                wastageDetailsDTO.setPlant((String) row[0]);
                wastageDetailsDTO.setId((int) row[1]);
                wastageDetailsDTO.setTotalWastageType((String) row[2]);
                wastageDetailsDTO.setTotalWastageQty((double) row[3]);
                wastageDetailsDTO.setTotalWastageUOM((String) row[4]);
                wastageDetailsDTO.setProjectNo((String) row[5]);
                wastageDetailsDTO.setProcessedQty((double) row[6]);
                wastageDetailsDTO.setPendingQty((double) row[7]);

                wastageDetailsDTOList.add(wastageDetailsDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wastageDetailsDTOList;
    }

    @Override
    public Integer saveOrganicWastageDet(String plant, OrganicWasteDET organicWasteDET) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_ORGANIC_WASTE_DET (PLANT, PROJECTNO, DATE, QTY, UOM, EMPCODE, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :date, :qty, :uom, :empCode, :crAt, :crBy)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", organicWasteDET.getProjectNo());
            query.setParameter("date", organicWasteDET.getDate());
            query.setParameter("qty", organicWasteDET.getQty());
            query.setParameter("uom", organicWasteDET.getUom());
            query.setParameter("empCode", organicWasteDET.getEmpCode());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public Integer saveOrganicWastageHdr(String plant, OrganicWasteHDR organicWasteHDR) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_ORGANIC_WASTE_HDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :totalQty, :totalUom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", organicWasteHDR.getProjectNo());
            query.setParameter("totalQty", organicWasteHDR.getTotalQty());
            query.setParameter("totalUom", organicWasteHDR.getTotalUOM());
            query.setParameter("processedQty", organicWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", organicWasteHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public OrganicWasteHDR getOrganicWasteHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        OrganicWasteHDR organicWasteHDR = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_ORGANIC_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                organicWasteHDR = OrganicWasteHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .processedQty((double) row[5])
                        .pendingQty((double) row[6])
                        .crAt((String) row[7])
                        .crBy((String) row[8])
                        .upAt((String) row[9])
                        .upBy((String) row[10])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return organicWasteHDR;
    }

    @Override
    public Integer updateOrganicWasteHdr(String plant, OrganicWasteHDR organicWasteHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_ORGANIC_WASTE_HDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", organicWasteHDR.getTotalQty());
            query.setParameter("totalUom", organicWasteHDR.getTotalUOM());
            query.setParameter("processedQty", organicWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", organicWasteHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("projectNo", organicWasteHDR.getProjectNo());

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
    public Integer saveBioGasDet(String plant, BioGasDET bioGasDET) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant + "_BIOGASDET (PLANT, PROJECTNO, DATE, QTY, UOM, EMPCODE, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :date, :qty, :uom, :empCode, :crAt, :crBy)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", bioGasDET.getProjectNo());
            query.setParameter("date", bioGasDET.getDate());
            query.setParameter("qty", bioGasDET.getQty());
            query.setParameter("uom", bioGasDET.getUom());
            query.setParameter("empCode", bioGasDET.getEmpCode());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            result = query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
             session.close();
        }
        return result;
    }

    @Override
    public BioGasHDR getBioGasHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        BioGasHDR bioGasHDR = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_BIOGASHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                bioGasHDR = BioGasHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .processedQty((double) row[5])
                        .pendingQty((double) row[6])
                        .crAt((String) row[7])
                        .crBy((String) row[8])
                        .upAt((String) row[9])
                        .upBy((String) row[10])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return bioGasHDR;
    }

    @Override
    public Integer updateBioGasHdr(String plant, BioGasHDR bioGasHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_BIOGASHDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", bioGasHDR.getTotalQty());
            query.setParameter("totalUom", bioGasHDR.getTotalUOM());
            query.setParameter("processedQty", bioGasHDR.getProcessedQty());
            query.setParameter("pendingQty", bioGasHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("projectNo", bioGasHDR.getProjectNo());

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
    public Integer saveBioGasHdr(String plant, BioGasHDR bioGasHDR) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_BIOGASHDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :totalQty, :totalUom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", bioGasHDR.getProjectNo());
            query.setParameter("totalQty", bioGasHDR.getTotalQty());
            query.setParameter("totalUom", bioGasHDR.getTotalUOM());
            query.setParameter("processedQty", bioGasHDR.getProcessedQty());
            query.setParameter("pendingQty", bioGasHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public Integer saveOWCDet(String plant, OWCDET owcDet) {
        Session session = sessionFactory.openSession();
        Integer detId = 0;
        try {
            String sql = "INSERT INTO " + plant  + "_OWCDET (PLANT, PROJECTNO, MACHINE_ID, PRODUCT, DATE, QTY, UOM, EMPCODE, CRAT, CRBY) " +
                    "OUTPUT INSERTED.ID VALUES (:plant, :projectNo, :machineId, :product, :date, :qty, :uom, :empCode, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", owcDet.getProjectNo());
            query.setParameter("machineId", owcDet.getMachineId());
            query.setParameter("product", owcDet.getProduct());
            query.setParameter("date", owcDet.getDate());
            query.setParameter("qty", owcDet.getQty());
            query.setParameter("uom", owcDet.getUom());
            query.setParameter("empCode", owcDet.getEmpCode());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            detId = (Integer) query.uniqueResult();
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
    public OWCHDR getOWCHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        OWCHDR owcHdr = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_OWCHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                owcHdr = OWCHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .crAt((String) row[5])
                        .crBy((String) row[6])
                        .upAt((String) row[7])
                        .upBy((String) row[8])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return owcHdr;
    }

    @Override
    public Integer updateOWCHdr(String plant, OWCHDR owcHdr) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_OWCHDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, " +
                    "UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", owcHdr.getTotalQty());
            query.setParameter("totalUom", owcHdr.getTotalUOM());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("projectNo", owcHdr.getProjectNo());

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
    public Integer saveOWCHdr(String plant, OWCHDR owcHdr) {
        Integer hdrId = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_OWCHDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, CRAT, CRBY) " +
                    " OUTPUT INSERTED.ID VALUES (:plant, :projectNo, :totalQty, :totalUom, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", owcHdr.getProjectNo());
            query.setParameter("totalQty", owcHdr.getTotalQty());
            query.setParameter("totalUom", owcHdr.getTotalUOM());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            hdrId = (Integer) query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return hdrId;
    }

    @Override
    public Integer saveOWCProductDET(String plant, OWCProductDET owcProductDET) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant  + "_OWCPRODUCTDET (PLANT, PROJECTNO, DET_ID, HDR_ID, PRODUCT, QTY, UOM, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :detId, :hdrId, :product, :qty, :uom, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", owcProductDET.getProjectNo());
            query.setParameter("detId", owcProductDET.getDetId());
            query.setParameter("hdrId", owcProductDET.getHdrId());
            query.setParameter("product", owcProductDET.getProduct());
            query.setParameter("qty", owcProductDET.getQty());
            query.setParameter("uom", owcProductDET.getUom());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public List<ProjectInventoryDTO> getAllProjectInventory(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<ProjectInventoryDTO> projectInventoryDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, TOTAL_QTY, PRODUCT, UOM, PROCESSED_QTY, PENDING_QTY FROM " +
                    plant + "_PROJECT_INVENTORY WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();

            projectInventoryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                ProjectInventoryDTO projectInventoryDTO = new ProjectInventoryDTO();
                projectInventoryDTO.setProjectNo((String) row[0]);
                projectInventoryDTO.setTotalQty((double) row[1]);
                projectInventoryDTO.setItem((String) row[2]);
                projectInventoryDTO.setUom((String) row[3]);
                projectInventoryDTO.setProcessedQty((double) row[4]);
                projectInventoryDTO.setPendingQty((double) row[5]);

                projectInventoryDTOList.add(projectInventoryDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return projectInventoryDTOList;
    }

    @Override
    public Integer saveOWCOutcomeDet(String plant, OWCOutcomeDET owcOutcomeDET) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant  + "_OWCOUTCOMEDET (PLANT, PROJECTNO, MACHINE_ID, PRODUCT, DATE, QTY, UOM, EMPCODE, CRAT, CRBY) " +
                    "VALUES (:plant, :projectNo, :machineId, :product, :date, :qty, :uom, :empCode, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", owcOutcomeDET.getProjectNo());
            query.setParameter("machineId", owcOutcomeDET.getMachineId());
            query.setParameter("product", owcOutcomeDET.getProduct());
            query.setParameter("date", owcOutcomeDET.getDate());
            query.setParameter("qty", owcOutcomeDET.getQty());
            query.setParameter("uom", owcOutcomeDET.getUom());
            query.setParameter("empCode", owcOutcomeDET.getEmpCode());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public OWCOutcomeHDR getOWCOutcomeHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        OWCOutcomeHDR owcOutcomeHdr = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_OWCOUTCOMEHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                owcOutcomeHdr = OWCOutcomeHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .processedQty((double) row[5])
                        .pendingQty((double) row[6])
                        .crAt((String) row[7])
                        .crBy((String) row[8])
                        .upAt((String) row[9])
                        .upBy((String) row[10])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return owcOutcomeHdr;
    }

    @Override
    public Integer updateOWCOutcomeHdr(String plant, OWCOutcomeHDR owcOutcomeHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_OWCOUTCOMEHDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, " +
                    "UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", owcOutcomeHDR.getTotalQty());
            query.setParameter("totalUom", owcOutcomeHDR.getTotalUOM());
            query.setParameter("processedQty", owcOutcomeHDR.getProcessedQty());
            query.setParameter("pendingQty", owcOutcomeHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", null);
            query.setParameter("projectNo", owcOutcomeHDR.getProjectNo());

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
    public Integer saveOWCOutcomeHdr(String plant, OWCOutcomeHDR owcOutcomeHDR) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_OWCOUTCOMEHDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :totalQty, :totalUom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", owcOutcomeHDR.getProjectNo());
            query.setParameter("totalQty", owcOutcomeHDR.getTotalQty());
            query.setParameter("totalUom", owcOutcomeHDR.getTotalUOM());
            query.setParameter("processedQty", owcOutcomeHDR.getProcessedQty());
            query.setParameter("pendingQty", owcOutcomeHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public Integer saveInorganicWastageDet(String plant, InorganicWasteDET inorganicWasteDET) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_INORGANIC_WASTE_DET (PLANT, PROJECTNO, DATE, ITEM, QTY, UOM, EMPCODE, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :date, :item, :qty, :uom, :empCode, :crAt, :crBy)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", inorganicWasteDET.getProjectNo());
            query.setParameter("date", inorganicWasteDET.getDate());
            query.setParameter("item", inorganicWasteDET.getItem());
            query.setParameter("qty", inorganicWasteDET.getQty());
            query.setParameter("uom", inorganicWasteDET.getUom());
            query.setParameter("empCode", inorganicWasteDET.getEmpCode());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public InorganicWasteProductDET getInorganicWasteProductDET(String plant, String projectNo, String itemName) {
        Session session = sessionFactory.openSession();
        InorganicWasteProductDET inorganicWasteProductDET = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, ITEM, QTY, UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_INORGANIC_WASTE_PRODUCT_DET WHERE PLANT = :plant AND PROJECTNO = :projectNo AND ITEM = :item";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);
            query.setParameter("item", itemName);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                inorganicWasteProductDET = InorganicWasteProductDET.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .item((String) row[3])
                        .qty((double) row[4])
                        .uom((String) row[5])
                        .processedQty((double) row[6])
                        .pendingQty((double) row[7])
                        .crAt((String) row[8])
                        .crBy((String) row[9])
                        .upAt((String) row[10])
                        .upBy((String) row[11])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return inorganicWasteProductDET;
    }

    @Override
    public Integer updateInorganicWasteProductDet(String plant, InorganicWasteProductDET inorganicWasteProductDET) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_INORGANIC_WASTE_PRODUCT_DET SET QTY = :qty, UOM = :uom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo AND ITEM = :item";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("qty", inorganicWasteProductDET.getQty());
            query.setParameter("uom", inorganicWasteProductDET.getUom());
            query.setParameter("processedQty", inorganicWasteProductDET.getProcessedQty());
            query.setParameter("pendingQty", inorganicWasteProductDET.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", inorganicWasteProductDET.getUpBy());
            query.setParameter("projectNo", inorganicWasteProductDET.getProjectNo());
            query.setParameter("item", inorganicWasteProductDET.getItem());

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
    public Integer saveInorganicWasteProductDet(String plant, InorganicWasteProductDET inorganicWasteProductDET) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_INORGANIC_WASTE_PRODUCT_DET (PLANT, PROJECTNO, ITEM, QTY, UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :item, :qty, :uom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", inorganicWasteProductDET.getProjectNo());
            query.setParameter("item", inorganicWasteProductDET.getItem());
            query.setParameter("qty", inorganicWasteProductDET.getQty());
            query.setParameter("uom", inorganicWasteProductDET.getUom());
            query.setParameter("processedQty", inorganicWasteProductDET.getProcessedQty());
            query.setParameter("pendingQty", inorganicWasteProductDET.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public InorganicWasteHDR getInorganicWasteHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        InorganicWasteHDR inorganicWasteHDR = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_INORGANIC_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                inorganicWasteHDR = InorganicWasteHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .processedQty((double) row[5])
                        .pendingQty((double) row[6])
                        .crAt((String) row[7])
                        .crBy((String) row[8])
                        .upAt((String) row[9])
                        .upBy((String) row[10])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return inorganicWasteHDR;
    }

    @Override
    public Integer updateInorganicWasteHdr(String plant, InorganicWasteHDR inorganicWasteHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_INORGANIC_WASTE_HDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", inorganicWasteHDR.getTotalQty());
            query.setParameter("totalUom", inorganicWasteHDR.getTotalUOM());
            query.setParameter("processedQty", inorganicWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", inorganicWasteHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", inorganicWasteHDR.getUpBy());
            query.setParameter("projectNo", inorganicWasteHDR.getProjectNo());

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
    public Integer saveInorganicWasteHdr(String plant, InorganicWasteHDR inorganicWasteHDR) {
        int result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_INORGANIC_WASTE_HDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :totalQty, :totalUom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", inorganicWasteHDR.getProjectNo());
            query.setParameter("totalQty", inorganicWasteHDR.getTotalQty());
            query.setParameter("totalUom", inorganicWasteHDR.getTotalUOM());
            query.setParameter("processedQty", inorganicWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", inorganicWasteHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public Integer saveRejectedWasteDet(String plant, RejectedWasteDET rejectedWasteDET) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant + "_REJECTED_WASTE_DET (PLANT, PROJECTNO, DATE, QTY, UOM, EMPCODE, WASTAGE_TYPE, CRAT, CRBY) " +
                    "VALUES(:plant, :projectNo, :date, :qty, :uom, :empCode, :wastageType, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", rejectedWasteDET.getProjectNo());
            query.setParameter("date", rejectedWasteDET.getDate());
            query.setParameter("qty", rejectedWasteDET.getQty());
            query.setParameter("uom", rejectedWasteDET.getUom());
            query.setParameter("empCode", rejectedWasteDET.getEmpCode());
            query.setParameter("wastageType", rejectedWasteDET.getWastageType());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            result = query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().commit();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public RejectedWasteHDR getRejectedWasteHDR(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        RejectedWasteHDR rejectedWasteHDR = null;
        try {
            String sql = "SELECT TOP 1 PLANT, ID, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY, UPAT, UPBY " +
                    " FROM "+ plant +"_REJECTED_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                rejectedWasteHDR = RejectedWasteHDR.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .totalUOM((String) row[4])
                        .processedQty((double) row[5])
                        .pendingQty((double) row[6])
                        .crAt((String) row[7])
                        .crBy((String) row[8])
                        .upAt((String) row[9])
                        .upBy((String) row[10])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return rejectedWasteHDR;
    }

    @Override
    public Integer updateRejectedWasteHdr(String plant, RejectedWasteHDR rejectedWasteHDR) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_REJECTED_WASTE_HDR SET TOTAL_QTY = :totalQty, TOTAL_UOM = :totalUom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy WHERE PLANT = :plant AND PROJECTNO = :projectNo";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", rejectedWasteHDR.getTotalQty());
            query.setParameter("totalUom", rejectedWasteHDR.getTotalUOM());
            query.setParameter("projectNo", rejectedWasteHDR.getProjectNo());
            query.setParameter("processedQty", rejectedWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", rejectedWasteHDR.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", rejectedWasteHDR.getUpBy());

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
    public Integer saveRejectedWasteHdr(String plant, RejectedWasteHDR rejectedWasteHDR) {
        Integer result = 0;
        Session session = sessionFactory.openSession();
        try {
            String sql = "INSERT INTO " + plant + "_REJECTED_WASTE_HDR (PLANT, PROJECTNO, TOTAL_QTY, TOTAL_UOM, PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) " +
                    " VALUES (:plant, :projectNo, :totalQty, :totalUom, :processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", rejectedWasteHDR.getProjectNo());
            query.setParameter("totalQty", rejectedWasteHDR.getTotalQty());
            query.setParameter("totalUom", rejectedWasteHDR.getTotalUOM());
            query.setParameter("processedQty", rejectedWasteHDR.getProcessedQty());
            query.setParameter("pendingQty", rejectedWasteHDR.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

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
    public boolean checkTotalWastageType(String plant, String wastageType, String projectNo) {
        Session session = sessionFactory.openSession();
        boolean recordExists = false;
        try {
            String sql = "SELECT COUNT(*) from " + plant + "_DAILY_WASTAGE_DETAILS_HDR WHERE TOTAL_WASTAGE_TYPE = :wastageType AND PROJECTNO = :projectNo AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("wastageType", wastageType);
            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkOrganicWasteHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_ORGANIC_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkRejectedWasteHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_REJECTED_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkInorganicWasteProduct(String plant, String projectNo, String itemName) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_INORGANIC_WASTE_PRODUCT_DET WHERE PLANT = :plant AND PROJECTNO = :projectNo AND ITEM = :item";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("item", itemName);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkInorganicWasteHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_INORGANIC_WASTE_HDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkBioGasHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_BIOGASHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkOWCHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_OWCHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkOWCOutcomeHDR(String plant, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_OWCOUTCOMEHDR WHERE PLANT = :plant AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkProjectInventory(String plant, String projectNo, String product) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_PROJECT_INVENTORY WHERE PLANT = :plant AND PROJECTNO = :projectNo AND PRODUCT = :product";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("product", product);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return recordExists;
    }



    @Override
    public List<BioGasDTO> getBioGasSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<BioGasDTO> bioGasDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, DATE, QTY, UOM, EMPCODE FROM " + plant + "_BIOGASDET WHERE PROJECTNO = :projectNo " +
                    "AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            bioGasDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                BioGasDTO bioGasDTO = new BioGasDTO();
                bioGasDTO.setProjectNo((String) row[0]);
                bioGasDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]));
                bioGasDTO.setQty((double) row[2]);
                bioGasDTO.setUom((String) row[3]);
                bioGasDTO.setEmpCode((String) row[4]);

                bioGasDTOList.add(bioGasDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return bioGasDTOList;
    }

    @Override
    public List<OWCMachineDTO> getOWCSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OWCMachineDTO> owcMachineDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, MACHINE_ID, (SELECT DESCRIPTION FROM " + plant +"_OWC_MACHINE_MST WHERE " +
                    "MACHINE_ID = owcDet.MACHINE_ID) AS MACHINE_NAME, DATE, QTY, UOM, EMPCODE FROM " + plant + "_OWCDET " +
                    "AS owcDet WHERE PROJECTNO = :projectNo AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            owcMachineDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                OWCMachineDTO owcMachineDTO = new OWCMachineDTO();
                owcMachineDTO.setProjectNo((String) row[0]);
                owcMachineDTO.setMachineId((String) row[1]);
                owcMachineDTO.setMachineName((String) row[2]);
                owcMachineDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[3]));
                owcMachineDTO.setQty((double) row[4]);
                owcMachineDTO.setUom((String) row[5]);
                owcMachineDTO.setEmpCode((String) row[6]);

                owcMachineDTOList.add(owcMachineDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return owcMachineDTOList;
    }

    @Override
    public List<OWCOutcomeDTO> getReceivedOWCOutcomeSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OWCOutcomeDTO> owcOutcomeDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, owc.MACHINE_ID, (SELECT DESCRIPTION FROM " + plant +"_OWC_MACHINE_MST WHERE MACHINE_ID = owc.MACHINE_ID) AS MACHINE_NAME, " +
                    "(SELECT ITEMDESC FROM " + plant + "_ITEMMST WHERE ITEM = owc.PRODUCT) AS PRODUCTNAME, DATE, QTY, UOM, EMPCODE FROM " + plant + "_OWCOUTCOMEDET owc WHERE PROJECTNO = :projectNo " +
                    "AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            owcOutcomeDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                OWCOutcomeDTO owcOutcomeDTO = new OWCOutcomeDTO();
                owcOutcomeDTO.setProjectNo((String) row[0]);
                owcOutcomeDTO.setMachineId((String) row[1]);
                owcOutcomeDTO.setMachineName((String) row[2]);
                owcOutcomeDTO.setProductName((String) row[3]);
                owcOutcomeDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[4]));
                owcOutcomeDTO.setQty((double) row[5]);
                owcOutcomeDTO.setUom((String) row[6]);
                owcOutcomeDTO.setEmpCode((String) row[7]);

                owcOutcomeDTOList.add(owcOutcomeDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return owcOutcomeDTOList;
    }

    @Override
    public List<WastageDTO> getWastageSummary(String plant, String projectNo, String date) {
        Session session = sessionFactory.openSession();
        List<WastageDTO> wastageDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, WASTAGE_TYPE, WASTAGE_QTY, WASTAGE_UOM, SUPERVISOR_CODE, DATE FROM " + plant + "_DAILY_WASTAGE_DETAILS_DET " +
                    "WHERE PROJECTNO = :projectNo AND PLANT = :plant AND (DATE = :date OR :date IS NULL)";
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
            wastageDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                WastageDTO wastageDTO = new WastageDTO();
                wastageDTO.setProjectNo((String) row[0]);
                wastageDTO.setWastageType((String) row[1]);
                wastageDTO.setWastageQty((double) row[2]);
                wastageDTO.setWastageUom((String) row[3]);
                wastageDTO.setSupervisorCode((String) row[4]);
                wastageDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[5]));

                wastageDTOList.add(wastageDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return wastageDTOList;
    }

    @Override
    public List<OrganicWastageSummaryDTO> getOrganicWastageSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OrganicWastageSummaryDTO> organicWastageSummaryDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, DATE, QTY, UOM FROM " + plant + "_ORGANIC_WASTE_DET WHERE PLANT = :plant " +
                    "AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            organicWastageSummaryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                OrganicWastageSummaryDTO organicWastageSummaryDTO = new OrganicWastageSummaryDTO();
                organicWastageSummaryDTO.setProjectNo((String) row[0]);
                organicWastageSummaryDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]));
                organicWastageSummaryDTO.setQty((double) row[2]);
                organicWastageSummaryDTO.setUom((String) row[3]);

                organicWastageSummaryDTOList.add(organicWastageSummaryDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return organicWastageSummaryDTOList;
    }

    @Override
    public List<RejectedWastageSummaryDTO> getRejectedWastageSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<RejectedWastageSummaryDTO> rejectedWastageSummaryDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, DATE, QTY, UOM, WASTAGE_TYPE FROM " + plant + "_REJECTED_WASTE_DET WHERE PLANT = :plant " +
                    "AND PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            rejectedWastageSummaryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                RejectedWastageSummaryDTO rejectedWastageSummaryDTO = new RejectedWastageSummaryDTO();
                rejectedWastageSummaryDTO.setProjectNo((String) row[0]);
                rejectedWastageSummaryDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]));
                rejectedWastageSummaryDTO.setQty((double) row[2]);
                rejectedWastageSummaryDTO.setUom((String) row[3]);
                rejectedWastageSummaryDTO.setWastageType((String) row[4]);

                rejectedWastageSummaryDTOList.add(rejectedWastageSummaryDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return rejectedWastageSummaryDTOList;
    }

    @Override
    public List<InorganicWastageSummaryDTO> getInorganicWastageSummary(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<InorganicWastageSummaryDTO> inorganicWastageSummaryDTOList = null;
        try {
            String sql = "SELECT IW.PROJECTNO, IW.DATE, IW.QTY, IW.UOM, IT.ITEMDESC  FROM " + plant + "_INORGANIC_WASTE_DET IW " +
                    " LEFT JOIN " + plant + "_ITEMMST IT ON IW.ITEM = IT.ITEM  WHERE IW.PLANT = :plant " +
                    "AND IW.PROJECTNO = :projectNo";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            inorganicWastageSummaryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                InorganicWastageSummaryDTO inorganicWastageSummaryDTO = new InorganicWastageSummaryDTO();
                inorganicWastageSummaryDTO.setProjectNo((String) row[0]);
                inorganicWastageSummaryDTO.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]));
                inorganicWastageSummaryDTO.setQty((double) row[2]);
                inorganicWastageSummaryDTO.setUom((String) row[3]);
                inorganicWastageSummaryDTO.setProductName((String) row[4]);

                inorganicWastageSummaryDTOList.add(inorganicWastageSummaryDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return inorganicWastageSummaryDTOList;
    }

    @Override
    public List<OWCMachineMstDTO> getOWCMachines(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OWCMachineMstDTO> OWCMachineMstDTOList = null;
        try {
            String sql = "SELECT PROJECTNO, MACHINE_ID, DESCRIPTION, STATUS FROM " + plant + "_OWC_MACHINE_MST " +
                    "WHERE PROJECTNO = :projectNo AND PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);


            List<Object[]> rows = query.list();
            OWCMachineMstDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                OWCMachineMstDTO owcMachineMstDTO = new OWCMachineMstDTO();
                owcMachineMstDTO.setProjectNo((String) row[0]);
                owcMachineMstDTO.setMachineId((String) row[1]);
                owcMachineMstDTO.setDescription((String) row[2]);
                owcMachineMstDTO.setStatus((String) row[3]);

                OWCMachineMstDTOList.add(owcMachineMstDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return OWCMachineMstDTOList;
    }

    @Override
    public ProjectInventory getProjectInventory(String plant, String projectNo, String product) {
        Session session = sessionFactory.openSession();
        ProjectInventory projectInventory = null;
        try {
            String sql = "SELECT PLANT, ID, PROJECTNO, TOTAL_QTY, PRODUCT, UOM, PROCESSED_QTY, PENDING_QTY FROM " +
                    plant + "_PROJECT_INVENTORY WHERE PLANT = :plant AND PROJECTNO = :projectNo AND product = :product";

            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);
            query.setParameter("product", product);

            Object[] row = (Object[]) query.uniqueResult();

            if(row != null) {
                projectInventory = ProjectInventory.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .totalQty((double) row[3])
                        .product((String) row[4])
                        .uom((String) row[5])
                        .processedQty((double) row[6])
                        .pendingQty((double) row[7])
                        .build();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return projectInventory;
    }

    @Override
    public Integer updateProjectInventory(String plant, ProjectInventory projectInventory, String product) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "UPDATE " + plant + "_PROJECT_INVENTORY SET TOTAL_QTY = :totalQty, UOM = :uom, " +
                    "PROCESSED_QTY = :processedQty, PENDING_QTY = :pendingQty, UPAT = :upAt, UPBY = :upBy " +
                    "WHERE PLANT = :plant AND PROJECTNO = :projectNo AND PRODUCT = :product";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            query.setParameter("plant", plant);
            query.setParameter("totalQty", projectInventory.getTotalQty());
            query.setParameter("uom", projectInventory.getUom());
            query.setParameter("projectNo", projectInventory.getProjectNo());
            query.setParameter("processedQty", projectInventory.getProcessedQty());
            query.setParameter("pendingQty", projectInventory.getPendingQty());
            query.setParameter("upAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("upBy", projectInventory.getUpBy());
            query.setParameter("product", product);

            result = query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public Integer saveProjectInventory(String plant, ProjectInventory projectInventory, String product) {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO " + plant + "_PROJECT_INVENTORY (PLANT, PROJECTNO, TOTAL_QTY, PRODUCT, UOM, " +
                    "PROCESSED_QTY, PENDING_QTY, CRAT, CRBY) VALUES (:plant, :projectNo, :totalQty, :product, :uom, " +
                    ":processedQty, :pendingQty, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectInventory.getProjectNo());
            query.setParameter("totalQty", projectInventory.getTotalQty());
            query.setParameter("product", product);
            query.setParameter("uom", projectInventory.getUom());
            query.setParameter("processedQty", projectInventory.getProcessedQty());
            query.setParameter("pendingQty", projectInventory.getPendingQty());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            result = query.executeUpdate();

            session.getTransaction().commit();


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public Integer saveProjectExternalInventory(String plant, ProjectExternalInventory projectExternalInventory, String product) {
        Session session = sessionFactory.openSession();
        Integer result = 0;

        try {
            String sql = "INSERT INTO " + plant + "_PROJECT_EXTERNAL_INVENTORY (PLANT, PROJECTNO, DATE, QTY, PRODUCT, UOM, " +
                    "CRAT, CRBY) VALUES (:plant, :projectNo, :date, :qty, :product, :uom, :crAt, :crBy)";

            session.beginTransaction();
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectExternalInventory.getProjectNo());
            query.setParameter("date", projectExternalInventory.getDate());
            query.setParameter("qty", projectExternalInventory.getQty());
            query.setParameter("product", projectExternalInventory.getProduct());
            query.setParameter("uom", projectExternalInventory.getUom());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", null);

            result = query.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally  {
            session.close();
        }
        return result;
    }

    @Override
    public List<OWCOutcomeProductDTO> getOWCOutcomeProducts(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<OWCOutcomeProductDTO> owcOutcomeProductDTOList = null;
        try{
            String sql = "SELECT (SELECT ITEMDESC FROM " + plant + "_ITEMMST WHERE " +
                    "ITEM = OWC.PRODUCT) AS PRODUCTNAME, SUM(QTY) AS QTY, UOM, OWC.PRODUCT AS PRODUCT FROM "+ plant +"_OWCOUTCOMEDET OWC " +
                    "WHERE PROJECTNO = :projectNo AND PLANT = :plant GROUP BY OWC.PRODUCT, UOM";
            Query query = session.createSQLQuery(sql);

            query.setParameter("projectNo", projectNo);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            owcOutcomeProductDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                OWCOutcomeProductDTO owcOutcomeProductDTO = new OWCOutcomeProductDTO();
                owcOutcomeProductDTO.setProductName((String) row[0]);
                owcOutcomeProductDTO.setQty((double) row[1]);
                owcOutcomeProductDTO.setUom((String) row[2]);
                owcOutcomeProductDTO.setProduct((String) row[3]);

                owcOutcomeProductDTOList.add(owcOutcomeProductDTO);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally  {
            session.close();
        }

        return owcOutcomeProductDTOList;
    }


}
