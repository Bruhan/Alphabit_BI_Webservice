package com.owner.process.usecases.sales_order.do_det;

import com.owner.process.persistence.models.DoDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoDetService {
	@Autowired
	DoDetRepository doDetRepository;

	public String checkDoDetPk(String pk0, Integer pk1) throws Exception {
		try {
			DoDet getVal = doDetRepository.findByDoNoAndDoLineNo(pk0, pk1);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public String checkDoDetPk1(String pk0) throws Exception {
		List<DoDet> getVal;
		try {
			getVal = doDetRepository.findByDoNo(pk0);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public List<DoDet> getAllDoDetPk(String pk0) throws Exception {
		List<DoDet> getVal;
		try {
			getVal = doDetRepository.findByDoNo(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public List<DoDet> getAllDoDetPkOrderByLn(String pk0) throws Exception {
		List<DoDet> getVal;
		try {
			getVal = doDetRepository.findByDoNoOrderByDoLineNo(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public DoDet getDoDetPk(String pk0,Integer pk1) throws Exception {
		DoDet getVal;
		try {
			getVal = doDetRepository.findByDoNoAndDoLineNo(pk0,pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}


	public String setDoDetDetails(DoDet val) throws Exception {
		try {
			doDetRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String deleteDoDetPk(String doNo, Integer i) throws Exception {
		try {
			doDetRepository.deleteByDoNoAndDoLineNo(doNo,i);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String setAllDoDetDetails(List<DoDet> val) throws Exception{
		try {
			doDetRepository.saveAll(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String deleteDoDetPk(String doNo) throws Exception {
		try {
			doDetRepository.deleteByDoNo(doNo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public List<DoDet> getByUKey(String pk0) throws Exception{
		List<DoDet> getVal;
		try{
			getVal = doDetRepository.findByUniqueKey(pk0);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public void deleteall(List<DoDet> doDet) throws Exception{
		try{
			doDetRepository.deleteAll(doDet);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void save(DoDet doDet) throws Exception{
		try{
			doDetRepository.save(doDet);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
