package com.revature.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
import com.revature.repositories.ServiceReportRepository;

public class ServiceReportService {
	
	ServiceReportRepository serviceReportRepository;

	@Autowired
	public ServiceReportService(ServiceReportRepository serviceReportRepository) {
		super();
		this.serviceReportRepository = serviceReportRepository;
	}

	@Transactional
	public ServiceReport createServiceReport(@Valid ServiceReport serviceReport) {
		return serviceReportRepository.create(serviceReport);
	}

	@Transactional
	public ServiceReport updateServiceReport(@Valid ServiceReport serviceReport) {
		return serviceReportRepository.update(serviceReport);
	}

	public ServiceReport getServiceReportById(int id) {
		Optional<ServiceReport> optionalServiceReport = serviceReportRepository.get(id);
		
		return optionalServiceReport.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public ServiceReport deleteServiceReport(@Valid ServiceReport serviceReport) {
		return serviceReportRepository.delete(serviceReport);
	}

}
