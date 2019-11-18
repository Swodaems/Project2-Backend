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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ServiceReportController;
import com.revature.entities.ServiceReport;
import com.revature.services.ServiceReportService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ServiceReportControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	ServiceReportService mockServiceReportService;
	
	@InjectMocks
	ServiceReportController serviceReportController;
	
	@Autowired
	ObjectMapper om;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(serviceReportController).build();
	}
	
	@Test
	public void testCreateServiceReportHappyPath() throws JsonProcessingException, Exception {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("Oil Change");
		serviceReport.setCost(50.0);

		ServiceReport returnedServiceReport = new ServiceReport();
		returnedServiceReport.setName(serviceReport.getName());
		returnedServiceReport.setCost(serviceReport.getCost());
		
		when(mockServiceReportService.createServiceReport(serviceReport))
			.thenReturn(returnedServiceReport);
		
		this.mockMvc.perform(post("/servicereports/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(serviceReport)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(om.writeValueAsString(returnedServiceReport)));
	}
	
	@Test
	public void testGetByIdHappyPath() throws JsonProcessingException, Exception {
		int id = 1;
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("New transmission");
		serviceReport.setCost(300.0);
		
		when(mockServiceReportService.getServiceReportById(id))
			.thenReturn(serviceReport);
		
		this.mockMvc.perform(get("/servicereports/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(serviceReport)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("Injected blinker fluid");
		serviceReport.setCost(420.69);
		
		when(mockServiceReportService.updateServiceReport(serviceReport))
			.thenReturn(serviceReport);
		
		this.mockMvc.perform(put("/servicereports/"))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(serviceReport)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("Injected blinker fluid");
		serviceReport.setCost(420.69);
		
		when(mockServiceReportService.updateServiceReport(serviceReport))
			.thenReturn(serviceReport);
		
		this.mockMvc.perform(delete("/servicereports/"))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(serviceReport)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
}
