package com.owner.process.usecases.project.workerchecklistdet;

import com.owner.process.persistence.models.WorkerCheckListDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerCheckListDetRepository extends JpaRepository<WorkerCheckListDet,Integer> {

    @Query(value="SELECT * from ##plant##WORKERCHECKLISTDET WHERE HDRID = ?1",nativeQuery = true)
    List<WorkerCheckListDet> getByHdrid(int hdrid);
}
