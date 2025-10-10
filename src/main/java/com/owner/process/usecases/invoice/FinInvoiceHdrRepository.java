package com.owner.process.usecases.invoice;

import com.owner.process.persistence.models.FinInvoiceHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinInvoiceHdrRepository  extends JpaRepository<FinInvoiceHdr, Long> {


    @Query(value="SELECT * FROM ##plant##FININVOICEHDR WHERE " +
            "BILL_STATUS = ?1",nativeQuery = true)
    List<FinInvoiceHdr> findbystatus(String status);
    @Query(value="SELECT TOP 1 * FROM ##plant##FININVOICEHDR WHERE " +
            "ID = ?1",nativeQuery = true)
    FinInvoiceHdr findbyid(int id);

    @Query(value="SELECT * FROM ##plant##FININVOICEHDR WHERE  BILL_STATUS IN ('DELIVERED'," +
            "'PARTIALLY PAID','PAID') ORDER BY CRAT DESC OFFSET ?1 ROWS FETCH NEXT ?2 ROW ONLY",nativeQuery = true)
    List<FinInvoiceHdr> findbyInvStatus(int page,int pcount);

    @Query(value="SELECT * FROM ##plant##FININVOICEHDR WHERE " +
            "BILL_STATUS = ?1 ORDER BY CRAT DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<FinInvoiceHdr> findbystatusPagenation(String status,int page,int pcount);

    @Query(value="SELECT TOP 1 * FROM ##plant##FININVOICEHDR WHERE " +
            "PEPPOL_DOC_ID = ?1",nativeQuery = true)
    FinInvoiceHdr findbydocid(String docid);

    /*@Query(value="SELECT A.* FROM (?1) AS A",nativeQuery = true)
    FinInvoiceHdr findbydocidusingplant(String qry);*/
}
