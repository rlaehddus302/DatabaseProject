package com.example.databaseProject.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.databaseProject.Information.Customer;
import com.example.databaseProject.Information.CustomerRepository;


public class AutoTimetableDetails implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer customer = customerRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(
				username + "is not exist"));
		User user = new User(username, customer.getPassword(), null);
		return user;
	}
	
}
