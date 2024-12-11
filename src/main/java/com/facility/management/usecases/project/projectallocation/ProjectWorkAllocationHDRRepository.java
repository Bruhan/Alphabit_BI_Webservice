package com.facility.management.usecases.project.projectallocation;

import com.facility.management.persistence.models.FinProject;
import com.facility.management.persistence.models.ProjectWorkAllocationHDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectWorkAllocationHDRRepository extends JpaRepository<ProjectWorkAllocationHDR, Integer> {

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR ORDER BY CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + " +
            "SUBSTRING(WORKDATE,1,2))AS DATE) DESC OFFSET ?1 ROWS FETCH NEXT ?2 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getall(int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE  CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) >= CAST(?1 AS datetime) " +
            "ORDER BY CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) " +
            "DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getbydatefdate(String fromdate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE  CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) <= CAST(?1 AS datetime) " +
            "ORDER BY CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) " +
            "DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getbydatetdate(String todate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE  CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + SUBSTRING(WORKDATE, 4, 2) + '-' + " +
            "SUBSTRING(WORKDATE, 1, 2)) AS datetime) >= CAST(?1 AS datetime) AND CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) <= CAST(?2 AS datetime) ORDER BY " +
            "CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) " +
            "DESC OFFSET ?3 ROWS FETCH NEXT ?4 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getbydateftdate(String fromdate,String todate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE PROJECTID = ?1 ORDER BY CAST((SUBSTRING(WORKDATE,7,4) + '-' + " +
            "SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) DESC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getallbyproject(int pid, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE PROJECTID = ?1 AND CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) >= CAST(?2 AS datetime) ORDER BY " +
            "CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) DESC " +
            "OFFSET ?3 ROWS FETCH NEXT ?4 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getgetbyprojectanddatefdate(int pid, String fromdate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE PROJECTID = ?1 AND CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) <= CAST(?2 AS datetime) ORDER BY " +
            "CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + SUBSTRING(WORKDATE,1,2))AS DATE) DESC " +
            "OFFSET ?3 ROWS FETCH NEXT ?4 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getgetbyprojectanddatetdate(int pid, String todate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE PROJECTID = ?1 AND CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + " +
            "SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) >= CAST(?2 AS datetime) AND " +
            "CAST((SUBSTRING(WORKDATE, 7, 4) + '-' + SUBSTRING(WORKDATE, 4, 2) + '-' + SUBSTRING(WORKDATE, 1, 2)) AS datetime) <= " +
            "CAST(?3 AS datetime) ORDER BY CAST((SUBSTRING(WORKDATE,7,4) + '-' + SUBSTRING(WORKDATE,4,2) + '-' + " +
            "SUBSTRING(WORKDATE,1,2))AS DATE) DESC OFFSET ?4 ROWS FETCH NEXT ?5 ROW ONLY",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getgetbyprojectanddate(int pid, String fromdate,String todate, int page, int pcount);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONHDR WHERE PROJECTID = ?1 AND WORKQUOTDETID = ?2 AND WORKDATE = ?3",nativeQuery = true)
    List<ProjectWorkAllocationHDR> getallbyprojectandworkandworkdate(int pid, int wqid, String workdate);

}
