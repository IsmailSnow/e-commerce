package com.book.api.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.entity.CartItem;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.security.entity.User;

public interface CartItemService {

	CartItem addBookToCartItem(Book book, User user, int qty);

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	// List<CartItem> findByOrder(Order order)

	CartItem updateCartItem(CartItem cartItem);

	void removeCartItem(CartItem cartItem);

	Optional<CartItem> findById(Long id);

	CartItem save(CartItem cartItem);
}
