package com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals.dao;

import com.owner.process.persistence.models.POSOutletsTerminals;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("POSOutletsTerminalsDao")
public class POSOutletsTerminalsDaoImpl implements POSOutletsTerminalsDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public POSOutletsTerminals findByOUTLETAndTERMINAL(String plant, String outlet, String terminal) {
        POSOutletsTerminals posOutletsTerminals = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            String sqlQuery = "SELECT TOP 1 * FROM [" + plant + "_POSOUTLETSTERMINALS] WHERE OUTLET = :outlet AND TERMINAL = :terminal AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery).addEntity(POSOutletsTerminals.class);
            query.setParameter("outlet", outlet);
            query.setParameter("terminal", terminal);
            query.setParameter("plant", plant);
            posOutletsTerminals = (POSOutletsTerminals) query.uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            throw ex;
        } finally {
            session.close();
        }
        return posOutletsTerminals;
    }


    @Override
    public List<POSOutletsTerminals> findBydevicename(String plant, Short devicestatus, String devicename) {
        List<POSOutletsTerminals> posOutletsTerminalsList = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            String sqlQuery = "SELECT * FROM [" + plant + "_POSOUTLETSTERMINALS] WHERE ISNULL(DEVICE_STATUS,'0') = :devicestatus AND DEVICE_NAME = :devicename AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery).addEntity(POSOutletsTerminals.class);
            query.setParameter("devicestatus", devicestatus);
            query.setParameter("devicename", devicename);
            query.setParameter("plant", plant);
            posOutletsTerminalsList = query.list();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            throw ex;
        } finally {
            session.close();
        }
        return posOutletsTerminalsList;
    }


    @Override
    public List<POSOutletsTerminals> findByDnameAndTerminal(String plant, Short devicestatus, String devicename, String terminal) {
        List<POSOutletsTerminals> posOutletsTerminalsList = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            String sqlQuery = "SELECT * FROM [" + plant + "_POSOUTLETSTERMINALS] WHERE ISNULL(DEVICE_STATUS,'0') = :devicestatus AND DEVICE_NAME = :devicename AND TERMINAL = :terminal AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery).addEntity(POSOutletsTerminals.class);
            query.setParameter("devicestatus", devicestatus);
            query.setParameter("devicename", devicename);
            query.setParameter("terminal", terminal);
            query.setParameter("plant", plant);
            posOutletsTerminalsList = query.list();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            throw ex;
        } finally {
            session.close();
        }
        return posOutletsTerminalsList;
    }


    @Override
    public List<POSOutletsTerminals> findByOUTLET(String plant, String outlet) {
        List<POSOutletsTerminals> posOutletsTerminalsList = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            String sqlQuery = "SELECT * FROM [" + plant + "_POSOUTLETSTERMINALS] WHERE OUTLET = :outlet AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery).addEntity(POSOutletsTerminals.class);
            query.setParameter("outlet", outlet);
            query.setParameter("plant", plant);
            posOutletsTerminalsList = query.list();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            throw ex;
        } finally {
            session.close();
        }
        return posOutletsTerminalsList;
    }


    @Override
    public List<POSOutletsTerminals> findByoutletandstatus(String plant, Short devicestatus, String outlet) {
        List<POSOutletsTerminals> posOutletsTerminalsList = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            String sqlQuery = "SELECT * FROM [" + plant + "_POSOUTLETSTERMINALS] WHERE ISNULL(DEVICE_STATUS,'0') = :devicestatus AND OUTLET = :outlet AND PLANT = :plant";
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery).addEntity(POSOutletsTerminals.class);
            query.setParameter("devicestatus", devicestatus);
            query.setParameter("outlet", outlet);
            query.setParameter("plant", plant);
            posOutletsTerminalsList = query.list();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            throw ex;
        } finally {
            session.close();
        }
        return posOutletsTerminalsList;
    }


    @Override
    public void addPOSOutletsTerminals(POSOutletsTerminals posOutletsTerminals) {
        Session session = sessionFactory.openSession();
        try {
            String plant = posOutletsTerminals.getPLANT();
            String sqlQuery = "INSERT INTO [" + plant + "_POSOUTLETSTERMINALS] " +
                    "(PLANT, OUTLET, TERMINAL, TERMINAL_NAME, IsActive, DEVICE_STATUS, DEVICE_NAME, " +
                    "FLOATAMOUNT, CRAT, CRBY, UPAT, UPBY, isAllowVariant, allowVariant) " +
                    "VALUES (:plant, :outlet, :terminal, :terminalName, :isActive, :deviceStatus, :deviceName, " +
                    ":floatAmount, :crat, :crby, :upat, :upby, :isAllowVariant, :allowVariant)";

            System.out.println(sqlQuery);

            session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery);
            query.setParameter("plant", posOutletsTerminals.getPLANT());
            query.setParameter("outlet", posOutletsTerminals.getOUTLET());
            query.setParameter("terminal", posOutletsTerminals.getTERMINAL());
            query.setParameter("terminalName", posOutletsTerminals.getTERMINAL_NAME());
            query.setParameter("isActive", posOutletsTerminals.getIsActive());
            query.setParameter("deviceStatus", posOutletsTerminals.getDEVICE_STATUS());
            query.setParameter("deviceName", posOutletsTerminals.getDEVICE_NAME());
            query.setParameter("floatAmount", posOutletsTerminals.getFLOATAMOUNT());
            query.setParameter("crat", posOutletsTerminals.getCRAT());
            query.setParameter("crby", posOutletsTerminals.getCRBY());
            query.setParameter("upat", posOutletsTerminals.getUPAT());
            query.setParameter("upby", posOutletsTerminals.getUPBY());
            query.setParameter("isAllowVariant", posOutletsTerminals.getISALLOWVARIANT());
            query.setParameter("allowVariant", posOutletsTerminals.getALLOWVARIANT());

            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

}
