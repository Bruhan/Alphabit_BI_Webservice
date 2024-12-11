package com.facility.management.usecases.customer;

import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.usecases.customer.dao.CustomerDao;
import com.facility.management.usecases.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    public List<CustomerDTO> getListOfCustomers(String plant) throws Exception {
        List<CustomerDTO> val = null;
        try {
            val = customerDao.getListOfCustomers(plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}
