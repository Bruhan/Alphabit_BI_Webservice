package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectWorkAllocation {
    private ProjectWorkAllocationHDR projectWorkAllocationHDR;
    private List<ProjectWorkAllocationDET> projectWorkAllocationDETList;
}
