package com.facility.management.usecases.inventory.dao;

import com.facility.management.persistence.models.InvMst;
import com.facility.management.usecases.inventory.pojo.InventoryPojo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("InventoryDao")
public class InventoryDaoImpl implements InventoryDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public InvMst findByIdAndItemAndLocationAndUserFieldFour(String plant, Integer pk0, String pk1, String pk2, String pk3) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ID = :pk0 AND ITEM = :pk1 AND LOC = :pk2 AND USERFLD4 = :pk3";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("pk0", pk0);
            query.setParameter("pk1", pk1);
            query.setParameter("pk2", pk2);
            query.setParameter("pk3", pk3);
            InvMst result = (InvMst) query.uniqueResult();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }


    }


    @Override
    public double getTQtyByItemLocBatch(String plant, String pk0, String pk1, String pk2) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT ISNULL(SUM(QTY), 0) AS QTY FROM " + plant + "_INVMST WHERE ITEM = :pk0 AND LOC = :pk1 AND USERFLD4 = :pk2";
            Query<Double> query = session.createSQLQuery(sql);
            query.setParameter("pk0", pk0);
            query.setParameter("pk1", pk1);
            query.setParameter("pk2", pk2);
            double result = (double) query.uniqueResult();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

    }


    @Override
    public List<InvMst> findByItemAndLocationAndUserFieldFour(String plant, String pk0, String pk1, String pk2) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ITEM = :pk0 AND LOC = :pk1 AND USERFLD4 = :pk2";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("pk0", pk0);
            query.setParameter("pk1", pk1);
            query.setParameter("pk2", pk2);
            List<InvMst> result = query.list();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        }finally {
            session.close();
        }
    }


    @Override
    public List<InvMst> getByItemLocBatch(String plant, String pk0, String pk1, String pk2) {
        Session session = sessionFactory.openSession();
        try{
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ITEM = :pk0 AND LOC = :pk1 " +
                    "AND USERFLD4 = :pk2 ORDER BY " +
                    "SUBSTRING(CONVERT(VARCHAR, CRAT, 112), 5, 4) + '-' + " +
                    "SUBSTRING(CONVERT(VARCHAR, CRAT, 112), 3, 2) + '-' + " +
                    "LEFT(CONVERT(VARCHAR, CRAT, 112), 2) ASC";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("pk0", pk0);
            query.setParameter("pk1", pk1);
            query.setParameter("pk2", pk2);
            List<InvMst> result = query.list();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }


    @Override
    public InvMst findByItemAndLoc(String plant, String item, String loc) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ITEM = :item AND LOC = :loc";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("item", item);
            query.setParameter("loc", loc);
            InvMst result = (InvMst) query.uniqueResult();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public List<InvMst> findallByLoc(String plant, String loc) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE LOC = :loc";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("loc", loc);
            List<InvMst> result =  query.list();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }


    @Override
    public List<InventoryPojo> findByLoc(String plt) {
       Session session = sessionFactory.openSession();

        try {
            String sql = "SELECT DISTINCT LOC AS location FROM " + plt + "_INVMST WHERE PLANT = :plt";
            Query<InventoryPojo> query = session.createSQLQuery(sql).addScalar("location", StringType.INSTANCE).setResultTransformer(Transformers.aliasToBean(InventoryPojo.class));
            query.setParameter("plt", plt);
            List<InventoryPojo> result = query.list();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }


    @Override
    public InvMst findByItem(String plant, String item) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ITEM = :item";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("item", item);
            InvMst result = (InvMst) query.uniqueResult();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }




    @Override
    public InvMst findByItemAndLocAndBatch(String plant, String item, String loc, String batch) {
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT * FROM " + plant + "_INVMST WHERE ITEM = :item AND LOC = :loc AND USERFLD4 = :batch";
            Query<InvMst> query = session.createSQLQuery(sql).addEntity(InvMst.class);
            query.setParameter("item", item);
            query.setParameter("loc", loc);
            query.setParameter("batch", batch);
            InvMst result = (InvMst) query.uniqueResult();
            session.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }


    @Override
    public void save(InvMst invMst) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String sql = "INSERT INTO [" + invMst.getPlant() + "_INVMST] ("
                    + "PLANT, ITEM, LOC, STATUS, SLED, QED, STKTYPE, QTY, QTYALLOC, CRAT, CRBY, UPAT, UPBY, EXPIREDATE, RECSTAT, "
                    + "USERFLD1, USERFLD2, USERFLD3, USERFLD4, USERFLD5, USERFLD6, USERFLG1, USERFLG2, USERFLG3, USERFLG4, USERFLG5, "
                    + "USERFLG6, USERTIME1, USERTIME2, USERTIME3, USERDBL1, USERDBL2, USERDBL3, USERDBL4, USERDBL5, USERDBL6, ISACTIVE, PRINTSTATUS, ID) "
                    + "VALUES (:plant, :item, :location, :status, :sLed, :qed, :stackType, :quantity, :quantityAllocation, :crAt, :crBy, :upAt, "
                    + ":upBy, :expireDate, :recStatus, :userFieldOne, :userFieldTwo, :userFieldThree, :userFieldFour, :userFieldFive, :userFieldSix, "
                    + ":userFlagOne, :userFlagTwo, :userFlagThree, :userFlagFour, :userFlagFive, :userFlagSix, :userTimeOne, :userTimeTwo, :userTimeThree, "
                    + ":userDblOne, :userDblTwo, :userDblThree, :userDblFour, :userDblFive, :userDblSix, :isActive, :printStatus)";

            Query<?> query = session.createSQLQuery(sql);
            query.setParameter("plant", invMst.getPlant());
            query.setParameter("item", invMst.getItem());
            query.setParameter("location", invMst.getLocation());
            query.setParameter("status", invMst.getStatus());
            query.setParameter("sLed", invMst.getSLed());
            query.setParameter("qed", invMst.getQed());
            query.setParameter("stackType", invMst.getStackType());
            query.setParameter("quantity", invMst.getQuantity());
            query.setParameter("quantityAllocation", invMst.getQuantityAllocation());
            query.setParameter("crAt", invMst.getCrAt());
            query.setParameter("crBy", invMst.getCrBy());
            query.setParameter("upAt", invMst.getUpAt());
            query.setParameter("upBy", invMst.getUpBy());
            query.setParameter("expireDate", invMst.getExpireDate());
            query.setParameter("recStatus", invMst.getRecStatus());
            query.setParameter("userFieldOne", invMst.getUserFieldOne());
            query.setParameter("userFieldTwo", invMst.getUserFieldTwo());
            query.setParameter("userFieldThree", invMst.getUserFieldThree());
            query.setParameter("userFieldFour", invMst.getUserFieldFour());
            query.setParameter("userFieldFive", invMst.getUserFieldFive());
            query.setParameter("userFieldSix", invMst.getUserFieldSix());
            query.setParameter("userFlagOne", invMst.getUserFlagOne());
            query.setParameter("userFlagTwo", invMst.getUserFlagTwo());
            query.setParameter("userFlagThree", invMst.getUserFlagThree());
            query.setParameter("userFlagFour", invMst.getUserFlagFour());
            query.setParameter("userFlagFive", invMst.getUserFlagFive());
            query.setParameter("userFlagSix", invMst.getUserFlagSix());
            query.setParameter("userTimeOne", invMst.getUserTimeOne());
            query.setParameter("userTimeTwo", invMst.getUserTimeTwo());
            query.setParameter("userTimeThree", invMst.getUserTimeThree());
            query.setParameter("userDblOne", invMst.getUserDblOne());
            query.setParameter("userDblTwo", invMst.getUserDblTwo());
            query.setParameter("userDblThree", invMst.getUserDblThree());
            query.setParameter("userDblFour", invMst.getUserDblFour());
            query.setParameter("userDblFive", invMst.getUserDblFive());
            query.setParameter("userDblSix", invMst.getUserDblSix());
            query.setParameter("isActive", invMst.getIsActive());
            query.setParameter("printStatus", invMst.getPrintStatus());

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * @param invMst
     */
    @Override
    public void update(InvMst invMst) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String sql = "UPDATE [" + invMst.getPlant() + "_INVMST] SET "
                    + "PLANT=:plant, ITEM=:item, LOC=:location, STATUS=:status, SLED=:sLed, QED=:qed, STKTYPE=:stackType, QTY=:quantity, " +
                    "QTYALLOC=:quantityAllocation, CRAT=:crAt, CRBY=:crBy, UPAT=:upAt, UPBY=:upBy, EXPIREDATE=:expireDate, RECSTAT=:recStatus, "
                    + "USERFLD1=:userFieldOne, USERFLD2=:userFieldTwo, USERFLD3=:userFieldThree, USERFLD4=:userFieldFour, USERFLD5=:userFieldFive, " +
                    "USERFLD6=:userFieldSix, USERFLG1=:userFlagOne, USERFLG2=:userFlagTwo, USERFLG3=:userFlagThree, USERFLG4=:userFlagFour, USERFLG5=:userFlagFive, "
                    + "USERFLG6=:userFlagSix, USERTIME1=:userTimeOne, USERTIME2=:userTimeTwo, USERTIME3=:userTimeThree, USERDBL1=:userDblOne, USERDBL2=:userDblTwo, " +
                    "USERDBL3=:userDblThree, USERDBL4=:userDblFour, USERDBL5=:userDblFive, USERDBL6=:userDblSix, ISACTIVE=:isActive, PRINTSTATUS=:printStatus "
                    + "WHERE ID=:id";


            Query<?> query = session.createSQLQuery(sql);
            query.setParameter("plant", invMst.getPlant());
            query.setParameter("item", invMst.getItem());
            query.setParameter("location", invMst.getLocation());
            query.setParameter("status", invMst.getStatus());
            query.setParameter("sLed", invMst.getSLed());
            query.setParameter("qed", invMst.getQed());
            query.setParameter("stackType", invMst.getStackType());
            query.setParameter("quantity", invMst.getQuantity());
            query.setParameter("quantityAllocation", invMst.getQuantityAllocation());
            query.setParameter("crAt", invMst.getCrAt());
            query.setParameter("crBy", invMst.getCrBy());
            query.setParameter("upAt", invMst.getUpAt());
            query.setParameter("upBy", invMst.getUpBy());
            query.setParameter("expireDate", invMst.getExpireDate());
            query.setParameter("recStatus", invMst.getRecStatus());
            query.setParameter("userFieldOne", invMst.getUserFieldOne());
            query.setParameter("userFieldTwo", invMst.getUserFieldTwo());
            query.setParameter("userFieldThree", invMst.getUserFieldThree());
            query.setParameter("userFieldFour", invMst.getUserFieldFour());
            query.setParameter("userFieldFive", invMst.getUserFieldFive());
            query.setParameter("userFieldSix", invMst.getUserFieldSix());
            query.setParameter("userFlagOne", invMst.getUserFlagOne());
            query.setParameter("userFlagTwo", invMst.getUserFlagTwo());
            query.setParameter("userFlagThree", invMst.getUserFlagThree());
            query.setParameter("userFlagFour", invMst.getUserFlagFour());
            query.setParameter("userFlagFive", invMst.getUserFlagFive());
            query.setParameter("userFlagSix", invMst.getUserFlagSix());
            query.setParameter("userTimeOne", invMst.getUserTimeOne());
            query.setParameter("userTimeTwo", invMst.getUserTimeTwo());
            query.setParameter("userTimeThree", invMst.getUserTimeThree());
            query.setParameter("userDblOne", invMst.getUserDblOne());
            query.setParameter("userDblTwo", invMst.getUserDblTwo());
            query.setParameter("userDblThree", invMst.getUserDblThree());
            query.setParameter("userDblFour", invMst.getUserDblFour());
            query.setParameter("userDblFive", invMst.getUserDblFive());
            query.setParameter("userDblSix", invMst.getUserDblSix());
            query.setParameter("isActive", invMst.getIsActive());
            query.setParameter("printStatus", invMst.getPrintStatus());
            query.setParameter("id", invMst.getId());

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
