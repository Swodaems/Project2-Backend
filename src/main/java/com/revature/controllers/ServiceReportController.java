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
	public ServiceReport createServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return serviceReportService.createServiceReport(serviceReport);
	}
	
	@PutMapping
	public ServiceReport updateServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return serviceReportService.updateServiceReport(serviceReport);
	}
	
	@GetMapping("/{id}")
	public ServiceReport getServiceReportById(@RequestHeader("Authorization") String token,@PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return serviceReportService.getServiceReportById(id);
	}
	
	@DeleteMapping
	public ServiceReport deleteServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return serviceReportService.deleteServiceReport(serviceReport);
	}
	
}
