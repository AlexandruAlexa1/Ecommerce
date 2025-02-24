var trackRecordCount;

$(document).ready(function() {
	trackRecordCount = $(".hiddenTrackId").length + 1;
	
	$("#trackList").on("click", ".linkRemoveTrack", function(e) {
		e.preventDefault();
		deleteTrack($(this));
		updateTrackCountNumbers();
	});
	
	$("#track").on("click", ".linkAddTrack", function(e) {
		e.preventDefault();
		addNewTrackRecord();
	});
	
	$("#trackList").on("change", ".dropDownStatus", function(e) {
		dropDownList = $(this);
		rowNumber = dropDownList.attr("rowNumber");
		selectedOption = $("option:selected", dropDownList);
		
		defaultNote = selectedOption.attr("defaultDescription");
		$("#trackNote" + rowNumber).text(defaultNote);
	});
});

function deleteTrack(link) {
	rowNumber = link.attr("rowNumber");
	$("#rowTrack" + rowNumber).remove();
}

function updateTrackCountNumbers() {
	$(".divCountTrack").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
 	});
}

function addNewTrackRecord() {
	htmlCode = generateTrackCode();
	$("#trackList").append(htmlCode);
}

function generateTrackCode() {
	nextCount = trackRecordCount + 1;
	trackRecordCount++;
	rowId = "rowTrack" + nextCount;
	trackNoteId = "trackNote" + nextCount;
	currentDateTime = formatCurrentDateTime();
	
	htmlCode = `
		<div class="order-form-trak"
			id="${rowId}">
			
			<input type="hidden" name="trackId" value="0" class="hiddenTrackId" />
			
			<div class="header">
				<span class="divCountTrack">${nextCount - 1}</span>
				<a href="" class="fas fa-trash linkRemoveTrack" style="color: red;" rowNumber="${nextCount}"></a>
			</div>
			
			<div>
				<div class="input-group">
					<label>Time:</label>
					<input type="datetime-local" name="trackDate" value="${currentDateTime}"/>
				</div>
				
				<div class="input-group">
					<label>Status:</label>
					<select name="trackStatus" class="dropDownStatus" required rowNumber="${nextCount}">
				`;
	
	htmlCode += $("#trackStatusOptions").clone().html();
	
	htmlCode += `	</select>
				</div>
				
				<div class="input-group">
					<label>Notes:</label>
					<textarea rows="2" cols="10" name="trackNotes" id="${trackNoteId}"
						required></textarea>
				</div>
			</div>
		</div>
	`;
	
	return htmlCode;
}

function formatCurrentDateTime() {
	date = new Date();
	year = date.getFullYear();
	month = date.getMonth() + 1;
	day = date.getDate();
	hour = date.getHours();
	minute = date.getMinutes();
	second = date.getSeconds();
	
	if (month < 10) month = "0" + month;
	if (day < 10) day = "0" + day;
	
	if (hour < 10) hour = "0" + hour;
	if (minute < 10) minute = "0" + minute;
	if (second < 10) second = "0" + second;
	
	return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
}
