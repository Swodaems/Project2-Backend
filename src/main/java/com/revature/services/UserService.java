package com.revature.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.ServiceReport;
import com.revature.entities.User;
import com.revature.entities.Vehicle;
import com.revature.models.Credentials;
import com.revature.repositories.UserRepository;
import com.revature.util.AuthUtil;


@Service
public class UserService {
	
	UserRepository userRepository;
	AuthUtil authUtil;

	@Autowired
	public UserService(UserRepository userRepository, AuthUtil authUtil) {
		super();
		this.userRepository = userRepository;
		this.authUtil = authUtil;
	}

	@Transactional
	public User createUser(User user) {
		String generatedPassword=null;
		try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte salt[] =getSalt();
            //byte salt[]=DatatypeConverter.parseHexBinary(scannerUtil.getStringInput());
            //for(int i=0;i<salt.length;i++) System.out.print(salt[i]+' ');
            StringBuilder sb2 = new StringBuilder();
            for(int i=0;i<salt.length;i++) sb2.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
            //System.out.println(sb2);
            user.setSalt(sb2.toString());
            md.update(salt);
            //Get the hash's bytes 
            byte[] bytes = md.digest(user.getPassword().getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
    
        }
		user.setPassword(generatedPassword);
		return userRepository.create(user);
	}

	public List<Vehicle> getVehiclesByUserId(int id) {
		Optional<List<Vehicle>> optionalVehicles = 
					userRepository.getVehiclesByUserId(id);
		return optionalVehicles.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}
	public List<ServiceReport> getServiceReportsByUserId(int id) {
		Optional<List<ServiceReport>> optionalServiceReports = 
					userRepository.getServiceReportsByUserId(id);
		return optionalServiceReports.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}
	public User getUser(int id) {
		Optional<User> optionalUser = 
				userRepository.getById(id);
		return optionalUser.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public String login(Credentials credentials) {
		Optional<User> optionalUser= 
				userRepository.getByEmail(credentials.getEmail());
		if(optionalUser == null) throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
		User user = authenticate(credentials.getPassword(), optionalUser.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.FORBIDDEN)));
		
		return authUtil.generateToken(user);
	}
	
	public User authenticate(String password, User user) {
		String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Add password bytes to digest
            byte salt[]=DatatypeConverter.parseHexBinary(user.getSalt());
            md.update(salt);
            //Get the hash's bytes 
            byte[] bytes = md.digest(password.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
    
        }
        if( generatedPassword.equals(user.getPassword())) return user;
        else throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
	}
	private byte[] getSalt() throws NoSuchAlgorithmException
	{
	    //Always use a SecureRandom generator
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    //Create array for salt
	    byte[] salt = new byte[16];
	    //Get a random salt
	    sr.nextBytes(salt);
	    //return salt
	    return salt;
	}

	@Transactional
	public User updateUser(@Valid User user) {
		return userRepository.update(user);
	}

	@Transactional
	public User deleteUser(@Valid User user) {
		return userRepository.delete(user);
	}

}
