package com.energytrade.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.energytrade.app.model.AllSellOrder;

public class AllContractDto{
	
	private int contractId;
	private int sellOrderId;
	private int buyerId;
	private String contractStatus;
	private String isCancellable;
	private String isEditable;
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

	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	private BigDecimal sellerEnergyTfr;
	private BigDecimal sellerFine;
	private BigDecimal buyerEnergyTfr;
	private BigDecimal buyerFine;
	public BigDecimal getBuyerEnergyTfr() {
		return buyerEnergyTfr;
	}
	public void setBuyerEnergyTfr(BigDecimal buyerEnergyTfr) {
		this.buyerEnergyTfr = buyerEnergyTfr;
	}
	public BigDecimal getSellerEnergyTfr() {
		return sellerEnergyTfr;
	}
	public void setSellerEnergyTfr(BigDecimal sellerEnergyTfr) {
		this.sellerEnergyTfr = sellerEnergyTfr;
	}
	public BigDecimal getSellerFine() {
		return sellerFine;
	}
	public void setSellerFine(BigDecimal sellerFine) {
		this.sellerFine = sellerFine;
	}
	public BigDecimal getBuyerFine() {
		return buyerFine;
	}
	public void setBuyerFine(BigDecimal buyerFine) {
		this.buyerFine = buyerFine;
	}
	private BigDecimal energy;
	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	public String getIsCancellable() {
		return isCancellable;
	}
	public void setIsCancellable(String isCancellable) {
		this.isCancellable = isCancellable;
	}
	private AllSellOrderDto sellorder;
	public AllSellOrderDto getSellorder() {
		return sellorder;
	}
	public void setSellorder(AllSellOrderDto sellorder) {
		this.sellorder = sellorder;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public int getSellOrderId() {
		return sellOrderId;
	}
	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
		
}