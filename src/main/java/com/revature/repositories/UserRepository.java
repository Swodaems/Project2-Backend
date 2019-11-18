package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.type.StringType;
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
		
		List<Vehicle> vehicles = user.getVehicles();
		Hibernate.initialize(vehicles);
		return Optional.of(vehicles);
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
	public Optional<User> getByEmail(String email){
		Session session = em.unwrap(Session.class);
		String hql = "from User u where u.email like :email";
		User user = session.createQuery(hql, User.class)
				.setParameter("email", email, StringType.INSTANCE)
				.getSingleResult();
		return Optional.ofNullable(user);
	}
}
