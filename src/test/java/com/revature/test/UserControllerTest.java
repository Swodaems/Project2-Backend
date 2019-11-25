package com.revature.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.internal.Integers;
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
import com.revature.controllers.UserController;
import com.revature.entities.Company;
import com.revature.entities.Insurance;
import com.revature.entities.Role;
import com.revature.entities.ServiceReport;
import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.models.Creds;
import com.revature.models.UserData;
import com.revature.models.VehicleData;
import com.revature.services.UserService;
import com.revature.util.AuthUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserService mockUserService;

	@Mock
	private AuthUtil mockAuthUtil;

	@InjectMocks
	private UserController userController;

	@Autowired
	ObjectMapper om;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();

		String jwt = "faketoken";

		Creds cred = new Creds();
		cred.setEmail("fake@email.com");
		cred.setId(1);
		cred.setRole("customer");

		when(mockAuthUtil.parseJWT(jwt)).thenReturn(cred);
	}

	String jwt = "faketoken";

	@Test
	public void testCreateUserHappyPath() throws JsonProcessingException, Exception {
		Vehicle vehicle = new Vehicle(1, null, 0, null, null, null, 0, null, null, null, null, null);
		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(vehicle);

		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testing");
		user.setVehicles(vehicles);

		List<Integer> i = new ArrayList<>();
		i.add(1);

		UserData returnedUser = new UserData();
		returnedUser.setFirstName(user.getFirstName());
		returnedUser.setLastName(user.getLastName());
		returnedUser.setEmail(user.getEmail());
		returnedUser.setVehicleIds(i);

		when(mockUserService.createUser(user)).thenReturn(user);

		this.mockMvc.perform(post("/user/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(user))).andDo(print())
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(om.writeValueAsString(returnedUser)));
	}

	@Test
	public void testGetUserHappyPath() throws JsonProcessingException, Exception {
		Creds cred = new Creds();
		cred.setId(1);

		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");

		when(mockUserService.getUser(cred.getId())).thenReturn(user);

		this.mockMvc.perform(get("/user/user/").header("Authorization", jwt)).andDo(print())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(user))).andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void testGetUserVehicles() throws JsonProcessingException, Exception {
		int id = 1;
		List<Integer> i = new ArrayList<>();
		i.add(1);
		VehicleData vehicleData = new VehicleData();
		vehicleData.setName("My Jeep");
		vehicleData.setModel("Cherokee");
		vehicleData.setYear(2001);
		vehicleData.setInsuranceId(1);
		vehicleData.setServiceReportIds(i);
		vehicleData.setUserId(1);

		VehicleData vehicleData2 = new VehicleData();
		vehicleData2.setName("My Jeep2");
		vehicleData2.setModel("Cherokee");
		vehicleData2.setYear(1989);
		vehicleData2.setInsuranceId(1);
		vehicleData2.setServiceReportIds(i);
		vehicleData2.setUserId(1);

		User user = new User();
		user.setId(1);

		List<ServiceReport> sr = new ArrayList<>();
		ServiceReport serv = new ServiceReport(1, null, null, id, null, null, null, null, null);
		sr.add(serv);

		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setModel("Cherokee");
		vehicle.setYear(2001);
		vehicle.setInsurance(new Insurance(1, "Geico", null));
		vehicle.setServiceReports(sr);
		vehicle.setUser(user);

		Vehicle vehicle2 = new Vehicle();
		vehicle2.setName("My Jeep2");
		vehicle2.setModel("Cherokee");
		vehicle2.setYear(1989);
		vehicle2.setInsurance(new Insurance(1, "Geico", null));
		vehicle2.setServiceReports(sr);
		vehicle2.setUser(user);

		List<Vehicle> vehicles = new ArrayList<>();
		List<VehicleData> vehiclesData = new ArrayList<>();

		vehicles.add(vehicle);
		vehicles.add(vehicle2);

		vehiclesData.add(vehicleData);
		vehiclesData.add(vehicleData2);

		// Stubbing getVehicle
		when(mockUserService.getVehiclesByUserId(id)).thenReturn(vehicles);

		this.mockMvc.perform(get("/user/" + id + "/vehicles").header("Authorization", jwt))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(vehiclesData)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void getUserVehiclesNotFound() throws Exception {
		int id = 1;

		// Stubbing the implementation of the getAuthorsById method
		when(mockUserService.getVehiclesByUserId(1)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		this.mockMvc.perform(get("/user/" + id + "/vehicles").header("Authorization", jwt))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");

		User returnedUser = new User();
		user.setFirstName(user.getFirstName());
		user.setLastName(user.getLastName());
		user.setEmail(user.getEmail());
		user.setPassword(user.getPassword());

		when(mockUserService.updateUser(user)).thenReturn(returnedUser);

		this.mockMvc
				.perform(put("/user/").header("Authorization", jwt).contentType(MediaType.APPLICATION_JSON)
						.content(om.writeValueAsString(user)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(returnedUser)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		Vehicle vehicle = new Vehicle(1, null, 0, null, null, null, 0, null, null, null, null, null);
		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(vehicle);
		
		List<Integer> i = new ArrayList<>();
		i.add(1);
		
		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");
		user.setVehicles(vehicles);
		
		User returnedUser = new User();
		user.setFirstName(user.getFirstName());
		user.setLastName(user.getLastName());
		user.setEmail(user.getEmail());
		user.setPassword(user.getPassword());
		user.setVehicles(vehicles);

		when(mockUserService.deleteUser(user)).thenReturn(returnedUser);
		
		UserData userData = new UserData();
		userData.setFirstName("Corey");
		userData.setLastName("Sch");
		userData.setEmail("coreysch@test.com");
		userData.setVehicleIds(i);

		this.mockMvc
				.perform(delete("/user/").header("Authorization", jwt)
						.contentType(MediaType.APPLICATION_JSON)
						.content(om.writeValueAsString(user)))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(userData)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void getByIdNotFound() throws Exception {
		// Stubbing the implementation of the getAuthorsById method
		when(mockUserService.getUser(1)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		this.mockMvc.perform(get("/user/user/").header("Authorization", jwt))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
