package com.book.api.security.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.security.entity.Role;
import com.book.api.security.entity.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
