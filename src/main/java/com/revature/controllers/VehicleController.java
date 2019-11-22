package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.revature.models.ServiceReportData;
import com.revature.models.VehicleData;
import com.revature.services.UserService;
import com.revature.services.VehicleService;
import com.revature.util.AuthUtil;

@RestController
@RequestMapping("vehicles")
@CrossOrigin
public class VehicleController {
	
	UserService userService;
	VehicleService vehicleService;
	AuthUtil authUtil;

	@Autowired
	public VehicleController(VehicleService vehicleService, UserService userService, AuthUtil authUtil) {
		super();
		this.userService = userService;
		this.vehicleService = vehicleService;
		this.authUtil = authUtil;
	}
	
	@GetMapping("/{id}")
	public VehicleData getVehicle(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return new VehicleData(vehicleService.getVehicle(id));
	}
	
	@GetMapping("/user/{userId}")
	public List<VehicleData> getUserVehicles(@RequestHeader("Authorization") String token, @PathVariable int userId) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        List<VehicleData> vds = new ArrayList<>();
        List<Vehicle> vs = vehicleService.getUserVehicles(userId);
        for(Vehicle v:vs) {
        	vds.add(new VehicleData(v));
        }
        return vds;
	}
	
	@GetMapping("/{id}/servicereports")
	public List<ServiceReportData> getVehicleServiceReports(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        List<ServiceReportData> srds = new ArrayList<>();
        List<ServiceReport> srs = vehicleService.getVehicleServiceReports(id);
        for(ServiceReport sr:srs) {
        	srds.add(new ServiceReportData(sr));
        }
        return srds;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VehicleData createVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        vehicle.setUser(userService.getUser(cred.getId()));
		return new VehicleData(vehicleService.createVehicle(vehicle));
	}
	
	@PutMapping
	public VehicleData updateVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        Vehicle oldVehicle = vehicleService.getVehicle(vehicle.getId());
        vehicle.setUser(oldVehicle.getUser());
        if(oldVehicle.getPhoto() != null && vehicle.getPhoto() == null) vehicle.setPhoto(oldVehicle.getPhoto());
        if(oldVehicle.getInsurance() != null && vehicle.getInsurance() == null) vehicle.setInsurance(oldVehicle.getInsurance());
        return new VehicleData(vehicleService.updateVehicle(vehicle));
	}
	
	@DeleteMapping
	public VehicleData deleteVehicle(@RequestHeader("Authorization") String token, @RequestBody @Valid Vehicle vehicle) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return new VehicleData(vehicleService.deleteVehicle(vehicle));
	}

}
