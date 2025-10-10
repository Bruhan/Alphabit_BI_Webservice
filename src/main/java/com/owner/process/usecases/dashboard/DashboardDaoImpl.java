package com.owner.process.usecases.dashboard;

import com.owner.process.persistence.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository("DashboardDao")
public class DashboardDaoImpl implements DashboardDao{

    @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<CustomerMst> getallCustmer(String plant,String search) {
        Session session = sessionFactory.openSession();
        List<CustomerMst> CustomerMstList = null;
        try {
            String sql = "SELECT * FROM "+ plant + "_CUSTMST WHERE PLANT = '" + plant + "'";
            if(search.length() != 0){
                sql += " AND (ISNULL(CUSTNO, '') LIKE '%"+search+"%' OR ISNULL(CNAME, '') LIKE '%"+search+"%')";
            }

            CustomerMstList = session.createSQLQuery(sql)
                    .addEntity(CustomerMst.class)
                    .list();
            return CustomerMstList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }
    public List<LocationMst> getallLocation(String plant,String search) {
        Session session = sessionFactory.openSession();
        List<LocationMst> LocationMstList = null;
        try {
            String sql = "SELECT ID,PLANT,LOC,LOCDESC FROM "+ plant + "_LOCMST WHERE PLANT = '" + plant + "' AND IsActive = 'Y'";
            if(search.length() != 0){
                sql += " AND (ISNULL(LOC, '') LIKE '%"+search+"%' OR ISNULL(LOCDESC, '') LIKE '%"+search+"%')";
            }

            LocationMstList = session.createSQLQuery(sql)
                    .addEntity(LocationMst.class)
                    .list();
            return LocationMstList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<PrdTypeMaster> getallSubcategory(String plant,String search) {
        Session session = sessionFactory.openSession();
        List<PrdTypeMaster> PrdTypeMasterList = null;
        try {
            String sql = "SELECT PRD_TYPE_ID,PRD_TYPE_DESC FROM "+ plant + "_PRD_TYPE_MST WHERE PLANT = '" + plant + "' AND IsActive = 'Y'";
            if(search.length() != 0){
                sql += " AND (ISNULL(PRD_TYPE_ID, '') LIKE '%"+search+"%' OR ISNULL(PRD_TYPE_DESC, '') LIKE '%"+search+"%')";
            }

            PrdTypeMasterList = session.createSQLQuery(sql)
                    .addEntity(PrdTypeMaster.class)
                    .list();
            return PrdTypeMasterList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }
    public List<EmpExecPojo> getallemployee(String plant, String search,String projecttype) {
        Session session = sessionFactory.openSession();
        List<EmpExecPojo> EmpExecPojoList = null;
        try {
            String sql = "SELECT ID,PLANT,EMPNO,FNAME FROM "+ plant + "_EMP_MST WHERE PLANT = '" + plant + "' AND DESGINATION = 'Executive' ";
            if(search.length() != 0){
                sql += " AND (ISNULL(EMPNO, '') LIKE '%"+search+"%' OR ISNULL(FNAME, '') LIKE '%"+search+"%')";
            }

            if(projecttype.length() != 0){
                sql += " AND EMPNO IN (SELECT SAFETYSUPERVISOR FROM C9361893885S2T_FINRECYCLEPROJECT WHERE PROJECT_SITETYPE = '"+projecttype+"')";
            }

            EmpExecPojoList = session.createSQLQuery(sql)
                    .addEntity(EmpExecPojo.class)
                    .list();
            return EmpExecPojoList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }
    public List<RecycleProject> getallproject(String plant,String custno,String location, String executive,String projectType, String search) {
        Session session = sessionFactory.openSession();
        List<RecycleProject> RecycleProjectList = null;
        try {
            String sql = "SELECT ID,PLANT,CUSTNO,PROJECT,PROJECT_NAME,ISNULL(LOC,'') AS LOC FROM "+ plant + "_FINRECYCLEPROJECT WHERE PLANT = '" + plant + "' ";
            if(custno.length() != 0){
                sql += " AND CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND LOC = '"+location+"'";
            }
            if(projectType.length() != 0){
                sql += " AND PROJECT_SITETYPE = '"+projectType+"'";
            }
            if(executive.length() != 0){
                sql += " AND SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(search.length() != 0){
                sql += " AND (ISNULL(PROJECT, '') LIKE '%"+search+"%' OR ISNULL(PROJECT_NAME, '') LIKE '%"+search+"%')";
            }


            RecycleProjectList = session.createSQLQuery(sql)
                    .addEntity(RecycleProject.class)
                    .list();
            return RecycleProjectList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<WastageDateWeight> getIncommingWaste(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        List<WastageDateWeight> WastageDateWeightList = new ArrayList<WastageDateWeight>();
        try {
            String sql = "SELECT A.DATE,ROUND(SUM(A.WASTAGE_QTY),2) AS TOTALQTY FROM "+plant+"_DAILY_WASTAGE_DETAILS_DET AS A " +
                    "LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

            sql += " GROUP BY A.PLANT,A.DATE ORDER BY A.DATE ASC";
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                WastageDateWeight wastageDateWeight = new WastageDateWeight();
                wastageDateWeight.setDate(new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[0]));
                wastageDateWeight.setTotalqty((double) row[1]);

                WastageDateWeightList.add(wastageDateWeight);
            }
            return WastageDateWeightList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<WastageTypeWeight> getIncommingWasteBytype(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        List<WastageTypeWeight> WastageTypeWeightList = new ArrayList<WastageTypeWeight>();
        try {
            String sql = "SELECT A.WASTAGE_TYPE,A.WASTAGE_UOM,ROUND(SUM(A.WASTAGE_QTY),2) AS TOTALQTY FROM "+plant+"_DAILY_WASTAGE_DETAILS_DET AS A " +
                    "LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

            sql += " GROUP BY A.PLANT,A.WASTAGE_TYPE,A.WASTAGE_UOM ORDER BY A.WASTAGE_TYPE ASC";
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                WastageTypeWeight wastageTypeWeight = new WastageTypeWeight();
                wastageTypeWeight.setWastageType((String) row[0]);
                wastageTypeWeight.setUom((String) row[1]);
                wastageTypeWeight.setTotalqty((double) row[2]);

                WastageTypeWeightList.add(wastageTypeWeight);
            }
            return WastageTypeWeightList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public double getOrganicWaste(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        Double totalquantity = 0.0;
        try {
            String sql = "SELECT ROUND(SUM(A.QTY),2) AS TOTALQTY FROM "+plant+"_ORGANIC_WASTE_DET AS A LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON " +
                    "A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Double.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public double getInOrganicWaste(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        Double totalquantity = 0.0;
        try {
            String sql = "SELECT ROUND(SUM(A.QTY),2) AS TOTALQTY FROM "+plant+"_INORGANIC_WASTE_DET AS A LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON " +
                    "A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Double.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }


    public List<ProductWastageWeight> getSgtInorganicWastePro(String plant, String fromdate, String todate, String custno, String location,String executive, String project,String subcategory, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        List<ProductWastageWeight> ProductWastageWeightList = new ArrayList<ProductWastageWeight>();
        try {
            String sql = "SELECT ISNULL(A.ITEM,'') AS ITEM,ISNULL(I.ITEMDESC,'') AS ITEMDESC,ROUND(SUM(A.QTY),2) AS TOTALQTY FROM "+plant+"_INORGANIC_WASTE_DET AS A " +
                    "LEFT JOIN "+plant+"_ITEMMST AS I ON A.ITEM=I.ITEM LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(subcategory.length() != 0){
                sql += " AND I.ITEMTYPE = '"+subcategory+"'";
            }

            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

            sql += " GROUP BY A.PLANT,A.ITEM,I.ITEMDESC ORDER BY A.ITEM ASC";
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                ProductWastageWeight productWastageWeight = new ProductWastageWeight();
                productWastageWeight.setItem((String) row[0]);
                productWastageWeight.setItemDesc((String) row[1]);
                productWastageWeight.setTotalqty((double) row[2]);

                ProductWastageWeightList.add(productWastageWeight);
            }
            return ProductWastageWeightList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public double getBiogasData(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        Double totalquantity = 0.0;
        try {
            String sql = "SELECT ROUND(ISNULL(SUM(A.QTY),0),2) AS TOTALQTY FROM "+plant+"_BIOGASDET AS A LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON " +
                    "A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }

            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Double.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public double getOWCdata(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        Double totalquantity = 0.0;
        try {
            String sql = "SELECT ROUND(ISNULL(SUM(A.QTY),0),2) AS TOTALQTY FROM "+plant+"_OWCDET AS A LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON " +
                    "A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Double.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public double getCattlefeeddata(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        Double totalquantity = 0.0;
        try {
            String sql = "SELECT ROUND(ISNULL(SUM(A.QTY),0),2) AS TOTALQTY FROM "+plant+"_COMPOSECATTLEFEEDDET AS A LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON " +
                    "A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Double.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<WastageTypeWeight> getRejectedWasteBytype(String plant, String fromdate, String todate, String custno, String location,String executive, String project, String projecttype) throws ParseException {
        Session session = sessionFactory.openSession();
        List<WastageTypeWeight> WastageTypeWeightList = new ArrayList<WastageTypeWeight>();
        try {
            /*String sql = "SELECT A.WASTAGE_TYPE,ISNULL(A.UOM,'') AS WASTAGE_UOM,ROUND(SUM(A.QTY),2) AS TOTALQTY FROM "+plant+"_REJECTED_WASTE_DET AS A " +
                    "LEFT JOIN "+plant+"_FINRECYCLEPROJECT AS B ON A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"' ";
            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

            sql += " GROUP BY A.PLANT,A.WASTAGE_TYPE,A.UOM ORDER BY A.WASTAGE_TYPE ASC";*/
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/


            String sql = "SELECT C.WASTAGE_TYPE,ISNULL(C.UOM,'') AS WASTAGE_UOM,ROUND(SUM(C.QTY),2) AS TOTALQTY FROM (SELECT A.PLANT,CASE WHEN B.PROJECT_SITETYPE='OFF-SITE' " +
                    "THEN (CASE WHEN A.WASTAGE_TYPE = 'REJECTED_WASTE' THEN 'INCINERATION' ELSE A.WASTAGE_TYPE END) ELSE A.WASTAGE_TYPE END AS WASTAGE_TYPE ,A.UOM," +
                    "A.QTY,A.DATE,B.CUSTNO, B.LOC,B.SAFETYSUPERVISOR,B.PROJECT,B.PROJECT_SITETYPE FROM "+plant+"_REJECTED_WASTE_DET AS A LEFT " +
                    "JOIN "+plant+"_FINRECYCLEPROJECT AS B ON A.PROJECTNO=B.PROJECT WHERE A.PLANT='"+plant+"'";

            if(fromdate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(fromdate);
                java.sql.Date sqlDatef = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE >= '"+sqlDatef+"'";
            }
            if(todate.length() != 0){
                java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
                java.sql.Date sqlDatet = new java.sql.Date(utilDate.getTime());
                sql += " AND A.DATE <= '"+sqlDatet+"'";
            }
            if(custno.length() != 0){
                sql += " AND B.CUSTNO = '"+custno+"'";
            }
            if(location.length() != 0){
                sql += " AND B.LOC = '"+location+"'";
            }
            if(executive.length() != 0){
                sql += " AND B.SAFETYSUPERVISOR = '"+executive+"'";
            }
            if(project.length() != 0){
                sql += " AND B.PROJECT = '"+project+"'";
            }
            if(projecttype.length() != 0){
                sql += " AND B.PROJECT_SITETYPE = '"+projecttype+"'";
            }

            sql += " ) C GROUP BY C.PLANT,C.WASTAGE_TYPE,C.UOM ORDER BY C.WASTAGE_TYPE ASC";

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                WastageTypeWeight wastageTypeWeight = new WastageTypeWeight();
                wastageTypeWeight.setWastageType((String) row[0]);
                wastageTypeWeight.setUom((String) row[1]);
                wastageTypeWeight.setTotalqty((double) row[2]);

                WastageTypeWeightList.add(wastageTypeWeight);
            }
            return WastageTypeWeightList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<AttDateValue> getAttendenceByProject(String plant, String fromdate, String todate, String project,String projectsitetype,String executive,String custno) throws ParseException {
        Session session = sessionFactory.openSession();
        List<AttDateValue> AttDateValueList = new ArrayList<AttDateValue>();
        try {
           /* String sql = " SELECT B.Att_Date AS ATT_DATE,SUM(B.VALUE) AS VALUE FROM "+plant+"_PROJECT_WORKEMPLOYEE AS A LEFT JOIN (Select E.EMPNO,SUM(Case when (ShiftStatus Like" +
                    " '%IN%' OR ShiftStatus Like '%OUT%' ) THEN 1 WHEN  (ShiftStatus Like '%ABS%'  ) THEN 0  WHEN  (ShiftStatus Like '%HOL%'  ) THEN 0" +
                    " WHEN  (ShiftStatus Like '%WEEK%'  ) THEN 0 ELSE 0 END) AS VALUE,ATT_DATE  from "+plant+"_Staffattendance V LEFT JOIN "+plant+"_EMP_MST E ON E.ID=V.EMPID  WHERE ATT_DATE" +
                    " BETWEEN '"+fromdate+"' and '"+todate+"' GROUP BY ATT_DATE,E.EMPNO) AS B ON B.EMPNO = A.EMP_ID WHERE A.PLANT='"+plant+"' ";*/


            /*String sql ="SELECT A.ATT_DATE,SUM(A.VALUE)  AS VALUE FROM (SELECT PLANT,EmpID, (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1') AS PROJECTNO," +
                    "(SELECT PROJECT_SITETYPE FROM  "+plant+"_FINRECYCLEPROJECT AS R WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W " +
                    "WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS PROJECTSITETYPE,(SELECT SAFETYSUPERVISOR FROM  C9361893885S2T_FINRECYCLEPROJECT AS R " +
                    "WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM C9361893885S2T_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS EXECUTIVE," +
                    "CASE WHEN (ShiftStatus LIKE '%IN%' OR ShiftStatus LIKE '%OUT%') THEN 1 WHEN (ShiftStatus LIKE '%ABS%') THEN 0 " +
                    "WHEN (ShiftStatus LIKE '%HOL%') THEN 0 WHEN (ShiftStatus LIKE '%WEEK%') THEN 0 ELSE 0 END AS VALUE, ATT_DATE as ATT_DATE FROM "+plant+"_Staffattendance V WHERE " +
                    "ATT_DATE BETWEEN '"+fromdate+"' AND '"+todate+"') AS A WHERE A.PLANT='"+plant+"'";*/

            /*String sql ="SELECT A.ATT_DATE,SUM(A.VALUE)  AS VALUE FROM (SELECT PLANT,EmpID, (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1') AS PROJECTNO," +
                    "(SELECT PROJECT_SITETYPE FROM  "+plant+"_FINRECYCLEPROJECT AS R WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W " +
                    "WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS PROJECTSITETYPE,(SELECT CUSTNO FROM  "+plant+"_FINRECYCLEPROJECT AS R WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W " +
                    "WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS CUSTNO,(SELECT SAFETYSUPERVISOR FROM  C9361893885S2T_FINRECYCLEPROJECT AS R " +
                    "WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM C9361893885S2T_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS EXECUTIVE," +
                    "CASE WHEN (ShiftStatus LIKE '%IN%') THEN 1 WHEN (ShiftStatus LIKE '%ABS%') THEN 0 " +
                    "WHEN (ShiftStatus LIKE '%HOL%') THEN 0 WHEN (ShiftStatus LIKE '%WEEK%') THEN 0 ELSE 0 END AS VALUE, ATT_DATE as ATT_DATE FROM "+plant+"_Staffattendance V WHERE " +
                    "ATT_DATE BETWEEN '"+fromdate+"' AND '"+todate+"') AS A WHERE A.PLANT='"+plant+"'";*/
            String sql ="SELECT A.ATT_DATE,SUM(A.VALUE)  AS VALUE FROM (SELECT PLANT,EmpID, (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1') AS PROJECTNO," +
                    "(SELECT PROJECT_SITETYPE FROM  "+plant+"_FINRECYCLEPROJECT AS R WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W " +
                    "WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS PROJECTSITETYPE,(SELECT CUSTNO FROM  "+plant+"_FINRECYCLEPROJECT AS R WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM "+plant+"_PROJECT_WORKERLIST AS W " +
                    "WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS CUSTNO,(SELECT SAFETYSUPERVISOR FROM  C9361893885S2T_FINRECYCLEPROJECT AS R " +
                    "WHERE R.PROJECT = (SELECT TOP 1 PROJECTNO FROM C9361893885S2T_PROJECT_WORKERLIST AS W WHERE W.EMPID= V.EMPID AND W.STATUS = '1')) AS EXECUTIVE," +
                    "1 AS VALUE, ATT_DATE as ATT_DATE FROM "+plant+"_Staffattendance V WHERE " +
                    "ATT_DATE BETWEEN '"+fromdate+"' AND '"+todate+"' AND ShiftStatus='MIN' GROUP BY PLANT,EMPID,ATT_DATE) AS A WHERE A.PLANT='"+plant+"'";



            if(project.length() != 0){
                sql += " AND A.PROJECTNO = '"+project+"'";
            }
            if(projectsitetype.length() != 0){
                sql += " AND A.PROJECTSITETYPE = '"+projectsitetype+"'";
            }
            if(executive.length() != 0){
                sql += " AND A.EXECUTIVE = '"+executive+"'";
            }
            if(custno.length() != 0){
                sql += " AND A.CUSTNO = '"+custno+"'";
            }

            sql += " GROUP BY A.ATT_DATE";
           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                AttDateValue attDateValue = new AttDateValue();
                attDateValue.setAttDate((Date) row[0]);
                attDateValue.setValue((Integer) row[1]);

                AttDateValueList.add(attDateValue);
            }
            return AttDateValueList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public List<AttDateValue> getAttendenceByDate(String plant, String fromdate, String todate) throws ParseException {
        Session session = sessionFactory.openSession();
        List<AttDateValue> AttDateValueList = new ArrayList<AttDateValue>();
        try {
            /*String sql = "Select SUM(Case when (ShiftStatus Like '%IN%' OR ShiftStatus Like '%OUT%' ) THEN 1 WHEN  (ShiftStatus Like '%ABS%'  ) THEN 0  WHEN  (ShiftStatus Like '%HOL%'  ) THEN 0" +
                    " WHEN  (ShiftStatus Like '%WEEK%'  ) THEN 0 ELSE 0 END) AS VALUE,ATT_DATE  from "+plant+"_Staffattendance V LEFT JOIN "+plant+"_EMP_MST E ON E.ID=V.EMPID  WHERE ATT_DATE " +
                    " BETWEEN '"+fromdate+"' and '"+todate+"' GROUP BY ATT_DATE";*/

            String sql = "Select SUM(Case when (ShiftStatus Like '%IN%') THEN 1 WHEN  (ShiftStatus Like '%ABS%'  ) THEN 0  WHEN  (ShiftStatus Like '%HOL%'  ) THEN 0" +
                    " WHEN  (ShiftStatus Like '%WEEK%'  ) THEN 0 ELSE 0 END) AS VALUE,ATT_DATE  from "+plant+"_Staffattendance V LEFT JOIN "+plant+"_EMP_MST E ON E.ID=V.EMPID  WHERE ATT_DATE " +
                    " BETWEEN '"+fromdate+"' and '"+todate+"' GROUP BY ATT_DATE";

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                AttDateValue attDateValue = new AttDateValue();
                attDateValue.setAttDate((Date)row[1]);
                attDateValue.setValue((Integer) row[0]);

                AttDateValueList.add(attDateValue);
            }
            return AttDateValueList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public Integer GetleadGenerationCount(String plant, String fromdate, String todate) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer totalquantity = 0;
        try {
            String sql = "SELECT COUNT(*)  FROM "+plant+"_CONTACTHDR WHERE PLANT='"+plant+"'";
            if(fromdate.length() != 0){
                sql += " AND CAST((SUBSTRING(CRAT, 1, 4) + '-' + SUBSTRING(CRAT, 5, 2) + '-' + SUBSTRING(CRAT, 7, 2)) AS date) >= '"+fromdate+"'";
            }
            if(todate.length() != 0){
                sql += " AND CAST((SUBSTRING(CRAT, 1, 4) + '-' + SUBSTRING(CRAT, 5, 2) + '-' + SUBSTRING(CRAT, 7, 2)) AS date) <= '"+todate+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Integer.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public Integer GetProjectCount(String plant, String fromdate, String todate) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer totalquantity = 0;
        try {
            String sql = "SELECT COUNT(*)  FROM "+plant+"_FINRECYCLEPROJECT WHERE PLANT='"+plant+"' and ISNULL(PROJECT_DATE,'')<>''";
            if(fromdate.length() != 0){
                sql += " AND CAST((SUBSTRING(PROJECT_DATE, 7, 4) + '-' + SUBSTRING(PROJECT_DATE, 4, 2) + '-' + SUBSTRING(PROJECT_DATE, 1, 2)) AS date) >= '"+fromdate+"'";
            }
            if(todate.length() != 0){
                sql += " AND CAST((SUBSTRING(PROJECT_DATE, 7, 4) + '-' + SUBSTRING(PROJECT_DATE, 4, 2) + '-' + SUBSTRING(PROJECT_DATE, 1, 2)) AS date) <= '"+todate+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Integer.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public Integer GetQuatationCount(String plant, String fromdate, String todate) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer totalquantity = 0;
        try {
            String sql = "SELECT COUNT(*)  FROM "+plant+"_FINRECYCLEQUOTATION WHERE PLANT='"+plant+"'";
            if(fromdate.length() != 0){
                sql += " AND CAST((SUBSTRING(QUOTATION_DATE, 7, 4) + '-' + SUBSTRING(QUOTATION_DATE, 4, 2) + '-' + SUBSTRING(QUOTATION_DATE, 1, 2)) AS date) >= '"+fromdate+"'";
            }
            if(todate.length() != 0){
                sql += " AND CAST((SUBSTRING(QUOTATION_DATE, 7, 4) + '-' + SUBSTRING(QUOTATION_DATE, 4, 2) + '-' + SUBSTRING(QUOTATION_DATE, 1, 2)) AS date) <= '"+todate+"'";
            }

           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Integer.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    public Integer GetQuotationOpenCount(String plant, String fromdate, String todate) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer totalquantity = 0;
        try {
            String sql = "SELECT COUNT(*)  FROM "+plant+"_FINRECYCLEQUOTATION WHERE PLANT='"+plant+"' AND PROJECT_STATUS = 'Open'";
            if(fromdate.length() != 0){
                sql += " AND CAST((SUBSTRING(QUOTATION_DATE, 7, 4) + '-' + SUBSTRING(QUOTATION_DATE, 4, 2) + '-' + SUBSTRING(QUOTATION_DATE, 1, 2)) AS date) >= '"+fromdate+"'";
            }
            if(todate.length() != 0){
                sql += " AND CAST((SUBSTRING(QUOTATION_DATE, 7, 4) + '-' + SUBSTRING(QUOTATION_DATE, 4, 2) + '-' + SUBSTRING(QUOTATION_DATE, 1, 2)) AS date) <= '"+todate+"'";
            }


           /* WastageDateWeightList = session.createSQLQuery(sql)
                    .addEntity(WastageDateWeight.class)
                    .list();*/
            NativeQuery<?> query = session.createSQLQuery(sql);
            totalquantity = Integer.valueOf(query.uniqueResult().toString());
            return totalquantity;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }
}
