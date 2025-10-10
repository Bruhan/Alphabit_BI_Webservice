package com.owner.process.usecases.auth;

import com.owner.process.persistence.models.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepositoryCust extends JpaRepository<CustomerInfo,Long> {
    @Query(value = "select TOP 1 * " +
            "from CUSTOMER_INFO where USER_ID = :username",nativeQuery = true)
    CustomerInfo findByUserId(String username);
    @Query(value = "select TOP 1 * " +
            "from CUSTOMER_INFO where USER_ID = :username and PLANT = :plant",nativeQuery = true)
    CustomerInfo findByUserIdAndPlant(String username,String plant);
    @Query(value = "select * from CUSTOMER_INFO where ISMANAGERAPPACCESS = '1' " +
            "and PLANT = :plant and CUSTNO = :custNo",nativeQuery = true)
    List<CustomerInfo> findByPlantAndManaccess(String plant, String custNo);
}
