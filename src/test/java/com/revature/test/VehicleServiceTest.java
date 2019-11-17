package com.revature.test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import com.revature.entities.Vehicle;
import com.revature.repositories.VehicleRepository;
import com.revature.services.VehicleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {

	@Mock
	private VehicleRepository mockVehicleRepository;
	
	@InjectMocks
	private VehicleService vehicleService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		Vehicle vehicle = new Vehicle();
		
		when(mockVehicleRepository.getById(id))
			.thenReturn(Optional.of(vehicle));
		
		Vehicle returnedVehicle = vehicleService.getVehicle(id);
		
		assertSame("Vehicle returns optional with same vehicle that repository provides", 
				vehicle, returnedVehicle);
		
		verify(mockVehicleRepository).getById(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		
		when(mockVehicleRepository.getById(id))
			.thenReturn(Optional.empty());
		
		vehicleService.getVehicle(id);
		
		fail("Exception should have been thrown due to empty optional");
	}
	
	@Test
	public void testCreateVehicle() {
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setYear(2001);
		vehicle.setModel("Cherokee");
		vehicle.setColor("Green");
		
		when(mockVehicleRepository.create(vehicle))
			.thenReturn(vehicle);
		
		Vehicle returnedVehicle = vehicleService.createVehicle(vehicle);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				vehicle, returnedVehicle);
		
		verify(mockVehicleRepository).create(vehicle);
	}
	
	@Test
	public void testGetUserVehiclesHappyPath() {
		int id = 5;
		List<Vehicle> userVehicles = new ArrayList<>();
		Vehicle vehicle = new Vehicle();
		vehicle.setName("My Jeep");
		vehicle.setYear(2001);
		vehicle.setModel("Cherokee");
		vehicle.setColor("Green");
		
		Vehicle vehicle2 = new Vehicle();
		vehicle.setName("My Ford");
		vehicle.setYear(2001);
		vehicle.setModel("Windstar");
		vehicle.setColor("White");
		
		userVehicles.add(vehicle);
		userVehicles.add(vehicle2);
		
		when(mockVehicleRepository.getByUserId(id))
			.thenReturn(Optional.of(userVehicles));
		
		List<Vehicle> returnedVehicles = vehicleService.getUserVehicles(id);
		
		assertSame("Vehicle returns optional with same vehicle that repository provides", 
				userVehicles, returnedVehicles);
		
		verify(mockVehicleRepository).getByUserId(id);
	}
	
}
