package com.ecommerce.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response, String contentType,
			String extension, String prefix) {
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyy_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = prefix + timestamp + extension;
		
		response.setContentType(contentType);
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}
}
