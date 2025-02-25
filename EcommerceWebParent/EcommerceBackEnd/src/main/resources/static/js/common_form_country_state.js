var dropdownCountries;
var dropdownStates;

$(document).ready(function() {
	dropdownCountries = $("#country");
	dropdownStates = $("#listStates");
	fieldState = $("#state");
	
	dropdownCountries.on("change", function() {
		loadStates4Country();
		$("#state").val("").focus();
	});
	
	loadStates4Country();
});

function loadStates4Country() {
	countryId = dropdownCountries.val();
	url = contextPath + "shipping_rates/list_by_country/" + countryId;
	
	$.get(url, function(response) {
		dropdownStates.empty();
		
		$.each(response, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(dropdownStates);
		});
	});
}