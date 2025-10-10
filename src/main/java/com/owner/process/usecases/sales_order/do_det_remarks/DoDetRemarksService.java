package com.owner.process.usecases.sales_order.do_det_remarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.owner.process.persistence.models.DoDetRemarks;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoDetRemarksService {
	@Autowired
	DoDetRemarksRepository doDetRemarksRepository;
	public String checkDoDetRemarksPk(String pk0,int pk1,String pk2) throws Exception {
		try {
			DoDetRemarks getVal = doDetRemarksRepository.findByDoNoAndDoLineNoAndItem(pk0,pk1,pk2);
			if(getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "0";
	}
	public String setDoDetRemarksDetails(DoDetRemarks val) throws Exception {
		try {
			doDetRemarksRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}

	public List<DoDetRemarks> findByDoNoOrderByDoLineNo(String pk0) throws Exception {
		List<DoDetRemarks> getVal = new ArrayList<DoDetRemarks>();
		try {
			getVal =doDetRemarksRepository.findByDoNoOrderByDoLineNo(pk0);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return getVal;
	}

	public String deleteDoDetRemarksDetails(String pk0) throws Exception {
		try {
			doDetRemarksRepository.deleteByDoNo(pk0);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}

	public List<DoDetRemarks> getByUKey(String pk0) throws Exception{
		List<DoDetRemarks> getVal;
		try{
			getVal = doDetRemarksRepository.findByUniqueKey(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public void deleteall(List<DoDetRemarks> doDetRemarks) throws Exception{
		try{
			doDetRemarksRepository.deleteAll(doDetRemarks);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void save(DoDetRemarks doDetRemarks) throws Exception{
		try{
			doDetRemarksRepository.save(doDetRemarks);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
