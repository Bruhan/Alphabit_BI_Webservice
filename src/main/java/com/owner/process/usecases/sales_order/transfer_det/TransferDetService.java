package com.owner.process.usecases.sales_order.transfer_det;

import com.owner.process.persistence.models.DoTransferDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferDetService {
	@Autowired
	TransferDetRepository transferDetRepository;

	public String checkDoTransferDetPk(String pk1, Integer pk2) throws Exception {
		try {
			DoTransferDet getVal = transferDetRepository.findByDoNoAndDoLineNo(pk1, pk2);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "0";
	}

	public String setDoTransferDetDetails(DoTransferDet val) throws Exception {
		try {
			transferDetRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}

	public String setAllDoTransferDetDetails(List<DoTransferDet> val) throws Exception {
		try {
			transferDetRepository.saveAll(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}

	public String deleteDoTransferDetPk(String doNo, Integer i) throws Exception {
		try {
			transferDetRepository.deleteByDoNoAndDoLineNo(doNo, i);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String deleteDoTransferDetPk(String doNo) throws Exception {
		try {
			transferDetRepository.deleteByDoNo(doNo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}
}
