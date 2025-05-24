package com.facility.management.usecases.product_request.dto;

import com.facility.management.usecases.product_request.enums.LNStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDetDTO {
    private String plant;
    private int productRequestHdrId;
    private int productRequestDetId;
    private String item;
    private String productName;
    private String productDescription;
    private LNStatus lnStatus;
    private int lnNo;
    private double quantity;
    private String uom;
    private double processedQty;
    private double balanceQty;
    private double receivedQty;
    private double nonReceivedQty;
}
