package com.book.api.security;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.security.entity.User;
import com.book.api.security.entity.UserShipping;
import com.book.api.security.service.UserService;
import com.book.api.security.service.UserShippingService;
import com.mysql.fabric.Response;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserShippingService userShippingService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> addNewUserShippingPost(@RequestBody UserShipping userShipping, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);
		return new ResponseEntity<String>("Shipping Added(Updated) Successfully!", HttpStatus.OK);

	}

	@RequestMapping("/getUserShippingList")
	public List<UserShipping> getUserShippingList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		return user.getUserShippingList();

	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ResponseEntity<String> removeUserShipping(@RequestBody String id) {
		userShippingService.removeById(Long.parseLong(id));
		return new ResponseEntity<String>("Shipping Removed Successfully!", HttpStatus.OK);

	}

	@RequestMapping(value = "setDefault", method = RequestMethod.POST)
	public ResponseEntity<String> setDefaultUserShippingPost(@RequestBody String id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(Long.parseLong(id), user);
		return new ResponseEntity<String>("Set Default Successfully!", HttpStatus.OK);

	}

}
