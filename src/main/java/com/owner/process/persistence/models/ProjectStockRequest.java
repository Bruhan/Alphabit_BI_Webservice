package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectStockRequest {
    private ProjectStockRequestHDR projectStockRequestHDR;
    private List<ProjectStockRequestDET> projectStockRequestDETList;
}
