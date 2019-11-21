package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.revature.entities.Insurance;
import com.revature.models.Creds;
import com.revature.services.InsuranceService;
import com.revature.util.AuthUtil;

@RestController
@RequestMapping("/insurance/")
public class InsuranceController {

	InsuranceService insuranceService;

	@Autowired
	public InsuranceController(InsuranceService insuranceService) {
		super();
		this.insuranceService = insuranceService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Insurance createInsurance(@RequestBody @Valid Insurance insurance, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return insuranceService.createInsurance(insurance);
	}
	
	@GetMapping("/{id}")
	public Insurance getInsurance(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return insuranceService.getInsurance(id);
	}
	
	@PutMapping
	public Insurance updateInsurance(@RequestBody @Valid Insurance insurance, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return insuranceService.updateInsurance(insurance);
	}
	
	@DeleteMapping
	public Insurance deleteInsurance(@RequestBody @Valid Insurance insurance, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return insuranceService.deleteInsurance(insurance);
	}
	
}
