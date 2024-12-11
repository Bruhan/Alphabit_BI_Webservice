package com.facility.management.usecases.auth.userlocation;

import com.facility.management.persistence.models.UserInfo;
import com.facility.management.persistence.models.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation,Long> {
    @Transactional
    @Query(value = "select TOP 1 * " +
            "from ##plant##USERLOCATION where USERID = ?1",nativeQuery = true)
    UserLocation findByUserId(int Userid);
}
