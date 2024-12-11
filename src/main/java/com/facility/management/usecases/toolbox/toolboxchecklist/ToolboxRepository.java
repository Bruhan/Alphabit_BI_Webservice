package com.facility.management.usecases.toolbox.toolboxchecklist;

import com.facility.management.persistence.models.*;
import com.facility.management.usecases.toolbox.pojo.ToolboxPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ToolboxRepository extends JpaRepository<ToolboxChecklistMst,Integer> {
    @Query(value = "select PLANT as plant,id,HDRDATE as Hdrdate,TOOLBOXMEETING_ID as toolboxMeetingId, TOOLBOXMEETING_NAME as ToolboxMeetingName,EMPLOYEECODE as employeeCode," +
            "SUPERVISORCODE as supervisorCode,STATUS as status from ##plant##TOOLBOX_CHECKLIST ", nativeQuery = true)
    List<ToolboxPojo> findAllToolbox();
    ToolboxChecklistMst findById(int id);
    List<ToolboxChecklistMst> findByemployeeCode(String empCode);
    List<ToolboxChecklistMst> findBysupervisorCode(String supervisorCode);
    @Modifying
    @Transactional
    @Query(value = "delete from ##plant##TOOLBOX_CHECKLIST where ID = ?1 ", nativeQuery = true)
    void deleteById(int id);

    @Query(value="SELECT * from ##plant##TOOLBOX_CHECKLIST WHERE TOOLBOXMEETING_ID = ?1",nativeQuery = true)
    List<ToolboxChecklistMst> getBymeetingid(int meetingid);
}

interface SafetyRepository extends JpaRepository<SafetyMst,Integer> {
    @Query(value = "select * from ##plant##SAFETYMST ", nativeQuery = true)
    List<SafetyMst> findall();
}

interface PredefinedcheklistcategoryRepository extends JpaRepository<PredfineChecklistCategory,Integer> {
    @Query(value = "select * from ##plant##PREDEFINEDCHEKLISTCATEGORY ", nativeQuery = true)
    List<PredfineChecklistCategory> findall();
}

interface PredefinedSafetyRepository extends JpaRepository<PredefinedSafetyMst,Integer> {
    @Query(value = "select * from ##plant##PREDEFINED_SAFETY_CHECKLISTMST ", nativeQuery = true)
    List<PredefinedSafetyMst> findall();

    @Query(value = "select * from ##plant##PREDEFINED_SAFETY_CHECKLISTMST WHERE CID = ?1", nativeQuery = true)
    List<PredefinedSafetyMst> getbycategoryid(int cid);
}