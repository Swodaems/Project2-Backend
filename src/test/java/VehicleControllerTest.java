import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.VehicleController;
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
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
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
	public void getByUserId() throws JsonProcessingException, Exception {
		int id = 1;
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setModel("Cherokee");
		vehicle.setYear(2001);
		
		Vehicle vehicle2 = new Vehicle();
		vehicle.setName("My Jeep2");
		vehicle.setModel("Cherokee");
		vehicle.setYear(1989);
		
		List<Vehicle> vehicles = null;
		
		vehicles.add(vehicle);
		vehicles.add(vehicle2);
		
		// Stubbing getVehicle
		when(mockVehicleService.getUserVehicles(id)).thenReturn(vehicles);

		this.mockMvc.perform(get("/vehicles/user/" + id))
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(content().json(om.writeValueAsString(vehicle)))
				.andExpect(status().is(HttpStatus.OK.value()));
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

}
