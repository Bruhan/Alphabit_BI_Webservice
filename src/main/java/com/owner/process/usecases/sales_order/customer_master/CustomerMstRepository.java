package com.owner.process.usecases.sales_order.customer_master;

import com.owner.process.persistence.models.CustomerMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMstRepository extends JpaRepository<CustomerMst, Long> {
    CustomerMst findByCustomerNo(String pk0);

    @Query(value = "select TOP 1 * " +
            "from ##plant##CUSTMST where CNAME = :customerName", nativeQuery = true)
    CustomerMst findByCustomerName(String customerName);

    List<CustomerMst> findByWorkPhone(String pk0);

    //	CustomerMst findByWorkPhone(String pk0);
    List<CustomerMst> findByEmail(String pk0);

    @Query(value = "SELECT TOP 1 * " +
            "  from ##plant##CUSTMST where CNAME = :pk0  order by id ", nativeQuery = true)
    CustomerMst findByDefaultAddress(String pk0);

    CustomerMst findFirstByCustomerNameOrderById(String pk0);

    CustomerMst findFirstByCustomerNameAndWorkPhoneAndAddressROneOrderById(String pk0, String pk1, String pk2);

    CustomerMst findFirstByCustomerNameAndEmailAndAddressROneOrderById(String customerName, String email, String addressROne);
}
