package com.owner.process.usecases.project.projectallocation;

import com.owner.process.persistence.models.ProjectWorkAllocationDET;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectWorkAllocationDETRepository extends JpaRepository<ProjectWorkAllocationDET, Integer> {
    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONDET WHERE HDRID = ?1",nativeQuery = true)
    List<ProjectWorkAllocationDET> getbyhdrid(int hdrid);

    @Query(value="SELECT * from ##plant##PROJECTWORKALLOCATIONDET WHERE HDRID = ?1 AND WORKDATE = ?2 AND EMP_ID = ?3",nativeQuery = true)
    List<ProjectWorkAllocationDET> getbyhdridworkdateandempid(int hdrid,String workdate,String empid);

    @Query(value="DELETE from ##plant##PROJECTWORKALLOCATIONDET WHERE HDRID = ?1",nativeQuery = true)
    void deletebyhdrid(int hdrid);

    @Transactional
    String deleteByHDRID(int hdrid);
}
