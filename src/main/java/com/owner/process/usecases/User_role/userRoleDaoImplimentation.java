package com.owner.process.usecases.User_role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userRoleDao")
public class userRoleDaoImplimentation implements userRoleDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public int getUserRoleID(String plant, String userrolename, String urlname) {
        Session session = sessionFactory.openSession();
        try {
            String sql = " SELECT Top 1 UL_PKEY FROM "+plant+"_USER_LEVEL_ACCOUNTING WHERE LEVEL_NAME = ?1 AND URL_NAME =?2";
            NativeQuery<?> query = session.createSQLQuery(sql);
            query.setParameter(1, userrolename);
            query.setParameter(2, urlname);
            return Integer.valueOf(query.uniqueResult().toString());
        }
        catch (Exception ex) {
            //throw ex;
            return 0;
        }
        finally {
            session.close();
        }
    }

    @Override
    public int getAccessStatusOfRoll(String plant, int umpkey) {
        Session session = sessionFactory.openSession();
        try {
            String sql = " SELECT Top 1 STATUS FROM "+plant+"_USER_MENU_ACCOUNTING WHERE UM_PKEY = ?1";
            NativeQuery<?> query = session.createSQLQuery(sql);
            query.setParameter(1, umpkey);
            return Integer.valueOf(query.uniqueResult().toString());
        }
        catch (Exception ex) {
            //throw ex;
            return 0;
        }
        finally {
            session.close();
        }
    }
}
