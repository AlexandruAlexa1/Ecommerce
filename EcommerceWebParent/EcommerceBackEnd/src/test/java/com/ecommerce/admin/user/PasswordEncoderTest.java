package com.ecommerce.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String originalPassword = "A@19d2js!djs";
		String encodedPassword = passwordEncoder.encode(originalPassword);
		
		System.out.println("Original Password: " + originalPassword);
		System.out.println("Encoded Password: " + encodedPassword);
		
		boolean matches = passwordEncoder.matches(originalPassword, encodedPassword);
		
		assertThat(matches).isTrue();
	}
}
