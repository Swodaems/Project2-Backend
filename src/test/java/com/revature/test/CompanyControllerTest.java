package com.revature.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.CompanyController;
import com.revature.entities.Company;
import com.revature.services.CompanyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	CompanyService mockCompanyService;
	
	@InjectMocks
	CompanyController companyController;
	
	@Autowired
	ObjectMapper om;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
	}
	
	@Test
	public void testCreateCompanyHappyPath() throws JsonProcessingException, Exception {
		Company company = new Company();
		company.setName("Auto Motors");
		company.setAddress("12 Car Street");
		company.setCity("Boston");
		company.setState("MA");

		Company returnedCompany = new Company();
		returnedCompany.setName(company.getName());
		returnedCompany.setAddress(company.getAddress());
		returnedCompany.setCity(company.getCity());
		returnedCompany.setState(company.getState());
		
		when(mockCompanyService.createCompany(company))
			.thenReturn(returnedCompany);
		
		this.mockMvc.perform(post("/company/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(company)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(om.writeValueAsString(returnedCompany)));
	}
	
	@Test
	public void testGetCompanyHappyPath() throws JsonProcessingException, Exception {
		int id = 1;
		Company company = new Company();
		company.setName("Auto Motors");
		company.setAddress("12 Car Street");
		company.setCity("Boston");
		company.setState("MA");
		
		when(mockCompanyService.getCompanyById(id))
			.thenReturn(company);
		
		this.mockMvc.perform(get("/company/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(company)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		Company company = new Company();
		company.setName("Auto Motors");
		company.setAddress("12 Car Street");
		company.setCity("Boston");
		company.setState("MA");
		
		Company returnedCompany = new Company();
		returnedCompany.setName(company.getName());
		returnedCompany.setAddress(company.getAddress());
		returnedCompany.setCity(company.getCity());
		returnedCompany.setState(company.getState());
		
		when(mockCompanyService.updateCompany(company))
			.thenReturn(returnedCompany);
		
		this.mockMvc.perform(put("/company/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(company)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(returnedCompany)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		Company company = new Company();
		company.setName("Auto Motors");
		company.setAddress("12 Car Street");
		company.setCity("Boston");
		company.setState("MA");
		
		when(mockCompanyService.deleteCompany(company))
			.thenReturn(company);
		
		this.mockMvc.perform(delete("/company/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(company)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(company)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void getByIdNotFound() throws Exception {
		int id = 1;
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockCompanyService.getCompanyById(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/company/" + id))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
