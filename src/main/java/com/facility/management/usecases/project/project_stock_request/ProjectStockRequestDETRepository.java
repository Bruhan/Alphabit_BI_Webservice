package com.facility.management.usecases.project.project_stock_request;

import com.facility.management.persistence.models.ProjectStockRequestDET;
import com.facility.management.persistence.models.ProjectStockRequestHDR;
import com.facility.management.persistence.models.ProjectWorkAllocationHDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectStockRequestDETRepository extends JpaRepository<ProjectStockRequestDET,Integer> {

    @Query(value="DELETE from ##plant##PROJECTSTOCKREQUESTDET WHERE HDRID = ?1",nativeQuery = true)
    String deleteByHdriDt(int hid);

    @Query(value="SELECT * from ##plant##PROJECTSTOCKREQUESTDET WHERE HDRID = ?1 ORDER BY ITEM ASC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<ProjectStockRequestDET> getbyhdrid(int hid ,int page, int pcount);

    @Transactional
    String deleteByHDRID(int hdrid);


}
