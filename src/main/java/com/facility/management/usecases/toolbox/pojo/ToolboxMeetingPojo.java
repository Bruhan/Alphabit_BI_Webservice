package com.facility.management.usecases.toolbox.pojo;

import com.facility.management.persistence.models.ToolBoxMeeting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolboxMeetingPojo {
    ToolBoxMeeting toolBoxMeeting;
    List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojo;
}
