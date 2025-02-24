let buttonLoad;
let dropDownCountry;
let addBtn;
let updateBtn;
let deleteBtn;
let labelCountryName;
let fieldCountryName;
let fieldCountryCode;

$(document).ready(function() {
	buttonLoad = $("#buttonLoadCountries");
	dropDownCountry = $("#dropDownCountires");
	addBtn = $("#add-country-btn");
	updateBtn = $("#update-country-btn");
	deleteBtn = $("#delete-country-btn");
	labelCountryName = $("#labelCountryName");
	fieldCountryName = $("#fieldCountryName");
	fieldCountryCode = $("#fieldCountryCode");
	
	buttonLoad.on("click", function() {
		loadCountries();
	});
	
	dropDownCountry.on("change", function() {
		changeFormStateToSelectedCountry();
	});
	
	addBtn.on("click", function() {
		if (addBtn.val() == "Add") {
			addCountry();
		} else {
			changeFormToNew();
		}
	});
	
	updateBtn.on("click", function() {
		updateCountry();
	});
	
	deleteBtn.on("click", function() {
		deleteCountry();
	})
});

function deleteCountry() {
	optionValue = dropDownCountry.val();
	countryId = optionValue.split("-")[0];
	
	url = contextPath + "countries/delete/" + countryId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownCountires option[value='" + optionValue + "']").remove();
		changeFormToNew();
		showMessage("The country has been deleted")
	}).fail(function() {
		showMessage("ERROR: Could not connect to server or server encountered an error")
	});
}

function updateCountry() {
	if (!validateFormCountry()) return;
	
	url = contextPath + "countries/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	
	countryId = dropDownCountry.val().split("-")[0];
	
	jasonData = {id: countryId, name: countryName, code: countryCode};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jasonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		$("#dropDownCountires option:selected").val(countryId + "-" + countryCode);
		$("#dropDownCountires option:selected").text(countryName);
		showMessage("The new country has been updated");
		
		changeFormToNew();
	}).fail(function() {
		showMessage("ERROR: Could not connect to server or server encountered an error")
	});;
}

function validateFormCountry() {
	formCountry = $("#formCountry");
	if (!formCountry.checkValidity()) {
		formCountry.reportValidity();
		false;
	}
	
	return true;
}

function addCountry() {
	if (!validateFormCountry()) return;
	
	url = contextPath + "countries/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	jsonData = {name: countryName, code: countryCode};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		selectNewlyAddedCountry(countryId, countryName, countryCode);
		showMessage("The new country has been added");
	}).fail(function() {
		showMessage("ERROR: Could not connect to server or server encountered an error")
	});;
}

function selectNewlyAddedCountry(countryId, countryName, countryCode) {
	optionValue = countryId + "-" + countryCode;
	$("<option>").val(optionValue).text(countryName).appendTo(dropDownCountry);
	
	$("#dropDownCountires option[value='" + optionValue + "']").prop("selected", true);
	
	fieldCountryCode.val("");
	fieldCountryCode.val("").focus();
}

function changeFormToNew() {
	labelCountryName.text("Country Name:");
	fieldCountryName.val("");
	fieldCountryCode.val("");
	
	addBtn.val("Add");
	updateBtn.prop("disabled", true);
	deleteBtn.prop("disabled", true);
}

function changeFormStateToSelectedCountry() {
	addBtn.prop("value", "New");
	updateBtn.prop("disabled", false);
	deleteBtn.prop("disabled", false);
	
	labelCountryName.text("Selected Country:");
	
	selectedCountryName = $("#dropDownCountires option:selected").text();
	fieldCountryName.val(selectedCountryName);
	
	countryCode = dropDownCountry.val().split("-")[1];
	fieldCountryCode.val(countryCode);
}

function loadCountries() {
	url = contextPath + "countries/list";
	$.get(url, function(response) {
		dropDownCountry.empty();
		
		$.each(response, function(index, country) {
			optionValue = country.id + "-" + country.code;
			$("<option>").val(optionValue).text(country.name).appendTo(dropDownCountry);
		});
	}).done(function() {
		buttonLoad.text("Refresh Country List");
		showMessage("All countries have been loaded")
	}).fail(function() {
		showMessage("ERROR: Could not connect to server or server encountered an error")
	});
}

function showMessage(value) {
	message = $("#country-message");
	message.css("display", "block");
	message.text(value);
	
	setTimeout(() => {
		message.css("display", "none");
	}, 5000)
}