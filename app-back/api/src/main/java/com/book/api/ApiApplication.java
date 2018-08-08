package com.book.api;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.book.api.config.SecurityUtility;
import com.book.api.security.entity.Role;
import com.book.api.security.entity.User;
import com.book.api.security.entity.UserRole;
import com.book.api.security.service.UserService;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("user");
		user.setPassword(SecurityUtility.passwordEncoder().encode("monkey"));
		user.setEmail("salmi.ismail8u@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
		userRoles.clear();

		User user1 = new User();
		user1.setUsername("admin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("monkey"));
		user1.setEmail("admin@gmail.com");
		Role role1 = new Role();
		role1.setRoleId(2);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1, role1));
		userService.createUser(user1, userRoles);
		userRoles.clear();

	}
}
