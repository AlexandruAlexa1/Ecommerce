package com.ecommerce.admin.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.common.entity.product.Product;
import com.ecommerce.common.entity.product.ProductImage;

public class ProductSaveHelper {
	
//	static void deleteExtraImagesWheredRemovedOnForm(Product product) {
//		String extraImageDir = "product-images/" + product.getId() + "/extras";
//		List<String> listObjectKeys = AmazonS3Util.listFolder(extraImageDir);
//		
//		for (String objectKey : listObjectKeys) {
//			int lastIndexOfSlash = objectKey.lastIndexOf("/");
//			String fileName = objectKey.substring(lastIndexOfSlash + 1, objectKey.length());
//			
//			if (!product.containsImageName(fileName)) {
//				AmazonS3Util.deleteFile(objectKey);
//				System.out.println("DELETED EXTRA IMAGE: " + objectKey);
//			}
//		}
//	}
	
	public static void deleteExtraImagesWheredRemovedOnForm(Product product) {
	    Path extraImageDir = Paths.get("product-images", String.valueOf(product.getId()), "extras");

	    // Verificăm dacă directorul există
	    if (Files.exists(extraImageDir) && Files.isDirectory(extraImageDir)) {
	        try (Stream<Path> files = Files.list(extraImageDir)) {
	            files.forEach(filePath -> {
	                String fileName = filePath.getFileName().toString();

	                // Ștergem fișierul dacă nu este în lista imaginilor produsului
	                if (!product.containsImageName(fileName)) {
	                    try {
	                        Files.delete(filePath);
	                        System.out.println("DELETED LOCAL EXTRA IMAGE: " + filePath);
	                    } catch (IOException e) {
	                        System.err.println("ERROR DELETING LOCAL IMAGE: " + fileName + " - " + e.getMessage());
	                    }
	                }
	            });
	        } catch (IOException e) {
	            System.err.println("ERROR ACCESSING DIRECTORY: " + extraImageDir + " - " + e.getMessage());
	        }
	    } else {
	        System.out.println("Directory does not exist or is not a directory: " + extraImageDir);
	    }
	}


	static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, Product product) {
		if (imageIDs == null || imageIDs.length == 0) return;
		
		Set<ProductImage> images = new HashSet<>();
		
		for (int count = 0; count < imageIDs.length; count++) {
			Integer id = Integer.parseInt(imageIDs[count]);
			String name = imageNames[count];
			
			images.add(new ProductImage(id, name, product));
		}
		
		product.setImages(images);
	}

	static void setProductDetails(String[] detailIDs, String[] detailNames, String[] detailValues, Product product) {
		if (detailNames == null || detailNames.length == 0) return;
		
		for (int count = 0; count < detailNames.length; count++ ) {
			String name = detailNames[count];
			String value = detailValues[count];
			Integer id = Integer.parseInt(detailIDs[count]);
			
			if (id != 0) {
				product.addDetail(id, name, value);
			} else if (!name.isEmpty() && !value.isEmpty()) {
				product.addDetail(name, value);
			}
		}
	}
	
	public static void saveUploadedImages(MultipartFile mainImageMultipart, MultipartFile[] extraImageMultiparts,
			Product savedProduct) throws IOException {
		// Directory de bază pentru salvare locală
		String baseDir = "../product-images/" + savedProduct.getId();

		// Salvarea imaginii principale
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			Path uploadPath = Paths.get(baseDir);

			// Șterge fișierele existente (exceptând directorul "extras")
			if (Files.exists(uploadPath)) {
				Files.list(uploadPath).filter(file -> !file.toString().contains("/extras")).forEach(file -> {
					try {
						Files.delete(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}

			// Creează directorul dacă nu există
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Salvează fișierul local
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(mainImageMultipart.getInputStream(), filePath);
		}

		// Salvarea imaginilor suplimentare
		if (extraImageMultiparts.length > 0) {
			Path extrasPath = Paths.get(baseDir, "extras");

			// Creează directorul "extras" dacă nu există
			if (!Files.exists(extrasPath)) {
				Files.createDirectories(extrasPath);
			}

			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (multipartFile.isEmpty())
					continue;

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				Path filePath = extrasPath.resolve(fileName);

				// Salvează fiecare imagine suplimentară
				Files.copy(multipartFile.getInputStream(), filePath);
			}
		}
	}

//	static void saveUploadedImages(MultipartFile mainImageMultipart,
//			MultipartFile[] extraImageMultiparts,
//			Product savedProduct) throws IOException {
//		if (!mainImageMultipart.isEmpty()) {
//			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
//			String uploadDir = "product-images/" + savedProduct.getId();
//			
//			List<String> listObjectKeys = AmazonS3Util.listFolder(uploadDir + "/");
//			for (String objectKey : listObjectKeys) {
//				if (!objectKey.contains("/extras/")) {
//					AmazonS3Util.deleteFile(objectKey);
//				}
//			}
//			
//			AmazonS3Util.uploadFile(uploadDir, fileName, mainImageMultipart.getInputStream());
//		}
//		
//		if (extraImageMultiparts.length > 0) {
//			String uploadDir = "product-images/" + savedProduct.getId() + "/extras";
//
//			for (MultipartFile multipartFile : extraImageMultiparts) {
//				if (multipartFile.isEmpty()) continue;
//				
//				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//				AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
//			}
//		}
//	}

	static void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, Product product) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					
					if (!product.containsImageName(fileName)) {
						product.addExtraImage(fileName);
					}
				}
			}
		}
	}

	static void setMainImageName(MultipartFile mainImageMultipart, Product product) {
		if(!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			product.setMainImage(fileName);
		}
	}
}
