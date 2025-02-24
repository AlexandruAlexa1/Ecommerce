var navbar;
var menu;
var detailModal;

$(document).ready(function() {
	menu = $(".menu");
	navbar = $(".navbar");
	detailModal = $(".modal");
	
	handleLinkViewNavList();
	handleLinkCloseWarningModal();
	handleLinkCloseModal();
});

function handleLinkViewNavList() {
	menu.on("click", function() {
		$("body").toggleClass("change");
	});
}

function handleLinkViewDetail() {
	$(".link-detail").on("click", function(e) {
		e.preventDefault();
		
		showDetailModal(this);
	});
}

function showDetailModal(link) {
	detailModal.addClass("change");
	
	linkDetailURL = $(link).attr("href");
	detailModal.find(".detail-modal-content").load(linkDetailURL);
}

function handleLinkCloseModal() {
	$(".close-modal").on("click", function() {
		detailModal.removeClass("change");
	})
}

function showWarningModal(title, message) {
	$(".warning-modal-title").text(title);
	$(".warning-modal-message").text(message);
	$(".warning-modal").addClass('change');
}

function handleLinkCloseWarningModal() {
	$(".warning-modal-btn").on("click", function() {
		$(".warning-modal").removeClass('change');
	})
}

function clearFilter() {
	window.location = moduleURL;
}
