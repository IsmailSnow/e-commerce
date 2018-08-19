package com.book.api.bookstore.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.bookstore.entity.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

}
