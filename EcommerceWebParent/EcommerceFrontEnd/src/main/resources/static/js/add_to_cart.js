$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function() {
		addToCart();
	})
});

function addToCart() {
	quantity = $("#quantity" + productId).val();
	url = contextPath + "cart/add/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		alert(response);
	}).fail(function() {
		alert("Error while adding product to shopping cart.");
	});
}