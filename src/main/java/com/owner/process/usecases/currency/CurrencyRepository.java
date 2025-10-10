package com.owner.process.usecases.currency;

import com.owner.process.persistence.models.CurrencyMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyMst,Long> {
    CurrencyMst findByCurrencyId(String id);
}

