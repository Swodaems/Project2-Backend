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

import com.revature.entities.Company;
import com.revature.services.CompanyService;

@RestController
@RequestMapping("company")
public class CompanyController {
	CompanyService companyService;
	@Autowired
	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}	

	@GetMapping("/{id}")
	public Company getCompany(@PathVariable int id) {
		return companyService.getCompanyById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Company createCompany(@RequestBody @Valid Company company) {
		return companyService.createCompany(company);
	}
	@PutMapping
	public Company updateCompany(@RequestBody @Valid Company company) {
		return companyService.updateCompany(company);
	}
	
	@DeleteMapping
	public Company deleteCompany(@RequestBody @Valid Company company) {
		return companyService.deleteCompany(company);
	}
}
