package com.book.api.bookstore.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.bookstore.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long > {

}
