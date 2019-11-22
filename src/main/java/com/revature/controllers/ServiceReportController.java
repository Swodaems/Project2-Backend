package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
import com.revature.models.VehicleData;
import com.revature.services.ServiceReportService;
import com.revature.util.AuthUtil;
import com.revature.util.PhotoUtil;

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
	public ServiceReportData createServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        System.out.println(serviceReport);
        return new ServiceReportData(serviceReportService.createServiceReport(serviceReport));
	}
	@PostMapping("/{id}/photo")
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceReportData AddPhoto(@RequestHeader("Authorization") String token, @PathVariable int id, HttpEntity<byte[]> requestEntity) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		String url=PhotoUtil.uploadPhoto(requestEntity.getBody());
		
		return new ServiceReportData(serviceReportService.addPhoto(id,url));
	}
	@PutMapping
	public ServiceReportData updateServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return new ServiceReportData(serviceReportService.updateServiceReport(serviceReport));
	}
	
	@GetMapping("/{id}")
	public ServiceReportData getServiceReportById(@RequestHeader("Authorization") String token,@PathVariable int id) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return new ServiceReportData(serviceReportService.getServiceReportById(id));
	}
	
	@DeleteMapping
	public ServiceReportData deleteServiceReport(@RequestHeader("Authorization") String token,@RequestBody @Valid ServiceReport serviceReport) {
		Creds cred = authUtil.parseJWT(token);
        if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        return new ServiceReportData(serviceReportService.deleteServiceReport(serviceReport));
	}
	
}
