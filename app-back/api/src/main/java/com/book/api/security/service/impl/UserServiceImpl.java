package com.book.api.security.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.config.SecurityUtility;
import com.book.api.security.entity.Role;
import com.book.api.security.entity.User;
import com.book.api.security.entity.UserBilling;
import com.book.api.security.entity.UserPayment;
import com.book.api.security.entity.UserRole;
import com.book.api.security.entity.UserShipping;
import com.book.api.security.entity.repository.RoleRepository;
import com.book.api.security.entity.repository.UserBillingRepository;
import com.book.api.security.entity.repository.UserPaymentRepository;
import com.book.api.security.entity.repository.UserRepository;
import com.book.api.security.entity.repository.UserShippingRepository;
import com.book.api.security.service.UserService;
import com.book.api.utility.MailContructor;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserBillingRepository userBillingRepository;

	@Autowired
	private UserPaymentRepository userPaymentRepository;

	@Autowired
	private MailContructor mailContructor;

	@Autowired
	private UserShippingRepository userShippingRepository;

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
			user.setUserPaymentList(new ArrayList<UserPayment>());
			retrieverdUser = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
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
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user) {
		saveUser(user);
		userBillingRepository.save(userBilling);
		userPaymentRepository.save(userPayment);
	}

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		saveUser(user);
	}

	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = userPaymentRepository.findAll();

		for (UserPayment userPayment : userPaymentList) {
			if (userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}

	@Override
	public void createNewPasswordForUser(User user) {
		String password = SecurityUtility.randomPassword();
		String encyptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encyptedPassword);
		saveUser(user);
		mailContructor.constructNewUserEmailAndSend(password, user);

	}

	@Override
	public void createUserToSave(String email, String username) {
		try {
			logger.info("starting creation of a new user");
			User user = new User();
			user.setEmail(email);
			user.setUsername(username);

			String password = SecurityUtility.randomPassword();
			String encryptdedPassword = SecurityUtility.passwordEncoder().encode(password);
			user.setPassword(encryptdedPassword);
			Role role = new Role();
			role.setRoleId(1);
			role.setName("ROLE_USER");
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, role));
			mailContructor.constructNewUserEmailAndSend(encryptdedPassword, user);
			createUser(user, userRoles);
			logger.info("Operation Sucess");
		} catch (Exception e) {
			logger.info("Operation Failed !");
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		saveUser(user);
	}

	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippingList = userShippingRepository.findAll();
		for (UserShipping userShipping : userShippingList) {
			if (userShipping.getId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}

	}
}
