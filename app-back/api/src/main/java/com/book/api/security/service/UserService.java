package com.book.api.security.service;

import java.util.Set;

import com.book.api.security.entity.User;
import com.book.api.security.entity.UserBilling;
import com.book.api.security.entity.UserPayment;
import com.book.api.security.entity.UserRole;
import com.book.api.security.entity.UserShipping;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String email);

	User saveUser(User user);

	User findById(Long id);

	void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);

	void createNewPasswordForUser(User user);

	void createUserToSave(String name, String email, String password);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(Long userShippingId, User user);

}
