package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestReceiveDTO {
    private int id;
    private String projectNo;
    private String receiverId;
    private String receivedDate;
    private String receiverRemarks;
    private String driverName;
    private String vehicleNo;
    private List<ProductRequestReceiveProductDTO> productRequestReceiveProductDTOList;
}
