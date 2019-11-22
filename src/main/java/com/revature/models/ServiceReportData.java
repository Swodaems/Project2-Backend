package com.revature.models;

import java.time.LocalDateTime;

import com.revature.entities.ServiceReport;

public class ServiceReportData {
	private int id;
	private String name;
	private int serviceTypeId;
	//private ServiceType type;
	private double cost;
	private LocalDateTime time;
	private String technicianNote;
	private String userNote;
	private int vehicleId;
	//private Vehicle vehicle;
	private String receipt;
	private int userId;
	//private User user;
	
	public int getId() {
		return id;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getTechnicianNote() {
		return technicianNote;
	}
	public void setTechnicianNote(String technicianNote) {
		this.technicianNote = technicianNote;
	}
	public String getUserNote() {
		return userNote;
	}
	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public ServiceReportData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceReportData(ServiceReport sr) {
		this.setCost(sr.getCost());
		this.setId(sr.getId());
		this.setName(sr.getName());
		this.setReceipt(sr.getReceipt());
		this.setServiceTypeId(sr.getType().getId());
		this.setTechnicianNote(sr.getTechnicianNote());
		this.setTime(sr.getTime());
		this.setUserId(sr.getUser().getId());
		this.setUserNote(sr.getUserNote());
		this.setVehicleId(sr.getVehicle().getId());
		
	}
}
