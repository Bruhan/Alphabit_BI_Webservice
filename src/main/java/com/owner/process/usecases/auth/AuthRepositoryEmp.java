package com.owner.process.usecases.auth;

import com.owner.process.persistence.models.HrEmpUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepositoryEmp extends JpaRepository<HrEmpUserInfo,Long> {
    @Query(value = "select TOP 1 * " +
            "from HREMPUSERINFO where EMPUSERID = :username",nativeQuery = true)
    HrEmpUserInfo findByUserId(String username);

    @Query(value = "select TOP 1 * " +
            "from HREMPUSERINFO where EMPUSERID = :username and PLANT = :plant",nativeQuery = true)
    HrEmpUserInfo findByUserIdAndPlant(String username,String plant);
}
