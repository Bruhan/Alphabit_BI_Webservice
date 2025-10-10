package com.owner.process.usecases.PltApprovalMatrix;


import com.owner.process.persistence.models.PltApprovalMatrix;
import com.owner.process.usecases.PltApprovalMatrix.pojo.PltApprovalMatrixPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PltApprovalMatrixRepository extends JpaRepository<PltApprovalMatrix,Long> {

    @Query(value="SELECT ISCREATE as isCreate,ISUPDATE as isUpdate,ISDELETE as isDelete FROM PLTAPPROVALMATRIX " +
            "where PLANT =:plant AND APPROVALTYPE =:type " ,nativeQuery = true)
    List<PltApprovalMatrixPojo> getPltSummaryByType(String plant, String type);
}
