package com.revature.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Insurance;
import com.revature.repositories.InsuranceRepository;
import com.revature.services.InsuranceService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsuranceServiceTest {
	
	@Mock
	InsuranceRepository mockInsuranceRepository;
	
	@InjectMocks
	InsuranceService insuranceService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceRepository.getById(id))
			.thenReturn(Optional.of(insurance));
		
		Insurance returnedInsurance = insuranceService.getInsurance(id);
		
		assertSame("User returns optional with same User that repository provides", 
				insurance, returnedInsurance);
		
		verify(mockInsuranceRepository).getById(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		
		when(mockInsuranceRepository.getById(id))
			.thenReturn(Optional.empty());
		
		insuranceService.getInsurance(id);
		
		fail("Exception should have been thrown due to empty optional");
	}
	
	@Test
	public void testCreateInsurance() {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceRepository.create(insurance))
			.thenReturn(insurance);
		
		Insurance returnedInsurance = insuranceService.createInsurance(insurance);
		
		assertSame("Insurance returns same Insurance the repository provides", 
				insurance, returnedInsurance);
		
		verify(mockInsuranceRepository).create(insurance);
	}
	
	@Test
	public void testUpdateInsurance() {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceRepository.update(insurance))
			.thenReturn(insurance);
		
		Insurance returnedInsurance = insuranceService.updateInsurance(insurance);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				insurance, returnedInsurance);
		
		verify(mockInsuranceRepository).update(insurance);
	}
	
	@Test
	public void testDeleteInsurance() {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceRepository.delete(insurance))
			.thenReturn(insurance);
		
		Insurance returnedInsurance = insuranceService.deleteInsurance(insurance);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				insurance, returnedInsurance);
		
		verify(mockInsuranceRepository).delete(insurance);
	}

}
