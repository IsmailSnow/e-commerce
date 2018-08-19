package com.book.api.bookstore;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.bookstore.entity.BillingAddress;
import com.book.api.bookstore.entity.CartItem;
import com.book.api.bookstore.entity.Order;
import com.book.api.bookstore.entity.Payment;
import com.book.api.bookstore.entity.ShippingAddress;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.bookstore.service.CartItemService;
import com.book.api.bookstore.service.OrderService;
import com.book.api.bookstore.service.ShoppingCartService;
import com.book.api.security.entity.User;
import com.book.api.security.service.UserService;
import com.book.api.utility.MailContructor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private MailContructor mailConstructor;
	

	@RequestMapping(value = "/checkout", method=RequestMethod.POST)
	public Order checkoutPost(
				@RequestBody HashMap<String, Object> mapper,
				Principal principal
			){
		
		Order result = new Order();
		ObjectMapper om = new ObjectMapper();
		
		ShippingAddress shippingAddress = om.convertValue(mapper.get("shippingAddress"), ShippingAddress.class);
		BillingAddress billingAddress = om.convertValue(mapper.get("billingAddress"), BillingAddress.class);
		Payment payment = om.convertValue(mapper.get("payment"), Payment.class);
		String shippingMethod = (String) mapper.get("shippingMethod");
		
		ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		User user = userService.findByUsername(principal.getName());
		Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);
		
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.FRANCE));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		if (shippingMethod.equals("groundShipping")) {
			estimatedDeliveryDate=today.plusDays(5);
		} else {
			estimatedDeliveryDate=today.plusDays(3);
		}
		
		result = order;
		
		return result;
		
	}


}
