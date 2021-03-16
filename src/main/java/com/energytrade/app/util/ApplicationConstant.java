package com.energytrade.app.util;

public enum ApplicationConstant {
	
	ORDER_CANCEL_STATUS("Hello! your order has been cancelled."),
	
	ACCEPT_TRADE("Your Trade request has been accepted"),
	//BUYER_CANCELLED("Your Trade request is cancelled by the buyer. Its now available for other buyers"),
	//SELLER_CANCELLED("Your buy order has been cancelled by the seller. Please look for other available orders."),
	//AGGREGATOR_CANCELLED("The aggregator has cancelled your trade. Please contact Helpline for more information."),
	ORDER_CANCELLED("Your upcoming trade is cancelled"),
	NON_TRADE_HOUR_CANCELLATION("The aggregator has cancelled your trade due to unforeseen scenarios. Please contact Helpline for more information."),
	
	 //APP_ID("9b0a5ec6-e306-4aa7-9713-722d8ee1f47c"); //DEV
	APP_ID("7b5d152f-7a97-479e-8461-e876941c55c4"); //UAT
	
	
	public String responseStatus;
	
	ApplicationConstant(String responseStatus){
		this.responseStatus= responseStatus;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	

}
