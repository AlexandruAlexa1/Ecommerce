package com.ecommerce.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.setting.Setting;
import com.ecommerce.common.entity.setting.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

	@Autowired
	private SettingRepository repo;
	
	@Test
	public void testCreateGeneralSettings() {
	    Setting set1 = new Setting("SITE_NAME", "Ecommerce Platform", SettingCategory.GENERAL);
	    Setting set2 = new Setting("COPYRIGHT", "© 2025 AA.", SettingCategory.GENERAL);
	    Setting set3 = new Setting("SITE_LOGO", "logo.png", SettingCategory.GENERAL);
	    
	    List<Setting> settings = new ArrayList<>();
	    settings.add(set1);
	    settings.add(set2);
	    settings.add(set3);
	    
	    repo.saveAll(settings);
	    
	    Iterable<Setting> iterable = repo.findAll();
	    
	    assertThat(iterable).size().isGreaterThan(0);
	}

	
	@Test
	public void testCreateCurrencySettings() {
		Setting set1 = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
		Setting set2 = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
		Setting set3 = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
		Setting set4 = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
		Setting set5 = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
		Setting set6 = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);
		
		List<Setting> settings = new ArrayList<>();
		settings.add(set1);
		settings.add(set2);
		settings.add(set3);
		settings.add(set4);
		settings.add(set5);
		settings.add(set6);
		
		repo.saveAll(settings);
		
		Iterable<Setting> iterable = repo.findAll();
		
		assertThat(iterable).size().isGreaterThan(0);
	}
	
	@Test
	public void testCreateMailSettings() {
		Setting set1 = new Setting("ORDER_CONFIRMATION_SUBJECT", "SUBJECT", SettingCategory.MAIL_TEMPLATES);
		Setting set2 = new Setting("ORDER_CONFIRMATION_CONTENT", "CONTENT", SettingCategory.MAIL_TEMPLATES);
		
		List<Setting> settings = new ArrayList<>();
		settings.add(set1);
		settings.add(set2);
		
		repo.saveAll(settings);
		
		Iterable<Setting> iterable = repo.findAll();
		
		assertThat(iterable).size().isGreaterThan(0);
	}
	
	@Test
	public void testCreateSmtpSettings() {
	    Setting set1 = new Setting("SMTP_SERVER_HOSTNAME", "smtp.example.com", SettingCategory.MAIL_SERVER);
	    Setting set2 = new Setting("SMTP_PORT", "587", SettingCategory.MAIL_SERVER);
	    Setting set3 = new Setting("SMTP_USERNAME", "user@example.com", SettingCategory.MAIL_SERVER);
	    Setting set4 = new Setting("SMTP_PASSWORD", "password123", SettingCategory.MAIL_SERVER);
	    Setting set5 = new Setting("SMTP_SERVER_AUTH_REQUIRED", "TRUE", SettingCategory.MAIL_SERVER);
	    Setting set6 = new Setting("SMTP_TLS_REQUIRED", "TRUE", SettingCategory.MAIL_SERVER);
	    Setting set7 = new Setting("SMTP_FROM_EMAIL", "noreply@example.com", SettingCategory.MAIL_SERVER);
	    Setting set8 = new Setting("SMTP_SENDER_NAME", "Example Sender", SettingCategory.MAIL_SERVER);
	    
	    List<Setting> settings = new ArrayList<>();
	    settings.add(set1);
	    settings.add(set2);
	    settings.add(set3);
	    settings.add(set4);
	    settings.add(set5);
	    settings.add(set6);
	    settings.add(set7);
	    settings.add(set8);
	    
	    repo.saveAll(settings);
	    
	    Iterable<Setting> iterable = repo.findAll();
	    
	    assertThat(iterable).size().isGreaterThan(0);
	}

	
	@Test
	public void testCreatePaymentSettings() {
		Setting set1 = new Setting("PAYPAL_API_BASE_URL", "URL", SettingCategory.PAYMENT);
		Setting set2 = new Setting("PAYPAL_API_CLIENT_ID", "ID", SettingCategory.PAYMENT);
		Setting set3 = new Setting("PAYPAL_API_CLIENT_SECRET", "SECRET", SettingCategory.PAYMENT);
		
		List<Setting> settings = new ArrayList<>();
		settings.add(set1);
		settings.add(set2);
		settings.add(set3);
		
		repo.saveAll(settings);
		
		Iterable<Setting> iterable = repo.findAll();
		
		assertThat(iterable).size().isGreaterThan(0);
	}
	
	@Test
	public void testListSettingsByCategory() {
		List<Setting> settings = repo.findByCategory(SettingCategory.GENERAL);
		settings.forEach(System.out::println);
	}
	
}
