package com.facility.management.usecases.project.worktypecategory;

import com.facility.management.persistence.models.ProjectWorkEmployee;
import com.facility.management.persistence.models.WorkTypCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTypCategoryRepository extends JpaRepository<WorkTypCategory,Integer> {
    @Query(value="SELECT TOP 1 * FROM ##plant##WORKTYPECATEGORY WHERE ID = ?1",nativeQuery = true)
    WorkTypCategory getbyid(int id);
}
