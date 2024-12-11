package com.facility.management.usecases.project.finproject;

import com.facility.management.persistence.models.FinProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinProjectRepository extends JpaRepository<FinProject, Integer> {
    @Query(value="SELECT * from ##plant##FINPROJECT WHERE SUPERVISOR = ?1 ORDER BY CRAT DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<FinProject> getallproject(String empcode, int page, int pcount);

    @Query(value="SELECT * from ##plant##FINPROJECT WHERE SUPERVISOR = ?1 AND PROJECT_STATUS = ?2 ORDER BY CRAT DESC OFFSET ?3 ROWS FETCH NEXT ?4 ROW ONLY",nativeQuery = true)
    List<FinProject> getprojectbystatus(String empcode,String status,int page, int pcount);

    @Query(value="SELECT * from ##plant##FINPROJECT WHERE MANAGERENG = ?1 ORDER BY CRAT DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<FinProject> getallprojectformanger(String empcode, int page, int pcount);

    @Query(value="SELECT * from ##plant##FINPROJECT WHERE MANAGERENG = ?1 AND PROJECT_STATUS = ?2 ORDER BY CRAT DESC OFFSET ?3 ROWS FETCH NEXT ?4 ROW ONLY",nativeQuery = true)
    List<FinProject> getprojectbystatusformanger(String empcode,String status,int page, int pcount);
}
