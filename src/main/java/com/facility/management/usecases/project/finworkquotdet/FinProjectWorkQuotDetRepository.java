package com.facility.management.usecases.project.finworkquotdet;

import com.facility.management.persistence.models.FinProjectWorkDet;
import com.facility.management.persistence.models.FinProjectWorkQuotDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinProjectWorkQuotDetRepository extends JpaRepository<FinProjectWorkQuotDet,Integer> {
    @Query(value="SELECT * FROM ##plant##FINPROJECT_WORKQUOTDET WHERE PROJECTHDRID = ?1 ORDER BY LNNO ASC",nativeQuery = true)
    List<FinProjectWorkQuotDet> getFinProjectWorkQuotDetbyProject(int projectid);

    @Query(value="SELECT TOP 1 * FROM ##plant##FINPROJECT_WORKQUOTDET WHERE ID = ?1 ORDER BY LNNO ASC",nativeQuery = true)
    FinProjectWorkQuotDet getFinProjectWorkQuotDetbyid(int ID);

    @Query(value="SELECT WORKTYPE_ID FROM ##plant##FINPROJECT_WORKQUOTDET WHERE ID = ?1 ORDER BY LNNO ASC",nativeQuery = true)
    Integer getWorktypeidbyid(int ID);
}
