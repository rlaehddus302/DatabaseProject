package com.example.databaseProject.Configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.databaseProject.Information.Customer;
import com.example.databaseProject.Information.CustomerRepository;

@Service
public class AutoTimetableDetails implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer customer = customerRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(
				username + "is not exist"));
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		User user = new User(username, "{noop}"+customer.getPassword(), authorities);
		return user;
	}
	
}
