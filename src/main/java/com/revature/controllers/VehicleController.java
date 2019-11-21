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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
import com.revature.models.Creds;
import com.revature.services.UserService;
import com.revature.services.VehicleService;
import com.revature.util.AuthUtil;

@RestController
@RequestMapping("vehicles")
public class VehicleController {
	
	UserService userService;
	VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService, UserService userService) {
		super();
		this.userService = userService;
		this.vehicleService = vehicleService;
	}
	
	@GetMapping("/{id}")
	public Vehicle getVehicle(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return vehicleService.getVehicle(id);
	}
	
	@GetMapping("/user/{userId}")
	public List<Vehicle> getUserVehicles(@RequestHeader("Authorization") String token, @PathVariable int userId) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return vehicleService.getUserVehicles(userId);
	}
	
	@GetMapping("/{id}/servicereports")
	public List<ServiceReport> getVehicleServiceReports(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return vehicleService.getVehicleServiceReports(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Vehicle createVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        	vehicle.setUser(userService.getUser(cred.getId()));
        return vehicleService.createVehicle(vehicle);
	}
	
	@PutMapping
	public Vehicle updateVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return vehicleService.updateVehicle(vehicle);
	}
	
	@DeleteMapping
	public Vehicle deleteVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = AuthUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return vehicleService.deleteVehicle(vehicle);
	}

}
