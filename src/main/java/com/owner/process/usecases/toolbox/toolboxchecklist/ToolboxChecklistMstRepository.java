package com.owner.process.usecases.toolbox.toolboxchecklist;

import com.owner.process.persistence.models.ToolboxChecklistMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolboxChecklistMstRepository extends JpaRepository<ToolboxChecklistMst,Integer> {
}
