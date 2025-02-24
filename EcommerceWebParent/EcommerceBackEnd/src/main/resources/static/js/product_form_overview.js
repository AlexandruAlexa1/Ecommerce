dropdownBrands = $("#brand");
dropdownCategories = $("#category");
	
$(document).ready(function() {
	$("#shortDescription").richText();
	$("#fullDescription").richText();
		
	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();
	})
		
	getCategoriesForNewForm();
});

function getCategoriesForNewForm()  {
	catIdField = $('#categoryId');
	editMode = false;
	
	if (catIdField.length) {
		editMode = true;
	}
	
	if (!editMode) getCategories();
}
	
function getCategories() {
	brandId = dropdownBrands.val();
	url = brandModuleUrl + "/" + brandId + "/categories";
		
	$.get(url, function(response) {
		$.each(response, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}
	
function checkUnique(form) {
	id = $("#id").val();
	name = $("#name").val();
	_csrf = $("input[name = '_csrf']").val();
	
	data = {id: id, name: name, _csrf: _csrf};
		
	$.post(checkUniqueURL, data, function(response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicate") {
			showWarningModal('There is another product having same name: ' + name);
		} else {
			showWarningModal('Unknow response from server');
		}
	}).fail(function() {
		showWarningModal("Could not connect to the server");
	});
		
	return false;
}