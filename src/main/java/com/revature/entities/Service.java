package com.revature.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name="services")
public class Service {
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
