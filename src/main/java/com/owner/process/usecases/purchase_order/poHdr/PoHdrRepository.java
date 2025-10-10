package com.owner.process.usecases.purchase_order.poHdr;

import com.owner.process.persistence.models.PoHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoHdrRepository extends JpaRepository<PoHdr,Integer> {
    List<PoHdr> findByApprovalSataus(String PK0);
    List<PoHdr> findByPurchaseNo(String PK0);
    List<PoHdr> findByUniqueKey(String PK0);

    @Query(value="SELECT * FROM ##plant##POHDR AS H  WHERE APPROVAL_STATUS IN " +
            "('CREATE APPROVAL PENDING','EDIT APPROVAL PENDING','DELETE APPROVAL PENDING') " +
            "ORDER BY H.CRAT DESC OFFSET ?1 ROWS FETCH NEXT ?2 ROW ONLY",nativeQuery = true)
    List<PoHdr> findByApprovalSatausPagination(int page,int pcount);
}
