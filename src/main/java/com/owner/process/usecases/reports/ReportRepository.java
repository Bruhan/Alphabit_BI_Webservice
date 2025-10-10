package com.owner.process.usecases.reports;

import com.owner.process.persistence.models.FinJournalDet;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<FinJournalDet, Long> {

    @Query(value = "select (SUM(ISNULL(jd.DEBITS, 0))-SUM(ISNULL(jd.CREDITS, 0))) as cashInHand " +
            "from ##plant##FINJOURNALDET jd inner join ##plant##FINJOURNALHDR hdr " +
            "on hdr.ID=jd.JOURNALHDRID where jd.ACCOUNT_ID in (156,157)", nativeQuery = true)
    double getCashInHand();

    @Query(value = "select (SUM(ISNULL(jd.DEBITS, 0))-SUM(ISNULL(jd.CREDITS, 0))) as cashAtBank " +
            "from ##plant##FINJOURNALDET jd inner join ##plant##FINCHARTOFACCOUNTS ca " +
            "on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINJOURNALHDR  hdr " +
            "on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTDETAILTYPE in (?1) AND ca.ID not in (156,157)", nativeQuery = true)
    double getCashAtBank(String accDetType);

    @Query(value = "select ISNULL(sum(jd.CREDITS)-sum(jd.DEBITS),'0') as totalSalesAmount " +
            "from ##plant##FINJOURNALDET jd inner join ##plant##FINCHARTOFACCOUNTS ca " +
            "on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINJOURNALHDR hdr " +
            "on hdr.ID=jd.JOURNALHDRID " +
            "where CONVERT(DATETIME,hdr.JOURNAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 AND ca.ACCOUNTTYPE in (8)", nativeQuery = true)
    double getTotalSalesAmount(String stDt, String edDt);

    @Query(value = "select ISNULL(sum(jd.DEBITS)-sum(jd.CREDITS),'0') as totalCostOfSalesAmount " +
            "from ##plant##FINJOURNALDET jd inner join ##plant##FINCHARTOFACCOUNTS ca " +
            "on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINJOURNALHDR hdr " +
            "on hdr.ID=jd.JOURNALHDRID " +
            "where CONVERT(DATETIME,hdr.JOURNAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 AND ca.ACCOUNTTYPE in (10)",nativeQuery = true)
    double getTotalCostOfSalesAmount(String stDt,String edDt);

    @Query(value = "select ISNULL(sum(jd.CREDITS)-sum(jd.DEBITS),'0') as totalIncomeAmount " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca " +
            "on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINJOURNALHDR  hdr " +
            "on hdr.ID=jd.JOURNALHDRID " +
            "where CONVERT(DATETIME,hdr.JOURNAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 AND ca.ACCOUNTTYPE in (9)",nativeQuery = true)
    double getTotalIncomeAmount(String stDt,String edDt);

    @Query(value = " select ISNULL(sum(jd.DEBITS)-sum(jd.CREDITS),'0') as totalExpenseAmount " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca " +
            "on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINJOURNALHDR  hdr " +
            "on hdr.ID=jd.JOURNALHDRID " +
            "where CONVERT(DATETIME,hdr.JOURNAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 AND ca.ACCOUNTTYPE in (11)", nativeQuery = true)
    double getTotalExpenseAmount(String stDt, String edDt);

    @Query(value = "select VENDNO as id,VNAME as name from ##plant##VENDMST " +
            "ORDER BY VNAME asc", nativeQuery = true)
    List<SupplierListPojo> getSupplierList();

    @Query(value = "select top 1 VNAME as name from ##plant##VENDMST " +
            "where VENDNO = ?1", nativeQuery = true)
    String getSupplierName(String id);

    @Query(value = "select count(pcs.status) as totalCount ," +
            "ISNULL(sum(pcs.totalAmount),0) as totalAmount from (select " +
            "CAST(((ISNULL(jd.DEBITS, 0))-(ISNULL(jd.CREDITS, 0))) AS DECIMAL(18,5))AS totalAmount," +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS supplierName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT BI.BILL_STATUS FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT SD.CREDIT_STATUS FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS status," +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' " +
            "THEN ISNULL((SELECT BI.BILL FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' " +
            "THEN ISNULL((SELECT SD.BILL FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' END AS CONDITIONS" +
            " from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE='3' AND ca.ACCOUNTDETAILTYPE = '6' AND (hdr.TRANSACTION_TYPE = 'BILL' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES') " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104)BETWEEN ?1 AND ?2 ) pcs " +
            "where pcs.supplierName LIKE ?3% AND pcs.status LIKE ?4% AND pcs.CONDITIONS != ''", nativeQuery = true)
    AccountsAggregationPojo getPurchaseSummaryAgg(String stDt, String edDt, String spNm, String stNm);


    @Query(value = "select * from (select hdr.JOURNAL_DATE as journalDate,jd.ID as id,CAST(((ISNULL(jd.DEBITS, 0))-(ISNULL(jd.CREDITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS supplierName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT BI.BILL FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT SD.CREDITNOTE FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS reference, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT BI.BILL_STATUS FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT SD.CREDIT_STATUS FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS status, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT BI.BILL FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT SD.BILL FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS conditions from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE='3' AND ca.ACCOUNTDETAILTYPE = '6' AND (hdr.TRANSACTION_TYPE = 'BILL' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES') " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104)BETWEEN ?1 AND ?2) pchs " +
            "Where pchs.supplierName LIKE ?3%  AND " +
            "pchs.status LIKE ?4% AND pchs.conditions != ''" +
            "order by pchs.ID desc ", nativeQuery = true)
    List<PurchaseSummaryPojo> getPurchaseSummary(String stDt, String edDt, String spNm, String st, Pageable pg);

    @Query(value = "SELECT CUSTNO as id,CNAME as name FROM ##plant##CUSTMST " +
            "ORDER BY CNAME ASC", nativeQuery = true)
    List<CustomerListPojo> getCustomerList();

    @Query(value = "select top 1 CNAME as cName from ##plant##CUSTMST " +
            "where CUSTNO = ?1", nativeQuery = true)
    String getCustomerName(String id);

    @Query(value = "select count(sls.status) as totalCount ," +
            "ISNULL(sum(sls.totalAmount),0) as totalAmount from (select " +
            "CAST(((ISNULL(jd.CREDITS, 0))-(ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount," +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT CM.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS CM ON IV.CUSTNO = CM.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS customerName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IV.BILL_STATUS FROM ##plant##FININVOICEHDR AS IV WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.CREDIT_STATUS FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS status, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IV.INVOICE FROM ##plant##FININVOICEHDR AS IV WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-')    " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.INVOICE FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-')  " +
            "ELSE '-'END AS CONDITIONS "+
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE='8' AND (hdr.TRANSACTION_TYPE = 'INVOICE' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES') " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2 ) sls " +
            "where sls.customerName LIKE ?3% AND sls.status LIKE ?4%  AND sls.CONDITIONS != ''", nativeQuery = true)
    AccountsAggregationPojo getSalesSummaryAgg(String stDt, String edDt, String cstNm, String stNm);

    @Query(value = "select * from (select hdr.JOURNAL_DATE as journalDate,jd.ID as id,CAST(((ISNULL(jd.CREDITS, 0))-(ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT CM.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS CM ON IV.CUSTNO = CM.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS customerName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IV.INVOICE FROM ##plant##FININVOICEHDR AS IV WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.CREDITNOTE FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS reference, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IV.BILL_STATUS FROM ##plant##FININVOICEHDR AS IV WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.CREDIT_STATUS FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS status, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IV.INVOICE FROM ##plant##FININVOICEHDR AS IV WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.INVOICE FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS conditions from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE='8' AND (hdr.TRANSACTION_TYPE = 'INVOICE' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES') " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2 ) sls " +
            "where sls.customerName LIKE ?3% AND sls.status LIKE ?4% AND conditions != ''" +
            "order by sls.id desc ", nativeQuery = true)
    List<SalesSummaryPojo> getSalesSummary(String stDt, String edDt, String cstNm, String st, Pageable pg);

    @Query(value = "select a.ID as id,a.ACCOUNT_NAME as text,d.ACCOUNTDETAILTYPE as accountDetailType,a.ACCOUNTDETAILTYPE as detailId " +
            ",b.ACCOUNTTYPE as accountType,a.ACCOUNTTYPE as accountTypeId,a.ISSUBACCOUNT as isSubAccount " +
            ",ISNULL(a.SUBACCOUNTNAME,'') subAccountName,c.ID as mainAccId " +
            "FROM ##plant##FINCHARTOFACCOUNTS  a left join ##plant##FINACCOUNTTYPE  b " +
            "on a.ACCOUNTTYPE=b.ID left join ##plant##FINMAINACCOUNT  c " +
            "on b.MAINACCOUNTID=c.ID join ##plant##FINACCOUNTDETAILTYPE  d on d.ID=a.ACCOUNTDETAILTYPE " +
            "where a.plant =?1  AND a.ACCOUNTTYPE = ?2 AND a.ACCOUNT_NAME LIKE '%' ORDER BY a.ID ", nativeQuery = true)
    List<AccountListPojo> getAccountsList(String plt, String accType);

    @Query(value = "SELECT ACCOUNT_NAME FROM ##plant##FINCHARTOFACCOUNTS  WHERE " +
            "PLANT = ?1 AND Id =?2", nativeQuery = true)
    String getAccountName(String plt, String accId);

    @Query(value = "select count(inc.accountName) as totalCount,ISNULL(sum(inc.totalAmount),0) as totalAmount from (select " +
            "CAST(((ISNULL(jd.CREDITS, 0))-(ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount," +
            "jd.ACCOUNT_NAME as accountName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS customerName from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad " +
            "on ad.ID=ca.ACCOUNTDETAILTYPE inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID " +
            "where ca.ACCOUNTTYPE in (9) AND " +
            "CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2 ) inc " +
            "where inc.accountName LIKE ?3% AND inc.customerName LIKE ?4% ", nativeQuery = true)
    AccountsAggregationPojo getIncomeSummaryAgg(String stDt, String edDt, String accNm, String cstNm);

    @Query(value = "select * from ( " +
            "select hdr.ID as id, hdr.JOURNAL_DATE as journalDate,jd.ACCOUNT_NAME as accountName,CAST(((ISNULL(jd.CREDITS, 0))-(ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS customerName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT INVOICE FROM ##plant##FININVOICEHDR  WHERE INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT REFERENCE FROM ##plant##FINRECEIVEHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT REFERENCE FROM ##plant##FINPAYMENTHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-' " +
            "END AS reference from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad " +
            "on ad.ID=ca.ACCOUNTDETAILTYPE inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID " +
            "where ca.ACCOUNTTYPE in (9) AND " +
            "CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2 ) inc " +
            "where inc.accountName LIKE ?3% AND inc.customerName LIKE ?4% " +
            "order by inc.id desc ", nativeQuery = true)
    List<IncomeSummaryPojo> getIncomeSummary(String stDt, String edDt, String accNm, String cstNm, Pageable pg);

    @Query(value = "select count(exp.accountName) as totalCount,ISNULL(sum(exp.totalAmount),0) as totalAmount from (select " +
            "CAST((ISNULL(jd.DEBITS, 0)) AS DECIMAL(18,5))AS totalAmount," +
            "jd.ACCOUNT_NAME as accountName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS supplierName " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE in (11) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' ) " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 ) as exp " +
            "where exp.accountName LIKE ?3% AND exp.supplierName LIKE ?4% AND exp.totalAmount != '0'", nativeQuery = true)
    AccountsAggregationPojo getExpenseSummaryAgg(String stDt, String edDt, String accNm, String supNm);

    @Query(value = "select * from ( " +
            "select hdr.ID as id,hdr.JOURNAL_DATE as journalDate,jd.ACCOUNT_NAME as accountName,CAST(((ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS supplierName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT INVOICE FROM ##plant##FININVOICEHDR  WHERE INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT REFERENCE FROM ##plant##FINRECEIVEHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT REFERENCE FROM ##plant##FINPAYMENTHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT BI.BILL FROM ##plant##FINBILLHDR AS BI WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CN.CREDITNOTE FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT SD.CREDITNOTE FROM ##plant##FINVENDORCREDITNOTEHDR AS SD WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN ISNULL((SELECT ISNULL(EX.REFERENCE,'') FROM ##plant##FINEXPENSESHDR AS EX WHERE EX.ID = hdr.TRANSACTION_ID),'-') " +
            "ELSE '-'END AS reference " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTTYPE in (11) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' ) " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2) exp " +
            "where exp.supplierName LIKE ?4 AND exp.accountName LIKE ?3 AND exp.totalAmount != '0'" +
            "order by exp.id desc ", nativeQuery = true)
    List<ExpenseSummaryPojo> getExpenseSummary(String stDt, String edDt, String accNm, String supNm, Pageable pg);

    @Query(value = "select a.ID as id,a.ACCOUNT_NAME as text,d.ACCOUNTDETAILTYPE as accountDetailType,a.ACCOUNTDETAILTYPE as detailId " +
            ",b.ACCOUNTTYPE as accountType, " +
            "CASE " +
            "WHEN a.ISSUBACCOUNT = '0' THEN ISNULL((SELECT ACCOUNT_NAME FROM FINCHARTOFACCOUNTS  WHERE Id =ISNULL(a.SUBACCOUNTNAME,'') ),'-') " +
            "ELSE '-' END AS subAccountName " +
            "FROM ##plant##FINCHARTOFACCOUNTS  a left join ##plant##FINACCOUNTTYPE  b on a.ACCOUNTTYPE=b.ID left join ##plant##FINMAINACCOUNT  c " +
            "on b.MAINACCOUNTID=c.ID join ##plant##FINACCOUNTDETAILTYPE  d on d.ID=a.ACCOUNTDETAILTYPE " +
            "where a.plant =?1 AND a.ACCOUNTTYPE = ?2 AND  a.ACCOUNTDETAILTYPE = ?3 or a.ACCOUNTDETAILTYPE = ?4 " +
            "ORDER BY a.ID ", nativeQuery = true)
    List<AccountListPojo> getPaymentAccountsList(String plt, String accType, String accDetTyOne, String accDetTyTwo);

    @Query(value = "select count(pis.accountName) as totalCount,ISNULL(sum(pis.totalAmount),0) as totalAmount from (select " +
            "CAST((ISNULL(jd.CREDITS, 0)) AS DECIMAL(18,5))AS totalAmount, " +
            "jd.ACCOUNT_NAME as accountName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS supplierName " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTDETAILTYPE in (8,9) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' OR hdr.TRANSACTION_TYPE = 'EXPENSE PAID') " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2) pis " +
            "where pis.accountName LIKE ?3% AND pis.supplierName LIKE ?4%  AND pis.totalAmount != '0'", nativeQuery = true)
    AccountsAggregationPojo getPaymentIssuedSummaryAgg(String stDt, String edDt, String accNm, String supNm);

    @Query(value = "Select * from ( " +
            "select jd.ID as id,hdr.JOURNAL_DATE as journalDate,hdr.TRANSACTION_ID as transactionId,jd.ACCOUNT_NAME as accountName " +
            ",CAST(((ISNULL(jd.CREDITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS supplierName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT DEPOSIT_TO FROM ##plant##FINRECEIVEHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PAID_THROUGH FROM ##plant##FINPAYMENTHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS paidTo " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTDETAILTYPE in (8,9) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' ) " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2) pis " +
            "where pis.accountName LIKE ?3% AND pis.supplierName LIKE ?4% AND pis.totalAmount != '0'" +
            "order by pis.id desc ", nativeQuery = true)
    List<PaymentIssuedSummaryPojo> getPaymentIssuedSummary(String stDt, String edDt, String accNm, String supNm, Pageable pg);

    @Query(value = "select count(prs.accountName) as totalCount,ISNULL(sum(prs.totalAmount),0) as totalAmount from (select " +
            "CAST(((ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "jd.ACCOUNT_NAME as accountName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS customerName " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTDETAILTYPE in (8,9) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' ) " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2) prs " +
            "where prs.accountName LIKE ?3% AND prs.customerName LIKE ?4% AND prs.totalAmount != '0'", nativeQuery = true)
    AccountsAggregationPojo getPaymentReceiptSummaryAgg(String stDt, String edDt, String accNm, String cstNm);

    @Query(value = "Select * from ( " +
            "select hdr.ID as id,hdr.JOURNAL_DATE as journalDate,hdr.TRANSACTION_ID as transactionId,jd.ACCOUNT_NAME as accountName " +
            ",CAST(((ISNULL(jd.DEBITS, 0))) AS DECIMAL(18,5))AS totalAmount, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN ISNULL((SELECT IVC.CNAME FROM ##plant##FININVOICEHDR AS IV LEFT JOIN ##plant##CUSTMST AS IVC ON IVC.CUSTNO = IV.CUSTNO WHERE IV.INVOICE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT RVC.CNAME FROM ##plant##FINRECEIVEHDR AS RV LEFT JOIN ##plant##CUSTMST AS RVC ON RVC.CUSTNO = RV.CUSTNO WHERE RV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PVC.VNAME FROM ##plant##FINPAYMENTHDR AS PV LEFT JOIN ##plant##VENDMST AS PVC ON PV.VENDNO = PVC.VENDNO WHERE PV.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINBILLHDR AS BI LEFT JOIN ##plant##VENDMST AS VM ON BI.VENDNO = VM.VENDNO WHERE BI.BILL = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN ISNULL((SELECT VM.VNAME FROM ##plant##FINVENDORCREDITNOTEHDR AS SD LEFT JOIN ##plant##VENDMST AS VM ON SD.VENDNO = VM.VENDNO WHERE SD.ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN ISNULL((SELECT CM.CNAME  FROM ##plant##FINCUSTOMERCREDITNOTEHDR AS CN LEFT JOIN ##plant##CUSTMST AS CM ON CM.CUSTNO = CN.CUSTNO WHERE CN.CREDITNOTE = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS customerName, " +
            "CASE " +
            "WHEN hdr.TRANSACTION_TYPE = 'INVOICE' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'SALESPAYMENT' THEN ISNULL((SELECT DEPOSIT_TO FROM ##plant##FINRECEIVEHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' THEN ISNULL((SELECT PAID_THROUGH FROM ##plant##FINPAYMENTHDR WHERE ID = hdr.TRANSACTION_ID),'-') " +
            "WHEN hdr.TRANSACTION_TYPE = 'BILL' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' THEN '-' " +
            "WHEN hdr.TRANSACTION_TYPE = 'EXPENSE' THEN '-' " +
            "ELSE '-'END AS depositTo " +
            "from ##plant##FINJOURNALDET  jd inner join ##plant##FINCHARTOFACCOUNTS  ca on ca.ID=jd.ACCOUNT_ID inner join ##plant##FINACCOUNTDETAILTYPE  ad on ad.ID=ca.ACCOUNTDETAILTYPE " +
            "inner join ##plant##FINJOURNALHDR  hdr on hdr.ID=jd.JOURNALHDRID where ca.ACCOUNTDETAILTYPE in (8,9) " +
            "AND (hdr.TRANSACTION_TYPE = 'EXPENSE' OR hdr.TRANSACTION_TYPE = 'SUPPLIERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'CUSTOMERCREDITNOTES' OR hdr.TRANSACTION_TYPE = 'BILL' OR " +
            "hdr.TRANSACTION_TYPE = 'PURCHASEPAYMENT' OR hdr.TRANSACTION_TYPE = 'SALESPAYMENT' OR hdr.TRANSACTION_TYPE = 'INVOICE' ) " +
            "AND CONVERT(DATETIME, hdr.JOURNAL_DATE, 104) BETWEEN ?1 AND ?2) prs " +
            "where prs.accountName LIKE ?3% AND prs.customerName LIKE ?4% AND prs.totalAmount != '0'" +
            "order by prs.id desc ", nativeQuery = true)
    List<PaymentReceiptSummaryPojo> getPaymentReceiptSummary(String stDt, String edDt, String accNm, String cstNm, Pageable pg);

    @Query(value = "select count(aps.supplier) as totalCount,ISNULL(sum(totalPayable),0) as totalAmount from ( " +
            "SELECT ISNULL((SELECT VNAME FROM [##plant##VENDMST] V WHERE V.VENDNO = A.VENDNO),'') as supplier,SUM(AMOUNT_PAYABLE) as amountPayable " +
            ",SUM(PDC_AMOUNT) as pdcAmount, (SUM(AMOUNT_PAYABLE) + SUM(PDC_AMOUNT)) as totalPayable FROM ( SELECT VENDNO " +
            ",CAST(SUM(AMOUNTPAID) AS DECIMAL(18,2)) AS AMOUNT_PAYABLE, 0 AS PDC_AMOUNT FROM ##plant##FINPAYMENTHDR WHERE VENDNO " +
            "IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=6 AND ACCOUNTDETAILTYPE=18) AND PAID_THROUGH " +
            "NOT IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=8) " +
            "AND CONVERT(DATETIME, PAYMENT_DATE, 104) BETWEEN ?1 AND ?2 GROUP BY VENDNO UNION SELECT VENDNO, 0 AS AMOUNT_PAYABLE " +
            ", CAST(ISNULL(SUM(CHEQUE_AMOUNT),0) AS DECIMAL(18,2)) PDC_AMOUNT " +
            "FROM [##plant##FINPAYMENTPDC]  WHERE STATUS = 'NOT PROCESSED'  AND CONVERT(DATETIME, CHEQUE_DATE, 104) BETWEEN ?1 AND ?2 " +
            "OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) BETWEEN ?1 AND ?2 GROUP BY VENDNO )A " +
            "where VENDNO LIKE ?3 GROUP BY VENDNO)aps ", nativeQuery = true)
    AccountsAggregationPojo getAccountPayableSummaryAgg(String stDt, String edDt, String supId);


    @Query(value = "SELECT ISNULL((SELECT VNAME FROM [##plant##VENDMST] V WHERE V.VENDNO = A.VENDNO),'') as supplier,SUM(AMOUNT_PAYABLE) as amountPayable " +
            ",SUM(PDC_AMOUNT) as pdcAmount, (SUM(AMOUNT_PAYABLE) + SUM(PDC_AMOUNT)) as totalPayable FROM ( SELECT VENDNO " +
            ",CAST(SUM(AMOUNTPAID) AS DECIMAL(18,2)) AS AMOUNT_PAYABLE, 0 AS PDC_AMOUNT FROM ##plant##FINPAYMENTHDR WHERE VENDNO " +
            "IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=6 AND ACCOUNTDETAILTYPE=18) AND PAID_THROUGH " +
            "NOT IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=8) " +
            "AND CONVERT(DATETIME, PAYMENT_DATE, 104) BETWEEN ?1 AND ?2 GROUP BY VENDNO UNION SELECT VENDNO, 0 AS AMOUNT_PAYABLE " +
            ", CAST(ISNULL(SUM(CHEQUE_AMOUNT),0) AS DECIMAL(18,2)) PDC_AMOUNT " +
            "FROM [##plant##FINPAYMENTPDC]  WHERE STATUS = 'NOT PROCESSED'  AND CONVERT(DATETIME, CHEQUE_DATE, 104) BETWEEN ?1 AND ?2 " +
            "OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) BETWEEN ?1 AND ?2 GROUP BY VENDNO )A " +
            "where VENDNO LIKE ?3% GROUP BY VENDNO ", nativeQuery = true)
    List<AccountsPayableSummaryPojo> getAccountPayableSummary(String stDt, String edDt, String supId, Pageable pg);

    @Query(value = "select count(ars.customer) as totalCount,ISNULL(sum(totalReceivable),0) as totalAmount from ( " +
            "SELECT ISNULL((SELECT CNAME FROM [##plant##CUSTMST] C WHERE C.CUSTNO = A.CUSTNO),'') as customer " +
            ", (SUM(AMOUNT_RECEIVED) + SUM(PDC_AMOUNT)) as totalReceivable " +
            "FROM ( SELECT CUSTNO,CAST(SUM(AMOUNTRECEIVED) AS DECIMAL(18,2)) AS AMOUNT_RECEIVED, 0 AS PDC_AMOUNT FROM ##plant##FINRECEIVEHDR " +
            "WHERE CUSTNO IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=7) AND DEPOSIT_TO " +
            "NOT IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=8) " +
            "AND CONVERT(DATETIME, RECEIVE_DATE, 104) BETWEEN '2018-01-01' AND '2021-01-01' GROUP BY CUSTNO UNION SELECT CUSTNO, 0 AS AMOUNT_PAYABLE " +
            ", CAST(ISNULL(SUM(CHEQUE_AMOUNT),0) AS DECIMAL(18,2)) PDC_AMOUNT FROM [##plant##FINRECEIVEPDC]  WHERE STATUS = 'NOT PROCESSED' " +
            "AND CONVERT(DATETIME, CHEQUE_DATE, 104) BETWEEN '2018-01-01' AND '2021-01-01' OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) " +
            "BETWEEN '2018-01-01' AND '2021-01-01' GROUP BY CUSTNO )A " +
            "WHERE CUSTNO LIKE 'C00%' GROUP BY CUSTNO) ars ", nativeQuery = true)
    AccountsAggregationPojo getAccountReceivableSummaryAgg(String stDt, String edDt, String cstId);

    @Query(value = "SELECT ISNULL((SELECT CNAME FROM [##plant##CUSTMST] C WHERE C.CUSTNO = A.CUSTNO),'') as customer ,SUM(AMOUNT_RECEIVED) as amountReceivable " +
            ",SUM(PDC_AMOUNT) as pdcAmount, (SUM(AMOUNT_RECEIVED) + SUM(PDC_AMOUNT)) as totalReceivable " +
            "FROM ( SELECT CUSTNO,CAST(SUM(AMOUNTRECEIVED) AS DECIMAL(18,2)) AS AMOUNT_RECEIVED, 0 AS PDC_AMOUNT FROM ##plant##FINRECEIVEHDR " +
            "WHERE CUSTNO IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=7) AND DEPOSIT_TO " +
            "NOT IN (SELECT ACCOUNT_NAME FROM [##plant##FINCHARTOFACCOUNTS] WHERE ACCOUNTTYPE=3 AND ACCOUNTDETAILTYPE=8) " +
            "AND CONVERT(DATETIME, RECEIVE_DATE, 104) BETWEEN ?1 AND ?2 GROUP BY CUSTNO UNION SELECT CUSTNO, 0 AS AMOUNT_PAYABLE " +
            ", CAST(ISNULL(SUM(CHEQUE_AMOUNT),0) AS DECIMAL(18,2)) PDC_AMOUNT FROM [##plant##FINRECEIVEPDC]  WHERE STATUS = 'NOT PROCESSED' " +
            "AND CONVERT(DATETIME, CHEQUE_DATE, 104) BETWEEN ?1 AND ?2 OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) " +
            "BETWEEN ?1 AND ?2 GROUP BY CUSTNO )A " +
            "WHERE CUSTNO LIKE ?3% GROUP BY CUSTNO ", nativeQuery = true)
    List<AccountsReceivableSummaryPojo> getAccountReceivableSummary(String stDt, String edDt, String cstId, Pageable pg);

    @Query(value = "select count(supplier) as totalCount,ISNULL(sum(chequeAmount),0) as totalAmount from ( " +
            "SELECT paymentId,PAYMENT_DATE as paymentDate, ISNULL((SELECT VNAME FROM [##plant##VENDMST] V WHERE V.VENDNO = A.VENDNO),'') as supplier, " +
            "BANK_BRANCH as bankBranch,CHECQUE_NO as chequeNo,CHEQUE_DATE as chequeDate " +
            ",ISNULL(CHEQUE_REVERSAL_DATE,'')as chequeReversalDate ,CAST(CHEQUE_AMOUNT AS DECIMAL(18,5)) as chequeAmount  FROM ##plant##FINPAYMENTPDC A " +
            "WHERE STATUS IN ('NOT PROCESSED','PROCESSED')  AND CONVERT(DATETIME, CHEQUE_DATE, 104) between ?1 and ?2  OR " +
            "CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) between ?1 and ?2) pis " +
            "WHERE pis.supplier like ?3% ", nativeQuery = true)
    AccountsAggregationPojo getPdcIssuedSummaryAgg(String stDt, String edDt, String supNm);

    @Query(value = "select * from ( " +
            "SELECT paymentId,PAYMENT_DATE as paymentDate, ISNULL((SELECT VNAME FROM [##plant##VENDMST] V WHERE V.VENDNO = A.VENDNO),'') as supplier, " +
            "BANK_BRANCH as bankBranch,CHECQUE_NO as chequeNo,CHEQUE_DATE as chequeDate,status " +
            ",ISNULL(CHEQUE_REVERSAL_DATE,'')as chequeReversalDate ,CAST(CHEQUE_AMOUNT AS DECIMAL(18,5)) as chequeAmount  FROM ##plant##FINPAYMENTPDC A " +
            "WHERE STATUS IN ('NOT PROCESSED','PROCESSED')  AND CONVERT(DATETIME, CHEQUE_DATE, 104) between ?1 and ?2 OR " +
            "CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) between ?1 and ?2) pis " +
            "WHERE pis.supplier like ?3% ", nativeQuery = true)
    List<PdcIssuedSummaryPojo> getPdcIssuedSummary(String stDt, String edDt, String supNm, Pageable pg);

    @Query(value = "select count(customer) as totalCount,ISNULL(sum(chequeAmount),0) as totalAmount from ( " +
            "SELECT RECEIVEID as receiveId ,RECEIVE_DATE as receiveDate , ISNULL((SELECT CNAME FROM [##plant##CUSTMST] C " +
            "WHERE C.CUSTNO = A.CUSTNO),'') as customer , BANK_BRANCH as bankbranch ,CHECQUE_NO as chequeNo,status " +
            ",CHEQUE_DATE as chequeDate " +
            ",ISNULL(CHEQUE_REVERSAL_DATE,'') as chequeReversalDate,CAST(CHEQUE_AMOUNT AS DECIMAL(18,5)) as chequeAmount  FROM ##plant##FINRECEIVEPDC A " +
            "WHERE STATUS IN ('NOT PROCESSED','PROCESSED')   AND CONVERT(DATETIME, CHEQUE_DATE, 104) between ?1 and ?2 " +
            "OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) between ?1 and ?2 ) prs " +
            "WHERE prs.customer like ?3% ", nativeQuery = true)
    AccountsAggregationPojo getPdcReceivedSummaryAgg(String stDt, String edDt, String cstNm);

    @Query(value = "select * from ( " +
            "SELECT RECEIVEID as receiveId ,RECEIVE_DATE as receiveDate , ISNULL((SELECT CNAME FROM [##plant##CUSTMST] C " +
            "WHERE C.CUSTNO = A.CUSTNO),'') as customer , BANK_BRANCH as bankbranch ,CHECQUE_NO as chequeNo,status " +
            ",CHEQUE_DATE as chequeDate " +
            ",ISNULL(CHEQUE_REVERSAL_DATE,'') as chequeReversalDate,CAST(CHEQUE_AMOUNT AS DECIMAL(18,5)) as chequeAmount  FROM ##plant##FINRECEIVEPDC A " +
            "WHERE STATUS IN ('NOT PROCESSED','PROCESSED')   AND CONVERT(DATETIME, CHEQUE_DATE, 104) between ?1 and ?2 " +
            "OR CONVERT(DATETIME, CHEQUE_REVERSAL_DATE, 104) between ?1 and ?2 ) prs " +
            "WHERE prs.customer like ?3% ", nativeQuery = true)
    List<PdcReceivedSummaryPojo> getPdcReceivedSummary(String stDt, String edDt, String cstNm, Pageable pg);

    @Query(value = "SELECT SUM((OP.REQTY - OP.SHQTY) * OP.UNITCOST) AS OPENCOST FROM ( SELECT IT.ITEM, CASE WHEN " +
            "(ISNULL(IT.ISCOMPRO,0) = 1) THEN ( CASE WHEN ((select TOP 1 ISNULL(SHOWPREVIOUSSALESCOST, 0) AS FLAG " +
            "from ##plant##OUTBOUND_RECIPT_INVOICE_HDR WHERE PLANT=IT.PLANT AND ORDERTYPE='Outbound Order') = 1) " +
            "THEN (SELECT SUM(PDMDT.UNITCOST * PDM.QTY) FROM ##plant##PODET AS PDMDT JOIN ##plant##PROD_BOM_MST " +
            "AS PDM ON PDM.CITEM=PDMDT.ITEM AND PDM.PITEM=IT.ITEM and PDM.BOMTYPE='KIT' WHERE PDMDT.ID IN " +
            "(ISNULL((SELECT MAX(PDMDTI.ID) FROM ##plant##PODET AS PDMDTI WHERE PDMDTI.ITEM=PDM.CITEM),0))) " +
            "WHEN ((select TOP 1 ISNULL(SHOWPREVIOUSSALESCOST, 0) AS FLAG from ##plant##OUTBOUND_RECIPT_INVOICE_HDR " +
            "WHERE PLANT=IT.PLANT AND ORDERTYPE='Outbound Order') = 0) THEN ( SELECT SUM(POMAVG.AVGE*POMAVG.QTY) " +
            "FROM (SELECT AVG.AVERAGE_COST AS AVGE, AVG.QTY FROM (SELECT (( (SELECT CASE WHEN (SELECT COUNT(CURRENCYID) " +
            "FROM ##plant##RECVDET R WHERE ITEM=PDM.CITEM AND CURRENCYID IS NOT NULL AND tran_type IN( 'IB'," +
            "'GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING' ) )>0 THEN (Select ISNULL(CAST" +
            "(ISNULL(SUM(CASE WHEN A.TRAN_TYPE='GOODSRECEIPTWITHBATCH' THEN 0 ELSE A.UNITCOST END), 0)/SUM(VC) " +
            "AS DECIMAL(20, 5)), 0) AS AVERGAGE_COST from (select TRAN_TYPE, RECQTY VC, CASE WHEN TRAN_TYPE IN " +
            "('INVENTORYUPLOAD', 'DE-KITTING', 'KITTING') THEN (isnull(R.unitcost*(SELECT CURRENCYUSEQT FROM " +
            "##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')), 0)*R.RECQTY) ELSE CAST( " +
            "(CAST(ISNULL( ISNULL((select ISNULL(QPUOM, 1) from ##plant##UOM where UOM=''), 1) * ( " +
            "ISNULL(((isnull(R.unitcost*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL" +
            "(P.CURRENCYID,'SGD')), 0)+ ISNULL((SELECT (SUM(E.QTY*LANDED_COST)/SUM(E.QTY)) FROM ##plant##RECVDET c " +
            "left join ##plant##FINBILLHDR d on c.PONO = d.PONO and c.GRNO = d.GRNO left join ##plant##FINBILLDET e " +
            "on d.ID = e.BILLHDRID where c.pono =R.pono and c.LNNO=R.LNNO and e.ITEM = PDM.CITEM OR c.TRAN_TYPE" +
            "='GOODSRECEIPTWITHBATCH' AND c.item = PDM.CITEM), 0) + (ISNULL(R.unitcost*(SELECT CURRENCYUSEQT " +
            "FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')), 0) * (((ISNULL(P.LOCALEXPENSES, " +
            "0)+ CASE WHEN (SELECT SUM(LANDED_COST) FROM ##plant##FINBILLHDR c join ##plant##FINBILLDET d ON " +
            "c.ID = d.BILLHDRID and c.PONO = R.PONO and d.LNNO = R.LNNO) is null THEN P.SHIPPINGCOST ELSE 0 " +
            "END)*100)/NULLIF((ISNULL((select SUM(s.qtyor*s.UNITCOST*s.CURRENCYUSEQT) from ##plant##podet s " +
            "where s.pono=R.pono), 0)), 0))/100))/ (SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE " +
            "CURRENCYID=ISNULL(P.CURRENCYID,'SGD'))), 0)) , 0) *(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST " +
            "WHERE CURRENCYID='SGD')*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL" +
            "(P.CURRENCYID,'SGD')) AS DECIMAL(20, 5)) ) * CAST((SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST " +
            "WHERE CURRENCYID='SGD')AS DECIMAL(20, 5)) / CAST((SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST " +
            "WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20, 5)) * RECQTY AS DECIMAL(20, 5)) END AS " +
            "UNITCOST from ##plant##RECVDET R LEFT JOIN ##plant##POHDR P ON R.PONO = P.PONO where item =PDM.CITEM " +
            "AND ISNULL(R.UNITCOST,0) != 0 AND tran_type IN('IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD'," +
            "'DE-KITTING','KITTING') ) A) ELSE ( SELECT CASE WHEN (SELECT COUNT(*) FROM ##plant##RECVDET " +
            "WHERE ITEM=PDM.CITEM AND tran_type IN( 'INVENTORYUPLOAD','DE-KITTING','KITTING' ) )>0 THEN " +
            "(SELECT ISNULL(SUM(UNITCOST), 0) FROM ##plant##RECVDET C where item = PDM.CITEM AND " +
            "ISNULL(C.UNITCOST,0) != 0 AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING')) " +
            "ELSE CAST(((SELECT M.COST FROM ##plant##ITEMMST M WHERE M.ITEM = PDM.CITEM)*(SELECT CURRENCYUSEQT " +
            "FROM ##plant##CURRENCYMST WHERE CURRENCYID='SGD')) AS DECIMAL(20, 5)) END) END AS AVERAGE_COST)/ " +
            "(SELECT ISNULL(U.QPUOM, '1') FROM ##plant##ITEMMST AS I LEFT JOIN ##plant##UOM AS U ON " +
            "I.PURCHASEUOM = U.UOM WHERE I.ITEM=PDM.CITEM)) * (SELECT ISNULL(U.QPUOM, '1') FROM ##plant##ITEMMST AS I " +
            "LEFT JOIN ##plant##UOM AS U ON I.INVENTORYUOM = U.UOM WHERE I. ITEM=PDM.CITEM)) AS AVERAGE_COST, " +
            "PDM.QTY FROM ##plant##PROD_BOM_MST AS PDM WHERE PDM.PITEM=IT.ITEM AND PDM.BOMTYPE='KIT' ) AS AVG ) POMAVG) " +
            "ELSE (SELECT SUM(LPS.COST) FROM (SELECT ULP.COST AS COST FROM ##plant##ITEMMST AS ULP JOIN ##plant##PROD_BOM_MST " +
            "AS PDM ON PDM.CITEM=ULP.ITEM AND PDM.PITEM=IT.ITEM AND PDM.BOMTYPE='KIT') AS LPS) END ) ELSE ( CASE WHEN ( " +
            "( select TOP 1 ISNULL(SHOWPREVIOUSSALESCOST, 0) AS FLAG from ##plant##OUTBOUND_RECIPT_INVOICE_HDR WHERE " +
            "PLANT=IT.PLANT AND ORDERTYPE='Outbound Order' ) = 1 ) THEN (SELECT DT.UNITCOST FROM ##plant##PODET AS DT " +
            "WHERE DT.ID IN (SELECT MAX(DTI.ID) FROM ##plant##PODET AS DTI WHERE DTI.ITEM=IT.ITEM)) WHEN ( ( " +
            "select TOP 1 ISNULL(SHOWPREVIOUSSALESCOST, 0) AS FLAG from ##plant##OUTBOUND_RECIPT_INVOICE_HDR WHERE " +
            "PLANT=IT.PLANT AND ORDERTYPE='Outbound Order' ) = 0 ) THEN (SELECT AVG.AVERAGE_COST AS AVGE FROM (SELECT " +
            "(( (SELECT CASE WHEN (SELECT COUNT(CURRENCYID) FROM ##plant##RECVDET R WHERE ITEM=IT.ITEM AND CURRENCYID " +
            "IS NOT NULL AND tran_type IN( 'IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING' ) )>0 " +
            "THEN (Select ISNULL(CAST(ISNULL(SUM(CASE WHEN A.TRAN_TYPE='GOODSRECEIPTWITHBATCH' THEN 0 ELSE A.UNITCOST " +
            "END), 0)/SUM(VC) AS DECIMAL(20, 5)), 0) AS AVERGAGE_COST from (select TRAN_TYPE, RECQTY VC, " +
            "CASE WHEN TRAN_TYPE IN ('INVENTORYUPLOAD', 'DE-KITTING', 'KITTING') THEN (isnull(R.unitcost*(" +
            "SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')), 0)*R.RECQTY) " +
            "ELSE CAST( (CAST(ISNULL( ISNULL((select ISNULL(QPUOM, 1) from ##plant##UOM where UOM=''), 1) * " +
            "( ISNULL(((isnull(R.unitcost*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL" +
            "(P.CURRENCYID,'SGD')), 0)+ ISNULL((SELECT (SUM(E.QTY*LANDED_COST)/SUM(E.QTY)) FROM ##plant##RECVDET " +
            "c left join ##plant##FINBILLHDR d on c.PONO = d.PONO and c.GRNO = d.GRNO left join ##plant##FINBILLDET " +
            "e on d.ID = e.BILLHDRID where c.pono =R.pono and c.LNNO=R.LNNO and e.ITEM = IT.ITEM OR c.TRAN_TYPE=" +
            "'GOODSRECEIPTWITHBATCH' AND c.item = IT.ITEM), 0) + (ISNULL(R.unitcost*(SELECT CURRENCYUSEQT FROM " +
            "##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')), 0) * (((ISNULL(P.LOCALEXPENSES, 0)+ " +
            "CASE WHEN (SELECT SUM(LANDED_COST) FROM ##plant##FINBILLHDR c join ##plant##FINBILLDET d ON c.ID = " +
            "d.BILLHDRID and c.PONO = R.PONO and d.LNNO = R.LNNO) is null THEN P.SHIPPINGCOST ELSE 0 END)*100)/NULLIF" +
            "((ISNULL((select SUM(s.qtyor*s.UNITCOST*s.CURRENCYUSEQT) from ##plant##podet s where s.pono=R.pono), 0)), " +
            "0))/100))/ (SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD'))), " +
            "0)) , 0) *(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID='SGD')*(SELECT CURRENCYUSEQT " +
            "FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20, 5)) ) * " +
            "CAST((SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID='SGD')AS DECIMAL(20, 5)) / CAST((SELECT " +
            "CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20, 5)) * " +
            "RECQTY AS DECIMAL(20, 5)) END AS UNITCOST from ##plant##RECVDET R LEFT JOIN ##plant##POHDR P ON R.PONO = " +
            "P.PONO where item =IT.ITEM AND ISNULL(R.UNITCOST,0) != 0 AND tran_type IN('IB','GOODSRECEIPTWITHBATCH'," +
            "'INVENTORYUPLOAD','DE-KITTING','KITTING') ) A) ELSE ( SELECT CASE WHEN (SELECT COUNT(*) FROM ##plant##RECVDET " +
            "WHERE ITEM=IT.ITEM AND tran_type IN( 'INVENTORYUPLOAD','DE-KITTING','KITTING' ) )>0 THEN " +
            "(SELECT ISNULL(SUM(UNITCOST), 0) FROM ##plant##RECVDET C where item = IT.ITEM AND ISNULL(C.UNITCOST,0) != 0 " +
            "AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING')) ELSE CAST(((SELECT M.COST FROM ##plant##ITEMMST M " +
            "WHERE M.ITEM = IT.ITEM)*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST WHERE CURRENCYID='SGD')) AS " +
            "DECIMAL(20, 5)) END) END AS AVERAGE_COST)/ (SELECT ISNULL(U.QPUOM, '1') FROM ##plant##ITEMMST AS I " +
            "LEFT JOIN ##plant##UOM AS U ON I.PURCHASEUOM = U.UOM WHERE I.ITEM=IT.ITEM)) * (SELECT ISNULL(U.QPUOM, '1') " +
            "FROM ##plant##ITEMMST AS I LEFT JOIN ##plant##UOM AS U ON I.SALESUOM = U.UOM WHERE I. ITEM=IT.ITEM)) AS " +
            "AVERAGE_COST) AS AVG) ELSE IT.COST END ) END AS UNITCOST, " +
            "ISNULL((SELECT ((SUM(RDT.RECQTY)*(SELECT TOP 1 ISNULL(U.QPUOM, '1') FROM ##plant##UOM AS U WHERE U.UOM = IT.PURCHASEUOM))/(SELECT TOP 1 ISNULL(U.QPUOM, '1') FROM ##plant##UOM AS U WHERE U.UOM = IT.SALESUOM)) AS qty FROM ##plant##RECVDET AS RDT WHERE CONVERT(DATETIME, RDT.RECVDATE, 104) < CONVERT(DATETIME, ?2, 104) AND RDT.ITEM = IT.ITEM),0) AS REQTY, " +
            "ISNULL((SELECT ((SUM(SHS.PICKQTY)*(SELECT TOP 1 ISNULL(U.QPUOM, '1') FROM ##plant##UOM AS U WHERE U.UOM = IT.PURCHASEUOM))/(SELECT TOP 1 ISNULL(U.QPUOM, '1') FROM ##plant##UOM AS U WHERE U.UOM = IT.SALESUOM)) AS qty FROM ##plant##SHIPHIS AS SHS WHERE CONVERT(DATETIME, SHS.ISSUEDATE, 104) < CONVERT(DATETIME, ?2, 104) AND SHS.ITEM = IT.ITEM),0) AS SHQTY " +
            "FROM ##plant##ITEMMST AS IT WHERE IT.PLANT = ?1) AS OP", nativeQuery = true)
    double getOpeningbalancetotal(String plant,String fromdate);

}
