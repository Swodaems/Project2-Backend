package com.revature.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="services")
public class ServiceReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name="type_id")
	private ServiceType type;
	private double cost;
	private LocalDateTime time;
	@Column(name="technician_note")
	private String technicianNote;
	@Column(name="user_note")
	private String userNote;
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicle;
	private String receipt;
	
	
	public String getReceipt() {
		return receipt;
	}


	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((technicianNote == null) ? 0 : technicianNote.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userNote == null) ? 0 : userNote.hashCode());
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		ServiceReport other = (ServiceReport) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (technicianNote == null) {
			if (other.technicianNote != null)
				return false;
		} else if (!technicianNote.equals(other.technicianNote))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userNote == null) {
			if (other.userNote != null)
				return false;
		} else if (!userNote.equals(other.userNote))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", type=" + type + ", cost=" + cost + ", time=" + time
				+ ", technicianNote=" + technicianNote + ", userNote=" + userNote + ", vehicle=" + vehicle + ", user="
				+ user + "]";
	}


	public ServiceReport() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ServiceReport(int id, String name, ServiceType type, double cost, LocalDateTime time, String technicianNote,
			String userNote, Vehicle vehicle, User user) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.time = time;
		this.technicianNote = technicianNote;
		this.userNote = userNote;
		this.vehicle = vehicle;
		this.user = user;
	}


	//Is this meant to represent the technician
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;


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


	public ServiceType getType() {
		return type;
	}


	public void setType(ServiceType type) {
		this.type = type;
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


	public Vehicle getVehicle() {
		return vehicle;
	}


	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
}
