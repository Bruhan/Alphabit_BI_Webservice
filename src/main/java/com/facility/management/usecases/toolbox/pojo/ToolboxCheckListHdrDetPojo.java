package com.facility.management.usecases.toolbox.pojo;

import com.facility.management.persistence.models.ToolboxChecklistDet;
import com.facility.management.persistence.models.ToolboxChecklistMst;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolboxCheckListHdrDetPojo {
    ToolboxChecklistMst toolboxChecklistMst;
    List<ToolboxChecklistDet> toolboxChecklistDetList;
}
