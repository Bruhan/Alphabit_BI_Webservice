package com.facility.management.usecases.chart_of_accounts;

import com.facility.management.persistence.models.ChartOfAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartOfAccountsRepository extends JpaRepository<ChartOfAccounts, Long> {
    ChartOfAccounts findByAccountName(String pk0);
}
