package com.facility.management.usecases.auth.dao;

import com.facility.management.persistence.models.*;

import java.util.List;

public interface AuthDao {
    UserInfo findByUserId(String username);
    UserInfo findByUserIdAndDept(String username,String dept);
    List<UserInfo> findByStore(String dept);
    List<UserInfo> findByDelivery(String dept);
    EmployeeMaster findByEmployeeLoginId(String username, String plant);
    HREmpUserInfo findByEmployeeLoginId2(String username);

    FinRecycleProject findProjectBySupervisor(String empNo, String plant);

    boolean checkSupervisorHasMultipleProjects(String empno, String plant);

    List<FinProjectMultiSupervisor> findMultiSupervisorsById(String empuserid, String plant);

    FinRecycleProject findProjectByIds(List<Integer> projectHDRIdList, String plant);


    boolean checkEmployeeType(String plant, String username, String type);

    EmployeeMaster getEmployeeByUsername(String plant, String username);
}
