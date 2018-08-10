package com.book.api.security.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.security.entity.User;
import com.book.api.security.entity.UserRole;
import com.book.api.security.entity.repository.RoleRepository;
import com.book.api.security.entity.repository.UserRepository;
import com.book.api.security.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {

		User retrieverdUser = userRepository.findByEmail(user.getEmail());
		if (retrieverdUser != null) {
			logger.info("User with username {} already exist , no PostConstruct is applied", user.getUsername());
		} else {
			for (UserRole role : userRoles) {
				roleRepository.save(role.getRole());
			}
			// user.getUserRoles().addAll(userRoles);
			retrieverdUser = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
	}

}
