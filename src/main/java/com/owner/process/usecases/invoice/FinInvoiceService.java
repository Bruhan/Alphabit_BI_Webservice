package com.owner.process.usecases.invoice;


import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.FinInvoiceDet;
import com.owner.process.persistence.models.FinInvoiceHdr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class FinInvoiceService {
    @Autowired
    FinInvoiceHdrRepository finInvoiceHdrRepository;
    @Autowired
    FinInvoiceDetRepository finInvoiceDetRepository;
    @Autowired
    EntityManager entityManager;

    public int setInvoiceHdr(FinInvoiceHdr finInvoiceHdr) throws Exception {
        int id = 0;
        try {
            FinInvoiceHdr InvoiceHdr = finInvoiceHdrRepository.save(finInvoiceHdr);
            id = InvoiceHdr.getId();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return id;
    }

    public String setInvoiceDet(FinInvoiceDet finInvoiceDet) throws Exception {
        try {
            finInvoiceDetRepository.save(finInvoiceDet);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return "1";
    }

    public List<FinInvoiceHdr> getHdrbystatus(String status) throws Exception{
        List<FinInvoiceHdr> val;
        try{
            val = finInvoiceHdrRepository.findbystatus(status);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<FinInvoiceHdr> getHdrbystatusPagenation(String status,int page,int pcount) throws Exception{
        List<FinInvoiceHdr> val;
        try{
            val = finInvoiceHdrRepository.findbystatusPagenation(status,page,pcount);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<FinInvoiceHdr> getHdrbyInvStatus(int page,int pcount) throws Exception{
        List<FinInvoiceHdr> val;
        try{
            val = finInvoiceHdrRepository.findbyInvStatus(page,pcount);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public FinInvoiceHdr getHdrbyid(int id) throws Exception{
        FinInvoiceHdr val;
        try{
            val = finInvoiceHdrRepository.findbyid(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<FinInvoiceDet> getDetbyHdrid(int hdrid) throws Exception{
        List<FinInvoiceDet> val;
        try{
            val = finInvoiceDetRepository.findbyhdrid(hdrid);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public FinInvoiceHdr findbydocid(String docid) throws Exception{
        FinInvoiceHdr val;
        try{
            val = finInvoiceHdrRepository.findbydocid(docid);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public FinInvoiceHdr findbydocidusingplant(String docid,String tablename) throws Exception{
        FinInvoiceHdr val;
        try{
            String querysets = "SELECT TOP 1 * FROM "+tablename+"_FININVOICEHDR WHERE PEPPOL_DOC_ID = '"+docid+"'";
            //val = finInvoiceHdrRepository.findbydocidusingplant(querysets);
            String sql = String.format(querysets);
            System.out.println(sql);
            List<FinInvoiceHdr> test = entityManager.createNativeQuery(sql,FinInvoiceHdr.class).getResultList();
            System.out.println(test);
            val = test.get(0);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public int updateInvoiceHdr(FinInvoiceHdr finInvoiceHdr,String tablename) throws Exception {
        int id = 0;
        try {
            String querysets = "update "+tablename+"_FININVOICEHDR set ADJUSTMENT='"+finInvoiceHdr.getAdjustment()+"', " +
                    "BILL_STATUS='"+finInvoiceHdr.getBillStatus()+"', CRAT='"+finInvoiceHdr.getCrAt()+"', " +
                    "CRBY='"+finInvoiceHdr.getCrBy()+"', CREDITNOTESSTATUS='"+finInvoiceHdr.getCreditNotesStatus()+"', " +
                    "CURRENCYID='"+finInvoiceHdr.getCurrencyId()+"', CURRENCYUSEQT='"+finInvoiceHdr.getCurrencyUseqt()+"', " +
                    "CUSTNO='"+finInvoiceHdr.getCustNo()+"', DEDUCT_INV='"+finInvoiceHdr.getDeductInv()+"', " +
                    "DELIVERY_DATE='"+finInvoiceHdr.getDeliveryDate()+"', DELIVERY_PERSON='"+finInvoiceHdr.getDeliveryPerson()+"', " +
                    "DELIVERY_PERSON_EMAIL='"+finInvoiceHdr.getDeliveryPersonEmail()+"', DISCOUNT='"+finInvoiceHdr.getDiscount()+"', " +
                    "DISCOUNT_ACCOUNT='"+finInvoiceHdr.getDiscountAccount()+"', DISCOUNT_TYPE='"+finInvoiceHdr.getDiscountType()+"', " +
                    "DONO='"+finInvoiceHdr.getDoNo()+"', DUE_DATE='"+finInvoiceHdr.getDueDate()+"', " +
                    "EMPNO='"+finInvoiceHdr.getEmpNo()+"', GINO='"+finInvoiceHdr.getGiNo()+"', " +
                    "INCOTERMS='"+finInvoiceHdr.getIncoterms()+"', INVOICE='"+finInvoiceHdr.getInvoice()+"', " +
                    "INVOICE_DATE='"+finInvoiceHdr.getInvoiceDate()+"', ISDISCOUNTTAX='"+finInvoiceHdr.getIsDiscountTax()+"', " +
                    "ISEXPENSE='"+finInvoiceHdr.getIsExpense()+"', ISORDERDISCOUNTTAX='"+finInvoiceHdr.getIsOrderDiscountTax()+"', " +
                    "ISSHIPPINGTAX='"+finInvoiceHdr.getIsShippingTax()+"', ITEM_RATES='"+finInvoiceHdr.getItemRates()+"', " +
                    "JobNum='"+finInvoiceHdr.getJobNum()+"', NOTE='"+finInvoiceHdr.getNote()+"', " +
                    "ORDER_DISCOUNT='"+finInvoiceHdr.getOrderDiscount()+"', ORDERDISCOUNTTYPE='"+finInvoiceHdr.getOrderDiscountType()+"', " +
                    "ORDERTYPE='"+finInvoiceHdr.getOrderType()+"', ORIGIN='"+finInvoiceHdr.getOrigin()+"', " +
                    "OUTBOUD_GST='"+finInvoiceHdr.getOutboundGst()+"', PAYMENT_TERMS='"+finInvoiceHdr.getPaymentTerms()+"', " +
                    "PEPPOL_DOC_ID='"+finInvoiceHdr.getPeppolDocId()+"', PEPPOL_STATUS='"+finInvoiceHdr.getPeppolStatus()+"', " +
                    "PLANT='"+finInvoiceHdr.getPlant()+"', PROJECTID='"+finInvoiceHdr.getProjectId()+"', " +
                    "SALES_LOCATION='"+finInvoiceHdr.getSalesLocation()+"', SHIPPINGCOST='"+finInvoiceHdr.getShippingCost()+"', " +
                    "SHIPPINGCUSTOMER='"+finInvoiceHdr.getShippingCustomer()+"', SHIPPINGID='"+finInvoiceHdr.getShippingId()+"', " +
                    "SUB_TOTAL='"+finInvoiceHdr.getSubTotal()+"', TAXAMOUNT='"+finInvoiceHdr.getTaxAmount()+"', " +
                    "TAXID='"+finInvoiceHdr.getTaxId()+"', TAX_STATUS='"+finInvoiceHdr.getTaxStatus()+"', " +
                    "TAXTREATMENT='"+finInvoiceHdr.getTaxTreatment()+"', TERMSCONDITIONS='"+finInvoiceHdr.getTermsConditions()+"', " +
                    "TOTAL_AMOUNT='"+finInvoiceHdr.getTotalAmount()+"', TOTAL_PAYING='"+finInvoiceHdr.getTotalPaying()+"', " +
                    "TRANSPORTID='"+finInvoiceHdr.getTransportId()+"', UPAT='"+finInvoiceHdr.getUpAt()+"', " +
                    "UPBY='"+finInvoiceHdr.getUpBy()+"' where ID='"+finInvoiceHdr.getId()+"'";
            //val = finInvoiceHdrRepository.findbydocidusingplant(querysets);
            String sql = String.format(querysets);
            System.out.println(sql);
            entityManager.createNativeQuery(sql,FinInvoiceHdr.class).getResultList();
            id = 1;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return id;
    }

}
