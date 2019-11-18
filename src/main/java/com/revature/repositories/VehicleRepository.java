package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.User;
import com.revature.entities.Vehicle;
@Repository
public class VehicleRepository {
	
	@Autowired(required=true)
	EntityManager em;

	public Vehicle create(@Valid Vehicle vehicle) {
		Session session = em.unwrap(Session.class);
		session.save(vehicle);
		return vehicle;
	}

	public Optional<Vehicle> getById(int id) {
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(Vehicle.class, id));
	}

	public Optional<List<Vehicle>> getByUserId(int userId) {
		Session session = em.unwrap(Session.class);
		User user = session.get(User.class, userId);
		
		if (user == null) 
			return Optional.empty();
		
		List<Vehicle> vehicles = user.getVehicles();
		Hibernate.initialize(vehicles);
		return Optional.of(vehicles);
	}

	public Vehicle update(@Valid Vehicle vehicle) {
		Session session = em.unwrap(Session.class);
		session.update(vehicle);
		return vehicle;
	}

	public Vehicle delete(@Valid Vehicle vehicle) {
		Session session = em.unwrap(Session.class);
		session.delete(vehicle);
		return vehicle;
	}

}
