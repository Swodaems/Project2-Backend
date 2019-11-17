package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.User;
import com.revature.entities.Vehicle;


@Repository
public class UserRepository {

	@Autowired(required = true)
	EntityManager em;
	
	public Optional<List<Vehicle>> getVehiclesByUserId(int id) {
		Session session = em.unwrap(Session.class);
		User user = session.get(User.class, id);
		
		if (user == null) 
			return Optional.empty();
		
		List<Vehicle> books = user.getVehicles();
		Hibernate.initialize(books);
		return Optional.of(books);
	}


	public User create(User user) {
		Session session = em.unwrap(Session.class);
		session.save(user);
		return user;
	}

	public Optional<User> getById(int id) {
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(User.class, id));
	}
}
