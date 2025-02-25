package com.ecommerce.admin.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.admin.setting.SettingService;
import com.ecommerce.common.entity.setting.Setting;

@Controller
public class ReportController {
	@Autowired private SettingService settingService;

	@GetMapping("/reports")
	public String viewSalesReportHome(HttpServletRequest request) {
		loadCurrencySettings(request);
		
		return "reports/reports";
	}
	
	private void loadCurrencySettings(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for (Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}
	}
}
