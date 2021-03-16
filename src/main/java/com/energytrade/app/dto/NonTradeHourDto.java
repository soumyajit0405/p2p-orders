package com.energytrade.app.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.energytrade.app.model.AllSellOrder;

public class NonTradeHourDto{
	
	private int nonTradeHourId;
	private byte activeStatus;
	private String nonTradeReason;
	private String status;
	private String startTime;
	private String endTime;
	private String location;
	private int duration;
	private int localityId;
	private String locationName;
	
	private String isEditable;
	private String isCancellable;
	
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
	public int getLocalityId() {
		return localityId;
	}
	public void setLocalityId(int localityId) {
		this.localityId = localityId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getEndTime() {
		return endTime;
	}
	public int getNonTradeHourId() {
		return nonTradeHourId;
	}
	public void setNonTradeHourId(int nonTradeHourId) {
		this.nonTradeHourId = nonTradeHourId;
	}
	public byte getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getNonTradeReason() {
		return nonTradeReason;
	}
	public void setNonTradeReason(String nonTradeReason) {
		this.nonTradeReason = nonTradeReason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTi8me() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}