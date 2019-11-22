package com.revature.models;

import java.util.ArrayList;
import java.util.List;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;

public class VehicleData {
	private int id;
	private String name;
	private int year;
	private String model;
	private String make;
	private String color;
	private double mileage;
	private int userId;
	/*
	 * @ManyToOne
	 
	@JoinColumn(name="user_id")
	@JsonIgnoreProperties({"Vehicle","ServiceReport"})
	@JsonProperty("User")
	private User user;
	*/
	private int insuranceId;
	/*
	 * @ManyToOne
	 
	@JoinColumn(name="insurance_id")
	@JsonProperty("Insurance")
	private Insurance insurance;
	*/
	private List<Integer> serviceReportIds;
	/*@OneToMany
	@JoinColumn(name="vehicle_id")
	@JsonIgnoreProperties({"Vehicle","User"})
	@JsonProperty("ServiceReport")
	private List<ServiceReport> serviceReports;
	*/
	private String VIN;
	private String photo;
	public int getId() {
		return id;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}
	public List<Integer> getServiceReportIds() {
		return serviceReportIds;
	}
	public void setServiceReportIds(List<Integer> serviceReportIds) {
		this.serviceReportIds = serviceReportIds;
	}
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String vIN) {
		VIN = vIN;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public VehicleData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VehicleData(Vehicle vehicle) {
		this.setUserId(vehicle.getUser().getId());
		this.setName(vehicle.getName());
		if(vehicle.getInsurance() != null) this.setInsuranceId(vehicle.getInsurance().getId());
		List<Integer> serviceReportIds=new ArrayList<>();
		if(vehicle.getServiceReports()!= null) {
			for(ServiceReport sr:vehicle.getServiceReports()) {
				serviceReportIds.add(sr.getId());
			}
		}
		this.setServiceReportIds(serviceReportIds);
		this.setId(vehicle.getId());
		this.setYear(vehicle.getYear());
		this.setColor(vehicle.getColor());
		this.setMake(vehicle.getMake());
		this.setModel(vehicle.getModel());
		this.setMileage(vehicle.getMileage());
		this.setPhoto(vehicle.getPhoto());
		this.setVIN(vehicle.getVIN());
	}
}
