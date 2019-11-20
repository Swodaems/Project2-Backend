package com.revature.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vehicle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int year;
	private String model;
	private String make;
	private String color;
	private double mileage;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	@ManyToOne
	@JoinColumn(name="insurance_id")
	private Insurance insurance;
	@OneToMany
	@JoinColumn(name="vehicle_id")
	private List<ServiceReport> serviceReports;
	private String photo;
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public List<ServiceReport> getServices() {
		return serviceReports;
	}
	public void setServices(List<ServiceReport> serviceReports) {
		this.serviceReports = serviceReports;
	}
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Insurance getInsurance() {
		return insurance;
	}
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
	public Vehicle(int id, String name, int year, String model, String make, String color, double mileage, User user,
			Insurance insurance, List<ServiceReport> serviceReports, String photo) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.model = model;
		this.make = make;
		this.color = color;
		this.mileage = mileage;
		this.user = user;
		this.insurance = insurance;
		this.serviceReports = serviceReports;
		this.photo = photo;
	}
	public Vehicle() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + id;
		result = prime * result + ((insurance == null) ? 0 : insurance.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mileage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((serviceReports == null) ? 0 : serviceReports.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + year;
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
		Vehicle other = (Vehicle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (id != other.id)
			return false;
		if (insurance == null) {
			if (other.insurance != null)
				return false;
		} else if (!insurance.equals(other.insurance))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (Double.doubleToLongBits(mileage) != Double.doubleToLongBits(other.mileage))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (serviceReports == null) {
			if (other.serviceReports != null)
				return false;
		} else if (!serviceReports.equals(other.serviceReports))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", name=" + name + ", year=" + year + ", model=" + model + ", make=" + make
				+ ", color=" + color + ", mileage=" + mileage + ", user=" + user + ", insurance=" + insurance
				+ ", serviceReports=" + serviceReports + ", photo=" + photo + "]";
	}
	
	
}
