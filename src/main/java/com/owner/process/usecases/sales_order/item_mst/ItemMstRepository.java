package com.owner.process.usecases.sales_order.item_mst;

import com.owner.process.persistence.models.ItemMst;
import com.owner.process.usecases.sales_order.salessummary.salesSummaryPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMstRepository extends JpaRepository<ItemMst,Long> {
	ItemMst findByItem(String pk0);

	/*@Query(value="SELECT CASE WHEN  (SELECT COUNT(CURRENCYID) FROM ##plant##RECVDET R WHERE ITEM=?1 AND CURRENCYID IS NOT NULL AND tran_type \n" +
			"IN('IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING') )>0 THEN  \n" +
			"(Select ISNULL(CAST(ISNULL(SUM(CASE WHEN A.TRAN_TYPE='GOODSRECEIPTWITHBATCH' THEN 0 \n" +
			"ELSE A.UNITCOST END),0)/SUM(VC) AS DECIMAL(20,5)),0) AS AVERGAGE_COST from  \n" +
			"(select TRAN_TYPE,RECQTY VC,CASE WHEN TRAN_TYPE IN ('INVENTORYUPLOAD','DE-KITTING','KITTING') THEN\n" +
			"(isnull(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0)*R.RECQTY) ELSE CAST( (CAST(ISNULL(\n" +
			"ISNULL((select ISNULL(QPUOM,1) from ##plant##UOM where UOM=''),1) * ( ISNULL(((isnull(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0)+ ISNULL((SELECT (SUM(E.QTY*LANDED_COST)/SUM(E.QTY)) FROM ##plant##RECVDET c left join ##plant##FINBILLHDR d on\n" +
			"c.PONO = d.PONO and c.GRNO = d.GRNO left join ##plant##FINBILLDET e on d.ID = e.BILLHDRID where c.pono =R.pono and c.LNNO=R.LNNO and e.ITEM = ?1 \n" +
			"OR c.TRAN_TYPE='GOODSRECEIPTWITHBATCH' AND c.item = ?1),0) +  (ISNULL(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0) * (((ISNULL(P.LOCALEXPENSES,0)+ CASE WHEN (SELECT SUM(LANDED_COST) FROM ##plant##FINBILLHDR c join \n" +
			"##plant##FINBILLDET d  ON c.ID = d.BILLHDRID and c.PONO = R.PONO and d.LNNO = R.LNNO) is null THEN P.SHIPPINGCOST ELSE 0 END)*100)/NULLIF((ISNULL((select \n" +
			"SUM(s.qtyor*s.UNITCOST*s.CURRENCYUSEQT) from ##plant##podet s where s.pono=R.pono),0)),0))/100))/ (SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD'))),0) / (ISNULL((select ISNULL(QPUOM,1) from ##plant##UOM where UOM=?2),1))) ,0) \n" +
			"*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST    WHERE  CURRENCYID='SGD')*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST    \n" +
			"WHERE  CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20,5)) )  * CAST((SELECT CURRENCYUSEQT  \n" +
			"FROM ##plant##CURRENCYMST WHERE  CURRENCYID='SGD')AS DECIMAL(20,5))  / CAST((SELECT CURRENCYUSEQT \n" +
			"FROM ##plant##CURRENCYMST WHERE  CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20,5))   \n" +
			"* RECQTY AS DECIMAL(20,5)) END AS UNITCOST from ##plant##RECVDET R LEFT JOIN ##plant##POHDR P ON R.PONO = P.PONO \n" +
			" where item =?1 AND ISNULL(R.UNITCOST,0) != 0 AND tran_type IN('IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING')    ) A)   \n" +
			" ELSE (SELECT CASE WHEN (SELECT COUNT(*) FROM ##plant##RECVDET WHERE ITEM=?1 AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING') )>0\n" +
			" THEN (SELECT SUM(UNITCOST) FROM ##plant##RECVDET C where item = ?1 \n" +
			" AND ISNULL(C.UNITCOST,0) != 0 AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING')) ELSE \n" +
			" CAST(((SELECT M.COST FROM ##plant##ITEMMST M WHERE M.ITEM = ?1)*(SELECT CURRENCYUSEQT  FROM\n" +
			" ##plant##CURRENCYMST WHERE  CURRENCYID='SGD')) AS DECIMAL(20,5))   END) END  AS AVERAGE_COST",nativeQuery = true)
	String getAvgCostOfItem(String item, String uom);*/

	@Query(value="SELECT CASE WHEN  (SELECT COUNT(CURRENCYID) FROM ##plant##RECVDET R WHERE ITEM=?1 AND CURRENCYID IS NOT NULL AND tran_type \n" +
			"IN('IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING') )>0 THEN  \n" +
			"(Select ISNULL(CAST(ISNULL(SUM(CASE WHEN A.TRAN_TYPE='GOODSRECEIPTWITHBATCH' THEN 0 \n" +
			"ELSE A.UNITCOST END),0)/SUM(VC) AS DECIMAL(20,5)),0) AS AVERGAGE_COST from  \n" +
			"(select TRAN_TYPE,RECQTY VC,CASE WHEN TRAN_TYPE IN ('INVENTORYUPLOAD','DE-KITTING','KITTING') THEN\n" +
			"(isnull(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0)*R.RECQTY) ELSE CAST( (CAST(ISNULL(\n" +
			"ISNULL((select ISNULL(QPUOM,1) from ##plant##UOM where UOM=''),1) * ( ISNULL(((isnull(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0)+ ISNULL((SELECT (SUM(E.QTY*LANDED_COST)/SUM(E.QTY)) FROM ##plant##RECVDET c left join ##plant##FINBILLHDR d on\n" +
			"c.PONO = d.PONO and c.GRNO = d.GRNO left join ##plant##FINBILLDET e on d.ID = e.BILLHDRID where c.pono =R.pono and c.LNNO=R.LNNO and e.ITEM = ?1 \n" +
			"OR c.TRAN_TYPE='GOODSRECEIPTWITHBATCH' AND c.item = ?1),0) +  (ISNULL(R.unitcost*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE  \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD')),0) * (((ISNULL(P.LOCALEXPENSES,0)+ CASE WHEN (SELECT SUM(LANDED_COST) FROM ##plant##FINBILLHDR c join \n" +
			"##plant##FINBILLDET d  ON c.ID = d.BILLHDRID and c.PONO = R.PONO and d.LNNO = R.LNNO) is null THEN P.SHIPPINGCOST ELSE 0 END)*100)/NULLIF((ISNULL((select \n" +
			"SUM(s.qtyor*s.UNITCOST*s.CURRENCYUSEQT) from ##plant##podet s where s.pono=R.pono),0)),0))/100))/ (SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST WHERE \n" +
			"CURRENCYID=ISNULL(P.CURRENCYID,'SGD'))),0)) ,0) \n" +
			"*(SELECT CURRENCYUSEQT FROM ##plant##CURRENCYMST    WHERE  CURRENCYID='SGD')*(SELECT CURRENCYUSEQT  FROM ##plant##CURRENCYMST    \n" +
			"WHERE  CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20,5)) )  * CAST((SELECT CURRENCYUSEQT  \n" +
			"FROM ##plant##CURRENCYMST WHERE  CURRENCYID='SGD')AS DECIMAL(20,5))  / CAST((SELECT CURRENCYUSEQT \n" +
			"FROM ##plant##CURRENCYMST WHERE  CURRENCYID=ISNULL(P.CURRENCYID,'SGD')) AS DECIMAL(20,5))   \n" +
			"* RECQTY AS DECIMAL(20,5)) END AS UNITCOST from ##plant##RECVDET R LEFT JOIN ##plant##POHDR P ON R.PONO = P.PONO \n" +
			" where item =?1 AND ISNULL(R.UNITCOST,0) != 0 AND tran_type IN('IB','GOODSRECEIPTWITHBATCH','INVENTORYUPLOAD','DE-KITTING','KITTING')    ) A)   \n" +
			" ELSE (SELECT CASE WHEN (SELECT COUNT(*) FROM ##plant##RECVDET WHERE ITEM=?1 AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING') )>0\n" +
			" THEN (SELECT ISNULL(SUM(UNITCOST),0) FROM ##plant##RECVDET C where item = ?1 \n" +
			" AND ISNULL(C.UNITCOST,0) != 0 AND tran_type IN('INVENTORYUPLOAD','DE-KITTING','KITTING')) ELSE \n" +
			" CAST(((SELECT M.COST FROM ##plant##ITEMMST M WHERE M.ITEM = ?1)*(SELECT CURRENCYUSEQT  FROM\n" +
			" ##plant##CURRENCYMST WHERE  CURRENCYID='SGD')) AS DECIMAL(20,5))   END) END  AS AVERAGE_COST",nativeQuery = true)
	String getAvgCostOfItem(String item);

	@Query(value="select TOP 1 ISNULL(SHOWPREVIOUSSALESCOST,0) AS FLAG from ##plant##OUTBOUND_RECIPT_INVOICE_HDR " +
			"WHERE PLANT=?1 AND ORDERTYPE=?2",nativeQuery = true)
	int getSalesPriceCodition(String plant,String ordertype);

	@Query(value="SELECT ISNULL(UNITPRICE,'0') AS UNITPRICE FROM ##plant##ITEMMST WHERE ITEM=?1",nativeQuery = true)
	String getListPriceOfItem(String item);

	@Query(value="SELECT ISNULL(COST,'0') AS COST FROM ##plant##ITEMMST WHERE ITEM=?1",nativeQuery = true)
	String getListCostOfItem(String item);

	@Query(value="SELECT UNITPRICE FROM ##plant##DODET WHERE ID IN (SELECT MAX(ID) " +
			"FROM ##plant##DODET WHERE ITEM=?1)",nativeQuery = true)
	String getorderPricOfItem(String item);

	@Query(value="SELECT UNITCOST FROM ##plant##PODET WHERE ID IN (SELECT MAX(ID) " +
			"FROM ##plant##PODET WHERE ITEM=?1)",nativeQuery = true)
	String getorderCostOfItem(String item);

	@Query(value="SELECT ISNULL(U.QPUOM,'1') FROM ##plant##ITEMMST AS I LEFT JOIN ##plant##UOM AS U ON " +
			"I.PURCHASEUOM = U.UOM WHERE I. ITEM=?1",nativeQuery = true)
	String getItemPurchaseQpuom(String item);

	@Query(value="SELECT ISNULL(U.QPUOM,'1') FROM ##plant##ITEMMST AS I LEFT JOIN ##plant##UOM AS U ON " +
			"I.SALESUOM = U.UOM WHERE I. ITEM=?1",nativeQuery = true)
	String getItemSalesQpuom(String item);

}
