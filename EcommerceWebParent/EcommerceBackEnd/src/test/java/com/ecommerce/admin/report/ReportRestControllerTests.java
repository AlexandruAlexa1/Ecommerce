package com.ecommerce.admin.report;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportRestControllerTests {

	@Autowired private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "userName", password = "a2@jdk", authorities = {"SalesPerson"})
	public void testGetReportDataLast7Days() throws Exception {
		String requestURL = "/reposts/sales_by_date/last_7_days";
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "userName", password = "a2@jdk", authorities = {"SalesPerson"})
	public void testGetReportDataLast6Months() throws Exception {
		String requestURL = "/reposts/sales_by_date/last_6_months";
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "userName", password = "a2@jdk", authorities = {"SalesPerson"})
	public void testGetReportDataByDateRange() throws Exception {
		String startDate = "2021-03-16";
		String endDate = "2021-04-16";
		String requestURL = "/reposts/sales_by_date/" + startDate + "/" + endDate;
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "userName", password = "a2@jdk", authorities = {"SalesPerson"})
	public void testGetReportDataByCategory() throws Exception {
		String requestURL = "/reports/category/last_28_days";
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "userName", password = "a2@jdk", authorities = {"SalesPerson"})
	public void testGetReportDataByProduct() throws Exception {
		String requestURL = "/reports/product/last_28_days";
		
		mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andDo(print());
	}
}
