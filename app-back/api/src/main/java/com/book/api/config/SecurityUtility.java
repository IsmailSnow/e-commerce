package com.book.api.config;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {

	private static final String CODE = "SALT";

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(CODE.getBytes()));
	}

	@Bean
	public static String randomPassword() {
		String CHARS = "AZELUREGHPUHREVLURELU64896612";
		StringBuilder build = new StringBuilder();
		Random rand = new Random();
		while (build.length() < 18) {
			int index = (int) (rand.nextFloat() * CHARS.length());
			build.append(CHARS).charAt(index);
		}
		return build.toString();
	}
}
