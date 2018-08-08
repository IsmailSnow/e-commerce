package com.book.api.security.service;

import java.util.Set;

import com.book.api.security.entity.User;
import com.book.api.security.entity.UserRole;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles);

}
