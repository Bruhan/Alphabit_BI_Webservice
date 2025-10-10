package com.owner.process.usecases.toolbox.toolboxchecklistdet;

import com.owner.process.persistence.models.ToolboxChecklistDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolboxChecklistDetRepository extends JpaRepository<ToolboxChecklistDet,Integer> {

    @Query(value="SELECT TOP 1 * from ##plant##TOOLBOX_CHECKLIST_DET WHERE ID = ?1",nativeQuery = true)
    ToolboxChecklistDet getbyid(int id);

    @Query(value="SELECT * from ##plant##TOOLBOX_CHECKLIST_DET WHERE HDRID = ?1",nativeQuery = true)
    List<ToolboxChecklistDet> getbyhdrid(int hdrid);

}
