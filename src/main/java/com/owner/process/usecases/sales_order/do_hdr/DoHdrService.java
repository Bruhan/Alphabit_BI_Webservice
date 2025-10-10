package com.owner.process.usecases.sales_order.do_hdr;

import com.owner.process.persistence.models.DoHdr;
import com.owner.process.usecases.sales_order.salessummary.salesSummaryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoHdrService {
	@Autowired
	DoHdrRepository doHdrRepository;
	public String checkDoHdrPk(String pk0) throws Exception {
		try {
			DoHdr getVal = doHdrRepository.findByDoNo(pk0);
			if(getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public DoHdr getDoHdrPk(String pk0) throws Exception {
		DoHdr getVal;
		try {
			getVal = doHdrRepository.findByDoNo(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String setDoHdrDetails(DoHdr val) throws Exception {
		try {
			doHdrRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public List<salesSummaryPojo> getSalesOrderSummary(String pk0) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderSummary(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<DoHdr> getbystatus(String pk0) throws Exception {
		List<DoHdr> getVal;
		try {
			getVal=doHdrRepository.findByOrderStatus(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByAppStatus(String status,String appStatus) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByAppStatus(status,appStatus);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByNotStatus(String status,String appStatus) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByNotStatus(status,appStatus);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByAppStatusFD(String status,String appStatus,String custCode,String dono,String search,String fromDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByAppStatusFD(status,appStatus,custCode,dono,search,fromDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByNotStatusFD(String status,String appStatus,String custCode,String dono,String search,String fromDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByNotStatusFD(status,appStatus,custCode,dono,search,fromDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByAppStatusTD(String status,String appStatus,String custCode,String dono,String search,String toDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByAppStatusTD(status,appStatus,custCode,dono,search,toDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByNotStatusTD(String status,String appStatus,String custCode,String dono,String search,String toDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByNotStatusTD(status,appStatus,custCode,dono,search,toDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByAppStatusDF(String status,String appStatus,String custCode,String dono,String search,String fromDate,String toDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByAppStatusDF(status,appStatus,custCode,dono,search,fromDate,toDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByNotStatusDF(String status,String appStatus,String custCode,String dono,String search,String fromDate,String toDate) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByNotStatusDF(status,appStatus,custCode,dono,search,fromDate,toDate);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByAppStatusFilter(String status,String appStatus,String custCode,String dono,String search) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByAppStatusFilter(status,appStatus,custCode,dono,search);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<salesSummaryPojo> getSalesOrderByNotStatusFilter(String status,String appStatus,String custCode,String dono,String search) throws Exception {
		List<salesSummaryPojo> getVal = new ArrayList<salesSummaryPojo>();
		try {
			getVal=doHdrRepository.getSalesOrderByNotStatusFilter(status,appStatus,custCode,dono,search);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<DoHdr> getDoHdrByAppCustOrderStatus(String pk0) throws Exception {
		List<DoHdr> getVal;
		try {
			getVal = doHdrRepository.findByappCustOrderStatus(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public DoHdr getDoHdrByDoNo(String pk0) throws Exception {
		DoHdr getVal = new DoHdr();
		try {
			List<DoHdr> getVallist = doHdrRepository.findByDono(pk0);
			for (DoHdr doHdr:getVallist) {
				getVal = doHdr;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public DoHdr getDoHdrByUkey(String pk0) throws Exception {
		DoHdr getVal = new DoHdr();
		try {
			List<DoHdr> getVallist = doHdrRepository.findByUniqueKey(pk0);
			for (DoHdr doHdr:getVallist) {
				getVal = doHdr;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public void saveDoHdr(DoHdr doHdr) throws Exception {
		try {
			doHdrRepository.save(doHdr);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void delete(DoHdr doHdr) throws Exception {
		try {
			doHdrRepository.delete(doHdr);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
