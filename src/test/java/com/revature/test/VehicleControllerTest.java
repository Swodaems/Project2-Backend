package com.revature.test;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.revature.controllers.VehicleController;
import com.revature.entities.ServiceReport;
import com.revature.entities.Vehicle;
import com.revature.services.VehicleService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class VehicleControllerTest {

	private MockMvc mockMvc;

	@Mock
	VehicleService mockVehicleService;

	@InjectMocks
	private VehicleController vehicleController;

	@Autowired
	ObjectMapper om;

	// Initializing mockito annotations (if they're not parsed)
	// Initializing mock MVC controller
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleController)
				.setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();
	}

	@Test
	public void getByIdHappyPath() throws JsonProcessingException, Exception {
		int id = 1;
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setModel("Cherokee");
		vehicle.setYear(2001);

		// Stubbing getVehicle
		when(mockVehicleService.getVehicle(id)).thenReturn(vehicle);

		this.mockMvc.perform(get("/vehicles/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(vehicle)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testGetUserVehicles() throws JsonProcessingException, Exception {
		int id = 1;
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setModel("Cherokee");
		vehicle.setYear(2001);
		
		Vehicle vehicle2 = new Vehicle();
		vehicle.setName("My Jeep2");
		vehicle.setModel("Cherokee");
		vehicle.setYear(1989);
		
		List<Vehicle> vehicles = new ArrayList<>();
		
		vehicles.add(vehicle);
		vehicles.add(vehicle2);
		
		// Stubbing getVehicle
		when(mockVehicleService.getUserVehicles(id)).thenReturn(vehicles);

		this.mockMvc.perform(get("/vehicles/user/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(vehicles)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void getByUserIdNotFound() throws Exception {
		int id = 1;
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockVehicleService.getUserVehicles(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/vehicles/user/" + id))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void testGetVehicleServiceReports() throws JsonProcessingException, Exception {
		int id = 1;
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("Injected blinker fluid");
		serviceReport.setCost(420.69);
		
		ServiceReport serviceReport2 = new ServiceReport();
		serviceReport.setName("Replaced grill with googly eyes");
		serviceReport.setCost(3.49);
		
		List<ServiceReport> serviceReports = new ArrayList<>();
		
		serviceReports.add(serviceReport);
		serviceReports.add(serviceReport2);
		
		// Stubbing getVehicle
		when(mockVehicleService.getVehicleServiceReports(id)).thenReturn(serviceReports);

		this.mockMvc.perform(get("/vehicles/" + id + "/servicereports")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(serviceReports)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(serviceReports)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void getVehicleServiceReportsNotFound() throws Exception {
		int id = 1;
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockVehicleService.getVehicleServiceReports(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/vehicles/" + id + "/servicereports"))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void createVehicleTest() throws JsonProcessingException, Exception {
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setYear(2001);
		vehicle.setModel("Cherokee");
		vehicle.setColor("Green");

		Vehicle returnedVehicle = new Vehicle();
		returnedVehicle.setName(vehicle.getName());
		returnedVehicle.setYear(vehicle.getYear());
		returnedVehicle.setModel(vehicle.getModel());
		returnedVehicle.setColor(vehicle.getColor());
		
		when(mockVehicleService.createVehicle(vehicle))
			.thenReturn(returnedVehicle);
		
		this.mockMvc.perform(post("/vehicles/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(vehicle)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(om.writeValueAsString(returnedVehicle)));
	}
	
	@Test
	public void getByIdNotFound() throws Exception {
		int id = 1;
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockVehicleService.getVehicle(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/vehicles/" + id))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
