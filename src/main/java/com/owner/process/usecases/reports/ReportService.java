package com.owner.process.usecases.reports;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.usecases.reports.accounts_agg.AccountsAggregationPojo;
import com.owner.process.usecases.reports.accounts_payable.AccountsPayableSummaryPojo;
import com.owner.process.usecases.reports.accounts_receivable.AccountsReceivableSummaryPojo;
import com.owner.process.usecases.reports.expense_info.ExpenseSummaryPojo;
import com.owner.process.usecases.reports.income_info.AccountListPojo;
import com.owner.process.usecases.reports.income_info.IncomeSummaryPojo;
import com.owner.process.usecases.reports.payment_issued.PaymentIssuedSummaryPojo;
import com.owner.process.usecases.reports.payment_issued.PaymentReceiptSummaryPojo;
import com.owner.process.usecases.reports.pdc_issued_info.PdcIssuedSummaryPojo;
import com.owner.process.usecases.reports.pdc_received_info.PdcReceivedSummaryPojo;
import com.owner.process.usecases.reports.purchase_info.PurchaseSummaryPojo;
import com.owner.process.usecases.reports.purchase_info.SupplierListPojo;
import com.owner.process.usecases.reports.sales_info.CustomerListPojo;
import com.owner.process.usecases.reports.sales_info.SalesSummaryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    public double getCashInHand(String plant) throws Exception {
        double val;
        //String accountId = "156,157";
        try {
            val = reportRepository.getCashInHand();
            if (val == 0.0)
                return 0.0;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Not schedule yet for emp");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public double getCashAtBank(String plant) throws Exception {
        double val;
        String accDetType = "8";
        try {
            val = reportRepository.getCashAtBank(accDetType);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public double getTotalSalesAmount(String plant, String startDate, String endDate) throws Exception {
        double val;
        try {
            val = reportRepository.getTotalSalesAmount(startDate, endDate);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public double getTotalCostOfSalesAmount(String plant, String startDate, String endDate) throws Exception {
        double val;
        try {
            val = reportRepository.getTotalCostOfSalesAmount(startDate, endDate);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public double getTotalIncomeAmount(String plant, String startDate, String endDate) throws Exception {
        double val;
        try {
            val = reportRepository.getTotalIncomeAmount(startDate, endDate);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public double getTotalExpenseAmount(String plant, String startDate, String endDate) throws Exception {
        double val;
        try {
            val = reportRepository.getTotalExpenseAmount(startDate, endDate);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<SupplierListPojo> getSupplierList(String plant) throws Exception {
        List<SupplierListPojo> val;
        try {
            val = reportRepository.getSupplierList();
            if (val == null)
                throw new ResourceNotFoundException();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public String getSupplierName(String plant,String id) throws Exception{
        String val;
        try {
            val = reportRepository.getSupplierName(id);
            if (val == null)
                return "%";
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getPurchaseSummaryAgg(String stDt, String edDt, String supNm, String stsNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getPurchaseSummaryAgg(stDt, edDt, supNm, stsNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<PurchaseSummaryPojo> getPurchaseSummary(String stDt, String edDt, String supNm, String stsNm, int ps, int pn) throws Exception {
        List<PurchaseSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getPurchaseSummary(stDt, edDt, supNm, stsNm, pg);
            if (val.size() == 0) {
                return val;
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public List<CustomerListPojo> getCustomerList(String plant) throws Exception{
        List<CustomerListPojo> val;
        try {
            val = reportRepository.getCustomerList();
            if (val == null)
                throw new ResourceNotFoundException();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public String getCustomerName(String plant,String customerId) throws Exception{
        String val;
        try {
            val = reportRepository.getCustomerName(customerId);
            if (val == null)
                return "%";
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getSalesSummaryAgg(String stDt, String edDt, String cstNm, String stsNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getSalesSummaryAgg(stDt, edDt, cstNm, stsNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public List<SalesSummaryPojo> getSalesSummary(String stDt,String edDt,String cstNm,String st,int ps,int pn) throws Exception{
        List<SalesSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getSalesSummary(stDt,edDt,cstNm,st,pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public List<AccountListPojo> getIncomeAccountsList(String plant) throws Exception{
        List<AccountListPojo> val;
        String accType = "9";
        try {
            val = reportRepository.getAccountsList(plant,accType);
            if (val == null)
                throw new ResourceNotFoundException();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public String getAccountName(String plant,String accId) throws Exception{
        String val;
        try {
            val = reportRepository.getAccountName(plant, accId);
            if (val == null)
                return "%";
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getIncomeSummaryAgg(String stDt, String edDt, String accNm, String cstNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getIncomeSummaryAgg(stDt, edDt, accNm, cstNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<IncomeSummaryPojo> getIncomeSummary(String stDt, String edDt, String accNm, String cstNm, int ps, int pn) throws Exception {
        List<IncomeSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getIncomeSummary(stDt, edDt, accNm, cstNm, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<AccountListPojo> getExpenseAccountsList(String plant) throws Exception{
        List<AccountListPojo> val;
        String accType = "11";
        try {
            val = reportRepository.getAccountsList(plant, accType);
            if (val == null)
                throw new ResourceNotFoundException();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getExpenseSummaryAgg(String stDt, String edDt, String accNm, String supNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getExpenseSummaryAgg(stDt, edDt, accNm, supNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<ExpenseSummaryPojo> getExpenseSummary(String stDt, String edDt, String accNm, String supNm, int ps, int pn) throws Exception {
        List<ExpenseSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getExpenseSummary(stDt, edDt, accNm, supNm, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }


    public List<AccountListPojo> getPaymentAccountsList(String plant) throws Exception{
        List<AccountListPojo> val;
        String accType = "3";
        String accDetTyOne = "8";
        String accDetTyTwo = "9";
        try {
            val = reportRepository.getPaymentAccountsList(plant,accType,accDetTyOne,accDetTyTwo);
            if (val == null)
                throw new ResourceNotFoundException();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getPaymentIssuedSummaryAgg(String stDt, String edDt, String accNm, String supNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getPaymentIssuedSummaryAgg(stDt, edDt, accNm, supNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public List<PaymentIssuedSummaryPojo> getPaymentIssuedSummary(String stDt, String edDt,String accNm, String spNm, int ps,int pn) throws Exception{
        List<PaymentIssuedSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getPaymentIssuedSummary(stDt,edDt,accNm,spNm,pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getPaymentReceiptSummaryAgg(String stDt, String edDt, String accNm, String cstNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getPaymentReceiptSummaryAgg(stDt, edDt, accNm, cstNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<PaymentReceiptSummaryPojo> getPaymentReceiptSummary(String stDt, String edDt, String accNm, String supNm, int ps, int pn) throws Exception {
        List<PaymentReceiptSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getPaymentReceiptSummary(stDt, edDt, accNm, supNm, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getAccountPayableSummaryAgg(String stDt, String edDt, String supId) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getAccountPayableSummaryAgg(stDt, edDt, supId);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<AccountsPayableSummaryPojo> getAccountPayableSummary(String stDt, String edDt, String supId, int ps, int pn) throws Exception {
        List<AccountsPayableSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getAccountPayableSummary(stDt, edDt, supId, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getAccountReceivableSummaryAgg(String stDt, String edDt, String cstId) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getAccountReceivableSummaryAgg(stDt, edDt, cstId);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<AccountsReceivableSummaryPojo> getAccountReceivableSummary(String stDt, String edDt, String cstId, int ps, int pn) throws Exception {
        List<AccountsReceivableSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getAccountReceivableSummary(stDt, edDt, cstId, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getPdcIssuedSummaryAgg(String stDt, String edDt, String supNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getPdcIssuedSummaryAgg(stDt, edDt, supNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<PdcIssuedSummaryPojo> getPdcIssuedSummary(String stDt, String edDt, String supNm, int ps, int pn) throws Exception {
        List<PdcIssuedSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getPdcIssuedSummary(stDt, edDt, supNm, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public AccountsAggregationPojo getPdcReceivedSummaryAgg(String stDt, String edDt, String cstNm) throws Exception {
        AccountsAggregationPojo val;
        try {
            val = reportRepository.getPdcReceivedSummaryAgg(stDt, edDt, cstNm);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<PdcReceivedSummaryPojo> getPdcReceivedSummary(String stDt, String edDt, String cstNm, int ps, int pn) throws Exception {
        List<PdcReceivedSummaryPojo> val;
        Pageable pg = PageRequest.of(pn, ps);
        try {
            val = reportRepository.getPdcReceivedSummary(stDt, edDt, cstNm, pg);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Empty");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }


    public double getOpeningbalancetotal(String plant,String fromdate) throws Exception {
        double val;
        String accDetType = "8";
        try {
            val = reportRepository.getOpeningbalancetotal(plant,fromdate);
            if (val == 0.0)
                return 0.0;
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}
