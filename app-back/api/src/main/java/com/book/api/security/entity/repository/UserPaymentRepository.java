package com.book.api.security.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.security.entity.UserPayment;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{

}
