$(document).ready(function() {
	$(".LinkVoteReview").on("click", function(e) {
		e.preventDefault();
		voteReview($(this));
	});
});

function voteReview(currentLink) {
	requestURL = currentLink.attr("href");
	
	$.ajax({
		type: 'POST',
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(voteResult) {
		if (voteResult.successful) {
			updateVoteCountAndIcons(currentLink, voteResult);
		}
		
		showWarningModal("Successfully Voted", voteResult.message);
	}).fail(function() {
		showWarningModal("Warning", "Error voting review.");
	});
}

function updateVoteCountAndIcons(currentLink, voteResult) {
	reviewId = currentLink.attr("reviewId");
	linkVoteUp = $("#linkVoteUp-" + reviewId);
	linkVoteDown = $("#linkVoteDown-" + reviewId);
	
	$("#voteCount-" + reviewId).text(voteResult.voteCount + " Votes");
	
	message = voteResult.message;
	
	if (message.includes("successfully voted up")) {
		hightlightVoteUpIcon(currentLink, linkVoteDown);
	} else if (message.includes("successfully voted down")) {
		hightlightVoteDownIcon(currentLink, linkVoteUp);
	} else if (message.includes("unvoted down")) {
		unhighlightVoteDownIcon(linkVoteDown);
	} else if (message.includes("unvoted up")) {
		unhighlightVoteUpIcon(linkVoteUp);
	}
}

function hightlightVoteUpIcon(linkVoteUp, linkVoteDown) {
	linkVoteUp.removeClass("far").addClass("fas");
	linkVoteUp.attr("title", "Undo vote up this review");
	linkVoteDown.removeClass("fas").addClass("far");
}

function hightlightVoteDownIcon(linkVoteDown, linkVoteUp) {
	linkVoteDown.removeClass("far").addClass("fas");
	linkVoteDown.attr("title", "Undo vote down this review");
	linkVoteUp.removeClass("fas").addClass("far");
}

function unhighlightVoteDownIcon(linkVoteDown) {
	linkVoteDown.attr("title", "Vote down this review");
	linkVoteDown.removeClass("fas").addClass("far");
}

function unhighlightVoteUpIcon(linkVoteUp) {
	linkVoteUp.attr("title", "Vote up this review");
	linkVoteUp.removeClass("fas").addClass("far");
}






