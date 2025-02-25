$(document).ready(function() {
	$(".linkVoteQuestion").on("click", function(e) {
		e.preventDefault();
		voteQuestion($(this));
	});
});

function voteQuestion(currentLink) {
	requestURL = currentLink.attr("href");
	
	$.post({
		type: 'POST',
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue)
		}
	}).done(function(voteResult) {
		updateVoteCountAndIcons(currentLink, voteResult);
		showWarningModal("Vote Question", voteResult.message);
	}).fail(function() {
		showWarningModal("", "Error voting question.");
	});
}

function updateVoteCountAndIcons(currentLink, voteResult) {
	questionId = currentLink.attr("questionId");
	linkVoteUp = $("#linkVoteUp-" + questionId);
	linkVoteDown = $("#linkVoteDown-" + questionId);
	
	$("#voteCountQuestion-" + questionId).text(voteResult.voteCount + " Votes");
	
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
	linkVoteUp.removeClass("icon-silver").addClass("icon-green");
	linkVoteUp.attr("title", "Undo vote up this review");
	linkVoteDown.removeClass("icon-green").addClass("icon-silver");
}

function hightlightVoteDownIcon(linkVoteDown, linkVoteUp) {
	linkVoteDown.removeClass("icon-silver").addClass("icon-green");
	linkVoteDown.attr("title", "Undo vote down this review");
	linkVoteUp.removeClass("icon-green").addClass("icon-silver");
}

function unhighlightVoteDownIcon(linkVoteDown) {
	linkVoteDown.attr("title", "Vote down this review");
	linkVoteDown.removeClass("icon-green").addClass("icon-silver");
}

function unhighlightVoteUpIcon(linkVoteUp) {
	linkVoteUp.attr("title", "Vote up this review");
	linkVoteUp.removeClass("icon-green").addClass("icon-silver");
}






