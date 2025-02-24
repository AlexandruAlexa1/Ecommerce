package com.ecommerce.admin.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ecommerce.admin.AbstractExporter;
import com.ecommerce.common.entity.Customer;

public class CustomerCsvExporter extends AbstractExporter{
	
	public void export(List<Customer> listCustomers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "customers_");

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID", "First name", "Last Name", "E-mail", "Phone", "City", "State", "Country", "Enabled",
				"Address 1", "Address 2", "Postal Code"};
		String[] fieldMapping = {"id", "firstName", "lastName", "email", "phoneNumber", "city", "state", "country", "enabled",
				"addressLine1", "addressLine2", "postalCode"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (Customer customer : listCustomers) {
			csvWriter.write(customer, fieldMapping);
		}
		
		csvWriter.close();
	}
}
