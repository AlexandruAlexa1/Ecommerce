function addQuestion() {
	questionContent = $("#questionContent").val();
	
	requestURL = contextPath + "questions/post_question/" + productId;
	requestBody = {questionContent: questionContent, productId: productId};
	
	$.ajax({
		type: 'POST',
		url: requestURL,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(requestBody),
		contentType: 'application/json'
	}).done(function(response) {
		$("#questionContent").val("");
		showWarningModal("Post Question", response);
	}).fail(function() {
		showWarningModal("Warning", "ERROR: Could not connect to server or server encountered an error");
	});
	
	return false;
}