package com.book.api.security;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.config.SecurityConfig;
import com.book.api.config.SecurityUtility;
import com.book.api.security.entity.Role;
import com.book.api.security.entity.User;
import com.book.api.security.entity.UserRole;
import com.book.api.security.entity.repository.UserRepository;
import com.book.api.security.service.UserService;
import com.book.api.utility.MailContructor;

@RestController
@RequestMapping("/user")
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailContructor mailContructor;

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ResponseEntity<String> newUserPost(@RequestBody HashMap<String, String> mapper) throws Exception {

		String username = mapper.get("username");
		String email = mapper.get("email");

		if (userService.findByUsername(username) == null) {
			return new ResponseEntity<>("usernameExists", HttpStatus.BAD_REQUEST);
		}
		if (userService.findByEmail(email) != null) {
			return new ResponseEntity<>("emailExists", HttpStatus.BAD_REQUEST);
		}
		userService.createUserToSave(email, username);
		return new ResponseEntity<String>("User Added Successfully", HttpStatus.OK);

	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgetPassword(@RequestBody HashMap<String, String> mapper) throws Exception {
		User user = userService.findByEmail(mapper.get("email"));
		if (user == null) {
			return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
		}
		userService.createNewPasswordForUser(user);
		return new ResponseEntity<String>("Password updated Successfully", HttpStatus.OK);

	}

	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public ResponseEntity<String> profileInfo(@RequestBody HashMap<String, Object> mapper) throws Exception {
		Integer id = (Integer) mapper.get("id");
		String email = (String) mapper.get("email");
		String username = (String) mapper.get("username");
		String firstName = (String) mapper.get("firstName");
		String lastName = (String) mapper.get("lastName");
		String newPassword = (String) mapper.get("newPassword");
		String currentPassword = (String) mapper.get("currentPassword");

		User user = userService.findById(Long.valueOf(id));

		if (Objects.isNull(user)) {
			throw new Exception("User not found");
		}
		if (userService.findByEmail(email) != null) {
			if (userService.findByEmail(email).getId() != user.getId()) {
				return new ResponseEntity<String>("Email not found!", HttpStatus.BAD_REQUEST);
			}
		}
		if (userService.findByUsername(username) != null) {
			if (userService.findByUsername(username).getId() != user.getId()) {
				return new ResponseEntity<String>("Username not found!", HttpStatus.BAD_REQUEST);
			}
		}

		BCryptPasswordEncoder bCryptPasswordEncoder = SecurityUtility.passwordEncoder();
		String dbPassword = user.getPassword();

		if (Objects.nonNull(currentPassword))
			if (bCryptPasswordEncoder.matches(currentPassword, dbPassword)) {
				if (!Objects.isNull(newPassword) && !newPassword.isEmpty() && !newPassword.equals("")) {
					user.setPassword(bCryptPasswordEncoder.encode(newPassword));
					user.setEmail(email);
				} else {
					return new ResponseEntity<String>("Choose a coorect password!", HttpStatus.BAD_REQUEST);
				}

			} else {
				return new ResponseEntity<String>("Incorrect current password!", HttpStatus.BAD_REQUEST);
			}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		userService.saveUser(user);
		return new ResponseEntity<String>("Updated success", HttpStatus.OK);

	}

	@RequestMapping("/currentUser")
	public User getCurrentUser(Principal principal) {
		User user = new User();
		if (principal != null) {
			user = userService.findByUsername(principal.getName());
		}
		return user;
	}

}
