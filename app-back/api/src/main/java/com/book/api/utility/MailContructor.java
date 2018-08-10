package com.book.api.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.book.api.security.entity.User;

@Component
public class MailContructor {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	public void constructNewUserEmailAndSend(String password, User user){
		String message = "\nPlease use the following credentials to log in and edit your personal information including your own password."
				+ "\nUsername:" + user.getUsername() + "\nPassword:" + password;

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Le's Bookstore - New User");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		mailSender.send(email);

	}

}
