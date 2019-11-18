package com.revature.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.repositories.UserRepository;


@Service
public class UserService {
	
	UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Transactional
	public User createUser(User user) {
		return userRepository.create(user);
	}

	public List<Vehicle> getVehiclesByUserId(int id) {
		Optional<List<Vehicle>> optionalVehicles = 
					userRepository.getVehiclesByUserId(id);
		return optionalVehicles.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public User getUser(int id) {
		Optional<User> optionalUser = 
				userRepository.getById(id);
		return optionalUser.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public User updateUser(@Valid User user) {
		return userRepository.update(user);
	}

	@Transactional
	public User deleteUser(@Valid User user) {
		return userRepository.delete(user);
	}

}
