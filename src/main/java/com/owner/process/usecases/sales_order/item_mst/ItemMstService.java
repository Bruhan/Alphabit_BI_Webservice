package com.owner.process.usecases.sales_order.item_mst;

import com.owner.process.persistence.models.ItemMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemMstService {
	@Autowired
	ItemMstRepository itemMstRepository;
	public String checkItemMstPk(String pk0) throws Exception {
		try {
			ItemMst getVal = itemMstRepository.findByItem(pk0);
			if(getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public ItemMst getItemMstPk(String pk0) throws Exception {
		ItemMst getVal;
		try {
			getVal = itemMstRepository.findByItem(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemMst> getItemMstall() throws Exception {
		List<ItemMst> getVal;
		try {
			getVal = itemMstRepository.findAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String setItemMstDetails(ItemMst val) throws Exception {
		try {
			itemMstRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public Boolean checkItemMstNonStock(String pk0) throws Exception {
		ItemMst getVal;
		try {
			getVal = itemMstRepository.findByItem(pk0);
			if (getVal == null)
				return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal.getNonStackFlag().equals("Y");
	}

	public String getAvgCost(String pk0,String pk1) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getAvgCostOfItem(pk0);
			String p = itemMstRepository.getItemPurchaseQpuom(pk0);
			String s = itemMstRepository.getItemSalesQpuom(pk0);
			double val = (Double.valueOf(getVal)/Double.valueOf(p))*Double.valueOf(s);
			getVal = String.valueOf(val);
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getAvgCostpurchase(String pk0,String pk1) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getAvgCostOfItem(pk0);
			/*String p = itemMstRepository.getItemPurchaseQpuom(pk0);
			String s = itemMstRepository.getItemSalesQpuom(pk0);
			double val = (Double.valueOf(getVal)/Double.valueOf(p))*Double.valueOf(s);
			getVal = String.valueOf(val);*/
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public int getSalesPriceCodition(String pk0,String pk1) throws Exception {
		int getVal;
		try {
			getVal = itemMstRepository.getSalesPriceCodition(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getListPriceOfItem(String pk0) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getListPriceOfItem(pk0);
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getorderPricOfItem(String pk0) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getorderPricOfItem(pk0);
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}


	public String getListCostOfItem(String pk0) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getListCostOfItem(pk0);
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getorderCostOfItem(String pk0) throws Exception {
		String getVal;
		try {
			getVal = itemMstRepository.getorderCostOfItem(pk0);
			if (getVal == null)
				return "0";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}
}
