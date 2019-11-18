package com.revature.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Insurance;
import com.revature.repositories.InsuranceRepository;

@Service
public class InsuranceService {
	
	InsuranceRepository insuranceRepository;

	@Autowired
	public InsuranceService(InsuranceRepository insuranceRepository) {
		super();
		this.insuranceRepository = insuranceRepository;
	}

	@Transactional
	public Insurance createInsurance(@Valid Insurance insurance) {
		return insuranceRepository.create(insurance);
	}

	public Insurance getInsurance(int id) {
		Optional<Insurance> optionalInsurance = 
				insuranceRepository.getById(id);
		return optionalInsurance.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public Insurance updateInsurance(@Valid Insurance insurance) {
		return insuranceRepository.update(insurance);
	}

	@Transactional
	public Insurance deleteInsurance(@Valid Insurance insurance) {
		return insuranceRepository.delete(insurance);
	}

}
