package com.ecommerce.admin.brand;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ecommerce.admin.AbstractExporter;
import com.ecommerce.common.entity.Brand;

public class BrandCsvExporter extends AbstractExporter {

	public void export(HttpServletResponse response, List<Brand> listBrands) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "brands_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"Brand ID", "Brand Name"};
		String[] fieldMapping = {"id", "name"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (Brand brand : listBrands) {
			csvWriter.write(brand, fieldMapping);
		}
		
		csvWriter.close();
	}

}
