package com.facility.management.usecases.activity_log;

import com.facility.management.persistence.models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ##plant##MOVHIS (CRAT,ORDNUM,REMARKS,RECID,TRANDATE,DIRTYPE,CRBY,PLANT) " +
            "VALUES (:crAt,:orNo,:rmk" +
            ",:recId,:traDt,:dirTy,:crBy,:plt)", nativeQuery = true)
    Integer saveActivity(String plt,String dirTy,String traDt,String recId,
                         String orNo,String crBy,String rmk,String crAt);
}
