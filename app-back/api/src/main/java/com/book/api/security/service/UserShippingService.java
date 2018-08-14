package com.book.api.security.service;

import com.book.api.security.entity.UserShipping;

public interface UserShippingService {
	UserShipping findById(Long id);

	void removeById(Long id);
}
