var dropdownCategories;
var chosenCategories;
var addCategoryBtn;
var removeBtn;
var moveUpBtn;
var moveDownBtn;
var categoryForm;
var sectionForm;

$(document).ready(function() {
	dropdownCategories = $('#categories');
	chosenCategories = $('#chosen-categories');
	addCategoryBtn = $('#add-category-btn');
	removeBtn = $('#remove-category-btn');
	moveUpBtn = $('#move-up-category-btn');
	moveDownBtn = $('#move-down-category-btn');
	categoryForm = $('#section-form-category');
	sectionForm = $('#sectionForm');
	
	addCategoryBtn.on('click', function() {
		addCategory();
	});
	
	removeBtn.on('click', function() {
		removeCategory();
	});
	
	moveUpBtn.on('click', function() {
		moveUpCategory();
	});
	
	moveDownBtn.on('click', function() {
		moveDownCategory();
	});
})

function addCategory() {
	selectedCategory = $('#categories option:selected');
	countryName = selectedCategory.text();
	categoryID = selectedCategory.val();
	
	optionElement = `<option value="${categoryID}">${countryName}</option>`;
	
	// Check if category is already added
	optionExists = false;
	
	$('#chosen-categories option').each(function() {
		if (this.value == categoryID) {
			optionExists = true;
		}
	});
	
	if (optionExists) {
		showWarningModal(countryName + ' is already added!')
	} else {
		chosenCategories.append(optionElement);
	}
}

function removeCategory() {
	selectedCategory = $('#chosen-categories option:selected').remove();
}

function moveUpCategory() {
	selectedCategory = $('#chosen-categories option:selected');
	
	if (selectedCategory.length) {
		selectedCategory.first().prev().before(selectedCategory);
	}
}

function moveDownCategory() {
	selectedCategory = $('#chosen-categories option:selected');
	
	if (selectedCategory.length) {
		selectedCategory.last().next().after(selectedCategory);
	}
}

function selectAllCategories(form) {
	if (chosenCategories.has('option').length > 0) {
		options = $('#chosen-categories option');
		
		for (i = 0; i < options.length; i++) {
			options[i].selected = 'true';
		}
		
		form.submit();
	} else {
		showWarningModal('You need to add categories!');
	}
	
	return false;
}
