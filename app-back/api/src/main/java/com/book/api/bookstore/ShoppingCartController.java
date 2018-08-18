package com.book.api.bookstore;

import java.awt.PageAttributes.MediaType;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.entity.CartItem;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.bookstore.service.BookService;
import com.book.api.bookstore.service.CartItemService;
import com.book.api.bookstore.service.ShoppingCartService;
import com.book.api.security.entity.User;
import com.book.api.security.service.UserService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping("/add")
	public ResponseEntity<String> addItem(@RequestBody HashMap<String, String> mapper, Principal principal) {
		String bookId = (String) mapper.get("bookId");
		String qty = (String) mapper.get("qty");

		User user = userService.findByUsername(principal.getName());
		Book book = bookService.findOne(Long.parseLong(bookId));

		if (Integer.parseInt(qty) > book.getInStockNumber()) {
			return new ResponseEntity("Not Enough Stock!", HttpStatus.BAD_REQUEST);
		}

		CartItem cartItem = cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));

		return new ResponseEntity("Book Added Successfully!", HttpStatus.OK);
	}

	@RequestMapping("/getCartItemList")
	public List<CartItem> getCartItemList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		//shoppingCartService.updateShoppingCart(shoppingCart);

		return cartItemList;
	}

	@RequestMapping(value="/getShoppingCart")
	public ShoppingCart getShoppingCart(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		return shoppingCart;
	}

	@RequestMapping(value="/removeItem" ,method=RequestMethod.POST)
	public ResponseEntity removeItem(@RequestBody String id) {
		Optional<CartItem> cartItem = cartItemService.findById(Long.parseLong(id));
		if (cartItem.isPresent()) {
			cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)).get());
			return new ResponseEntity("Cart Item Removed Successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity("Cart Item had not been founded!", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping("/updateCartItem")
	public ResponseEntity updateCartItem(@RequestBody HashMap<String, String> mapper) {
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");

		Optional<CartItem> cartItem = cartItemService.findById(Long.parseLong(cartItemId));
		if (cartItem.isPresent()) {
			cartItem.get().setQty(Integer.parseInt(qty));
			cartItemService.updateCartItem(cartItem.get());
			return new ResponseEntity("Cart Item Updated Successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity("Cart Item is not updated", HttpStatus.BAD_REQUEST);
		}
	}

}
