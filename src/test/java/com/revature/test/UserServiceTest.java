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

import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Mock
	private UserRepository mockUserRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		User user = new User();
		
		when(mockUserRepository.getById(id))
			.thenReturn(Optional.of(user));
		
		User returnedUser = userService.getUser(id);
		
		assertSame("User returns optional with same User that repository provides", 
				user, returnedUser);
		
		verify(mockUserRepository).getById(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		
		when(mockUserRepository.getById(id))
			.thenReturn(Optional.empty());
		
		userService.getUser(id);
		
		fail("Exception should have been thrown due to empty optional");
	}
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");
		
		when(mockUserRepository.create(user))
			.thenReturn(user);
		
		User returnedUser = userService.createUser(user);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				user, returnedUser);
		
		verify(mockUserRepository).create(user);
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
		
		when(mockUserRepository.getVehiclesByUserId(id))
			.thenReturn(Optional.of(userVehicles));
		
		List<Vehicle> returnedVehicles = userService.getVehiclesByUserId(id);
		
		assertSame("Vehicle returns optional with same vehicle that repository provides", 
				userVehicles, returnedVehicles);
		
		verify(mockUserRepository).getVehiclesByUserId(id);
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");
		
		when(mockUserRepository.update(user))
			.thenReturn(user);
		
		User returnedUser = userService.updateUser(user);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				user, returnedUser);
		
		verify(mockUserRepository).update(user);
	}
	
	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setFirstName("Corey");
		user.setLastName("Sch");
		user.setEmail("coreysch@test.com");
		user.setPassword("testingpassword");
		
		when(mockUserRepository.delete(user))
			.thenReturn(user);
		
		User returnedUser = userService.deleteUser(user);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				user, returnedUser);
		
		verify(mockUserRepository).delete(user);
	}

}
