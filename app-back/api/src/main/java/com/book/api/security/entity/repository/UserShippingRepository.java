package com.book.api.security.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.security.entity.UserShipping;

@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {

}
