package com.owner.process.usecases.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AuthTokensDao {
	private  String accessToken;
	private  String refreshToken;
	private  int showOpeninfStock;
	private  int showClosingStock;
	private  int showPurchase;
	private  int showErpSales;
	private  int showPosSales;
	private  int showProjectmanagement;
	private  int showPurchaseApproval;
}
