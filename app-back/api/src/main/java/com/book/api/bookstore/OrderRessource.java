package com.book.api.bookstore;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.bookstore.entity.Order;
import com.book.api.security.entity.User;
import com.book.api.security.service.UserService;

@RestController
@RequestMapping("/order")
public class OrderRessource {

	@Autowired
	private UserService userService;

	@RequestMapping("/getOrderList")
	public List<Order> getOrderList(Principal principal) {

		User user = userService.findByUsername(principal.getName());
		return user.getOrderList();
	}

}
