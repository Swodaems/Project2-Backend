package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.models.Creds;
import com.revature.models.ServiceReportData;
import com.revature.services.ServiceReportService;
import com.revature.util.AuthUtil;

@RestController
@RequestMapping("/servicereports/")
@CrossOrigin
public class ServiceReportController {

	ServiceReportService serviceReportService;
	AuthUtil authUtil;

	@Autowired
	public ServiceReportController(ServiceReportService serviceReportService, AuthUtil authUtil) {
		super();
		this.serviceReportService = serviceReportService;
		this.authUtil = authUtil;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceReportData createServiceReport(@RequestHeader("Authorization") String token,
			@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
		if (cred == null)
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		System.out.println(serviceReport);
		return new ServiceReportData(serviceReportService.createServiceReport(serviceReport));
	}

	@PutMapping
	public ServiceReportData updateServiceReport(@RequestHeader("Authorization") String token,
			@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
		if (cred == null)
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		ServiceReport oldReport = serviceReportService.getServiceReportById(serviceReport.getId());
		serviceReport.setVehicle(oldReport.getVehicle());
		serviceReport.setTime(oldReport.getTime());
		if (oldReport.getUser() != null && serviceReport.getUser() == null)
			serviceReport.setUser(oldReport.getUser());
		if (oldReport.getUserNote() != null && serviceReport.getUserNote() == null)
			serviceReport.setUserNote(oldReport.getUserNote());
		if (oldReport.getTechnicianNote() != null && serviceReport.getTechnicianNote() == null)
			serviceReport.setTechnicianNote(oldReport.getTechnicianNote());
		if (oldReport.getType() != null && serviceReport.getType() == null)
			serviceReport.setType(oldReport.getType());
		if (oldReport.getReceipt() != null && serviceReport.getReceipt() == null)
			serviceReport.setReceipt(oldReport.getReceipt());
		return new ServiceReportData(serviceReportService.updateServiceReport(serviceReport));
	}

	@GetMapping("/{id}")
	public ServiceReportData getServiceReportById(@RequestHeader("Authorization") String token, @PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
		if (cred == null)
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return new ServiceReportData(serviceReportService.getServiceReportById(id));
	}

	@DeleteMapping
	public ServiceReportData deleteServiceReport(@RequestHeader("Authorization") String token,
			@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
		if (cred == null)
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return new ServiceReportData(serviceReportService.deleteServiceReport(serviceReport));
	}

}
