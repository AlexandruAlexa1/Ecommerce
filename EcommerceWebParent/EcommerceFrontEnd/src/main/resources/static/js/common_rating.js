// Common JS code for product & reviews
$(document).ready(function() {
	formatRatingNumber();
});

$(".product-detail-rating-star").rating({
	displayOnly: true,
	hoverOnClear: false,
	showCaption: false,
	theme: 'krajee-svg'
});

function formatRatingNumber() {
	ratingText = $("#ratingNumber").text();
	formattedRating = $.number(ratingText, 2, decimalSeparator, thousandSeparator);
	$("#ratingNumber").text(formattedRating);
}