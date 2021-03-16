package com.energytrade.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.energytrade.app.model.AllSellOrder;

public class AllSellOrderDto{
	
	private int sellOrderId;
	private int sellerId;
	private int userDeviceId;
	private BigDecimal powerToSell;
	private BigDecimal ratePerUnit;
	private BigDecimal totalAmount;
	private BigDecimal energy;
	private String isFineApplicable;
	private String blockChainStatus;
	
	public String getBlockChainStatus() {
		return blockChainStatus;
	}

	public void setBlockChainStatus(String blockChainStatus) {
		this.blockChainStatus = blockChainStatus;
	}

	public String getIsFineApplicable() {
		return isFineApplicable;
	}

	public void setIsFineApplicable(String isFineApplicable) {
		this.isFineApplicable = isFineApplicable;
	}

	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	private String transferEndTs;
	private String transferStartTs;
	private String deviceTypeName;
	private String orderStatus;
	private String localityName;
	private int localityId;
	private String isEditable;
	private String isCancellable;
	private int deviceTypeId;
	private String buyerName;
	private String sellerName;
	private BigDecimal sellerFine;
	private BigDecimal sellerEnergyTfr;
	
	public BigDecimal getSellerFine() {
		return sellerFine;
	}
	public void setSellerFine(BigDecimal sellerFine) {
		this.sellerFine = sellerFine;
	}
	public BigDecimal getSellerEnergyTfr() {
		return sellerEnergyTfr;
	}
	public void setSellerEnergyTfr(BigDecimal sellerEnergyTfr) {
		this.sellerEnergyTfr = sellerEnergyTfr;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	public String getIsCancellable() {
		return isCancellable;
	}
	public void setIsCancellable(String isCancellable) {
		this.isCancellable = isCancellable;
	}
	public String getLocalityName() {
		return localityName;
	}
	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}
	public int getLocalityId() {
		return localityId;
	}
	public void setLocalityId(int localityId) {
		this.localityId = localityId;
	}
	public int getSellOrderId() {
		return sellOrderId;
	}
	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getUserDeviceId() {
		return userDeviceId;
	}
	public void setUserDeviceId(int userDeviceId) {
		this.userDeviceId = userDeviceId;
	}
	public BigDecimal getPowerToSell() {
		return powerToSell;
	}
	public void setPowerToSell(BigDecimal powerToSell) {
		this.powerToSell = powerToSell;
	}
	public BigDecimal getRatePerUnit() {
		return ratePerUnit;
	}
	public void setRatePerUnit(BigDecimal ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTransferEndTs() {
		return transferEndTs;
	}
	public void setTransferEndTs(String transferEndTs) {
		this.transferEndTs = transferEndTs;
	}
	public String getTransferStartTs() {
		return transferStartTs;
	}
	public void setTransferStartTs(String transferStartTs) {
		this.transferStartTs = transferStartTs;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}