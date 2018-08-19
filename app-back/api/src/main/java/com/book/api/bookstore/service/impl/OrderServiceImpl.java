package com.book.api.bookstore.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.api.bookstore.entity.BillingAddress;
import com.book.api.bookstore.entity.Book;
import com.book.api.bookstore.entity.CartItem;
import com.book.api.bookstore.entity.Order;
import com.book.api.bookstore.entity.Payment;
import com.book.api.bookstore.entity.ShippingAddress;
import com.book.api.bookstore.entity.ShoppingCart;
import com.book.api.bookstore.entity.repository.BillingAddressRepository;
import com.book.api.bookstore.entity.repository.OrderRepository;
import com.book.api.bookstore.entity.repository.PaymentRepository;
import com.book.api.bookstore.entity.repository.ShippingAddressRepository;
import com.book.api.bookstore.service.BookService;
import com.book.api.bookstore.service.CartItemService;
import com.book.api.bookstore.service.OrderService;
import com.book.api.security.entity.User;
import com.book.api.utility.MailContructor;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillingAddressRepository billingAddressRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private BookService bookService;

	@Autowired
	private MailContructor mailConstructor;

	public Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			Book book = cartItem.getBook();
			cartItem.setOrder(order);
			book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
		}

		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);

		return order;
	}

	public Optional<Order> findOne(Long id) {
		return orderRepository.findById(id);
	}

}
