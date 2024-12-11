package com.facility.management.usecases.project.projectworkemployee;

import com.facility.management.persistence.models.FinProjectWorkDet;
import com.facility.management.persistence.models.ProjectWorkEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ProjectWorkEmployeeRepository extends JpaRepository<ProjectWorkEmployee,Integer> {

    @Query(value="SELECT * FROM ##plant##PROJECT_WORKEMPLOYEE WHERE PROJECTNO = ?1 ORDER BY CRAT ASC",nativeQuery = true)
    List<ProjectWorkEmployee> getEmployeebyProject(String projectno);

    @Query(value="SELECT * FROM ##plant##PROJECT_WORKEMPLOYEE WHERE PROJECTNO = ?1 AND WORKID =?2 ORDER BY CRAT ASC",nativeQuery = true)
    List<ProjectWorkEmployee> getEmployeebyProjectByworkId(String projectno, int workid);

    @Query(value="SELECT * FROM ##plant##PROJECT_WORKEMPLOYEE WHERE PROJECTNO = ?1 AND WORKID =?2 AND WORKLNNO =?3 ORDER BY CRAT ASC",nativeQuery = true)
    List<ProjectWorkEmployee> getEmployeebyProjectByworkIdANDLnno(String projectno, int workid, int lineno);
}
