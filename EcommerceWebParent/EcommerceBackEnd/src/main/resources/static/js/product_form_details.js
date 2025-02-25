$(document).ready(function() {
	$('a[name = "linkRemoveDetail"]').each(function(index) {
		$(this).click(function() {
			removeDetailSectionByIndex(index);
		});
	});
});

function addNextDetailSection() {
	allDivDetails = $("[id^='divDetail']");
	divDetailsCount = allDivDetails.length;
	
	detailSection = `
		<div id="divDetail${divDetailsCount}">
			<input type="hidden" name="detailIDs" value="0" />
			<label>Name:</label>
			<input type="text" name="detailNames" maxlength="255" />
			<label>Value:</label>
			<input type="text" name="detailValues" maxlength="255" />
		</div>
	`;
	
	$('#divProductDetails').append(detailSection);
	
	previousDetailSection = allDivDetails.last();
	previousDetailSectionID = previousDetailSection.attr("id");
	
	linkRemove = `
		<a  class="fas fa-times-circle fa-2x icon-aqua link-remove-detail"
		   	href="javascript:removeDetailSectionById('${previousDetailSectionID}')"
			title="Close"></a>
	`;
	
	previousDetailSection.append(linkRemove);
	
	$("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
	$('#' + id).remove();
}

function removeDetailSectionByIndex(index) {
	$('#divDetail' + index).remove();
}






