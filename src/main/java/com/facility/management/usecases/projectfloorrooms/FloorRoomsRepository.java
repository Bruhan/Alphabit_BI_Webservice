package com.facility.management.usecases.projectfloorrooms;


import com.facility.management.persistence.models.ProjectFloorRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRoomsRepository extends JpaRepository<ProjectFloorRoom, Integer> {

}
