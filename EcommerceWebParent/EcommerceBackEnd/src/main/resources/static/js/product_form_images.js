let extraImagesCount = 0;
	
$(document).ready(function() {
	
	$("input[name = 'extraImage']").each(function(index) {
		extraImagesCount++;
		
		$(this).change(function() {
			showExtraImage(this, index);
		});
	});
	
	$("a[name='linkRemoveExtraImage']").each(function(index, item) {
		$(item).click(() => {
			removeExtraImage(index);
		});
	});
	
});

function showExtraImage(inputFile, index) {
	file = inputFile.files[0];
	fileName = file.name;
	fileSize = inputFile.files[0].size;
	reader = new FileReader();
	reader.readAsDataURL(file);
	
	imageNameHiddenField = $("#imageName" + index);
	
	if (imageNameHiddenField.length) {
		imageNameHiddenField.val(fileName);
	}
	
	if (fileSize > MAX_FILE_SIZE) {
		inputFile.setCustomValidity("You must choose an image less than " + MAX_FILE_SIZE + " bytes!");
		inputFile.reportValidity();
	} else {
		inputFile.setCustomValidity("");
		
		reader.onload = (e) => {
			$('#extraImage' + index).attr('src', e.target.result);
		}
		
		if (index >= extraImagesCount -1) {
			addNextExtraImageSection(index + 1);
		}
	}
}

function addNextExtraImageSection(index) {
	linkRemove = `<a href="javascript:removeExtraImage(${index - 1})"
		class="fas fa-times-circle icon-aqua close-extra-section" title="Close"></a>`
	
	extraImage = `
		<div id="divExtraImage${index}" class="img-card">
			<label id="extraImageHeader${index}">Extra Image #${index + 1}:</label>
			<img id="extraImage${index}" src="${defaultImage}" alt="Extra image #${index + 1} preview">
			<input type="file" onchange="showExtraImage(this, ${index})" name="extraImage" accept="image/png, image/jpeg"/>
		</div>
	`;
	
	$('#divProductImages').append(extraImage);
	$('#extraImageHeader' + (index - 1)).append(linkRemove);
	
	extraImagesCount++;
}

function removeExtraImage(index) {
	$('#divExtraImage' + index).remove();
	$('#extraImageHeader' + index).remove();
}