package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
import com.revature.services.VehicleService;

@RestController
@RequestMapping("vehicles")
public class VehicleController {
	
	VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	@GetMapping("/{id}")
	public Vehicle getVehicle(@PathVariable int id) {
		return vehicleService.getVehicle(id);
	}
	
	@GetMapping("/user/{userId}")
	public List<Vehicle> getUserVehicles(@PathVariable int userId) {
		return vehicleService.getUserVehicles(userId);
	}
	
	@GetMapping("/{id}/servicereports")
	public List<ServiceReport> getVehicleServiceReports(@PathVariable int id) {
		return vehicleService.getVehicleServiceReports(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Vehicle createVehicle(@RequestBody @Valid Vehicle vehicle) {
		return vehicleService.createVehicle(vehicle);
	}
	
	@PutMapping
	public Vehicle updateVehicle(@RequestBody @Valid Vehicle vehicle) {
		return vehicleService.updateVehicle(vehicle);
	}
	
	@DeleteMapping
	public Vehicle deleteVehicle(@RequestBody @Valid Vehicle vehicle) {
		return vehicleService.deleteVehicle(vehicle);
	}

}
