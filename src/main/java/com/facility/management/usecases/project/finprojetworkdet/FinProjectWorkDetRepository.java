package com.facility.management.usecases.project.finprojetworkdet;

import com.facility.management.persistence.models.FinProjectItemPojo;
import com.facility.management.persistence.models.FinProjectWorkDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinProjectWorkDetRepository extends JpaRepository<FinProjectWorkDet,Integer> {

    @Query(value="SELECT * FROM ##plant##FINPROJECT_WORKDET WHERE PROJECTHDRID = ?1 ORDER BY LNNO ASC",nativeQuery = true)
    List<FinProjectWorkDet> getFinProjectWorkDetbyProject(int projectid);
}
