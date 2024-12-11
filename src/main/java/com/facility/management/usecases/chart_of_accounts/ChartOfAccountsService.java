package com.facility.management.usecases.chart_of_accounts;

import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.ChartOfAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartOfAccountsService {
	@Autowired
	ChartOfAccountsRepository chartOfAccountsRepository;

	public Boolean checkChartOfAccountsPk1(String pk0) throws Exception {
		try {
			ChartOfAccounts getVal = chartOfAccountsRepository.findByAccountName(pk0);
			if (getVal == null)
				return false;
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return true;
	}

	public ChartOfAccounts getChartOfAccountsPk1(String pk0) throws Exception {
		ChartOfAccounts getVal;
		try {
			getVal = chartOfAccountsRepository.findByAccountName(pk0);
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String setChartOfAccountsDetails(ChartOfAccounts val) throws Exception {
		try {
			chartOfAccountsRepository.save(val);
		} catch (Exception e) {
			LoggerConfig.logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return "1";
	}
}
