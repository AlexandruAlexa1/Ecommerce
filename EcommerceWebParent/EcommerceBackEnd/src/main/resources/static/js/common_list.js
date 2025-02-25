var confirmModal;
var detailModal;
var closeModal;

$(document).ready(function() {
	confirmModal = $("#confirmModal");
	detailModal = $("#detailModal");
	closeModal = $(".close-modal");
	
	handleLinkCloseModal();
	hideMessage();
})

function handleLinkDelete(entityName) {
	$(".link-delete").on("click", function(e) {
		e.preventDefault();
		
		confirmModal.addClass("change");
		url = $(this).attr("href");
		entityId = $(this).attr("entityId");
		
		$(".confirm-modal-content .message").text("Are you sure you want to delete this " + entityName + " with ID " + entityId + "?");
		$(".delete-link").attr("href", url);
	});
}

function handleLinkCloseModal() {
	closeModal.on("click", function() {
		confirmModal.removeClass("change");
		detailModal.removeClass("change");
	});
}

function clearFilter() {
	window.location = moduleURL;
}

function handleLinkViewDetail(link) {
	link.on('click', function(e) {
		e.preventDefault();
		
		url = $(this).attr('href');
		
		// Show Modal
		detailModal.addClass('change');
		
		// Load Content
		detailModal.find('.detail-modal-content').load(url);
	});
}

function hideMessage() {
	setTimeout(function() {
		$('#message').remove();
	}, 10000);
}
