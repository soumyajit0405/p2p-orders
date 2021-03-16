package com.energytrade.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.energytrade.app.model.AllSellOrder;

public class SearchOrdersDto{
	
	private int sellOrderId;
	private int sellerId;
	private int userDeviceId;
	private BigDecimal powerToSell;
	private BigDecimal ratePerUnit;
	private BigDecimal totalAmount;
	private String transferEndTs;
	private String transferStartTs;
	private String deviceTypeName;
	private String orderStatus;
	private String localityName;
	private int localityId;
	private String isEditable;
	private String isCancellable;
	private String locationName;
	private int localitionId;
	private BigDecimal energy;
	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getLocalitionId() {
		return localitionId;
	}
	public void setLocalitionId(int localitionId) {
		this.localitionId = localitionId;
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