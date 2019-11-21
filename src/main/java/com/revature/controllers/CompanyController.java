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

import com.revature.entities.Company;
import com.revature.models.Creds;
import com.revature.services.CompanyService;
import com.revature.util.AuthUtil;

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
	public Company getCompany(@PathVariable int id, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.getCompanyById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Company createCompany(@RequestBody @Valid Company company, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.createCompany(company);
	}
	@PutMapping
	public Company updateCompany(@RequestBody @Valid Company company, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.updateCompany(company);
	}
	
	@DeleteMapping
	public Company deleteCompany(@RequestBody @Valid Company company, @RequestHeader("Authorization") String token) {
		Creds cred = AuthUtil.parseJWT(token);
		if(cred == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		return companyService.deleteCompany(company);
	}
}
