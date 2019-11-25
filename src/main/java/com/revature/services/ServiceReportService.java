package com.revature.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
import com.revature.repositories.ServiceReportRepository;

@Service
public class ServiceReportService {

	ServiceReportRepository serviceReportRepository;
	VehicleService vehicleService;

	@Autowired
	public ServiceReportService(ServiceReportRepository serviceReportRepository, VehicleService vehicleService) {
		super();
		this.serviceReportRepository = serviceReportRepository;
		this.vehicleService = vehicleService;
	}

	@Transactional
	public ServiceReport createServiceReport(@Valid ServiceReport serviceReport) {
		serviceReport.setVehicle(vehicleService.getVehicle(serviceReport.getVehicle().getId()));
		return serviceReportRepository.create(serviceReport);
	}

	@Transactional
	public ServiceReport updateServiceReport(@Valid ServiceReport serviceReport) {
		return serviceReportRepository.update(serviceReport);
	}

	public ServiceReport getServiceReportById(int id) {
		Optional<ServiceReport> optionalServiceReport = serviceReportRepository.get(id);

		return optionalServiceReport.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public ServiceReport deleteServiceReport(@Valid ServiceReport serviceReport) {
		return serviceReportRepository.delete(serviceReport);
	}

	public ServiceReport addPhoto(int id, String url) {
		// TODO Auto-generated method stub
		Optional<ServiceReport> optionalServiceReport = serviceReportRepository.addPhoto(id, url);
		return optionalServiceReport.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

}
