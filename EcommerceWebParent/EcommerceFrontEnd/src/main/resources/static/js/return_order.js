var returnModal;
var modalTitle;
var reasonText;
var orderId;
var returnOrderForm;
var divReason;
var confirmMessage;
var sendRquestBtn;
var cancelBtn;

$(document).ready(function() {
	returnModal = $(".return-order-modal");
	modalTitle = $(".return-modal-title");
	reasonText = $("#reasonText");
	returnOrderForm = $(".return-order-form");
	divReason = $(".reason-wrapper");
	confirmMessage = $(".return-confirm-message");
	sendRquestBtn = $(".send-equest-btn");
	cancelBtn = $(".cancel-btn");
	
	handleLinkReturnOrder();
	handleLinkCloseReturnModal();
});

function handleLinkReturnOrder() {
	$(".link-return").on("click", function(e) {
		e.preventDefault();
		showModal($(this));
	})
}

function showModal(link) {
	confirmMessage.hide();
	returnOrderForm.show();
	sendRquestBtn.show();
	cancelBtn.text("Cancel");
	reasonText.val("");
	
	orderId = link.attr("orderID");
	modalTitle.text("Return Order ID #" + orderId);
	returnModal.fadeIn("slow");
}

function handleLinkCloseReturnModal() {
	cancelBtn.on("click", function() {
		returnModal.hide();
	});
}

function submitReturnOrderForm() {
	reason = $("input[name='returnReason']:checked").val();
	note = reasonText.val();

	sendReturnOrderRequest(reason, note);
	
	return false;
}

function sendReturnOrderRequest(reason, note) {
	requestURL = contextPath + "orders/return";
	requestBody = {orderId: orderId, reason: reason, note: note};
	
	$.ajax({
		method: 'POST',
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		},
		data: JSON.stringify(requestBody),
		contentType: 'application/json'
	}).done(function(response) {
		showMessageModal("Thank you for your return request. Return request has been sent!");
		updateStatusTextAndHideReturnButton(orderId);
	}).fail(function(err) {
		showMessageModal(err.responseText);
	})
}

function showMessageModal(message) {
	divReason.hide();
	sendRquestBtn.hide();
	cancelBtn.text("Close");
	confirmMessage.text(message);
	
	confirmMessage.show();
}

function updateStatusTextAndHideReturnButton(orderId) {
	$(".textOrderStatus" + orderId).each(function(index) {
		$(this).text("RETURN_REQUESTED");
	});
	
	$(".linkReturn" + orderId).each(function(index) {
		$(this).hide();
	})
}