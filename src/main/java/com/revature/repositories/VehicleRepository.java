package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Repository;
import com.revature.entities.Vehicle;
@Repository
public class VehicleRepository {

	public Vehicle create(@Valid Vehicle vehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Vehicle> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<List<Vehicle>> getByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
