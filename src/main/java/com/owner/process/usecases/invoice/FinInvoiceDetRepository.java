package com.owner.process.usecases.invoice;


import com.owner.process.persistence.models.FinInvoiceDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinInvoiceDetRepository extends JpaRepository<FinInvoiceDet, Long> {
    @Query(value="SELECT * FROM ##plant##FININVOICEDET WHERE " +
            "INVOICEHDRID = ?1",nativeQuery = true)
    List<FinInvoiceDet> findbyhdrid(int invoiceno);
}


