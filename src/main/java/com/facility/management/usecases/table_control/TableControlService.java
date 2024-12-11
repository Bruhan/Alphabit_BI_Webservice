package com.facility.management.usecases.table_control;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.TableControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableControlService {
	@Autowired
	TableControlRepository tableControlRepository;

	public String checkTableControlPk(String pk0, String pk1) throws Exception {
		try {
			TableControl getVal = tableControlRepository.findByPlantAndFunc(pk0, pk1);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "0";
	}

	public TableControl getTableControlPk(String pk0, String pk1) throws Exception {
		TableControl getVal;
		try {
			getVal = tableControlRepository.findByPlantAndFunc(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String setTableControlDetails(TableControl val) throws Exception {
		try {
			tableControlRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}

	public String setTableControlNew(String plant, String fuction,String prefix,String crBy) throws Exception {
		try {
			TableControl val = new TableControl();
			val.setPlant(plant);
			val.setFunc(fuction);
			val.setPrefix(prefix);
			val.setMinSeq("0000000");
			val.setMaxSeq("9999999");
			val.setNxtSeq("0");
			val.setCrAt(new DateTimeCalc().getUcloTodayDateTime());
			val.setCrBy(crBy);
			tableControlRepository.save(val);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "1";
	}
}
