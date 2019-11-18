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

import com.revature.entities.ServiceReport;
import com.revature.repositories.ServiceReportRepository;
import com.revature.services.ServiceReportService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceReportServiceTest {
	
	@Mock
	private ServiceReportRepository mockServiceReportRepository;
	
	@InjectMocks
	private ServiceReportService serviceReportService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		ServiceReport serviceReport = new ServiceReport();
		
		when(mockServiceReportRepository.get(id))
			.thenReturn(Optional.of(serviceReport));
		
		ServiceReport returnedServiceReport = serviceReportService.getServiceReportById(id);
		
		assertSame("ServiceReport returns optional with same ServiceReport that repository provides", 
				serviceReport, returnedServiceReport);
		
		verify(mockServiceReportRepository).get(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		
		when(mockServiceReportRepository.get(id))
			.thenReturn(Optional.empty());
		
		serviceReportService.getServiceReportById(id);
		
		fail("Exception should have been thrown due to empty optional");
	}
	
	@Test
	public void testCreateServiceReport() {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("buffed out rust damage on the hood");
		
		when(mockServiceReportRepository.create(serviceReport))
			.thenReturn(serviceReport);
		
		ServiceReport returnedServiceReport = 
				serviceReportService.createServiceReport(serviceReport);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				serviceReport, returnedServiceReport);
		
		verify(mockServiceReportRepository).create(serviceReport);
	}
	
	@Test
	public void testDeleteServiceReport() {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("buffed out rust damage on the hood");
		
		when(mockServiceReportRepository.delete(serviceReport))
			.thenReturn(serviceReport);
		
		ServiceReport returnedServiceReport = 
				serviceReportService.deleteServiceReport(serviceReport);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				serviceReport, returnedServiceReport);
		
		verify(mockServiceReportRepository).delete(serviceReport);
	}
	
	@Test
	public void testUpdateServiceReport() {
		ServiceReport serviceReport = new ServiceReport();
		serviceReport.setName("buffed out rust damage on the hood");
		
		when(mockServiceReportRepository.update(serviceReport))
			.thenReturn(serviceReport);
		
		ServiceReport returnedServiceReport = 
				serviceReportService.updateServiceReport(serviceReport);
		
		assertSame("Vehicle returns same vehicle the repository provides", 
				serviceReport, returnedServiceReport);
		
		verify(mockServiceReportRepository).update(serviceReport);
	}

}
