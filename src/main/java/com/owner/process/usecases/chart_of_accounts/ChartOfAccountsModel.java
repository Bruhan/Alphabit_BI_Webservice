package com.owner.process.usecases.chart_of_accounts;

import com.owner.process.persistence.models.ChartOfAccounts;

public class ChartOfAccountsModel {
    public ChartOfAccounts setCoaModel(String accountName, String plant, String crAt, String crBy) {
        ChartOfAccounts chartOfAccounts = new ChartOfAccounts();
        chartOfAccounts.setPlant(plant);
        chartOfAccounts.setAccountType("3");
        chartOfAccounts.setAccountDetailType(7);
        chartOfAccounts.setAccountName(accountName);
        chartOfAccounts.setDescription("");
        chartOfAccounts.setIsSubAccount(0);
        chartOfAccounts.setSubAccountName("");
        chartOfAccounts.setOpeningBalance(0.0F);
        chartOfAccounts.setCrAt(crAt);
        chartOfAccounts.setCrBy(crBy);
        return chartOfAccounts;
    }
}
