package com.owner.process.usecases.peppol;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.FinInvoiceHdr;
import com.owner.process.persistence.models.PEPPOL_DOC_IDS;
import com.owner.process.persistence.models.TableControl;
import com.owner.process.usecases.PeppolDoc.PeppolDocService;
import com.owner.process.usecases.invoice.FinInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${spring.base.path}")
public class peppolController {

    @Autowired
    PeppolDocService peppolDocService;
    @Autowired
    FinInvoiceService finInvoiceService;

    @RequestMapping(value = "/peppol/invoicestatus", method = RequestMethod.POST)
    public void newtableControl(HttpServletRequest request, @RequestBody peppolOutboudStatusPojo val) throws Exception {
        System.out.println(val);
        LoggerConfig.logger.info("peppol status");
        LoggerConfig.logger.info("peppol status");
        LoggerConfig.logger.info("peppol status");
        LoggerConfig.logger.info(val.getStatus());
        LoggerConfig.logger.info(val.getDocId());
        LoggerConfig.logger.info(val.getEvent());
        LoggerConfig.logger.info(val.getMessage());
        LoggerConfig.logger.info(val.getTransmissionTime());
        LoggerConfig.logger.info("peppol status");
        LoggerConfig.logger.info("peppol status");
        LoggerConfig.logger.info("peppol status");
        PEPPOL_DOC_IDS pepoldcis = peppolDocService.getDocval(val.getDocId());
        FinInvoiceHdr invoice = finInvoiceService.findbydocidusingplant(val.getDocId(),pepoldcis.getPlant());
        if(val.getStatus().equalsIgnoreCase("Transmission-Success")){
            invoice.setPeppolStatus((short) 2);
        }else{
            invoice.setPeppolStatus((short) 3);
        }
        finInvoiceService.updateInvoiceHdr(invoice,pepoldcis.getPlant());
    }

    @RequestMapping(value = "/peppol/test", method = RequestMethod.POST)
    public void newtableControl(HttpServletRequest request, @RequestParam String docid) throws Exception {
       try {
           PEPPOL_DOC_IDS pepoldcis = peppolDocService.getDocval(docid);
           FinInvoiceHdr invoice = finInvoiceService.findbydocidusingplant(docid, pepoldcis.getPlant());
           System.out.println(invoice);
       }catch (Exception e){
           System.out.println(e);
       }

    }
}
