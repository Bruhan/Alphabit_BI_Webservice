package com.owner.process.usecases.toolbox.pojo;

import com.owner.process.persistence.models.ToolboxChecklistDet;
import com.owner.process.persistence.models.ToolboxChecklistMst;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToolboxCheckListHdrDetPojo {
    ToolboxChecklistMst toolboxChecklistMst;
    List<ToolboxChecklistDet> toolboxChecklistDetList;
}
