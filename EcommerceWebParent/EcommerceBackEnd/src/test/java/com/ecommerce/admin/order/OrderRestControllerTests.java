package com.ecommerce.admin.order;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderRestControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "user", password="password", authorities = {"Shipper"})
	public void testUpdateOrderStatus() throws Exception {
		Integer orderId = 1;
		String status = "RETURNED";
		String requestURL = "/orders_shipper/update/" + orderId + "/" + status;
		
		mockMvc.perform(post(requestURL).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print());
	}
}
