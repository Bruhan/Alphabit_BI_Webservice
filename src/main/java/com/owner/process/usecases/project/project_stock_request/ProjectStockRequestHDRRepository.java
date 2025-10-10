package com.owner.process.usecases.project.project_stock_request;

import com.owner.process.persistence.models.ProjectStockRequestHDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStockRequestHDRRepository extends JpaRepository<ProjectStockRequestHDR, Integer> {

    @Query(value="SELECT TOP 1 * from ##plant##PROJECTSTOCKREQUESTHDR WHERE ID = ?1",nativeQuery = true)
    ProjectStockRequestHDR getByid(int id);
    @Query(value="SELECT * from ##plant##PROJECTSTOCKREQUESTHDR WHERE PROJECTID = ?1 ORDER BY CRAT DESC",nativeQuery = true)
    List<ProjectStockRequestHDR> getByProject(int pid);

    @Query(value="SELECT * from ##plant##PROJECTSTOCKREQUESTHDR WHERE APPROVER_CODE = ?1 AND REQUESTSTATUS = 'OPEN' ORDER BY CRAT DESC",nativeQuery = true)
    List<ProjectStockRequestHDR> getByManagerCode(String empcode);

    @Query(value="SELECT * from ##plant##PROJECTSTOCKREQUESTHDR WHERE APPROVER_CODE = ?1 AND ISNULL(APPROVAL_STATUS,'') = ?2 AND REQUESTSTATUS = 'OPEN' ORDER BY CRAT DESC",nativeQuery = true)
    List<ProjectStockRequestHDR> getByManagerCodeWithSataus(String empcode,String status);

}
