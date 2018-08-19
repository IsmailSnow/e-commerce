package com.book.api.bookstore.service;

import com.book.api.bookstore.entity.BillingAddress;
import com.book.api.bookstore.entity.Order;
import com.book.api.bookstore.entity.Payment;
import com.book.api.bookstore.entity.ShippingAddress;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.security.entity.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);

}
