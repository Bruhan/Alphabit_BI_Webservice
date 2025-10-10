package com.owner.process.usecases.sales_order.transfer_header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.owner.process.persistence.models.DoTransferHdr;

@Service
public class TransferHdrService {
	@Autowired
	TransferHdrRepository transferHdrRepository;
	public String checkDoTransferHdrPk(String val) throws Exception {
		try {
			DoTransferHdr getVal = transferHdrRepository.findByDoNo(val);
			if(getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
	return "0";
	}
	public String setDoTransferHdrDetails(DoTransferHdr val) throws Exception {
		try {
			transferHdrRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}
}
