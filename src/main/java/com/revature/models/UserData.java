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
	// private Role role;
	private int roleId;
	// private Company company;
	private int CompanyId;
	// private List<ServiceReport> serviceReports;
	private List<Integer> ServiceReportIds;
	// private List<Vehicle> vehicles;
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
		this.id = user.getId();
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		if(user.getEmail() != null) this.setEmail(user.getEmail());
		if(user.getPhoto() != null) this.setPhoto(user.getPhoto());
		if (user.getRole() != null)
			this.setRoleId(user.getRole().getId());
		if (user.getCompany() != null)
			this.setCompanyId(user.getCompany().getId());
		List<Integer> vehicleIds = new ArrayList<>();
		if (user.getVehicles() != null) {
			for (Vehicle v : user.getVehicles()) {
				vehicleIds.add(v.getId());
			}
		}
		if(user.getVehicles() != null) this.setVehicleIds(vehicleIds);List<Integer> serviceReportIds = new ArrayList<>();
		if(user.getServiceReports() != null) {
			for (ServiceReport sr : user.getServiceReports()) {
				serviceReportIds.add(sr.getId());
			}
		}

	}

	@Override
	public String toString() {
		return "UserData [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", photo=" + photo + ", roleId=" + roleId + ", CompanyId=" + CompanyId + ", ServiceReportIds="
				+ ServiceReportIds + ", vehicleIds=" + vehicleIds + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CompanyId;
		result = prime * result + ((ServiceReportIds == null) ? 0 : ServiceReportIds.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + roleId;
		result = prime * result + ((vehicleIds == null) ? 0 : vehicleIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (CompanyId != other.CompanyId)
			return false;
		if (ServiceReportIds == null) {
			if (other.ServiceReportIds != null)
				return false;
		} else if (!ServiceReportIds.equals(other.ServiceReportIds))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (roleId != other.roleId)
			return false;
		if (vehicleIds == null) {
			if (other.vehicleIds != null)
				return false;
		} else if (!vehicleIds.equals(other.vehicleIds))
			return false;
		return true;
	}
	
	
}
