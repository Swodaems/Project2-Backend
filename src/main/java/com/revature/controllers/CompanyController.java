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

import com.revature.entities.Company;
import com.revature.models.Creds;
import com.revature.services.CompanyService;
import com.revature.util.AuthUtil;

@RestController
@RequestMapping("company")
@CrossOrigin
public class CompanyController {
	
	CompanyService companyService;
	AuthUtil authUtil;
	
	@Autowired
	public CompanyController(CompanyService companyService, AuthUtil authUtil) {
		super();
		this.companyService = companyService;
		this.authUtil = authUtil;
	}	

	@GetMapping("/{id}")
	public Company getCompany(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.getCompanyById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Company createCompany(@RequestBody @Valid Company company, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.createCompany(company);
	}
	@PutMapping
	public Company updateCompany(@RequestBody @Valid Company company, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.updateCompany(company);
	}
	
	@DeleteMapping("/{id}")
	public Company deleteCompany(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = authUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		Company company=companyService.getCompanyById(id);
		return companyService.deleteCompany(company);
	}
}
