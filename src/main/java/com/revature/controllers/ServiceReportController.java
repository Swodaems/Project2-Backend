package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.services.ServiceReportService;

@RestController
@RequestMapping("/servicereports/")
public class ServiceReportController {

	ServiceReportService serviceReportService;

	@Autowired
	public ServiceReportController(ServiceReportService serviceReportService) {
		super();
		this.serviceReportService = serviceReportService;
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> exceptionHandler(HttpClientErrorException e) {
		return ResponseEntity
					.status(e.getStatusCode())
					.body(e.getMessage());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceReport createServiceReport(@RequestBody @Valid ServiceReport serviceReport) {
		return serviceReportService.createServiceReport(serviceReport);
	}
	
	@PutMapping
	public ServiceReport updateServiceReport(@RequestBody @Valid ServiceReport serviceReport) {
		return serviceReportService.updateServiceReport(serviceReport);
	}
	
	@GetMapping("/{id}")
	public ServiceReport getServiceReportById(@PathVariable int id) {
		return serviceReportService.getServiceReportById(id);
	}
	
	@DeleteMapping
	public ServiceReport deleteServiceReport(@RequestBody @Valid ServiceReport serviceReport) {
		return serviceReportService.deleteServiceReport(serviceReport);
	}
	
}
