package com.revature.controllers;

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
import com.revature.services.UserService;
import com.revature.util.AuthUtil;


@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}	
	
	@GetMapping("/test")
	public void test(@RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		System.out.println(token);
	}

	@GetMapping("/user")
	public User getUser(@RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		
		return userService.getUser(cred	.getId());
	}

	@GetMapping("/{id}/vehicles")
	public List<Vehicle> getUserVehicles(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return userService.getVehiclesByUserId(id);
	}
	
	@GetMapping("/{id}/servicereports")
	public List<ServiceReport> getUserServiceReports(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return userService.getServiceReportsByUserId(id);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Valid User user) {
		return userService.createUser(user);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String login(@RequestBody Credentials credentials) {
		String token = userService.login(credentials);
		return token;
	}
	
	@PutMapping
	public User updateUSer(@RequestBody @Valid User user, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return userService.updateUser(user);
	}
	
	@DeleteMapping
	public User deleteUser(@RequestBody @Valid User user, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return userService.deleteUser(user);
	}
	
}
