package com.ecommerce.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/states/list_by_country/**").hasAnyAuthority("Admin", "SalesPerson")
			.antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
			.antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
			
			.antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
			
			.antMatchers("/products/edit/**", "/products/save", "/products/check_unique")
				.hasAnyAuthority("Admin", "Editor", "SalesPerson")
				
			.antMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
				.hasAnyAuthority("Admin", "SalesPerson", "Editor", "Shipper")
				
			.antMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
			
			.antMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "SalesPerson", "Shipper")
			
			.antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "SalesPerson", "Assistant", "Shipper")
			
			.antMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin", "SalesPerson")
			
			.antMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
			
			.antMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistent")
			
			.antMatchers("/questions/**").hasAnyAuthority("Admin", "Assistent")
			
			.antMatchers("/articles/**").hasAnyAuthority("Admin", "Editor")
			
			.antMatchers("/sections/**").hasAnyAuthority("Admin", "Editor")
			
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll()
			.and().rememberMe().key("Abcdefj3jdk3jakcjanweq_16");
			http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/css/**", "/webjars/**");
	}
}
