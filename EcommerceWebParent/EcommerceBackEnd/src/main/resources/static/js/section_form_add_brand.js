var dropdownBrands;
var chosenBrands;
var addBrandBtn;
var removeBtn;
var moveUpBtn;
var moveDownBtn;

$(document).ready(function() {
	dropdownBrands = $('#brands');
	chosenBrands = $('#chosen-brands');
	addBrandBtn = $('#add-brand-btn');
	removeBtn = $('#remove-brand-btn');
	moveUpBtn = $('#move-up-brand-btn');
	moveDownBtn = $('#move-down-brand-btn');
	
	addBrandBtn.on('click', function() {
		addBrand();
	});
	
	removeBtn.on('click', function() {
		removeBrand();
	});
	
	moveUpBtn.on('click', function() {
		moveUpBrand();
	});
	
	moveDownBtn.on('click', function() {
		moveDownBrand();
	});
})

function addBrand() {
	selectedBrand = $('#brands option:selected');
	brandName = selectedBrand.text();
	brandID = selectedBrand.val();
	
	optionElement = `<option value="${brandID}">${brandName}</option>`;
	
	// Check if brand is already added
	optionExists = false;
	
	$('#chosen-brands option').each(function() {
		if (this.value == brandID) {
			optionExists = true;
		}
	});
	
	if (optionExists) {
		showWarningModal(brandName +  ' is already added!')
	} else {
		chosenBrands.append(optionElement);
	}
}

function removeBrand() {
	selectedBrand = $('#chosen-brands option:selected').remove();
}

function moveUpBrand() {
	selectedBrand = $('#chosen-brands option:selected');
	
	if (selectedBrand.length) {
		selectedBrand.first().prev().before(selectedBrand);
	}
}

function moveDownBrand() {
	selectedBrand = $('#chosen-brands option:selected');
	
	if (selectedBrand.length) {
		selectedBrand.last().next().after(selectedBrand);
	}
}

function selectAllBrands(form) {
	if (chosenBrands.has('option').length > 0) {
		options = $('#chosen-brands option');
		
		for (i = 0; i < options.length; i++) {
			options[i].selected = 'true';
		}
		
		form.submit();
	} else {
		showWarningModal('You need to add brands!');
	}
	
	return false;
}
