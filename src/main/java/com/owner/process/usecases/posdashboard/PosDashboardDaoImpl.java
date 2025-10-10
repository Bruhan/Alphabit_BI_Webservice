package com.owner.process.usecases.posdashboard;

import com.owner.process.persistence.models.*;
import com.owner.process.usecases.posdashboard.dto.InventoryDTO;
import com.owner.process.usecases.posdashboard.dto.InventoryQuantityDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("PosDashboardDao")
public class PosDashboardDaoImpl implements PosDashboardDao{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<PosSalesReport> getpsosalesreport(String plant) {
        Session session = sessionFactory.openSession();
        List<PosSalesReport> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' GROUP BY A.PAYMENTMODE";

          /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReport posSalesReport = new PosSalesReport();
                posSalesReport.setPAYMENTMODE((String) row[0]);
                posSalesReport.setBILLCOUNT((Integer) row[1]);
                posSalesReport.setAMOUNT((Double) row[2]);
                posSalesReport.setPERCENTAGE((Double) row[3]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReport> getpsosalesreportbyfate(String plant, String fromdate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReport> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReport posSalesReport = new PosSalesReport();
                posSalesReport.setPAYMENTMODE((String) row[0]);
                posSalesReport.setBILLCOUNT((Integer) row[1]);
                posSalesReport.setAMOUNT((Double) row[2]);
                posSalesReport.setPERCENTAGE((Double) row[3]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReport> getpsosalesreportbytodate(String plant, String todate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReport> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReport posSalesReport = new PosSalesReport();
                posSalesReport.setPAYMENTMODE((String) row[0]);
                posSalesReport.setBILLCOUNT((Integer) row[1]);
                posSalesReport.setAMOUNT((Double) row[2]);
                posSalesReport.setPERCENTAGE((Double) row[3]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReport> getpsosalesreportbydate(String plant, String fromdate, String todate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReport> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED'" +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE";

           /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReport posSalesReport = new PosSalesReport();
                posSalesReport.setPAYMENTMODE((String) row[0]);
                posSalesReport.setBILLCOUNT((Integer) row[1]);
                posSalesReport.setAMOUNT((Double) row[2]);
                posSalesReport.setPERCENTAGE((Double) row[3]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }


    @Override
    public List<PosSalesReportOutTerm> getpsosalesreportoutterm(String plant,String Outlet,String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTerm> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND B.PLANT = '"+plant+"'";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL";

          /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTerm posSalesReport = new PosSalesReportOutTerm();
                posSalesReport.setOUTLET((String) row[0]);
                posSalesReport.setTERMINAL((String) row[1]);
                posSalesReport.setPAYMENTMODE((String) row[2]);
                posSalesReport.setBILLCOUNT((Integer) row[3]);
                posSalesReport.setAMOUNT((Double) row[4]);
                posSalesReport.setPERCENTAGE((Double) row[5]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTerm> getpsosalesreportbyfateoutterm(String plant, String fromdate,String Outlet,String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTerm> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTerm posSalesReport = new PosSalesReportOutTerm();
                posSalesReport.setOUTLET((String) row[0]);
                posSalesReport.setTERMINAL((String) row[1]);
                posSalesReport.setPAYMENTMODE((String) row[2]);
                posSalesReport.setBILLCOUNT((Integer) row[3]);
                posSalesReport.setAMOUNT((Double) row[4]);
                posSalesReport.setPERCENTAGE((Double) row[5]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTerm> getpsosalesreportbytodateoutterm(String plant, String todate,String Outlet,String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTerm> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTerm posSalesReport = new PosSalesReportOutTerm();
                posSalesReport.setOUTLET((String) row[0]);
                posSalesReport.setTERMINAL((String) row[1]);
                posSalesReport.setPAYMENTMODE((String) row[2]);
                posSalesReport.setBILLCOUNT((Integer) row[3]);
                posSalesReport.setAMOUNT((Double) row[4]);
                posSalesReport.setPERCENTAGE((Double) row[5]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTerm> getpsosalesreportbydateoutterm(String plant, String fromdate, String todate,String Outlet,String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTerm> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL";

           /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTerm posSalesReport = new PosSalesReportOutTerm();
                posSalesReport.setOUTLET((String) row[0]);
                posSalesReport.setTERMINAL((String) row[1]);
                posSalesReport.setPAYMENTMODE((String) row[2]);
                posSalesReport.setBILLCOUNT((Integer) row[3]);
                posSalesReport.setAMOUNT((Double) row[4]);
                posSalesReport.setPERCENTAGE((Double) row[5]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

/*-----------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public List<PosSalesReportWithDate> getpsosalesreportwithdate(String plant) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' GROUP BY A.PAYMENTMODE,B.ORDDATE";

          /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportWithDate posSalesReport = new PosSalesReportWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setPAYMENTMODE((String) row[1]);
                posSalesReport.setBILLCOUNT((Integer) row[2]);
                posSalesReport.setAMOUNT((Double) row[3]);
                posSalesReport.setPERCENTAGE((Double) row[4]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportWithDate> getpsosalesreportbyfatewithdate(String plant, String fromdate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE,B.ORDDATE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportWithDate posSalesReport = new PosSalesReportWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setPAYMENTMODE((String) row[1]);
                posSalesReport.setBILLCOUNT((Integer) row[2]);
                posSalesReport.setAMOUNT((Double) row[3]);
                posSalesReport.setPERCENTAGE((Double) row[4]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportWithDate> getpsosalesreportbytodatewithdate(String plant, String todate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE,B.ORDDATE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportWithDate posSalesReport = new PosSalesReportWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setPAYMENTMODE((String) row[1]);
                posSalesReport.setBILLCOUNT((Integer) row[2]);
                posSalesReport.setAMOUNT((Double) row[3]);
                posSalesReport.setPERCENTAGE((Double) row[4]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportWithDate> getpsosalesreportbydatewithdate(String plant, String fromdate, String todate) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime) " +
                    "GROUP BY A.PAYMENTMODE,B.ORDDATE";

           /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportWithDate posSalesReport = new PosSalesReportWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setPAYMENTMODE((String) row[1]);
                posSalesReport.setBILLCOUNT((Integer) row[2]);
                posSalesReport.setAMOUNT((Double) row[3]);
                posSalesReport.setPERCENTAGE((Double) row[4]);


                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }


    @Override
    public List<PosSalesReportOutTermWithDate> getpsosalesreportouttermwithdate(String plant, String Outlet, String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTermWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND B.PLANT = '"+plant+"'";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL,B.ORDDATE";

          /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTermWithDate posSalesReport = new PosSalesReportOutTermWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setOUTLET((String) row[1]);
                posSalesReport.setTERMINAL((String) row[2]);
                posSalesReport.setPAYMENTMODE((String) row[3]);
                posSalesReport.setBILLCOUNT((Integer) row[4]);
                posSalesReport.setAMOUNT((Double) row[5]);
                posSalesReport.setPERCENTAGE((Double) row[6]);

                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTermWithDate> getpsosalesreportbyfateouttermwithdate(String plant, String fromdate, String Outlet, String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTermWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL,B.ORDDATE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTermWithDate posSalesReport = new PosSalesReportOutTermWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setOUTLET((String) row[1]);
                posSalesReport.setTERMINAL((String) row[2]);
                posSalesReport.setPAYMENTMODE((String) row[3]);
                posSalesReport.setBILLCOUNT((Integer) row[4]);
                posSalesReport.setAMOUNT((Double) row[5]);
                posSalesReport.setPERCENTAGE((Double) row[6]);

                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTermWithDate> getpsosalesreportbytodateouttermwithdate(String plant, String todate, String Outlet, String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTermWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL,B.ORDDATE";

            /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTermWithDate posSalesReport = new PosSalesReportOutTermWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setOUTLET((String) row[1]);
                posSalesReport.setTERMINAL((String) row[2]);
                posSalesReport.setPAYMENTMODE((String) row[3]);
                posSalesReport.setBILLCOUNT((Integer) row[4]);
                posSalesReport.setAMOUNT((Double) row[5]);
                posSalesReport.setPERCENTAGE((Double) row[6]);

                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<PosSalesReportOutTermWithDate> getpsosalesreportbydateouttermwithdate(String plant, String fromdate, String todate, String Outlet, String Terminal) {
        Session session = sessionFactory.openSession();
        List<PosSalesReportOutTermWithDate> PosSalesReportList = new ArrayList<>();
        try {
            String sql = "SELECT B.ORDDATE,B.OUTLET,B.TERMINAL,A.PAYMENTMODE,COUNT(A.DONO) AS BILLCOUNT,SUM(A.AMOUNT) AS AMOUNT,ROUND(SUM(A.AMOUNT) * 100.0 / SUM(SUM(A.AMOUNT)) OVER (), 2) " +
                    "AS PERCENTAGE FROM "+plant+"_POS_PAYMODE_AMOUNT AS A LEFT JOIN "+plant+"_DOHDR AS B ON A.DONO = B.DONO WHERE B.ORDER_STATUS != 'CANCELLED' AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) >= CAST('"+fromdate+"' AS datetime) AND " +
                    "CAST((SUBSTRING(B.ORDDATE, 7, 4) + '-' + SUBSTRING(B.ORDDATE, 4, 2) + '-' + SUBSTRING(B.ORDDATE, 1, 2)) AS datetime) <= CAST('"+todate+"' AS datetime)  ";
            if(Outlet.length() > 0){
                sql +=" AND B.OUTLET = '"+Outlet+"'";
            }
            if(Terminal.length() > 0){
                sql +=" AND B.TERMINAL = '"+Terminal+"'";
            }

            sql +=" GROUP BY A.PAYMENTMODE,B.OUTLET,B.TERMINAL,B.ORDDATE";

           /*  PosSalesReportList = session.createSQLQuery(sql)
                    .addEntity(PosSalesReport.class)
                    .list();*/

            Query query = session.createSQLQuery(sql);
            List<Object[]> rows = query.list();


            for(Object[] row: rows) {
                PosSalesReportOutTermWithDate posSalesReport = new PosSalesReportOutTermWithDate();
                posSalesReport.setORDDATE((String) row[0]);
                posSalesReport.setOUTLET((String) row[1]);
                posSalesReport.setTERMINAL((String) row[2]);
                posSalesReport.setPAYMENTMODE((String) row[3]);
                posSalesReport.setBILLCOUNT((Integer) row[4]);
                posSalesReport.setAMOUNT((Double) row[5]);
                posSalesReport.setPERCENTAGE((Double) row[6]);

                PosSalesReportList.add(posSalesReport);
            }



            return PosSalesReportList;
        }
        catch (Exception ex) {
            throw ex;
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<InventoryDTO> getInventory(String plant, String item, String loc, int offset, Integer productsCount) {
        Session session = sessionFactory.openSession();
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT INV.ITEM, IT.ITEMDESC, INV.LOC, INV.QTY ")
                    .append("FROM ").append(plant).append("_INVMST INV ")
                    .append("LEFT JOIN ").append(plant).append("_ITEMMST IT ON INV.ITEM = IT.ITEM ")
                    .append("WHERE INV.PLANT = :plant AND INV.QTY != 0 ");

            // add optional filters
            if (item != null && !item.isEmpty()) {
                sql.append("AND INV.ITEM = :item ");
            }
            if (loc != null && !loc.isEmpty()) {
                sql.append("AND INV.LOC = :loc ");
            }

            sql.append("ORDER BY INV.ITEM OFFSET :offset ROWS FETCH NEXT :productsCount ROWS ONLY");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("plant", plant);

            if (item != null && !item.isEmpty()) {
                query.setParameter("item", item);
            }
            if (loc != null && !loc.isEmpty()) {
                query.setParameter("loc", loc);
            }

            query.setParameter("offset", offset);
            query.setParameter("productsCount", productsCount);

            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                InventoryDTO inventoryDTO = new InventoryDTO();
                inventoryDTO.setItem((String) row[0]);
                inventoryDTO.setItemDesc((String) row[1]);
                inventoryDTO.setLoc((String) row[2]);
                inventoryDTO.setQty(((BigDecimal) row[3]).doubleValue());

                inventoryDTOList.add(inventoryDTO);
            }

            return inventoryDTOList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public Long countInventory(String plant, String item, String loc) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT " +
                    "COUNT(*) AS totalCount " +
                    "FROM " + plant + "_INVMST " +
                    "WHERE PLANT = :plant AND QTY != 0 ";

            if (item != null && !item.isEmpty()) {
                sql += " AND ITEM = :item ";
            }
            if (loc != null && !loc.isEmpty()) {
                sql += " AND LOC = :loc ";
            }

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);

            if (item != null && !item.isEmpty()) {
                query.setParameter("item", item);
            }
            if (loc != null && !loc.isEmpty()) {
                query.setParameter("loc", loc);
            }

            return ((Number) query.uniqueResult()).longValue();

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public List<InventoryQuantityDTO> getInventoryQuantities(String plant, String item, String loc) {
        Session session = sessionFactory.openSession();
        List<InventoryQuantityDTO> inventoryQuantityDTOList = new ArrayList<>();
        try {
            String sql = "SELECT LOC, SUM(QTY) AS QTY " +
                    "FROM " + plant + "_INVMST " +
                    "WHERE PLANT = :plant AND QTY != 0 ";

            if (item != null && !item.isEmpty()) {
                sql += " AND ITEM = :item ";
            }
            if (loc != null && !loc.isEmpty()) {
                sql += " AND LOC = :loc ";
            }

            sql += " GROUP BY LOC ";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);

            if (item != null && !item.isEmpty()) {
                query.setParameter("item", item);
            }
            if (loc != null && !loc.isEmpty()) {
                query.setParameter("loc", loc);
            }

            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                InventoryQuantityDTO inventoryQuantityDTO = new InventoryQuantityDTO();

                inventoryQuantityDTO.setLoc((String) row[0]);
                inventoryQuantityDTO.setQty(((BigDecimal) row[1]).doubleValue());

                inventoryQuantityDTOList.add(inventoryQuantityDTO);
            }

            return inventoryQuantityDTOList;

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
}
