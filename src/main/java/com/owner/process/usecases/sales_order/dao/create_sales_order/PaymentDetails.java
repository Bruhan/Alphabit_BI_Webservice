
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {
    @JsonProperty("avs_result_code")
    private String avsResultCode;
    @JsonProperty("credit_card_bin")
    private String creditCardBin;
    @JsonProperty("credit_card_company")
    private String creditCardCompany;
    @JsonProperty("credit_card_expiration_month")
    private Integer creditCardExpirationMonth;
    @JsonProperty("credit_card_expiration_year")
    private Integer creditCardExpirationYear;
    @JsonProperty("credit_card_name")
    private String creditCardName;
    @JsonProperty("credit_card_number")
    private String creditCardNumber;
    @JsonProperty("credit_card_wallet")
    private String creditCardWallet;
    @JsonProperty("cvv_result_code")
    private String cvvResultCode;


}