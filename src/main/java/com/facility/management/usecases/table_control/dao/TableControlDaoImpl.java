package com.facility.management.usecases.table_control.dao;

import com.facility.management.persistence.models.TableControl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("TableControlDao")
public class TableControlDaoImpl implements TableControlDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void update(TableControl val) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            session.beginTransaction();

            // Construct the SQL query for insertion
            String sqlQuery = "UPDATE [" + val.getPlant() + "_TBLCONTROL] SET "+
                    "PLANT = :plant, FUNC = :func, DESCRIPTION = :description, PREFIX = :prefix, MINSEQ = :minSeq, MAXSEQ = :maxSeq, NXTSEQ = :nxtSeq, CRAT = :crAt, CRBY = :crBy, UPAT = :upAt, UPBY = :upBy " +
                    "WHERE ID = :id";
            // Set parameters for the SQL query
            session.createSQLQuery(sqlQuery)
                    .setParameter("plant", val.getPlant())
                    .setParameter("func", val.getFunc())
                    .setParameter("description", val.getDescription())
                    .setParameter("prefix", val.getPrefix())
                    .setParameter("minSeq", val.getMinSeq())
                    .setParameter("maxSeq", val.getMaxSeq())
                    .setParameter("nxtSeq", val.getNxtSeq())
                    .setParameter("crAt", val.getCrAt())
                    .setParameter("crBy", val.getCrBy())
                    .setParameter("upAt", val.getUpAt())
                    .setParameter("upBy", val.getUpBy())
                    .setParameter("id", val.getId())
                    .executeUpdate();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

}
