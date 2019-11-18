package com.revature.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.Company;
import com.revature.entities.User;

@Repository
public class CompanyRepository {
	@Autowired(required = true)
	EntityManager em;
	
	public Company create(Company company) {
		Session session = em.unwrap(Session.class);
		session.save(company);
		return company;
	}

	public Optional<Company> getById(int id) {
		// TODO Auto-generated method stub
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(Company.class, id));
	}

	public Company delete(@Valid Company company) {
		Session session = em.unwrap(Session.class);
		session.delete(company);
		return company;
	}

	public Company update(@Valid Company company) {
		Session session = em.unwrap(Session.class);
		session.update(company);
		return company;
	}

}
