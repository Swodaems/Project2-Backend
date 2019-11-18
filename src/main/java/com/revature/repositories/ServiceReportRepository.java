package com.revature.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
@Repository
public class ServiceReportRepository {
	
	@Autowired(required=true)
	EntityManager em;

	public ServiceReport create(@Valid ServiceReport serviceReport) {
		Session session = em.unwrap(Session.class);
		session.save(serviceReport);
		return serviceReport;
	}

	public ServiceReport update(@Valid ServiceReport serviceReport) {
		Session session = em.unwrap(Session.class);
		session.update(serviceReport);
		return serviceReport;
	}

	public Optional<ServiceReport> get(int id) {
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(ServiceReport.class, id));
	}

	public ServiceReport delete(@Valid ServiceReport serviceReport) {
		Session session = em.unwrap(Session.class);
		session.delete(serviceReport);
		return serviceReport;
	}

}
