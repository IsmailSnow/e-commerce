package com.book.api.bookstore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.entity.BookToCartItem;
import com.book.api.bookstore.entity.CartItem;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.bookstore.entity.repository.BookRepository;
import com.book.api.bookstore.entity.repository.BookToCartItemRepository;
import com.book.api.bookstore.entity.repository.CartItemRepository;
import com.book.api.bookstore.entity.repository.ShoppingCartRepository;
import com.book.api.bookstore.service.CartItemService;
import com.book.api.bookstore.service.ShoppingCartService;
import com.book.api.security.entity.User;
import com.book.api.security.entity.repository.UserRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public CartItem addBookToCartItem(Book book, User user, int qty) {

		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

		for (CartItem cartItem : cartItemList) {
			if (book.getId() == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubTotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				shoppingCartService.updateShoppingCart(user.getShoppingCart());
				book.setInStockNumber(Math.subtractExact(book.getInStockNumber(), qty));
				bookRepository.save(book);
				return cartItem;
			}
		}

		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		cartItem.setQty(qty);
		cartItem.setSubTotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));

		cartItem = cartItemRepository.save(cartItem);

		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		bookToCartItemRepository.save(bookToCartItem);
		shoppingCartService.updateShoppingCart(user.getShoppingCart());
		book.setInStockNumber(Math.subtractExact(book.getInStockNumber(), qty));
		bookRepository.save(book);
		return cartItem;

	}

	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice())
				.multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubTotal(bigDecimal);
		cartItemRepository.save(cartItem);

		return cartItem;
	}

	@Override
	public void removeCartItem(CartItem cartItem) {
		ShoppingCart shoppingcart = cartItem.getShoppingCart();
		cartItem.getBook().setInStockNumber(Math.addExact(cartItem.getQty(), cartItem.getBook().getInStockNumber()));
		bookRepository.save(cartItem.getBook());
		bookToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
		shoppingCartService.updateShoppingCart(shoppingcart);

	}

	@Override
	public Optional<CartItem> findById(Long id) {
		return cartItemRepository.findById(id);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

}
