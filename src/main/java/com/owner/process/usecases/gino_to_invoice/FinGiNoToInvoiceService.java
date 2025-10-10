package com.owner.process.usecases.gino_to_invoice;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.FinGiNoToInvoice;
import com.owner.process.persistence.models.RecvDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinGiNoToInvoiceService {
    @Autowired
    FinGiNoToInvoiceRepository finGiNoToInvoiceRepository;

    public void setFinGiNoToInvoice(FinGiNoToInvoice finGiNoToInvoice) throws Exception {
        try {
            finGiNoToInvoiceRepository.save(finGiNoToInvoice);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
    }
}
