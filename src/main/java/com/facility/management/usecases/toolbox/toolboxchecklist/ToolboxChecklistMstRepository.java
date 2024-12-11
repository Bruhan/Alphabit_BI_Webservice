package com.facility.management.usecases.toolbox.toolboxchecklist;

import com.facility.management.persistence.models.ToolboxChecklistMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolboxChecklistMstRepository extends JpaRepository<ToolboxChecklistMst,Integer> {
}
