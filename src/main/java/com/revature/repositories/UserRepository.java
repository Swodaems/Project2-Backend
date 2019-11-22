package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.ServiceReport;
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
	public Optional<List<ServiceReport>> getServiceReportsByUserId(int id) {
		Session session = em.unwrap(Session.class);
		User user = session.get(User.class, id);
		
		if (user == null) 
			return Optional.empty();
		
		List<Vehicle> vehicles = user.getVehicles();
		List<ServiceReport> services= new ArrayList<>();
		for(Vehicle v:vehicles) {
			services.addAll(v.getServiceReports());
		}
		Hibernate.initialize(services);
		return Optional.of(services);
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
		User user = new User();
		String hql = "from User u where u.email like :email";
		try {
			user = session.createQuery(hql, User.class)
					.setParameter("email", email, StringType.INSTANCE)
					.getSingleResult();
		} catch (NoResultException e) { return null; }
		return Optional.ofNullable(user);
	}


	public User update(@Valid User user) {
		Session session = em.unwrap(Session.class);
		session.merge(user);
		return user;
	}


	public User delete(@Valid User user) {
		Session session = em.unwrap(Session.class);
		session.delete(user);
		return user;
	}
}
