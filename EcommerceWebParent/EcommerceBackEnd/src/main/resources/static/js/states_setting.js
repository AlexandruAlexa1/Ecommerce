let loadButton;
let dropDownCountries;
let dropDownStates;
let addStateBtn;
let updateStateBtn;
let deleteStateBtn;
let fieldStateName;

$(document).ready(function() {
	loadButton = $("#loadCountriesBtn");
	dropDownCountries = $("#dropDownCountriesForStates");
	dropDownStates = $("#dropDownStates");
	addStateBtn = $("#add-state-btn");
	updateStateBtn = $("#update-state-btn");
	deleteStateBtn = $("#delete-state-btn");
	fieldStateName = $("#fieldStateName");
	
	loadButton.click(function() {
		loadCountries4States();
	});
	
	dropDownCountries.on("change", function() {
		loadStates();
	});
	
	dropDownStates.on("change", function() {
		changeFormState();
	});
	
	addStateBtn.click(function() {
		if (addStateBtn.val() == "Add") {
			addState();
		} else {
			changeFormStateToNew();
		}
	});
	
	updateStateBtn.click(function() {
		updateState();
	});
	
	deleteStateBtn.click(function() {
		deleteState();
	});
});

function deleteState() {
	selectedState = $("#dropDownStates option:selected");
	stateId = selectedState.val();
	url = contextPath + "states/delete/" + stateId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownStates option[value='" + stateId + "']").remove();
		changeFormStateToNew();
		showStateMessage("The State: " + selectedState.text() + " has been deleted!");
	}).fail(function() {
		showStateMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function updateState() {
	if (!validateFormState()) return;
	
	url = contextPath + "states/save";
	stateId = dropDownStates.val().split("-")[0]; 
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val().split("-")[0];
	countryName = selectedCountry.text();
	
	data = {id: stateId, name: stateName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(data),
		contentType: 'application/json'
	}).done(function(stateId) {
		$("#dropDownStates option:selected").text(stateName);
		showStateMessage("The state has beeen updated")
	}).fail(function() {
		showStateMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function validateFormState() {
	formCountry = $("#formState");
	if (!formCountry.checkValidity()) {
		formCountry.reportValidity();
		false;
	}
	
	return true;
}

function addState() {
	if (!validateFormState()) return;
	
	url = contextPath + "states/save";
	stateName = fieldStateName.val();
	
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val().split("-")[0];
	countryName = selectedCountry.text();
	
	jsonData = {name: stateName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId) {
		selectNewlyAddedState(stateId, stateName);
		showStateMessage("The new state has beeen added");
	}).fail(function() {
		showStateMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function selectNewlyAddedState(stateId, stateName) {
	optionValue = stateId;
	$("<option>").val(optionValue).text(stateName).appendTo(dropDownStates);
	
	$("#dropDownStates option[value='" + optionValue + "']").prop("selected", true);
	
	fieldStateName.val("");
	fieldStateName.val("").focus();
}

function changeFormState() {
	selectedStateName = $("#dropDownStates option:selected").text();
	fieldStateName.val(selectedStateName);
	
	addStateBtn.val("New");
	updateStateBtn.prop("disabled", false);
	deleteStateBtn.prop("disabled", false);
}

function loadStates() {
	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val().split("-")[0];
	url = contextPath + "states/list_by_country/" + countryId;
	
	$.get(url, function(response) {
		dropDownStates.empty();
		
		$.each(response, function(index, state) {
			$("<option>").val(state.id).text(state.name).appendTo(dropDownStates);
		});
	}).done(function() {
		changeFormStateToNew();
		showStateMessage("All states have been loaded for country " + selectedCountry.text());
	}).fail(function() {
		showStateMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function changeFormStateToNew() {
	fieldStateName.val("");
	
	addStateBtn.val("Add");
	updateStateBtn.prop("disabled", true);
	deleteStateBtn.prop("disabled", true);
}

function loadCountries4States() {
	url = contextPath + "countries/list";
	
	$.get(url, function(response) {
		dropDownCountries.empty();
		
		$.each(response, function(index, country) {
			value = country.id + "-" + country.code;
			$("<option>").val(value).text(country.name).appendTo(dropDownCountries);
		});
	}).done(function() {
		showStateMessage("All countries have been loaded");
	}).fail(function() {
		showStateMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function showStateMessage(value) {
	message = $("#state-message");
	message.css("display", "block");
	message.text(value);
	
	setTimeout(() => {
		message.hide();
	}, 10000)
} 