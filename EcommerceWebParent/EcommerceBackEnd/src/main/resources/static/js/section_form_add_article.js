var dropdownArticles;
var chosenArticles;
var addArticleBtn;
var removeBtn;
var moveUpBtn;
var moveDownBtn;

$(document).ready(function() {
	dropdownArticles = $('#articles');
	chosenArticles = $('#chosen-articles');
	addArticleBtn = $('#add-article-btn');
	removeBtn = $('#remove-article-btn');
	moveUpBtn = $('#move-up-article-btn');
	moveDownBtn = $('#move-down-article-btn');
	
	addArticleBtn.on('click', function() {
		addArticle();
	});
	
	removeBtn.on('click', function() {
		removeArticle();
	});
	
	moveUpBtn.on('click', function() {
		moveUpArticle();
	});
	
	moveDownBtn.on('click', function() {
		moveDownArticle();
	});
})

function addArticle() {
	selectedArticle = $('#articles option:selected');
	articleName = selectedArticle.text();
	articleID = selectedArticle.val();
	
	optionElement = `<option value="${articleID}">${articleName}</option>`;
	
	// Check if brand is already added
	optionExists = false;
	
	$('#chosen-articles option').each(function() {
		if (this.value == articleID) {
			optionExists = true;
		}
	});
	
	if (optionExists) {
		showWarningModal(articleName +  ' is already added!')
	} else {
		chosenArticles.append(optionElement);
	}
}

function removeArticle() {
	selectedArticle = $('#chosen-articles option:selected');
	
	if (selectedArticle.length) {
		selectedArticle.remove();
	} else {
		alert('You need to select a Article')
	}
}

function moveUpArticle() {
	selectedArticle = $('#chosen-articles option:selected');
	
	if (selectedArticle.length) {
		selectedArticle.first().prev().before(selectedArticle);
	} else {
		alert('You need to select a Article')
	}
}

function moveDownArticle() {
	selectedArticle = $('#chosen-articles option:selected');
	
	if (selectedArticle.length) {
		selectedArticle.last().next().after(selectedArticle);
	} else {
		alert('You need to select a Article')
	}
}

function selectAllArticles(form) {
	if (chosenArticles.has('option').length > 0) {
		options = $('#chosen-articles option');
		
		for (i = 0; i < options.length; i++) {
			options[i].selected = 'true';
		}
		
		form.submit();
	} else {
		showWarningModal('You need to add articles!');
	}
	
	return false;
}
