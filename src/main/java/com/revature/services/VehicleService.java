package com.revature.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Vehicle;
import com.revature.repositories.VehicleRepository;
@Service
public class VehicleService {
	
	VehicleRepository vehicleRepository;

	@Autowired
	public VehicleService(VehicleRepository vehicleRepository) {
		super();
		this.vehicleRepository = vehicleRepository;
	}

	public Vehicle getVehicle(int id) {
		Optional<Vehicle> optionalVehicle = vehicleRepository.getById(id);
		
		return optionalVehicle.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public Vehicle createVehicle(@Valid Vehicle vehicle) {
		return vehicleRepository.create(vehicle);
	}

	public List<Vehicle> getUserVehicles(int userId) {
		Optional<List<Vehicle>> optionalUserVehicles = vehicleRepository.getByUserId(userId);
		
		return optionalUserVehicles.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

}
