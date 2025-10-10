package com.owner.process.usecases.reports;

import com.google.gson.Gson;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultDao;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.usecases.reports.accounts_agg.AccountsAggregationPojo;
import com.owner.process.usecases.reports.accounts_payable.AccountsPayableSummaryDao;
import com.owner.process.usecases.reports.accounts_payable.AccountsPayableSummaryPojo;
import com.owner.process.usecases.reports.accounts_receivable.AccountsReceivableSummaryDao;
import com.owner.process.usecases.reports.accounts_receivable.AccountsReceivableSummaryPojo;
import com.owner.process.usecases.reports.expense_info.ExpenseSummaryDao;
import com.owner.process.usecases.reports.expense_info.ExpenseSummaryPojo;
import com.owner.process.usecases.reports.income_info.AccountListPojo;
import com.owner.process.usecases.reports.income_info.IncomeSummaryDao;
import com.owner.process.usecases.reports.income_info.IncomeSummaryPojo;
import com.owner.process.usecases.reports.payment_issued.PaymentIssuedSummaryDao;
import com.owner.process.usecases.reports.payment_issued.PaymentIssuedSummaryPojo;
import com.owner.process.usecases.reports.payment_issued.PaymentReceiptSummaryDao;
import com.owner.process.usecases.reports.payment_issued.PaymentReceiptSummaryPojo;
import com.owner.process.usecases.reports.pdc_issued_info.PdcIssuedSummaryDao;
import com.owner.process.usecases.reports.pdc_issued_info.PdcIssuedSummaryPojo;
import com.owner.process.usecases.reports.pdc_received_info.PdcReceivedSummaryDao;
import com.owner.process.usecases.reports.pdc_received_info.PdcReceivedSummaryPojo;
import com.owner.process.usecases.reports.purchase_info.PurchaseSummaryDao;
import com.owner.process.usecases.reports.purchase_info.PurchaseSummaryPojo;
import com.owner.process.usecases.reports.purchase_info.StatusDao;
import com.owner.process.usecases.reports.purchase_info.SupplierListPojo;
import com.owner.process.usecases.reports.sales_info.CustomerListPojo;
import com.owner.process.usecases.reports.sales_info.SalesSummaryDao;
import com.owner.process.usecases.reports.sales_info.SalesSummaryPojo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${spring.base.path}"+"/reports")
public class ReportsController {
    @Autowired
    ReportService reportService;
    @Autowired
    ClaimsSet claimsSet;

    private final String statusList = "[{'name': 'DRAFT','id': 1},{'name': 'OPEN','id':2}, "
            + "{'name': 'PAID','id':3},{'name': 'PARTIALLY PAID','id': 4}]";

    @GetMapping("/cash-details")
    public ResponseEntity<Object> getCashDetails(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();

        double cashInHandVal = reportService.getCashInHand(plant);
        double cashAtBankVal = reportService.getCashAtBank(plant);
        //map
        Map<String, Object> dashboardView = new HashMap<>();
        dashboardView.put("cashInHand", cashInHandVal);
        dashboardView.put("cashAtBank", cashAtBankVal);
        dashboardView.put("unit", unit);
        return new ResponseEntity<>(dashboardView, HttpStatus.OK);
    }

    @GetMapping("/profit-details")
    public ResponseEntity<Object> getProfitDetails(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        double grossProfit = reportService.getTotalSalesAmount(plant, startDate, endDate)
                - reportService.getTotalCostOfSalesAmount(plant, startDate, endDate);
        double netProfit = grossProfit + (reportService.getTotalIncomeAmount(plant, startDate, endDate)
                - reportService.getTotalExpenseAmount(plant, startDate, endDate));
        //map
        Map<String, Object> dashboardView = new HashMap<>();
        dashboardView.put("netProfit", netProfit);
        dashboardView.put("grossProfit", grossProfit);
        dashboardView.put("unit", unit);
        return new ResponseEntity<>(dashboardView, HttpStatus.OK);
    }

    @GetMapping("/summary/supplier")
    public ResponseEntity<Object> getSummarySuppliers(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        List<SupplierListPojo> supplierListPojo = reportService.getSupplierList(plant);
        //results
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(supplierListPojo);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/summary/status")
    public ResponseEntity<Object> getPurchaseStatus(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        StatusDao[] statusVal = new Gson().fromJson(statusList, StatusDao[].class);
        //results
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(statusVal);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/purchase/summary/sum")
    public ResponseEntity<Object> getPurchaseSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPurchaseSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/purchase/summary")
    public ResponseEntity<Object> getPurchaseSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "supplierId", required = false, defaultValue = "0") String supplierId
            , @RequestParam(value = "statusId", required = false, defaultValue = "0") int statusId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String supplierName = "%";
        String statusName = "%";
        if (!supplierId.equals("0"))
            supplierName = reportService.getSupplierName(plant, supplierId);
        if (statusId != 0) {
            StatusDao[] statusVal = new Gson().fromJson(statusList, StatusDao[].class);
            for (int i = 0; i < statusVal.length; i++) {
                if (statusVal[i].getId() == statusId) {
                    statusName = statusVal[i].getName();
                    i = statusVal.length;
                }
            }
        }
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPurchaseSummaryAgg(startDate, endDate, supplierName, statusName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<PurchaseSummaryPojo> purchaseSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            purchaseSummaryPojo = reportService.getPurchaseSummary(
                    startDate, endDate, supplierName, statusName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PurchaseSummaryDao> purchaseSummaryDao = modelMapper.map(purchaseSummaryPojo,
                new TypeToken<List<PurchaseSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(purchaseSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(purchaseSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/summary/customer")
    public ResponseEntity<Object> getSummaryCustomerList(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        List<CustomerListPojo> customerListPojo = reportService.getCustomerList(plant);
        //results
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(customerListPojo);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/sales/summary/sum")
    public ResponseEntity<Object> getSalesSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getSalesSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/sales/summary")
    public ResponseEntity<Object> getSalesSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "customerId", required = false, defaultValue = "0") String customerId
            , @RequestParam(value = "statusId", required = false, defaultValue = "0") int statusId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String customerName = "%";
        String statusName = "%";
        if (!customerId.equals("0"))
            customerName = reportService.getCustomerName(plant, customerId);
        if (statusId != 0) {
            StatusDao[] statusVal = new Gson().fromJson(statusList, StatusDao[].class);
            for (int i = 0; i < statusVal.length; i++) {
                if (statusVal[i].getId() == statusId) {
                    statusName = statusVal[i].getName();
                    i = statusVal.length;
                }
            }
        }
        AccountsAggregationPojo accountsAggregationPojo = reportService.getSalesSummaryAgg(startDate, endDate, customerName, statusName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<SalesSummaryPojo> salesSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            salesSummaryPojo = reportService.getSalesSummary(
                    startDate, endDate, customerName, statusName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<SalesSummaryDao> salesSummaryDao = modelMapper.map(salesSummaryPojo,
                new TypeToken<List<SalesSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(salesSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(salesSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/income/accounts")
    public ResponseEntity<Object> getIncomeAccountsList(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        List<AccountListPojo> accountListPojo = reportService.getIncomeAccountsList(plant);
        //result
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(accountListPojo);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/income/summary/sum")
    public ResponseEntity<Object> getIncomeSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getIncomeSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/income/summary")
    public ResponseEntity<Object> getIncomeSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId
            , @RequestParam(value = "customerId", required = false, defaultValue = "0") String customerId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String accountName = "%";
        String customerName = "%";
        if (!accountId.equals("0"))
            accountName = reportService.getAccountName(plant, accountId);
        if (!customerId.equals("0"))
            customerName = reportService.getCustomerName(plant, customerId);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getIncomeSummaryAgg(startDate, endDate, accountName, customerName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<IncomeSummaryPojo> incomeSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            incomeSummaryPojo = reportService.getIncomeSummary(
                    startDate, endDate, accountName, customerName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<IncomeSummaryDao> incomeSummaryDao = modelMapper.map(incomeSummaryPojo,
                new TypeToken<List<IncomeSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(incomeSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(incomeSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/expense/accounts")
    public ResponseEntity<Object> getExpenseAccountsList(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        List<AccountListPojo> accountListPojo = reportService.getExpenseAccountsList(plant);
        //result
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(accountListPojo);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/expense/summary/sum")
    public ResponseEntity<Object> getExpenseSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
    ) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getExpenseSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/expense/summary")
    public ResponseEntity<Object> getExpenseSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId
            , @RequestParam(value = "supplierId", required = false, defaultValue = "0") String supplierId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String accountName = "%";
        String supplierName = "%";
        if (!accountId.equals("0"))
            accountName = reportService.getAccountName(plant, accountId);
        if (!supplierId.equals("0"))
            supplierName = reportService.getSupplierName(plant, supplierId);

        AccountsAggregationPojo accountsAggregationPojo = reportService.getExpenseSummaryAgg(startDate, endDate, accountName, supplierName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<ExpenseSummaryPojo> expenseSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            expenseSummaryPojo = reportService.getExpenseSummary(
                    startDate, endDate, accountName, supplierName, pageSize, pageNumber - 1
            );

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<ExpenseSummaryDao> expenseSummaryDao = modelMapper.map(expenseSummaryPojo,
                new TypeToken<List<ExpenseSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(expenseSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(expenseSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/payment/accounts")
    public ResponseEntity<Object> getPaymentAccountsList(HttpServletRequest request
            , @RequestParam String plant) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String unit = claimsDao.getUnt();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        List<AccountListPojo> accountListPojo = reportService.getPaymentAccountsList(plant);
        //result
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(accountListPojo);
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/payment-issued/summary/sum")
    public ResponseEntity<Object> getPaymentIssuedSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPaymentIssuedSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/payment-issued/summary")
    public ResponseEntity<Object> getPaymentIssuedSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId
            , @RequestParam(value = "supplierId", required = false, defaultValue = "0") String supplierId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String accountName = "%";
        String supplierName = "%";
        if (!accountId.equals("0"))
            accountName = reportService.getAccountName(plant, accountId);
        if (!supplierId.equals("0"))
            supplierName = reportService.getSupplierName(plant, supplierId);

        AccountsAggregationPojo accountsAggregationPojo = reportService.getPaymentIssuedSummaryAgg(startDate, endDate, accountName, supplierName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<PaymentIssuedSummaryPojo> paymentIssuedSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            paymentIssuedSummaryPojo = reportService.getPaymentIssuedSummary(
                    startDate, endDate, accountName, supplierName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PaymentIssuedSummaryDao> paymentIssuedSummaryDao = modelMapper.map(paymentIssuedSummaryPojo,
                new TypeToken<List<PaymentIssuedSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(paymentIssuedSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(paymentIssuedSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/payment-receipt/summary/sum")
    public ResponseEntity<Object> getPaymentReceiptSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
    ) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPaymentReceiptSummaryAgg(startDate, endDate, "%", "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/payment-receipt/summary")
    public ResponseEntity<Object> getPaymentReceiptSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId
            , @RequestParam(value = "customerId", required = false, defaultValue = "0") String customerId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String accountName = "%";
        String customerName = "%";
        if (!accountId.equals("0"))
            accountName = reportService.getAccountName(plant, accountId);
        if (!customerId.equals("0"))
            customerName = reportService.getCustomerName(plant, customerId);

        AccountsAggregationPojo accountsAggregationPojo = reportService.getPaymentReceiptSummaryAgg(startDate, endDate, accountName, customerName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<PaymentReceiptSummaryPojo> paymentReceiptSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            paymentReceiptSummaryPojo = reportService.getPaymentReceiptSummary(
                    startDate, endDate, accountName, customerName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PaymentReceiptSummaryDao> paymentReceiptSummaryDao = modelMapper.map(paymentReceiptSummaryPojo,
                new TypeToken<List<PaymentReceiptSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(paymentReceiptSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(paymentReceiptSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/account-payable/summary/sum")
    public ResponseEntity<Object> getAccountPayableSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
    ) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getAccountPayableSummaryAgg(startDate, endDate, "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account-payable/summary")
    public ResponseEntity<Object> getAccountPayableSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "supplierId", required = false, defaultValue = "%") String supplierId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();

        AccountsAggregationPojo accountsAggregationPojo = reportService.getAccountPayableSummaryAgg(startDate, endDate, supplierId);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<AccountsPayableSummaryPojo> accountsPayableSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            accountsPayableSummaryPojo = reportService.getAccountPayableSummary(
                    startDate, endDate, supplierId, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<AccountsPayableSummaryDao> accountsPayableSummaryDao = modelMapper.map(accountsPayableSummaryPojo,
                new TypeToken<List<AccountsPayableSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(accountsPayableSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(accountsPayableSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/account-receivable/summary/sum")
    public ResponseEntity<Object> getAccountReceivableSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate)
            throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getAccountReceivableSummaryAgg(startDate, endDate, "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account-receivable/summary")
    public ResponseEntity<Object> getAccountReceivableSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "customerId", required = false, defaultValue = "%") String customerId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();

        AccountsAggregationPojo accountsAggregationPojo = reportService.getAccountReceivableSummaryAgg(startDate, endDate, customerId);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<AccountsReceivableSummaryPojo> accountsReceivableSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            accountsReceivableSummaryPojo = reportService.getAccountReceivableSummary(
                    startDate, endDate, customerId, pageSize, pageNumber - 1);
        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<AccountsReceivableSummaryDao> accountsReceivableSummaryDao = modelMapper.map(accountsReceivableSummaryPojo,
                new TypeToken<List<AccountsReceivableSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(accountsReceivableSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(accountsReceivableSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pdc-issued/summary/sum")
    public ResponseEntity<Object> getPdcIssuedSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPdcIssuedSummaryAgg(startDate, endDate, "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pdc-issued/summary")
    public ResponseEntity<Object> getPdcIssuedSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "supplierId", required = false, defaultValue = "0") String supplierId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String supplierName = "%";
        if (!supplierId.equals("0"))
            supplierName = reportService.getSupplierName(plant, supplierId);

        AccountsAggregationPojo accountsAggregationPojo = reportService.getPdcIssuedSummaryAgg(startDate, endDate, supplierName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<PdcIssuedSummaryPojo> pdcIssuedSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            pdcIssuedSummaryPojo = reportService.getPdcIssuedSummary(
                    startDate, endDate, supplierName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PdcIssuedSummaryDao> pdcIssuedSummaryDao = modelMapper.map(pdcIssuedSummaryPojo,
                new TypeToken<List<PdcIssuedSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(pdcIssuedSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(pdcIssuedSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/pdc-received/summary/sum")
    public ResponseEntity<Object> getPdcReceivedSummarySum(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        AccountsAggregationPojo accountsAggregationPojo = reportService.getPdcReceivedSummaryAgg(startDate, endDate, "%");
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pdc-received/summary")
    public ResponseEntity<Object> getPdcReceivedSummary(HttpServletRequest request
            , @RequestParam String plant
            , @RequestParam(value = "startDate", required = false, defaultValue = "2000-01-01") String startDate
            , @RequestParam(value = "endDate", required = false, defaultValue = "3000-12-31") String endDate
            , @RequestParam(value = "customerId", required = false, defaultValue = "0") String customerId
            , @RequestParam int pageSize, @RequestParam int pageNumber) throws Exception {
        String token = request.getHeader("Authorization");
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSetNewPlt(token, plant);
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime = new DateTimeCalc().getTodayDateTime();
        String customerName = "%";
        if (!customerId.equals("0"))
            customerName = reportService.getCustomerName(plant, customerId);

        AccountsAggregationPojo accountsAggregationPojo = reportService.getPdcReceivedSummaryAgg(
                startDate, endDate, customerName);
        int totalCount = accountsAggregationPojo.getTotalCount();
        double totalAmount = accountsAggregationPojo.getTotalAmount();
        List<PdcReceivedSummaryPojo> pdcReceivedSummaryPojo = new ArrayList<>();
        String message = "";
        if (totalCount == 0)
            message = "Zero Value";
        else if (totalCount < (pageNumber - 1) * pageSize)
            message = "count exceed";
        else
            pdcReceivedSummaryPojo = reportService.getPdcReceivedSummary(
                    startDate, endDate, customerName, pageSize, pageNumber - 1);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<PdcReceivedSummaryDao> pdcReceivedSummaryDao = modelMapper.map(pdcReceivedSummaryPojo,
                new TypeToken<List<PdcReceivedSummaryDao>>() {
                }.getType());
        //results
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(pdcReceivedSummaryDao);
        resultsDTO.setPageNumber(pageNumber);
        resultsDTO.setPageSize(pdcReceivedSummaryDao.size());
        resultsDTO.setTotalCount((long)totalCount);
        resultsDTO.setTotalAmount(totalAmount);
        resultsDTO.setMessage(message);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }
//    @GetMapping("/income/accounts/temp")
//    public ResponseEntity<Object> getIncomeAccountsListTemp(HttpServletRequest request) throws Exception {
//        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
//        String plant = claimsDao.getPlt();
//        String unit = claimsDao.getUnt();
//        String formattedDate = new DateTimeCalc().getTodayDate();
//        String formattedTime = new DateTimeCalc().getTodayDateTime();
//        List<AccountListPojoTemp> accountListPojoTemp = reportService.getIncomeAccountsListTemp(plant);
//        JSONArray childArray = new JSONArray();
//        String[] filterArray = {"9"};
//        Map<String, List<AccountListPojoTemp>> listGrouped = accountListPojoTemp.stream()
//                .filter(x -> Arrays.stream(filterArray)
//                        .anyMatch(e -> e.equals(x.getAccountTypeId())))
//                .collect(Collectors.groupingBy(w -> w.getAccountType()));
//        for (Map.Entry<String, List<AccountListPojoTemp>> entry : listGrouped.entrySet()) {
//            //System.out.println("Item : " + entry.getKey() + " value : " + entry.getValue() + "\n");
//            JSONObject childName = new JSONObject();
//
//            for (AccountListPojoTemp item : entry.getValue()) {
//                boolean isSub = "1".equals(item.getIsSubAccount());
//                //System.out.println(item.get("text")+"My Sub...."+isSub);
//                childName.put("text", item.getText());
//                childName.put("id", item.getId());
//                childName.put("issub", isSub);
//                if (isSub) {
//                    //String accName=coadao.getSubAccName(item.getSubAccountName(), plant);
//                    String accName = "dummy";
//                    childName.put("sub", accName);
//                }
//                childName.put("type", item.getAccountDetailType());
//                childName.put("acctype", item.getAccountType());
//                childArray.put(childName);
//            }
//        }
//        return new ResponseEntity<>(childArray, HttpStatus.OK);
//    }
}
