package com.owner.process.usecases.gino_to_invoice;

import com.owner.process.persistence.models.FinGiNoToInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinGiNoToInvoiceRepository extends JpaRepository<FinGiNoToInvoice,Integer> {

}
