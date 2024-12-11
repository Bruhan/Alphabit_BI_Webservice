package com.facility.management.usecases.customer.dao;

import com.facility.management.usecases.customer.dto.CustomerDTO;

import java.util.List;

public interface CustomerDao {
    public List<CustomerDTO> getListOfCustomers(String plant);
}
