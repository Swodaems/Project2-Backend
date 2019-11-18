package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.models.Credentials;
import com.revature.services.UserService;


@RestController
@RequestMapping("user")
public class UserController {
	UserService userService;
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}	

	@GetMapping("/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}



	@GetMapping("/{id}/vehicles")
	public List<Vehicle> getUserVehicles(@PathVariable int id) {
		return userService.getVehiclesByUserId(id);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Valid User user) {
		return userService.createUser(user);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User login(@RequestBody Credentials credentials) {
		return userService.login(credentials);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> 
		handleHttpClientError(HttpClientErrorException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.body(e.getMessage());
	}
	
	@PutMapping
	public User updateUSer(@RequestBody @Valid User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping
	public User deleteUser(@RequestBody @Valid User user) {
		return userService.deleteUser(user);
	}
}