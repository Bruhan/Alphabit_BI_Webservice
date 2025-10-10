package com.owner.process.usecases.outlets_and_terminals.POSOutlets.dao;

import com.owner.process.persistence.models.POSOutlets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("POSOutletsDao")
public class POSOutletsDaoImpl implements POSOutletsDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public POSOutlets findByOUTLET(String plant, String outlet) {
        Session session = null;
        POSOutlets result = null;
        try {
            session = sessionFactory.openSession();
            String sql = "SELECT TOP (1) * FROM "+ plant +"_POSOUTLETS WHERE OUTLET = :outlet";
            NativeQuery query = session.createSQLQuery(sql).addEntity(POSOutlets.class);
            query.setParameter("outlet", outlet);
            result = (POSOutlets) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public List<POSOutlets> findAll(String plant) {
        Session session = null;
        List<POSOutlets> result = null;
        try {
            session = sessionFactory.openSession();
            String sql = "SELECT * FROM "+ plant +"_POSOUTLETS";
            NativeQuery query = session.createSQLQuery(sql).addEntity(POSOutlets.class);
            result = query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
}
