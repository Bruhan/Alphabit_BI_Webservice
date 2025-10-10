package com.owner.process.usecases.auth;

import com.owner.process.persistence.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<UserInfo,Long> {
    @Query(value = "select TOP 1 * " +
            "from user_Info where USER_ID = :username",nativeQuery = true)
    UserInfo findByUserId(String username);
    @Query(value = "select TOP 1 * " +
            "from user_Info where USER_ID = :username and DEPT = :dept",nativeQuery = true)
    UserInfo findByUserIdAndDept(String username,String dept);

    @Query(value = "select * " +
            "from user_Info where ISACCESS_STOREAPP = '1' and DEPT = :dept",nativeQuery = true)
    List<UserInfo> findByStore(String dept);

    @Query(value = "select * " +
            "from user_Info where RIDER_APP_ACCESS = '1' and DEPT = :dept",nativeQuery = true)
    List<UserInfo> findByDelivery(String dept);


}
