package com.revature.services;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Company;

import com.revature.repositories.CompanyRepository;

@Service
public class CompanyService {
	CompanyRepository companyRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		super();
		this.companyRepository = companyRepository;
	}

	@Transactional
	public Company createCompany(Company company) {
		return CompanyRepository.create(company);
	}


	public Company getCompanyById(int id) {
		Optional<Company> optionalGenre = 
				CompanyRepository.getById(id);
		return optionalGenre.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}
}