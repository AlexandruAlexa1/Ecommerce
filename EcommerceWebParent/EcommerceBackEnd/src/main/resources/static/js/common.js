var menu;

$(document).ready(function() {
	menu = $(".menu");
	
	viewMenuList();
	handleLinkLogout();
	handleLinkCloseForm();
	handleLinkCloseWarningModal();
	
	$("#fileImage").on("change", function(){
		if (!checkFileSize(this)) {
			return;
		}
		
		showImageThumbnail(this);
	});
});

function handleLinkCloseForm() {
	$(".cancel-form-btn").on("click", function() {
		window.location = moduleUrl;
	});
	
	$("#fileImage").change(function() {
		if (!checkFileSize(this)) {
			return;
		}
		
		showImageThumbnail(this);
	});
}

function viewMenuList() {
	menu.on("click", function() {
		$(".navbar").toggleClass("change");
	});
}

function handleLinkLogout() {
	$('.logoutLink').each(function() {
		$(this).on('click', (event) => {
			event.preventDefault();
			$('#logoutForm').submit();
		})
	});
}

function showImageThumbnail(fileInput) {
	let file = fileInput.files[0];
	let reader = new FileReader();
	reader.onload = (e) => {
		$('#thumbnail').attr('src', e.target.result);
	}
	
	reader.readAsDataURL(file);
}

function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;
	
	if (fileSize > MAX_FILE_SIZE) {
		fileInput.setCustomValidity("You must choose an image less than " + MAX_FILE_SIZE + " bytes!");
		fileInput.reportValidity();
		
		return false;
	} else {
		fileInput.setCustomValidity("");
		
		return true;
	}
}

function openTab(event, tabName) {
	document.querySelectorAll(".tab-content").forEach((item) => {
		item.style.display = "none";
		item.classList.remove("active");
	})
		
	document.querySelectorAll(".tab-link").forEach((link) => {
		link.classList.remove("active");
	})
		
	document.getElementById(tabName).style.display = "block";
	event.currentTarget.classList.add("active");
}

function showWarningModal(message) {
	$(".warning-modal-message").text(message);
	$(".warning-modal").addClass('change');
}

function handleLinkCloseWarningModal() {
	$(".warning-modal-btn").on("click", function() {
		$(".warning-modal").removeClass('change');
	});
}
