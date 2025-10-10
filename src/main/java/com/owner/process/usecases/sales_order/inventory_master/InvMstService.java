package com.owner.process.usecases.sales_order.inventory_master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.owner.process.persistence.models.InvMst;

@Service
public class InvMstService {
	@Autowired
	InvMstRepository invMstRepository;
	public Boolean checkInvMstPk1(Integer pk0,String pk1,String pk2,String pk3) throws Exception {
		try {
			InvMst getVal = invMstRepository.findByIdAndItemAndLocationAndUserFieldFour(pk0,pk1,pk2,pk3);
			if(getVal == null)
				return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	public InvMst getInvMstPk1(Integer pk0,String pk1,String pk2,String pk3) throws Exception {
		InvMst getVal;
		try {
			getVal = invMstRepository.findByIdAndItemAndLocationAndUserFieldFour(pk0,pk1,pk2,pk3);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public InvMst getInvMstByILB(String pk0,String pk1,String pk2) throws Exception {
		InvMst getVal;
		try {
			getVal = invMstRepository.findByItemAndLocationAndUserFieldFour(pk0,pk1,pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public InvMst getInvMstPk2(Integer pk0) throws Exception {
		InvMst getVal;
		try {
			getVal = invMstRepository.findById(pk0).get();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public Boolean delInvMstPk2(Integer pk0) throws Exception {
		try {
			invMstRepository.deleteById(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}

	public String setInvMstDetails(InvMst val) throws Exception {
		try {
			invMstRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public double getTQtyByItemLocBatch(String pk0,String pk1,String pk2) throws Exception {
		double getVal =0.0;
		try {
			getVal = invMstRepository.getTQtyByItemLocBatch(pk0,pk1,pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public InvMst getInvMstILB(String pk0,String pk1,String pk2) throws Exception {
		InvMst getVal;
		try {
			getVal = invMstRepository.findByItemAndLocationAndUserFieldFour(pk0,pk1,pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public double getTQtyByItem(String pk0) throws Exception {
		double getVal =0.0;
		try {
			getVal = invMstRepository.getTQtyByItem(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}
}
