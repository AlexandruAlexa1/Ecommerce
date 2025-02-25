var modal;
var productDetailCount;

$(document).ready(function() {
	productDetailCount = $(".hiddenProductId").length;
	modal = $(".modal");
	
	$("#products").on("click", "#linkAddProduct", function(e) {
		e.preventDefault();
		showModal();
	});
	
	$(".close-modal").on("click", function(e) {
		modal.removeClass("change");
	});
});

function showModal() {
	modal.addClass("change");
}

function addProduct(productId, productName) {
	getShippingCost(productId);
}

function getShippingCost(productId) {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	
	state = $("#state").val();
	if (state.length == 0) {
		state = $("#city").val();
	}
	
	requestUrl = contextPath + "get_shipping_cost";
	params = {productId: productId, countryId: countryId, state: state};
	
	$.ajax({
		type: 'POST',
		url: requestUrl,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: params
	}).done(function(shippingCost) {
		getProductInfo(productId, shippingCost);
	}).fail(function(err) {
		showWarningModal(err.responseJSON.message);
		shippingCost = 0.0;
		getProductInfo(productId, shippingCost);
	}).always(function() {
		modal.removeClass("change");
	});

}

function getProductInfo(productId, shippingCost) {
	requestUrl = contextPath + "products/get/" + productId;
	
	$.get(requestUrl, function(productJson) {
		productName = productJson.name;
		mainImagePath = contextPath.substring(0, contextPath.length -1) + productJson.imagePath;
		productCost = $.number(productJson.cost, 2);
		productPrice = $.number(productJson.price, 2);
		
		htmlCode = generateProductCode(productId, productName, mainImagePath, productCost, productPrice, shippingCost);
		$("#productList").append(htmlCode);
		
		updateOrderAmounts();
	}).fail(function(err) {
		showWarningModal(err.responseJSON.message);
	});
}

function generateProductCode(productId, productName, mainImagePath, productCost, productPrice, shippingCost) {
	nextCount = productDetailCount + 1;
	productDetailCount++;
	rowId = "row" + nextCount;
	quantityId = "quantity" + nextCount;
	priceId = "price" + nextCount;
	subtotalId = "subtotal" + nextCount;
	
	htmlCode = `
		<div id="${rowId}" class="order-form-product">
			<input type="hidden" name="detailId" value="0" />
			<input type="hidden" name="productId" value="${productId}" class="hiddenProductId" />
			
			<div class="header">
				<span class="divCount">${nextCount}</span>
				<a href="" class="fas fa-trash linkRemove" rowNumber="${nextCount}" style="color: red;"></a>
			</div>
			
			<div class="details">
			<img src="${mainImagePath}" width="30%" />
			
			<div>
			<p>${productName}</p>
			<div>
				<table>
					<tr>
						<td>Product Cost:</td>
						<td>
							<input type="text" value="${productCost}" required style="width:100%;"
								name="productDetailCost"
								rowNumber="${nextCount}"
								class="cost-input"/>
						</td>
					</tr>
					<tr>
						<td>Quantity:</td>
						<td>
							<input type="number" step="1" min="1" max="5" class="quantity-input"
								name="quantity"
								id="${quantityId}"
								rowNumber="${nextCount}"
								value="1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td>Unit Price:</td>
						<td>
							<input type="text" class="price-input" required style="width:100%;"
								name="productPrice"
								id="${priceId}"
								rowNumber="${nextCount}"
								value="${productPrice}"/>
						</td>
					</tr>
					<tr>
						<td>Subtotal:</td>
						<td>
							<input type="text" class="subtotal-output" readonly style="width:100%;"
								name="productSubtotal"
								id="${subtotalId}"
								value="${productPrice}"/>
						</td>
					</tr>
					<tr>
						<td>Shipping Cost:</td>
						<td>
							<input type="text" value="${shippingCost}" required style="width:100%;"
								name="productShipCost"
								class="ship-input"/>
						</td>
					</tr>
				</table>
				</div>
			</div>
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