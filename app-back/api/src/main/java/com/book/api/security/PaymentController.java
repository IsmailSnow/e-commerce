package com.book.api.security;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.security.entity.User;
import com.book.api.security.entity.UserBilling;
import com.book.api.security.entity.UserPayment;
import com.book.api.security.service.UserPaymentService;
import com.book.api.security.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserPaymentService userPaymentService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> addNewCreditCardPost(@RequestBody UserPayment userPayment, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		UserBilling userBilling = userPayment.getUserBilling();
		userService.updateUserBilling(userBilling, userPayment, user);
		return new ResponseEntity<String>("Payment Added(Updated) Successfully!", HttpStatus.OK);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ResponseEntity<String> removePayment(@RequestBody String id, Principal principal) {
		userPaymentService.removeById(Long.valueOf(id));
		return new ResponseEntity<String>("Payment Removed Successfully!", HttpStatus.OK);
	}

	@RequestMapping(value = "/setDefault", method = RequestMethod.POST)
	public ResponseEntity<String> setDefaultPayment(@RequestBody String id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(Long.parseLong(id), user);
		return new ResponseEntity<String>("Payment Removed Successfully!", HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserPaymentList")
	public List<UserPayment> getUserPaymentList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		return user.getUserPaymentList();

	}
}
