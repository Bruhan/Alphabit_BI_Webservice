package com.owner.process.usecases.sales_order.do_hdr;

import com.owner.process.persistence.models.DoHdr;
import com.owner.process.usecases.sales_order.salessummary.salesSummaryPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoHdrRepository extends JpaRepository<DoHdr,Long> {
	DoHdr findByDoNo(String pk0);
	List<DoHdr> findByUniqueKey(String PK0);

	@Query(value="SELECT * from ##plant##DOHDR WHERE DONO =?1",nativeQuery = true)
	List<DoHdr> findByDono(String PK0);

	List<DoHdr> findByOrderStatus(String pk0);
	List<DoHdr> findByappCustOrderStatus(String PK0);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName," +
			"DELDATE AS date,ORDER_STATUS AS orderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE H.ORDER_STATUS =?1",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderSummary(String pk0);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') =?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByAppStatus(String status,String appStatus);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') !=?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByNotStatus(String status,String appStatus);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') =?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) >= CAST(?6 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByAppStatusFD(String status,String appStatus,String custCode,String dono,String search,String fromDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') !=?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) >= CAST(?6 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByNotStatusFD(String status,String appStatus,String custCode,String dono,String search,String fromDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') =?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) <= CAST(?6 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByAppStatusTD(String status,String appStatus,String custCode,String dono,String search,String toDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') !=?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) <= CAST(?6 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByNotStatusTD(String status,String appStatus,String custCode,String dono,String search,String toDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') =?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) >= CAST(?6 AS datetime) AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + " +
			"SUBSTRING(DELDATE, 1, 2)) AS datetime) <= CAST(?7 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByAppStatusDF(String status,String appStatus,String custCode,String dono,String search,String fromDate,String toDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') !=?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"AND CAST((SUBSTRING(DELDATE, 7, 4) + '-' + SUBSTRING(DELDATE, 4, 2) + '-' + SUBSTRING(DELDATE, 1, 2)) " +
			"AS datetime) >= CAST(?6 AS datetime) AND CAST((SUBSTRING(DELDATE, 7, 4) +'-' + SUBSTRING(DELDATE, 4, 2) + '-' + " +
			"SUBSTRING(DELDATE, 1, 2)) AS datetime) <= CAST(?7 AS datetime) order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByNotStatusDF(String status,String appStatus,String custCode,String dono,String search,String fromDate,String toDate);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') =?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByAppStatusFilter(String status,String appStatus,String custCode,String dono,String search);

	@Query(value="SELECT DONO AS dono,H.CustCode AS custCode,H.CustName AS custName,DELDATE AS date," +
			"ORDER_STATUS AS orderStatus,ISNULL(APP_CUST_ORDER_STATUS,'0') AS appOrderStatus,(SELECT COUNT(*) " +
			"FROM ##plant##DODET AS D WHERE D.DONO = H.DONO) AS itemCount " +
			"FROM ##plant##DOHDR AS H  WHERE ISNULL(ORDER_STATUS,'Draft') !=?1 AND " +
			"ISNULL(APP_CUST_ORDER_STATUS,'0') =?2 AND H.CustCode LIKE %?3% AND H.DONO LIKE %?4% AND " +
			"(H.CustCode LIKE %?5% OR H.CustName LIKE %?5% OR H.DONO LIKE %?5% OR H.ORDER_STATUS LIKE %?5%) " +
			"order by ID desc",nativeQuery = true)
	List<salesSummaryPojo> getSalesOrderByNotStatusFilter(String status,String appStatus,String custCode,String dono,String search);


}
