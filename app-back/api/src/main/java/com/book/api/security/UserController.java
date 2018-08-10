package com.book.api.security;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.config.SecurityUtility;
import com.book.api.security.entity.Role;
import com.book.api.security.entity.User;
import com.book.api.security.entity.UserRole;
import com.book.api.security.service.UserService;
import com.book.api.utility.MailContructor;

@RestController
@RequestMapping("/user")
public class UserController {

	private final static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailContructor mailContructor;
	@RequestMapping(value="/newUser",method=RequestMethod.POST)
	public ResponseEntity<String> newUserPost(@RequestBody HashMap<String, String> mapper) throws Exception{
		
		String username = mapper.get("username");
		String email = mapper.get("email");
		
		if(userService.findByUsername(username)==null) {
			return new ResponseEntity<>("usernameExists",HttpStatus.BAD_REQUEST);
		}
		if(userService.findByEmail(email)!=null) {
			return new ResponseEntity<>("emailExists",HttpStatus.BAD_REQUEST);
		}
		this.createUserToSave(email, username);
		return new ResponseEntity<String>("User Added Successfully",HttpStatus.OK);
		
	}
	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	public ResponseEntity<String> forgetPassword(@RequestBody HashMap<String, String> mapper) throws Exception{
		User user = userService.findByEmail(mapper.get("email"));
		if(user==null) {
			return new ResponseEntity<>("Email not found",HttpStatus.BAD_REQUEST);
		}
		String password =SecurityUtility.randomPassword();
		String encyptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encyptedPassword);
		userService.saveUser(user);
		mailContructor.constructNewUserEmailAndSend(password, user);;
		
		return new ResponseEntity<String>("Password updated Successfully",HttpStatus.OK);
		
	}
	
	
	public void createUserToSave(String email , String username){
		try {
			logger.info("starting creation of a new user");
			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			
			String password =SecurityUtility.randomPassword();
			String encryptdedPassword = SecurityUtility.passwordEncoder().encode(password);
			user.setPassword(encryptdedPassword );
			Role role = new Role();
			role.setRoleId(1);
			role.setName("ROLE_USER");
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user,role));
			mailContructor.constructNewUserEmailAndSend(encryptdedPassword,user);
			userService.createUser(user, userRoles);
			logger.info("Operation Sucess");
		} catch (Exception e) {
			logger.info("Operation Failed !");
			e.printStackTrace();
		}
	}
	
	
	
}
