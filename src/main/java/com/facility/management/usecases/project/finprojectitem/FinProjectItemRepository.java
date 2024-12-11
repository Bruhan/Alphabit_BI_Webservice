package com.facility.management.usecases.project.finprojectitem;

import com.facility.management.persistence.models.FinProjectItem;
import com.facility.management.persistence.models.FinProjectItemPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinProjectItemRepository extends JpaRepository<FinProjectItem,Integer> {

    @Query(value="SELECT B.ITEMDESC,A.* FROM ##plant##FINPROJECT_ITEMDET AS A LEFT JOIN ##plant##ITEMMST AS B ON A.ITEM=B.ITEM WHERE A.PROJECTHDRID = ?1 " +
            "ORDER BY A.LNNO ASC OFFSET ?2 ROWS FETCH NEXT ?3 ROW ONLY",nativeQuery = true)
    List<FinProjectItemPojo> getallitembyProject(int projectid, int page, int pcount);

    @Query(value="SELECT B.ITEMDESC,A.* FROM ##plant##FINPROJECT_ITEMDET AS A LEFT JOIN ##plant##ITEMMST AS B ON A.ITEM=B.ITEM WHERE A.PROJECTHDRID = ?1 ORDER BY A.LNNO ASC",nativeQuery = true)
    List<FinProjectItemPojo> getallitembyProjectNoPage(int projectid);
}
