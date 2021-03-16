package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the all_events database table.
 * 
 */
@Entity
@Table(name="all_events")
@NamedQuery(name="AllEvent.findAll", query="SELECT a FROM AllEvent a")
public class AllEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="event_id")
	private int eventId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="actual_power")
	private double actualPower;

	@Column(name="commited_power")
	private double commitedPower;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_end_time")
	private Date eventEndTime;

	@Column(name="event_name")
	private String eventName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_start_time")
	private Date eventStartTime;

	@Column(name="expected_price")
	private double expectedPrice;

	@Column(name="planned_power")
	private double plannedPower;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to EventStatusPl
	@ManyToOne
	@JoinColumn(name="event_status_id")
	private EventStatusPl eventStatusPl;

	//bi-directional many-to-one association to AllEventSet
	@ManyToOne
	@JoinColumn(name="event_set_id")
	private AllEventSet allEventSet;

	//bi-directional many-to-one association to EventCustomerMapping
	@OneToMany(mappedBy="allEvent")
	private List<EventCustomerMapping> eventCustomerMappings;

	public AllEvent() {
	}

	public int getEventId() {
		return this.eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}

	public double getActualPower() {
		return this.actualPower;
	}

	public void setActualPower(double actualPower) {
		this.actualPower = actualPower;
	}

	public double getCommitedPower() {
		return this.commitedPower;
	}

	public void setCommitedPower(double commitedPower) {
		this.commitedPower = commitedPower;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getEventEndTime() {
		return this.eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventStartTime() {
		return this.eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public double getExpectedPrice() {
		return this.expectedPrice;
	}

	public void setExpectedPrice(double expectedPrice) {
		this.expectedPrice = expectedPrice;
	}

	public double getPlannedPower() {
		return this.plannedPower;
	}

	public void setPlannedPower(double plannedPower) {
		this.plannedPower = plannedPower;
	}

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public Date getSyncTs() {
		return this.syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public EventStatusPl getEventStatusPl() {
		return this.eventStatusPl;
	}

	public void setEventStatusPl(EventStatusPl eventStatusPl) {
		this.eventStatusPl = eventStatusPl;
	}

	public AllEventSet getAllEventSet() {
		return this.allEventSet;
	}

	public void setAllEventSet(AllEventSet allEventSet) {
		this.allEventSet = allEventSet;
	}

	public List<EventCustomerMapping> getEventCustomerMappings() {
		return this.eventCustomerMappings;
	}

	public void setEventCustomerMappings(List<EventCustomerMapping> eventCustomerMappings) {
		this.eventCustomerMappings = eventCustomerMappings;
	}

	public EventCustomerMapping addEventCustomerMapping(EventCustomerMapping eventCustomerMapping) {
		getEventCustomerMappings().add(eventCustomerMapping);
		eventCustomerMapping.setAllEvent(this);

		return eventCustomerMapping;
	}

	public EventCustomerMapping removeEventCustomerMapping(EventCustomerMapping eventCustomerMapping) {
		getEventCustomerMappings().remove(eventCustomerMapping);
		eventCustomerMapping.setAllEvent(null);

		return eventCustomerMapping;
	}

}