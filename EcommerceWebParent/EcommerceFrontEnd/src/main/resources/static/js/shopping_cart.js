decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.';

$(document).ready(function() {
	$(".linkMinus").on("click", function(event) {
		event.preventDefault();
		decreaseQuantity($(this));
	});
	
	$(".linkPlus").on("click", function(event) {
		event.preventDefault();
		increaseQuantity($(this));
	});
	
	$(".linkDelete").on("click", function(event) {
		event.preventDefault();
		url = $(this);
		removeProduct(url);
	});
});

function decreaseQuantity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) - 1;
	
	if (newQuantity > 0) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		showWarningModal('Warning', 'Minimum quantity is 1');
	}
}

function increaseQuantity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) + 1;
	
	if (newQuantity <= 5) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		showWarningModal('Warning', 'Maximum quantity is 5');
	}
}

function updateQuantity(productId, quantity) {
	url = contextPath + "cart/update/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, productId);
		updateTotal();
	}).fail(function() {
		showWarningModal("Warning", "Error while updating product quantity.");
	})
}

function updateSubtotal(updatedSubtotal, productId) {
	$("#subtotal" + productId).text(formatCurrency(updatedSubtotal));
}

function updateTotal() {
	total = 0.0;
	productCount = 0;
	
	$(".subtotal").each(function(index, element) {
		productCount++;
		total += parseFloat(clearCurrencyFormat(element.innerHTML));
	});
	
	if (productCount < 1) {
		showEmptyShoppingCart();
	} else {
		$("#total").text(formatCurrency(total));
	}
}

function showEmptyShoppingCart() {
	$("#sectionTotal").hide();
	$("#sectionEmptyCartMessage").removeClass("d-none");
}

function removeProduct(link) {
	url = link.attr("href");
	
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
		removeProductHTML(rowNumber);
		updateTotal();
		updateCountNumber();
		showWarningModal("Remove Product", response);
	}).fail(function() {
		showWarningModal("Error while removing product.");
	});
}

function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
}

function updateCountNumber() {
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	});
}

function formatCurrency(amount) {
	return $.number(amount, decimalDigits, decimalSeparator, thousandsSeparator);
}

function clearCurrencyFormat(numberString) {
	result = numberString.replaceAll(thousandsSeparator, "");
	return result.replaceAll(decimalSeparator, ".");
}