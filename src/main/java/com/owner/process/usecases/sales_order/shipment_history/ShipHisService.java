package com.owner.process.usecases.sales_order.shipment_history;

import com.owner.process.persistence.models.ItemQty;
import com.owner.process.persistence.models.ItemQtyPrice;
import com.owner.process.persistence.models.SalesSummaryWithTax;
import com.owner.process.persistence.models.ShipHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShipHisService {
	@Autowired
	ShipHisRepository shipHisRepository;

	public Boolean checkShipHisPk2(String pk0) throws Exception {
		try {
			ShipHis getVal = shipHisRepository.findByDoNo(pk0);
			if (getVal == null)
				return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	public Boolean checkShipHisPk3(String pk0, String pk1) throws Exception {
		try {
			ShipHis getVal = shipHisRepository.findByDoNoAndItem(pk0, pk1);
			if (getVal == null)
				return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	public Boolean checkShipHisPk4(String pk0, String pk1, String pk2) throws Exception {
		try {
			ShipHis getVal = shipHisRepository.findByDoNoAndDoLineNoAndItem(pk0, pk1, pk2);
			if (getVal == null)
				return true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	public List<ShipHis> getShipHisPk1(String pk0, String pk1) throws Exception {
		List<ShipHis> getVal;
		try {
			getVal = shipHisRepository.findByDoNoAndDoLineNo(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ShipHis> getShipHisPk1OrderByStatus(String pk0, String pk1) throws Exception {
		List<ShipHis> getVal;
		try {
			getVal = shipHisRepository.findByDoNoAndDoLineNoOrderByStatus(pk0, pk1, "C", "O");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public ShipHis getShipHisPk2(String pk0) throws Exception {
		ShipHis getVal;
		try {
			getVal = shipHisRepository.findByDoNo(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ShipHis> getShipHisPk3(String pk0, String pk1) throws Exception {
		List<ShipHis> getVal;
		try {
			getVal = Collections.singletonList(shipHisRepository.findByDoNoAndItem(pk0, pk1));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public ShipHis getShipHisPk4(String pk0, String pk1, String pk2) throws Exception {
		ShipHis getVal;
		try {
			getVal = shipHisRepository.findByDoNoAndDoLineNoAndItem(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String setShipHisDetails(ShipHis val) throws Exception {
		try {
			shipHisRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String setAllShipHisDetails(List<ShipHis> val) throws Exception {
		try {
			shipHisRepository.saveAll(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String delShipmentHisPk(Integer pk0) throws Exception {
		Long status;
		try {
			status = shipHisRepository.deleteByBeanId(pk0);
			System.out.println(status);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public Integer deleteShipHisPk2(String pk0, Integer pk1) throws Exception {
		try {
			shipHisRepository.deleteByDoNoAndDoLineNo(pk0, pk1).getId();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return 0;
	}

	public List<ItemQty> getItemQty(String pk0, String pk1) throws Exception {
		List<ItemQty> getVal;
		try {
			getVal = shipHisRepository.findByItemQty(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyWithoutKit(String pk0, String pk1) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyWithOutKitAndDkit(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyWithoutKitpos(String pk0, String pk1) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyWithOutKitAndDkitpos(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyWithoutKiterp(String pk0, String pk1) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyWithOutKitAndDkiterp(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQty> getItemQtyToDate(String pk0) throws Exception {
		List<ItemQty> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyToDate(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQty> getItemQtyFromDate(String pk0) throws Exception {
		List<ItemQty> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyFromDate(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyByCustId(String pk0, String pk1, String pk2) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyByCustId(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyByterminalIdpos(String pk0, String pk1, String pk2) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.getItemQtyByterminalIdpos(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyByOutletIdpos(String pk0, String pk1, String pk2) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.getItemQtyByOutletIdpos(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyByOutletterminalIdpos(String pk0, String pk1, String pk2, String pk3) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.getItemQtyByOutletterminalIdpos(pk0, pk1, pk2, pk3);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<ItemQtyPrice> getItemQtyByCustIderp(String pk0, String pk1, String pk2) throws Exception {
		List<ItemQtyPrice> getVal;
		try {
			getVal = shipHisRepository.findByItemQtyByCustIderp(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getTotalSaleswithtax(String pk0, String pk1) throws Exception {
		String getVal;
		try {
			getVal = shipHisRepository.getTotalSaleswithtax(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getTotalCustSalesWithTax(String pk0, String pk1, String pk2) throws Exception {
		String getVal;
		try {
			getVal = shipHisRepository.getTotalCustSalesWithTax(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}


	public List<SalesSummaryWithTax> getSalesDetailWithTax(String pk0, String pk1, String pk2) throws Exception {
		List<SalesSummaryWithTax> getVal;
		try {
			getVal = shipHisRepository.getSalesDetailWithTax(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getTotalSalesSubTotalWithTax(String pk0, String pk1, String pk2) throws Exception {
		String getVal;
		try {
			getVal = shipHisRepository.getTotalSalesSubTotalWithTax(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String getTotalSalesTaxTotalWithTax(String pk0, String pk1, String pk2) throws Exception {
		String getVal;
		try {
			getVal = shipHisRepository.getTotalSalesTaxTotalWithTax(pk0, pk1, pk2);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}
}
