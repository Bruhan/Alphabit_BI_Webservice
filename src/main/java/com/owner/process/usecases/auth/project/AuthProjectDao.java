package com.owner.process.usecases.auth.project;

import com.owner.process.persistence.models.FinProjectMultiSupervisor;
import com.owner.process.persistence.models.FinRecycleProject;

import java.util.List;

public interface AuthProjectDao {
    FinRecycleProject findProjectBySupervisor(String empNo, String plant);
    FinRecycleProject findProjectByCustomer(String CustNo, String plant);
    List<FinProjectMultiSupervisor> findMultiSupervisorsById(String empuserid, String plant);
    FinRecycleProject findProjectByIds(List<Integer> projectHDRIdList, String plant);
    FinRecycleProject findProjectByProjectNo(String ProjectNo, String plant);
    FinRecycleProject findProjectByProjectType(String projectType, String plant);
}
