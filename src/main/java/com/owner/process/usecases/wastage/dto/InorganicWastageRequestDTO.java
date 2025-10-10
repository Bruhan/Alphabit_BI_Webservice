package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InorganicWastageRequestDTO {
    private String projectNo;
    private String empCode;
    private double inorganicWastageQty;
    private String inorganicWastageUOM;
    private double rejectedWastageQty;
    private String rejectedWastageUOM;
    private List<InorganicProductDTO> inorganicProductDTOList;
}
