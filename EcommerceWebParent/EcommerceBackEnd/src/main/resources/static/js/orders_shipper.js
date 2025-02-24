let iconNames = {
	'PICKED':'fa-people-carry',
	'SHIPPING':'fa-shipping-fast',
	'DELIVERED':'fa-box-open',
	'RETURNED':'fa-undo'
}
let modal;
let message;
let confirmBtn;
let closeBtn;

$(document).ready(function() {
	modal = $(".order-shipper-modal");
	message = $(".confirm-modal-content .message");
	confirmBtn = $(".delete-link");
	closeBtn = $(".close-link");
	
	$(".linkUpdateStatus").on("click", function(e) {
		e.preventDefault();
		link = $(this);
		showUpdateConfirmModal(link);
	});
	
	addEventHandlerForConfirmBtn();
});

function addEventHandlerForConfirmBtn() {
	confirmBtn.on("click", function(e) {
		e.preventDefault();
		sendRequestToUpdateOrderStatus($(this));
	});
}

function sendRequestToUpdateOrderStatus(button) {
	requestUrl = button.attr("href");
	
	$.ajax({
		type: 'POST',
		url: requestUrl,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		showMessageModal("Order update successfully");
		updateStatusIconColor(response.orderId, response.status);
	}).fail(function(err) {
		showMessageModal("Error updating order status");
	});
}

function updateStatusIconColor(orderId, status) {
	link = $("#link" + status + orderId);
	link.replaceWith("<i class='fas " + iconNames[status] + " icon-green'></i>");
}

function showUpdateConfirmModal(link) {
	$(".confirm-modal-content h1").text("Update Confirmation");
	closeBtn.text("No");
	confirmBtn.show();
	
	orderId = link.attr("orderId");
	status = link.attr("status");
	confirmBtn.attr("href", link.attr("href"));
	
	message.text("Are you sure you want to update status of the order ID #" + orderId + " to " + status + "?")
	
	modal.addClass("change");
}

function showMessageModal(requestMessage) {
	closeBtn.text("Close");
	confirmBtn.hide();
	message.text(requestMessage);
}
