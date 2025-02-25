package com.ecommerce.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends AbstractAddressWithCountry {

	@Column(length = 45, nullable = false, unique = true)
	private String email;
	
	@Column(length = 65, nullable = false)
	private String password;
	
	@Column(length = 64, name = "verification_code")
	private String verificationCode;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;
	
	@Column(name = "reset_password_token", length = 30)
	private String resetPasswordToken;
	
	public Customer() {
	}

	public Customer(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
}
