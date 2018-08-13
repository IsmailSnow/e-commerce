package com.book.api.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.security.entity.UserPayment;
import com.book.api.security.entity.repository.UserPaymentRepository;
import com.book.api.security.service.UserPaymentService;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

	@Autowired
	private UserPaymentRepository userPaymentRepository;

	@Override
	public UserPayment findById(Long id) {
		Optional<UserPayment> userp = userPaymentRepository.findById(id);
		if (userp.isPresent()) {
			return userp.get();
		} else {
			throw new RuntimeException("User payment not found!");
		}
	}

	@Override
	public void removeById(Long id) {
		userPaymentRepository.deleteById(id);

	}

}
