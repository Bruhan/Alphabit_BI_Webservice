package com.owner.process.usecases.recv_det;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptDetService {

    @Autowired
    ReceiptDetRepository receiptDetRepository;

    public int setReceiptDet(RecvDet recvDet) throws Exception {
        int val=0;
        try {
            RecvDet recv = receiptDetRepository.save(recvDet);
            val = recv.getId();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }


    public List<ItemQty> getItemQty(String pk0, String pk1) throws Exception {
        List<ItemQty> getVal;
        try {
            getVal = receiptDetRepository.findByItemQty(pk0,pk1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ItemQtyPrice> getItemQtyCost(String pk0, String pk1) throws Exception {
        List<ItemQtyPrice> getVal;
        try {
            getVal = receiptDetRepository.findByItemQtyCost(pk0,pk1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ItemQty> getItemQtyToDate(String pk0) throws Exception {
        List<ItemQty> getVal;
        try {
            getVal = receiptDetRepository.findByItemQtyToDate(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ItemQty> getItemQtyFromDate(String pk0) throws Exception {
        List<ItemQty> getVal;
        try {
            getVal = receiptDetRepository.findByItemQtyFromDate(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ItemQtyPrice> getItemQtyBySupId(String pk0,String pk1,String pk2) throws Exception {
        List<ItemQtyPrice> getVal;
        try {
            getVal = receiptDetRepository.findByItemQtyBySupliId(pk0,pk1,pk2);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public String getTotalPurchaseCostWithTax(String pk0, String pk1) throws Exception {
        String getVal;
        try {
            getVal = receiptDetRepository.getTotalPurchaseCostWithTax(pk0,pk1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
    public String getTotalPurCostOfSupplierWithTax(String pk0, String pk1,String pk2) throws Exception {
        String getVal;
        try {
            getVal = receiptDetRepository.getTotalPurCostOfSupplierWithTax(pk0,pk1,pk2);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PurchaseSummaryWithTax> getPurCostOfSupplierWithTax(String pk0, String pk1,String pk2) throws Exception {
        List<PurchaseSummaryWithTax> getVal;
        try {
            getVal = receiptDetRepository.getPurCostOfSupplierWithTax(pk0,pk1,pk2);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public String getTotalSubOfSupplierWithTax(String pk0, String pk1,String pk2) throws Exception {
        String getVal;
        try {
            getVal = receiptDetRepository.getTotalSubOfSupplierWithTax(pk0,pk1,pk2);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public String getTotalTaxOfSupplierWithTax(String pk0, String pk1,String pk2) throws Exception {
        String getVal;
        try {
            getVal = receiptDetRepository.getTotalTaxOfSupplierWithTax(pk0,pk1,pk2);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

}


