package com.facility.management.usecases.Supplier;

import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.VendMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VendorService {
    @Autowired
    VendorRepository vendorRepository;

    public List<VendMst> getallsupplier() throws Exception {
        List<VendMst> vendMst;
        try {
            vendMst = vendorRepository.findAll();
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return vendMst;
    }

    public VendMst findByVendNo(String customerName) throws Exception {
        VendMst vendMst;
        try {
            vendMst = vendorRepository.findByVendNo(customerName);
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return vendMst;
    }

    }
