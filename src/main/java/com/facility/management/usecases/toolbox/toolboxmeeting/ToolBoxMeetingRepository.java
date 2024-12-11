package com.facility.management.usecases.toolbox.toolboxmeeting;

import com.facility.management.persistence.models.ToolBoxMeeting;
import com.facility.management.persistence.models.ToolboxChecklistDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolBoxMeetingRepository extends JpaRepository<ToolBoxMeeting,Integer> {

    @Query(value="SELECT TOP 1 * from ##plant##TOOLBOX_MEETING WHERE ID = ?1",nativeQuery = true)
    ToolBoxMeeting getbyid(int id);

    @Query(value="SELECT * from ##plant##TOOLBOX_MEETING WHERE PROJECTCODE = ?1",nativeQuery = true)
    List<ToolBoxMeeting> getbyproject(String project);

    @Query(value="SELECT * from ##plant##TOOLBOX_MEETING WHERE SUPERVISORCODE = ?1",nativeQuery = true)
    List<ToolBoxMeeting> getBySupervisorCode(String empcode);
}
