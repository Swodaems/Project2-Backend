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
import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.models.Credentials;
import com.revature.models.Creds;
import com.revature.models.ServiceReportData;
import com.revature.models.UserData;
import com.revature.models.VehicleData;
import com.revature.services.UserService;
import com.revature.util.AuthUtil;


@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	UserService userService;
	AuthUtil authUtil;
	
	@Autowired
	public UserController(UserService userService, AuthUtil authUtil) {
		super();
		this.userService = userService;
		this.authUtil = authUtil;
	}
	
	@GetMapping("/test")
	public void test(@RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		System.out.println(token);
	}
	
	@GetMapping("/{id}")
	public UserData getUserById(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return new UserData(userService.getUser(id));
	}
	@GetMapping("technicians")
	public List<UserData> getTechniciansByName(@RequestHeader("Authorization") String token, @RequestBody User user){
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		List<User> users =userService.getTechniciansByName(user.getFirstName(), user.getLastName());
		List<UserData> userData=new ArrayList<>();
		for(User u : users) {
			userData.add(new UserData(u));
		}
		return userData;
	}
	@GetMapping("/user")
	public UserData getUser(@RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		
		return new UserData(userService.getUser(cred.getId()));
	}

	@GetMapping("/{id}/vehicles")
	public List<VehicleData> getUserVehicles(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		List<Vehicle> vs = userService.getVehiclesByUserId(id);
		List<VehicleData> vds = new ArrayList<>();
		for(Vehicle v :vs) {
			vds.add(new VehicleData(v));
		}
		return vds;
	}
	
	@GetMapping("/{id}/servicereports")
	public List<ServiceReportData> getUserServiceReports(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		List<ServiceReport> srs =userService.getServiceReportsByUserId(id);
		List<ServiceReportData> srds = new ArrayList<>();
		for(ServiceReport sr:srs) {
			srds.add(new ServiceReportData(sr));
		}
		return srds;
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserData createUser(@RequestBody @Valid User user) {
		return new UserData(userService.createUser(user));
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String login(@RequestBody Credentials credentials) {
		String token = userService.login(credentials);
		return token;
	}
	
	@PutMapping
	public UserData updateUser(@RequestBody @Valid User user, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		User oldUser = userService.getUser(cred.getId());
		user.setPassword(oldUser.getPassword());
		user.setSalt(oldUser.getSalt());
		user.setVehicles(oldUser.getVehicles());
		return new UserData(userService.updateUser(user));
	}
	
	@DeleteMapping
	public UserData deleteUser(@RequestBody @Valid User user, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return new UserData(userService.deleteUser(user));
	}
	
}
