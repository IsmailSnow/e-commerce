package com.book.api.security;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.security.service.UserService;

@RestController
public class LoginController {
	
	final private static org.slf4j.Logger logger =LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		logger.warn("Request is :"+request);
		String remoteHost = request.getRemoteHost();
		int portNumber = request.getRemotePort();
		logger.warn(remoteHost + ":" + portNumber);
		logger.warn(request.getRemoteAddr());
		session.setMaxInactiveInterval(3600);
		return Collections.singletonMap("token", session.getId());
	}

	@RequestMapping("/check")
	public ResponseEntity<String> checkSession() {
		return new ResponseEntity<String>("Session active!", HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/logout",method=RequestMethod.POST)
	public ResponseEntity<String> logout(HttpSession session) {
		SecurityContextHolder.clearContext();
		session.invalidate();
		return new ResponseEntity<String>("Logout Successfully !",HttpStatus.OK);
	}
	
	
	
	
}
