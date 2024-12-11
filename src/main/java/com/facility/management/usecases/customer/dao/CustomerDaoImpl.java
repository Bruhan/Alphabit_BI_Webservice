package com.facility.management.usecases.customer.dao;

import com.facility.management.usecases.customer.dto.CustomerDTO;
import com.facility.management.usecases.wastage.dto.OWCOutcomeDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("CustomerDao")
public class CustomerDaoImpl implements CustomerDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<CustomerDTO> getListOfCustomers(String plant) {
        Session session = sessionFactory.openSession();
        List<CustomerDTO> customerDTOList = null;
        try {
            String sql = "SELECT CUSTNO, CNAME, TELNO, EMAIL FROM " + plant + "_CUSTMST WHERE PLANT = :plant";
            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            customerDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustomerNo((String) row[0]);
                customerDTO.setCustomerName((String) row[1]);
                customerDTO.setTelNo((String) row[2]);
                customerDTO.setEmail((String) row[3]);

                customerDTOList.add(customerDTO);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
        return customerDTOList;
    }
}
