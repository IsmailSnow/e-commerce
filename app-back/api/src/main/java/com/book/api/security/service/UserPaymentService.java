package com.book.api.security.service;

import com.book.api.security.entity.UserPayment;

public interface UserPaymentService {
	
	UserPayment findById(Long id);
	
	void removeById(Long id);
	

}
