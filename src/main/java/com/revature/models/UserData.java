package com.revature.models;

import java.util.ArrayList;
import java.util.List;

import com.revature.entities.ServiceReport;
import com.revature.entities.User;
import com.revature.entities.Vehicle;

public class UserData {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String photo;
	//private Role role;
	private int roleId;
	//private Company company;
	private int CompanyId;
	//private List<ServiceReport> serviceReports;
	private List<Integer> ServiceReportIds;
	//private List<Vehicle> vehicles;
	private List<Integer> vehicleIds;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(int companyId) {
		CompanyId = companyId;
	}
	public List<Integer> getServiceReportIds() {
		return ServiceReportIds;
	}
	public void setServiceReportIds(List<Integer> serviceReportIds) {
		ServiceReportIds = serviceReportIds;
	}
	public List<Integer> getVehicleIds() {
		return vehicleIds;
	}
	public void setVehicleIds(List<Integer> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserData(User user) {
		this.id=user.getId();
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setPhoto(user.getPhoto());
		this.setRoleId(user.getRole().getId());
		this.setCompanyId(user.getCompany().getId());
		List<Integer> vehicleIds= new ArrayList<>();
		for(Vehicle v:user.getVehicles()) {
			vehicleIds.add(v.getId());
		}
		this.setVehicleIds(vehicleIds);
		List<Integer> serviceReportIds=new ArrayList<>();
		for(ServiceReport sr :user.getServices()) {
			serviceReportIds.add(sr.getId());
		}
		
	}
}
