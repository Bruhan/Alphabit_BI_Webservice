package com.owner.process.helpers.common.results;

import lombok.*;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class ResultsDTO {
    private Object results;
    private int pageNumber;
    private int pageSize;
    private String message;
    private Long totalCount;
    private int totalPageCount;
    private double totalAmount;
    private int statusCode;
}
