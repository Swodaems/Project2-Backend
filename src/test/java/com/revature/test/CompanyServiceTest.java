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

import com.revature.entities.Company;
import com.revature.repositories.CompanyRepository;
import com.revature.services.CompanyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceTest {
	
	@Mock
	CompanyRepository mockCompanyRepository;
	
	@InjectMocks
	CompanyService CompanyService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		Company company = new Company();
		company.setName("StateFarm");
		
		when(mockCompanyRepository.getById(id))
			.thenReturn(Optional.of(company));
		
		Company returnedCompany = CompanyService.getCompanyById(id);
		
		assertSame("Company returns optional with same Company that repository provides", 
				company, returnedCompany);
		
		verify(mockCompanyRepository).getById(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		
		when(mockCompanyRepository.getById(id))
			.thenReturn(Optional.empty());
		
		CompanyService.getCompanyById(id);
		
		fail("Exception should have been thrown due to empty optional");
	}
	
	@Test
	public void testCreateCompany() {
		Company company = new Company();
		company.setName("StateFarm");
		
		when(mockCompanyRepository.create(company))
			.thenReturn(company);
		
		Company returnedCompany = CompanyService.createCompany(company);
		
		assertSame("Company returns same Company the repository provides", 
				company, returnedCompany);
		
		verify(mockCompanyRepository).create(company);
	}
	
	@Test
	public void testUpdateCompany() {
		Company company = new Company();
		company.setName("StateFarm");
		
		when(mockCompanyRepository.update(company))
			.thenReturn(company);
		
		Company returnedCompany = CompanyService.updateCompany(company);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				company, returnedCompany);
		
		verify(mockCompanyRepository).update(company);
	}
	
	@Test
	public void testDeleteCompany() {
		Company company = new Company();
		company.setName("StateFarm");
		
		when(mockCompanyRepository.delete(company))
			.thenReturn(company);
		
		Company returnedCompany = CompanyService.deleteCompany(company);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				company, returnedCompany);
		
		verify(mockCompanyRepository).delete(company);
	}

}
