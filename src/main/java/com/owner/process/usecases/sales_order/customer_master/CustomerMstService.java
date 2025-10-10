package com.owner.process.usecases.sales_order.customer_master;

import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.CustomerMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerMstService {
	@Autowired
	CustomerMstRepository customerMstRepository;

	public CustomerMst checkCustomerMstPk(CustomerMst customerMst) throws Exception {
		customerMst.setId(0);
		CustomerMst getVal;
		try {
			getVal = customerMstRepository.
					findFirstByCustomerNameAndWorkPhoneAndAddressROneOrderById(
							customerMst.getCustomerName(), customerMst.getWorkPhone(), customerMst.getAddressROne());
			if (getVal == null)
				return customerMst;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		customerMst.setId(getVal.getId());
		customerMst.setCustomerNo(getVal.getCustomerNo());
		customerMst.setUserId(getVal.getCustomerNo());
		customerMst.setCrAt(getVal.getCrAt());
		customerMst.setCrBy(getVal.getCrBy());
		return customerMst;
	}

	public CustomerMst checkCustomerExistStatusUsgNum(String number, String name, CustomerMst customerMst) throws Exception {
		customerMst.setId(0);
		List<CustomerMst> customerMstList = getCustomerNoPkUsgWorkPhone(number);
		for (CustomerMst mst : customerMstList) {
			if (name.equals(mst.getCustomerName())) {
				//customerNo = customerMstList.get(i).getCustomerNo();
				customerMst.setId(mst.getId());
				customerMst.setCustomerNo(mst.getCustomerNo());
				break;
			}
		}
		return customerMst;
	}

	public CustomerMst checkCustomerExistStatusUsgEmail(CustomerMst customerMst) throws Exception {
		customerMst.setId(0);
		CustomerMst getVal;
		try {
			getVal = customerMstRepository.
					findFirstByCustomerNameAndEmailAndAddressROneOrderById(
							customerMst.getCustomerName(), customerMst.getEmail(), customerMst.getAddressROne());
			if (getVal == null)
				return customerMst;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		customerMst.setId(getVal.getId());
		customerMst.setCustomerNo(getVal.getCustomerNo());
		customerMst.setUserId(getVal.getCustomerNo());
		customerMst.setCrAt(getVal.getCrAt());
		customerMst.setCrBy(getVal.getCrBy());
		return customerMst;
	}

	public List<CustomerMst> getCustomerNoPkUsgEmail(String email) throws Exception {
		try {
			List<CustomerMst> val = customerMstRepository.findByEmail(email);
			if (val != null)
				return val;
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public List<CustomerMst> getCustomerNoPkUsgWorkPhone(String phoneNo) throws Exception {
		try {
			List<CustomerMst> val = customerMstRepository.findByWorkPhone(phoneNo);
			if (val != null)
				return val;
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public String getCustomerNoPkUsgWorkPhoneOld(String phoneNo) throws Exception {
		try {
			List<CustomerMst> val = customerMstRepository.findByWorkPhone(phoneNo);
			if (val != null)
				return val.get(0).getCustomerNo();
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String getCustomerNoPkUsgEmailOld(String email) throws Exception {
		try {
			List<CustomerMst> val = customerMstRepository.findByEmail(email);
			if (val != null)
				return val.get(0).getCustomerNo();
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String setCustomerMstDetails(CustomerMst val) throws Exception {
		try {
			customerMstRepository.save(val);
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public CustomerMst getCustomerDefaultAddress(String customerName) throws Exception {
		CustomerMst customerMst;
		try {
			customerMst = customerMstRepository.findByDefaultAddress(customerName);
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return customerMst;
	}

	public CustomerMst GetCustomerMstByCode(String CustCode) throws Exception {
		CustomerMst getVal;
		try {
			getVal = customerMstRepository.findByCustomerNo(CustCode);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<CustomerMst> getAllCustomer() throws Exception {
		try {
			List<CustomerMst> val = customerMstRepository.findAll();
			if (val != null)
				return val;
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return null;
	}
}
