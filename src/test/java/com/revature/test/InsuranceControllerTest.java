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
import com.revature.advisor.ExceptionHandlerAdvisor;
import com.revature.controllers.InsuranceController;
import com.revature.entities.Insurance;
import com.revature.services.InsuranceService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InsuranceControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	InsuranceService mockInsuranceService;
	
	@InjectMocks
	InsuranceController insuranceController;
	
	@Autowired
	ObjectMapper om;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(insuranceController)
				.setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();
	}
	
	@Test
	public void testCreateInsuranceHappyPath() throws JsonProcessingException, Exception {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");

		Insurance returnedInsurance = new Insurance();
		returnedInsurance.setCompanyName(insurance.getCompanyName());
		
		when(mockInsuranceService.createInsurance(insurance))
			.thenReturn(returnedInsurance);
		
		this.mockMvc.perform(post("/insurance/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(insurance)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(om.writeValueAsString(returnedInsurance)));
	}
	
	@Test
	public void testGetInsuranceHappyPath() throws JsonProcessingException, Exception {
		int id = 1;
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceService.getInsurance(id))
			.thenReturn(insurance);
		
		this.mockMvc.perform(get("/insurance/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(insurance)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		Insurance returnedInsurance = new Insurance();
		insurance.setCompanyName(insurance.getCompanyName());
		
		when(mockInsuranceService.updateInsurance(insurance))
			.thenReturn(returnedInsurance);
		
		this.mockMvc.perform(put("/insurance/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(insurance)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(returnedInsurance)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		Insurance insurance = new Insurance();
		insurance.setCompanyName("StateFarm");
		
		when(mockInsuranceService.deleteInsurance(insurance))
			.thenReturn(insurance);
		
		this.mockMvc.perform(delete("/insurance/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(insurance)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(insurance)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void getByIdNotFound() throws Exception {
		int id = 1;
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockInsuranceService.getInsurance(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/insurance/" + id))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
