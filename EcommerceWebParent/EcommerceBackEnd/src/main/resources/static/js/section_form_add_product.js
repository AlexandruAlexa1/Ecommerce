var addProductBtn;
var modal;
var productCount;

$(document).ready(function() {
	addProductBtn = $('#addProductBtn');
	modal = $('.modal');
	productCount = $(".hiddenProductId").length;
	
	addProductBtn.on('click', function() {
		showModal();
	});

	handleLinkCloseModal();
	handleLinkRemoveProduct();
});

function showModal() {
	modal.addClass('change');
}

function handleLinkCloseModal() {
	$('.close-modal').on("click", function() {
		modal.removeClass('change')
	});
}

function addProduct(productId) {
	requestUrl = contextPath + "products/get/" + productId;
	
	$.get(requestUrl, function(productJson) {
		productName = productJson.name;
		mainImagePath = contextPath.substring(0, contextPath.length -1) + productJson.imagePath;
		
		htmlCode = generateProductCode(productId, productName, mainImagePath);
		$(".section-form-products").append(htmlCode);
		
		modal.removeClass('change')
	}).fail(function(err) {
		showWarningModal(err.responseJSON.message);
	});
}

function generateProductCode(productId, productName, mainImagePath) {
	nextCount = productCount + 1;
	productCount++;
	
	htmlCode = `
		<div class="section-form-product" id="product${nextCount}">
			<input type="hidden" name="productId" value="${productId}" class="hiddenProductId" />
			<div class="section-form-product-links">
				<a href="" class="fa-solid fa-trash linkRemove" productNumber="${nextCount}" title="Remove this product"></a>
				<a href="" class="fa-solid fa-angle-left" title="Move product to left"></a>
				<a href="" class="fa-solid fa-angle-right" title="Move product to right"></a>
			</div>
			<img src="${mainImagePath}">
			<p>${productName}</p>
		</div>
	`;
	
	return htmlCode;
}

function isProductAlreadyAdded(productId) {
	productExists = false;
	
	$(".hiddenProductId").each(function(e) {
		aProductId = $(this).val();
		
		if (aProductId == productId) {
			productExists = true;
			return;
		}
	});
	
	return productExists;
}

function handleLinkRemoveProduct() {
	$(".section-form-products").on("click", ".linkRemove", function(e) {
		e.preventDefault();
		
		productNumber = $(this).attr('productNumber');
		$('#product' + productNumber).remove();
		
		productCount--;
	});
}

function checkIfProductsIsSelected(form) {
	if (productCount) {
		form.submit();
	} else {
		showWarningModal('You need to add products!');
	}
	
	return false;
}
