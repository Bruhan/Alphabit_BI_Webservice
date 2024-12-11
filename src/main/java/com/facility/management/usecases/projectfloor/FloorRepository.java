package com.facility.management.usecases.projectfloor;

import com.facility.management.persistence.models.ProjectFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<ProjectFloor, Integer> {

}
