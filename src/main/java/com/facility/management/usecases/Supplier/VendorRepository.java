package com.facility.management.usecases.Supplier;

import com.facility.management.persistence.models.VendMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendMst, Integer> {

    VendMst findByVendNo(String pk0);
}
