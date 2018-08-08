package com.book.api.security;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@RequestMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		System.out.println(request);
		String remoteHost = request.getRemoteHost();
		int portNumber = request.getRemotePort();
		System.out.println(remoteHost + ":" + portNumber);
		System.out.println(request.getRemoteAddr());
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
