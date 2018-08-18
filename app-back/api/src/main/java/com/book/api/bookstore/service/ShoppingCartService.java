package com.book.api.bookstore.service;

import com.book.api.bookstore.entity.ShoppingCart;

public interface ShoppingCartService {
	

	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	
	void clearShoppingCart(ShoppingCart shoppingCart);

}
