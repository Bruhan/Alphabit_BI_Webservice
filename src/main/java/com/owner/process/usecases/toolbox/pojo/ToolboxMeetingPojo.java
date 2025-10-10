package com.owner.process.usecases.toolbox.pojo;

import com.owner.process.persistence.models.ToolBoxMeeting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolboxMeetingPojo {
    ToolBoxMeeting toolBoxMeeting;
    List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojo;
}
