package com.book.api.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.security.entity.UserShipping;
import com.book.api.security.entity.repository.UserShippingRepository;
import com.book.api.security.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserShippingRepository userShippingRepository;

	@Override
	public UserShipping findById(Long id) {
		return userShippingRepository.findById(id).orElseThrow(() -> new RuntimeException("User Shipping not found"));
	}

	@Override
	public void removeById(Long id) {
		userShippingRepository.deleteById(id);

	}

}
