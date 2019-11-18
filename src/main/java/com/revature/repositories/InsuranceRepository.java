package com.revature.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.Insurance;

@Repository
public class InsuranceRepository {
	
	@Autowired(required=true)
	EntityManager em;

	public Insurance create(@Valid Insurance insurance) {
		Session session = em.unwrap(Session.class);
		session.save(insurance);
		return insurance;
	}

	public Optional<Insurance> getById(int id) {
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(Insurance.class, id));
	}

	public Insurance update(@Valid Insurance insurance) {
		Session session = em.unwrap(Session.class);
		session.update(insurance);
		return insurance;
	}

	public Insurance delete(@Valid Insurance insurance) {
		Session session = em.unwrap(Session.class);
		session.delete(insurance);
		return insurance;
	}

}
