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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Insurance;
import com.revature.services.InsuranceService;

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
	public Insurance createInsurance(@RequestBody @Valid Insurance insurance) {
		return insuranceService.createInsurance(insurance);
	}
	
	@GetMapping("/{id}")
	public Insurance getInsurance(@PathVariable int id) {
		return insuranceService.getInsurance(id);
	}
	
	@PutMapping
	public Insurance updateInsurance(@RequestBody @Valid Insurance insurance) {
		return insuranceService.updateInsurance(insurance);
	}
	
	@DeleteMapping
	public Insurance deleteInsurance(@RequestBody @Valid Insurance insurance) {
		return insuranceService.deleteInsurance(insurance);
	}
	
}
