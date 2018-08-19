package com.book.api.bookstore.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.bookstore.entity.BillingAddress;


@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {

}
