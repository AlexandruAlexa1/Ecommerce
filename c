warning: LF will be replaced by CRLF in EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.min.css.
The file will have its original line endings in your working directory
warning: LF will be replaced by CRLF in EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.scss.
The file will have its original line endings in your working directory
warning: LF will be replaced by CRLF in EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/navigation.html.
The file will have its original line endings in your working directory
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/article/ArticleController.java b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/article/ArticleController.java[m
[1mindex 8575ffb..db2e8a3 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/article/ArticleController.java[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/article/ArticleController.java[m
[36m@@ -94,7 +94,7 @@[m [mpublic class ArticleController {[m
 		return DEFAULT_REDIRECT_URL;[m
 	}[m
 	[m
[31m-	@GetMapping("/articles/detail/{id}")[m
[32m+[m	[32m@GetMapping("/articles/modal/detail/{id}")[m
 	public String viewArticleDetail(@PathVariable("id") Integer id, Model model,[m
 			RedirectAttributes redirectAttributes) {[m
 		try {[m
[36m@@ -107,4 +107,17 @@[m [mpublic class ArticleController {[m
 			return DEFAULT_REDIRECT_URL;[m
 		}[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32m@GetMapping("/articles/detail/{id}")[m
[32m+[m	[32mpublic String viewDetail(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {[m
[32m+[m		[32mtry {[m
[32m+[m			[32mArticle article = articleService.get(id);[m
[32m+[m			[32mmodel.addAttribute("article", article);[m
[32m+[m[41m			[m
[32m+[m			[32mreturn "articles/article";[m
[32m+[m		[32m} catch (ArticleNotFoundException e) {[m
[32m+[m			[32mredirectAttributes.addFlashAttribute("message", e.getMessage());[m
[32m+[m			[32mreturn DEFAULT_REDIRECT_URL;[m
[32m+[m		[32m}[m
[32m+[m	[32m}[m
 }[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuController.java b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuController.java[m
[1mindex 4fece57..ac95540 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuController.java[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuController.java[m
[36m@@ -52,20 +52,6 @@[m [mpublic class MenuController {[m
 		return DEFAULT_REDIRECT_URL;[m
 	}[m
 	[m
[31m-	@GetMapping("/menus/detail/{id}")[m
[31m-	public String viewMenuDetail(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,[m
[31m-			Model model) {[m
[31m-		try {[m
[31m-			Menu menu = menuService.get(id);[m
[31m-			model.addAttribute("menu", menu);[m
[31m-			[m
[31m-			return "menus/menu_detail_modal";[m
[31m-		} catch (MenuNotFoundException e) {[m
[31m-			redirectAttributes.addFlashAttribute("message", e.getMessage());[m
[31m-			return DEFAULT_REDIRECT_URL;[m
[31m-		}[m
[31m-	}[m
[31m-	[m
 	@GetMapping("/menus/{id}/enabled/{status}")[m
 	public String updateEnableStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,[m
 			RedirectAttributes redirectAttributes) {[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuRepository.java b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuRepository.java[m
[1mindex b05d494..82bae82 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuRepository.java[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuRepository.java[m
[36m@@ -1,5 +1,7 @@[m
 package com.ecommerce.admin.menu;[m
 [m
[32m+[m[32mimport java.util.List;[m
[32m+[m
 import org.springframework.data.jpa.repository.JpaRepository;[m
 import org.springframework.data.jpa.repository.Modifying;[m
 import org.springframework.data.jpa.repository.Query;[m
[36m@@ -7,6 +9,10 @@[m [mimport org.springframework.data.jpa.repository.Query;[m
 import com.ecommerce.common.entity.Menu;[m
 [m
 public interface MenuRepository extends JpaRepository<Menu, Integer> {[m
[32m+[m[41m	[m
[32m+[m	[32m@Query("SELECT m FROM Menu m ORDER BY m.type DESC, m.position ASC")[m
[32m+[m	[32mpublic List<Menu> findAll();[m
[32m+[m[41m	[m
 	Long countById(Integer id);[m
 	[m
 	@Query("UPDATE Menu m SET m.enabled = ?2 WHERE m.id = ?1")[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuService.java b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuService.java[m
[1mindex 0f94b57..6988079 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuService.java[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/menu/MenuService.java[m
[36m@@ -6,7 +6,6 @@[m [mimport java.util.NoSuchElementException;[m
 import javax.transaction.Transactional;[m
 [m
 import org.springframework.beans.factory.annotation.Autowired;[m
[31m-import org.springframework.data.domain.Sort;[m
 import org.springframework.stereotype.Service;[m
 [m
 import com.ecommerce.common.entity.Menu;[m
[36m@@ -19,9 +18,7 @@[m [mpublic class MenuService {[m
 	@Autowired private MenuRepository repo;[m
 	[m
 	public List<Menu> listAll() {[m
[31m-		Sort sort = Sort.by("type").descending();[m
[31m-		List<Menu> listMenus = repo.findAll(sort);[m
[31m-		[m
[32m+[m		[32mList<Menu> listMenus = repo.findAll();[m
 		return listMenus;[m
 	}[m
 [m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/security/WebSecurityConfig.java b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/security/WebSecurityConfig.java[m
[1mindex a094bcb..24f1df5 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/security/WebSecurityConfig.java[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/java/com/ecommerce/admin/security/WebSecurityConfig.java[m
[36m@@ -59,7 +59,7 @@[m [mpublic class WebSecurityConfig extends WebSecurityConfigurerAdapter {[m
 			[m
 			.antMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "SalesPerson", "Shipper")[m
 			[m
[31m-			.antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "SalesPerson", "Assistant")[m
[32m+[m			[32m.antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "SalesPerson", "Assistant", "Shipper")[m
 			[m
 			.antMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin", "SalesPerson")[m
 			[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/css/style.css b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/css/style.css[m
[1mindex f2e520b..9262e1c 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/css/style.css[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/css/style.css[m
[36m@@ -117,6 +117,19 @@[m [mbody {[m
 	}[m
 }[m
 [m
[32m+[m[32m/* Scrollbar */[m
[32m+[m[32m::-webkit-scrollbar {[m
[32m+[m[32m  width: 1rem;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m::-webkit-scrollbar-track {[m
[32m+[m[32m  box-shadow: inset 0 0 5px #000;[m[41m [m
[32m+[m[32m}[m
[32m+[m[41m [m
[32m+[m[32m::-webkit-scrollbar-thumb {[m
[32m+[m[32m  background: #555;[m[41m [m
[32m+[m[32m}[m
[32m+[m
 /* Icons colors */[m
 .icon-silver {[m
 	color: #999;[m
[36m@@ -398,13 +411,17 @@[m [mbody {[m
     background-color: #333;[m
 }[m
 [m
[32m+[m[32m.common-table th,[m
[32m+[m[32m.common-table tr {[m
[32m+[m	[32mborder-bottom: .1rem solid var(--primaryColor);[m
[32m+[m[32m}[m
[32m+[m
 .common-table th,[m
 .common-table th a,[m
[31m-.common-table tr td {[m
[32m+[m[32m.common-table tr {[m
 	font-size: 1.7rem;[m
     color: var(--primaryColor);[m
     font-weight: 600;[m
[31m-    border-bottom: .1rem solid var(--primaryColor);[m
     font-weight: 300;[m
     text-align: left;[m
 }[m
[36m@@ -446,20 +463,13 @@[m [mbody {[m
 [m
 @media screen and (max-width: 1500px) {[m
     .table-wrapper {[m
[31m-        width: 120rem;[m
[31m-    }[m
[31m-}[m
[31m-[m
[31m-@media screen and (max-width: 1250px) {[m
[31m-    .table-wrapper {[m
[31m-        width: 100rem;[m
[32m+[m[32m        width: 95%;[m
     }[m
 }[m
 [m
[31m-@media screen and (max-width: 1050px) {[m
[32m+[m[32m@media screen and (max-width: 1200px) {[m
     .table-wrapper {[m
[31m-        width: 100%;[m
[31m-        padding: 0 1rem;[m
[32m+[m[32m        width: 85rem;[m
     }[m
     [m
     .common-table {[m
[36m@@ -527,6 +537,12 @@[m [mbody {[m
     }[m
 }[m
 [m
[32m+[m[32m@media screen and (max-width: 900px) {[m
[32m+[m[32m    .table-wrapper {[m
[32m+[m[32m        width: 95%;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[32m+[m
 @media screen and (max-width: 800px) {[m
     .links-wrapper a {[m
         letter-spacing: .2rem;[m
[36m@@ -936,6 +952,66 @@[m [mbody {[m
 }[m
 /* End of Detail Modal */[m
 [m
[32m+[m[32m/* Warning Modal */[m
[32m+[m[32m.warning-modal {[m
[32m+[m	[32mwidth: 100%;[m
[32m+[m	[32mheight: 100vh;[m
[32m+[m	[32mbackground: rgba(0, 0, 0, 0.4);[m
[32m+[m	[32mposition: fixed;[m
[32m+[m	[32mtop: 0;[m
[32m+[m	[32mleft: 0;[m
[32m+[m	[32mz-index: 1;[m
[32m+[m	[32mdisplay: none;[m
[32m+[m	[32mopacity: 0;[m
[32m+[m	[32mtransition: opacity .3s;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.change.warning-modal {[m
[32m+[m	[32mdisplay: block;[m
[32m+[m	[32mopacity: 1;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-content {[m
[32m+[m	[32mwidth: 50rem;[m
[32m+[m	[32mbackground-color: #fff;[m
[32m+[m	[32mposition: absolute;[m
[32m+[m	[32mtop: 10rem;[m
[32m+[m	[32mleft: 50%;[m
[32m+[m	[32mtransform: translateX(-50%);[m
[32m+[m	[32mfont-size: 1.8rem;[m
[32m+[m	[32mpadding: .5rem;[m
[32m+[m	[32mbox-shadow: .2rem .4rem .8rem #000;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-content h2 {[m
[32m+[m	[32mtext-transform: uppercase;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-message {[m
[32m+[m	[32mpadding: 2rem 0;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-btn {[m
[32m+[m	[32mfloat: right;[m
[32m+[m	[32mfont-size: 1.6rem;[m
[32m+[m	[32mbackground-color: var(--primaryColor);[m
[32m+[m	[32mcolor: #fff;[m
[32m+[m	[32mletter-spacing: .2rem;[m
[32m+[m	[32mtext-shadow: .1rem .1rem .1rem #000;[m
[32m+[m	[32mbox-shadow: .1rem .1rem .1rem #000;[m
[32m+[m	[32mpadding: 0 .7rem;[m
[32m+[m	[32mfont-weight: bold;[m
[32m+[m	[32mcursor: pointer;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m@media screen and (max-width: 550px) {[m
[32m+[m	[32m.warning-modal-content {[m
[32m+[m		[32mwidth: 95%;[m
[32m+[m		[32mfont-size: 1.6rem;[m
[32m+[m	[32m}[m
[32m+[m[32m}[m
[32m+[m[32m/* End of Warning Modal */[m
[32m+[m
 /* Product Detail Modal */[m
 .product-detail-modal {[m
 	width: 110rem;[m
[36m@@ -1269,7 +1345,7 @@[m [mbody {[m
 [m
 .common-form {[m
     width: 100rem;[m
[31m-	margin: 0 auto;[m
[32m+[m	[32mmargin: 0 auto 5rem auto;[m
 	background: rgba(0, 0, 0, 0.2);[m
 	padding: 3rem 3rem 2rem 1rem;[m
 	box-shadow: .5rem .5rem 2rem rgba(0, 0, 0, 0.4);[m
[36m@@ -1329,7 +1405,8 @@[m [mbody {[m
 .common-form .input-group label,[m
 .common-form .list-roles label,[m
 .input-group-textarea label,[m
[31m-.input-group-select label {[m
[32m+[m[32m.input-group-select label,[m
[32m+[m[32m.input-group-checkbox label {[m
     font-size: 1.8rem;[m
     margin-right: 2rem;[m
     white-space: nowrap;[m
[36m@@ -1384,12 +1461,6 @@[m [mbody {[m
 	font-weight: 600;[m
 }[m
 [m
[31m-.enable {[m
[31m-	font-size: 1.8rem;[m
[31m-	color: var(--primaryColor);[m
[31m-	margin-bottom: 1rem;[m
[31m-}[m
[31m-[m
 .upload-img {[m
 	display: flex;[m
 	margin: 3rem 0;[m
[36m@@ -1448,6 +1519,12 @@[m [mbody {[m
 	font-size: 1.8rem;[m
 }[m
 [m
[32m+[m[32m.input-group-checkbox {[m
[32m+[m	[32mdisplay: flex;[m
[32m+[m[41m  [m	[32malign-items: center;[m
[32m+[m	[32mmargin-bottom: 2rem;[m
[32m+[m[32m}[m
[32m+[m
 .input-group input[type="number"] {[m
 	width: 20%;[m
 	background: transparent;[m
[36m@@ -1560,6 +1637,15 @@[m [mbody {[m
 	.common-form::after {[m
 	    width: 0;[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32m.common-form .input-group {[m
[32m+[m		[32mflex-direction: column;[m
[32m+[m		[32malign-items: unset;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
[32m+[m	[32m.input-group-textarea {[m
[32m+[m		[32mflex-direction: column;[m
[32m+[m	[32m}[m
 }[m
 [m
 @media screen and (max-width: 450px) {[m
[36m@@ -2330,6 +2416,9 @@[m [mbody {[m
 [m
 .order-form-product .details {[m
 	width: 100%;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.order-form-product .details a {[m
 	display: flex;[m
 }[m
 [m
[36m@@ -2370,7 +2459,6 @@[m [mbody {[m
 .iframe-add-product {[m
 	width: 100%;[m
 	height: 100vh;[m
[31m-	margin: 5rem 0;[m
 }[m
 [m
 .order-form-btn:hover {[m
[36m@@ -2395,4 +2483,12 @@[m [mbody {[m
 		font-size: 1.6rem;[m
 	}[m
 }[m
[31m-/* End of Order Form */[m
\ No newline at end of file[m
[32m+[m[32m/* End of Order Form */[m
[32m+[m
[32m+[m[32m/* Article */[m
[32m+[m[32m.article-wrapper {[m
[32m+[m	[32mwidth: 95%;[m
[32m+[m	[32mmargin: 0 auto;[m
[32m+[m	[32mfont-size: 1.6rem;[m
[32m+[m[32m}[m
[32m+[m[32m/* End of Article */[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/common.js b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/common.js[m
[1mindex 89422e0..3f78569 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/common.js[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/common.js[m
[36m@@ -6,6 +6,7 @@[m [m$(document).ready(function() {[m
 	viewMenuList();[m
 	handleLinkLogout();[m
 	handleLinkCloseForm();[m
[32m+[m	[32mhandleLinkCloseWarningModal();[m
 	[m
 	$("#fileImage").on("change", function(){[m
 		if (!checkFileSize(this)) {[m
[36m@@ -101,4 +102,15 @@[m [mfunction openTab(event, tabName) {[m
 		[m
 	document.getElementById(tabName).style.display = "block";[m
 	event.currentTarget.classList.add("active");[m
[31m-}[m
\ No newline at end of file[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32mfunction showWarningModal(message) {[m
[32m+[m	[32m$(".warning-modal-message").text(message);[m
[32m+[m	[32m$(".warning-modal").addClass('change');[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32mfunction handleLinkCloseWarningModal() {[m
[32m+[m	[32m$(".warning-modal-btn").on("click", function() {[m
[32m+[m			[32m$(".warning-modal").removeClass('change');[m
[32m+[m	[32m})[m
[32m+[m[32m}[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_add_product.js b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_add_product.js[m
[1mindex b66477c..17bd197 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_add_product.js[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_add_product.js[m
[36m@@ -45,7 +45,7 @@[m [mfunction getShippingCost(productId) {[m
 	}).done(function(shippingCost) {[m
 		getProductInfo(productId, shippingCost);[m
 	}).fail(function(err) {[m
[31m-		alert(err.responseJSON.message);[m
[32m+[m		[32mshowWarningModal(err.responseJSON.message);[m
 		shippingCost = 0.0;[m
 		getProductInfo(productId, shippingCost);[m
 	}).always(function() {[m
[36m@@ -68,7 +68,7 @@[m [mfunction getProductInfo(productId, shippingCost) {[m
 		[m
 		updateOrderAmounts();[m
 	}).fail(function(err) {[m
[31m-		alert(err.responseJSON.message);[m
[32m+[m		[32mshowWarningModal(err.responseJSON.message);[m
 	});[m
 }[m
 [m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_remove_product.js b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_remove_product.js[m
[1mindex 3ec6a46..0ef58b3 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_remove_product.js[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/order_form_remove_product.js[m
[36m@@ -3,7 +3,7 @@[m [m$(document).ready(function() {[m
 		e.preventDefault();[m
 		[m
 		if (doesOrderHaveOnlyOneProduct()) {[m
[31m-			alert("Could not remove product. The order must have eat least one product.");[m
[32m+[m			[32mshowWarningModal("Could not remove product. The order must have eat least one product.");[m
 			updateOrderAmounts();[m
 		} else {[m
 			removeProduct($(this));[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/product_form_overview.js b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/product_form_overview.js[m
[1mindex c00f07d..2744583 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/product_form_overview.js[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/js/product_form_overview.js[m
[36m@@ -46,12 +46,12 @@[m [mfunction checkUnique(form) {[m
 		if (response == "OK") {[m
 			form.submit();[m
 		} else if (response == "Duplicate") {[m
[31m-			alert('There is another product having same name: ' + name);[m
[32m+[m			[32mshowWarningModal('There is another product having same name: ' + name);[m
 		} else {[m
[31m-			alert('Unknow response from server');[m
[32m+[m			[32mshowWarningModal('Unknow response from server');[m
 		}[m
 	}).fail(function() {[m
[31m-		alert("Could not connect to the server");[m
[32m+[m		[32mshowWarningModal("Could not connect to the server");[m
 	});[m
 		[m
 	return false;[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.min.css b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.min.css[m
[1mindex 1e1ec8d..fc6aabd 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.min.css[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/static/richtext/richtext.min.css[m
[36m@@ -20,4 +20,4 @@[m
  along with this program.  If not, see <http://www.gnu.org/licenses/>.[m
 */[m
 [m
[31m-.richText {position: relative;background-color: #FAFAFA;border: #EFEFEF solid 1px;color: #333333;width: 100%;}.richText .richText-form {font-family: Calibri,Verdana,Helvetica,sans-serif;}.richText .richText-form label {display: block;padding: 10px 15px;}.richText .richText-form input[type="text"], .richText .richText-form input[type="file"], .richText .richText-form input[type="number"], .richText .richText-form select {padding: 10px 15px;border: #999999 solid 1px;min-width: 200px;width: 100%;}.richText .richText-form select {cursor: pointer;}.richText .richText-form button {margin: 10px 0;padding: 10px 15px;background-color: #3498db;border: none;color: #FAFAFA;cursor: pointer;-webkit-appearance: none;-moz-appearance: none;appearance: none;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;}.richText .richText-toolbar {min-height: 20px;border-bottom: #EFEFEF solid 1px;}.richText .richText-toolbar .richText-length {font-family: Verdana, Helvetica, sans-serif;font-size: 13px;vertical-align: middle;line-height: 34px;}.richText .richText-toolbar .richText-length .black {color: #000;}.richText .richText-toolbar .richText-length .orange {color: orange;}.richText .richText-toolbar .richText-length .red {color: red;}.richText .richText-toolbar ul {padding-left: 0;padding-right: 0;margin-top: 0;margin-bottom: 0;}.richText .richText-toolbar ul li {float: left;display: block;list-style: none;}.richText .richText-toolbar ul li a {display: block;padding: 10px 13px;border-right: #EFEFEF solid 1px;cursor: pointer;-webkit-transition: background-color 0.4s;-moz-transition: background-color 0.4s;transition: background-color 0.4s;}.richText .richText-toolbar ul li a .fa, .richText .richText-toolbar ul li a .fas, .richText .richText-toolbar ul li a .far, .richText .richText-toolbar ul li a svg {pointer-events: none;}.richText .richText-toolbar ul li a .richText-dropdown-outer {display: none;position: absolute;top: 0;left: 0;right: 0;bottom: 0;background-color: rgba(0, 0, 0, 0.3);cursor: default;}.richText .richText-toolbar ul li a .richText-dropdown-outer .richText-dropdown {position: relative;display: block;margin: 3% auto 0 auto;background-color: #FAFAFA;border: #EFEFEF solid 1px;min-width: 100px;width: 300px;max-width: 90%;-webkit-box-shadow: 0 0 5px 0 #333;-moz-box-shadow: 0 0 5px 0 #333;box-shadow: 0 0 5px 0 #333;}.richText .richText-toolbar ul li a .richText-dropdown-outer .richText-dropdown .richText-dropdown-close {position: absolute;top: 0;right: -23px;background: #FFF;color: #333;cursor: pointer;font-size: 20px;text-align: center;width: 20px;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown {list-style: none;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li {display: block;float: none;font-family: Calibri,Verdana,Helvetica,sans-serif;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li a {display: block;padding: 10px 15px;border-bottom: #EFEFEF solid 1px;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li a:hover {background-color: #FFFFFF;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline {margin: 10px 6px;float: left;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline a {display: block;padding: 0;margin: 0;border: none;-webkit-border-radius: 50%;-moz-border-radius: 50%;border-radius: 50%;-webkit-box-shadow: 0 0 10px 0 #999;-moz-box-shadow: 0 0 10px 0 #999;box-shadow: 0 0 10px 0 #999;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline a span {display: block;height: 30px;width: 30px;-webkit-border-radius: 50%;-moz-border-radius: 50%;border-radius: 50%;}.richText .richText-toolbar ul li a .richText-dropdown-outer div.richText-dropdown {padding: 10px 15px;}.richText .richText-toolbar ul li a:hover {background-color: #FFFFFF;}.richText .richText-toolbar ul li[data-disable="true"] {opacity: 0.1;}.richText .richText-toolbar ul li[data-disable="true"] a {cursor: default;}.richText .richText-toolbar ul li:not([data-disable="true"]).is-selected .richText-dropdown-outer {display: block;}.richText .richText-toolbar ul:after {display: block;content: "";clear: both;}.richText .richText-toolbar:last-child {font-size: 12px;}.richText .richText-toolbar:after {display: block;clear: both;content: "";}.richText .richText-editor {padding: 20px;background-color: #FFFFFF;border-left: #FFFFFF solid 2px;font-family: Calibri,Verdana,Helvetica,sans-serif;height: 300px;outline: none;overflow-y: scroll;overflow-x: auto;}.richText .richText-editor ul, .richText .richText-editor ol {margin: 10px 25px;}.richText .richText-editor table {margin: 10px 0;border-spacing: 0;width: 100%;}.richText .richText-editor table td, .richText .richText-editor table th {padding: 10px;border: #EFEFEF solid 1px;}.richText .richText-editor:focus {border-left: #3498db solid 2px;}.richText .richText-initial {margin-bottom: -4px;padding: 10px;background-color: #282828;border: none;color: #33FF33;font-family: Monospace,Calibri,Verdana,Helvetica,sans-serif;max-width: 100%;min-width: 100%;width: 100%;min-height: 400px;height: 400px;}.richText .richText-help {float: right;display: block;padding: 10px 15px;cursor: pointer;}.richText .richText-undo, .richText .richText-redo {float: left;display: block;padding: 10px 15px;border-right: #EFEFEF solid 1px;cursor: pointer;}.richText .richText-undo.is-disabled, .richText .richText-redo.is-disabled {opacity: 0.4;}.richText .richText-help-popup a {color: #3498db;text-decoration: underline;}.richText .richText-help-popup hr {margin: 10px auto 5px auto;border: none;border-top: #EFEFEF solid 1px;}.richText .richText-list.list-rightclick {position: absolute;background-color: #FAFAFA;border-right: #EFEFEF solid 1px;border-bottom: #EFEFEF solid 1px;}.richText .richText-list.list-rightclick li {padding: 5px 7px;cursor: pointer;list-style: none;}[m
\ No newline at end of file[m
[32m+[m[32m.richText {position: relative;background-color: #00cccc;border: #EFEFEF solid 1px;color: #333333;width: 100%;}.richText .richText-form {font-family: Calibri,Verdana,Helvetica,sans-serif;}.richText .richText-form label {display: block;padding: 10px 15px;}.richText .richText-form input[type="text"], .richText .richText-form input[type="file"], .richText .richText-form input[type="number"], .richText .richText-form select {padding: 10px 15px;border: #999999 solid 1px;min-width: 200px;width: 100%;}.richText .richText-form select {cursor: pointer;}.richText .richText-form button {margin: 10px 0;padding: 10px 15px;background-color: #3498db;border: none;color: #FAFAFA;cursor: pointer;-webkit-appearance: none;-moz-appearance: none;appearance: none;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;}.richText .richText-toolbar {min-height: 20px;border-bottom: #EFEFEF solid 1px;}.richText .richText-toolbar .richText-length {font-family: Verdana, Helvetica, sans-serif;font-size: 13px;vertical-align: middle;line-height: 34px;}.richText .richText-toolbar .richText-length .black {color: #000;}.richText .richText-toolbar .richText-length .orange {color: orange;}.richText .richText-toolbar .richText-length .red {color: red;}.richText .richText-toolbar ul {padding-left: 0;padding-right: 0;margin-top: 0;margin-bottom: 0;}.richText .richText-toolbar ul li {float: left;display: block;list-style: none;}.richText .richText-toolbar ul li a {display: block;padding: 10px 13px;border-right: #EFEFEF solid 1px;cursor: pointer;-webkit-transition: background-color 0.4s;-moz-transition: background-color 0.4s;transition: background-color 0.4s;}.richText .richText-toolbar ul li a .fa, .richText .richText-toolbar ul li a .fas, .richText .richText-toolbar ul li a .far, .richText .richText-toolbar ul li a svg {pointer-events: none;}.richText .richText-toolbar ul li a .richText-dropdown-outer {display: none;position: absolute;top: 0;left: 0;right: 0;bottom: 0;background-color: rgba(0, 0, 0, 0.3);cursor: default;}.richText .richText-toolbar ul li a .richText-dropdown-outer .richText-dropdown {position: relative;display: block;margin: 3% auto 0 auto;background-color: #FAFAFA;border: #EFEFEF solid 1px;min-width: 100px;width: 300px;max-width: 90%;-webkit-box-shadow: 0 0 5px 0 #333;-moz-box-shadow: 0 0 5px 0 #333;box-shadow: 0 0 5px 0 #333;}.richText .richText-toolbar ul li a .richText-dropdown-outer .richText-dropdown .richText-dropdown-close {position: absolute;top: 0;right: -23px;background: #FFF;color: #333;cursor: pointer;font-size: 20px;text-align: center;width: 20px;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown {list-style: none;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li {display: block;float: none;font-family: Calibri,Verdana,Helvetica,sans-serif;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li a {display: block;padding: 10px 15px;border-bottom: #EFEFEF solid 1px;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li a:hover {background-color: #FFFFFF;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline {margin: 10px 6px;float: left;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline a {display: block;padding: 0;margin: 0;border: none;-webkit-border-radius: 50%;-moz-border-radius: 50%;border-radius: 50%;-webkit-box-shadow: 0 0 10px 0 #999;-moz-box-shadow: 0 0 10px 0 #999;box-shadow: 0 0 10px 0 #999;}.richText .richText-toolbar ul li a .richText-dropdown-outer ul.richText-dropdown li.inline a span {display: block;height: 30px;width: 30px;-webkit-border-radius: 50%;-moz-border-radius: 50%;border-radius: 50%;}.richText .richText-toolbar ul li a .richText-dropdown-outer div.richText-dropdown {padding: 10px 15px;}.richText .richText-toolbar ul li a:hover {background-color: #FFFFFF;}.richText .richText-toolbar ul li[data-disable="true"] {opacity: 0.1;}.richText .richText-toolbar ul li[data-disable="true"] a {cursor: default;}.richText .richText-toolbar ul li:not([data-disable="true"]).is-selected .richText-dropdown-outer {display: block;}.richText .richText-toolbar ul:after {display: block;content: "";clear: both;}.richText .richText-toolbar:last-child {font-size: 12px;}.richText .richText-toolbar:after {display: block;clear: both;content: "";}.richText .richText-editor {font-size: 1.7rem; padding: 10px;background-color: #FFFFFF;border-left: #FFFFFF solid 2px;font-family: Calibri,Verdana,Helvetica,sans-serif;height: 300px;outline: none;overflow-y: scroll;overflow-x: auto;}.richText .richText-editor ul, .richText .richText-editor ol {margin: 10px 25px;}.richText .richText-editor table {margin: 10px 0;border-spacing: 0;width: 100%;}.richText .richText-editor table td, .richText .richText-editor table th {padding: 10px;border: #EFEFEF solid 1px;}.richText .richText-editor:focus {border-left: #3498db solid 2px;}.richText .richText-initial {margin-bottom: -4px;padding: 10px;background-color: #282828;border: none;color: #33FF33;font-family: Monospace,Calibri,Verdana,Helvetica,sans-serif;max-width: 100%;min-width: 100%;width: 100%;min-height: 400px;height: 400px;}.richText .richText-help {float: right;display: block;padding: 10px 15px;cursor: pointer;}.richText .richText-undo, .richText .richText-redo {float: left;display: block;padding: 10px 15px;border-right: #EFEFEF solid 1px;cursor: pointer;}.richText .richText-undo.is-disabled, .richText .richText-redo.is-disabled {opacity: 0.4;}.richText .richText-help-popup a {color: #3498db;text-decoration: underline;}.richText .richText-help-popup hr {margin: 10px auto 5px auto;border: none;border-top: #EFEFEF solid 1px;}.richText .richText-list.list-rightclick {position: absolute;background-color: #FAFAFA;border-right: #EFEFEF solid 1px;border-bottom: #EFEFEF solid 1px;}.richText .richText-list.list-rightclick li {padding: 5px 7px;cursor: pointer;list-style: none;}[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_detail_modal.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_detail_modal.html[m
[1mindex 2e67941..06255f4 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_detail_modal.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_detail_modal.html[m
[36m@@ -13,7 +13,7 @@[m
 	[m
 	<div class="detail-modal-input">[m
 		<label>Content:</label>[m
[31m-		<textarea th:utext="${article.content}"></textarea>[m
[32m+[m		[32m<iframe style="width:100%; height: 50rem;" th:src="@{'/articles/detail/' + ${article.id}}"></iframe>[m
 	</div>[m
 	[m
 	<div class="detail-modal-input">[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_form.html[m
[1mindex d49f6d4..fb8c98d 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/article_form.html[m
[36m@@ -27,7 +27,7 @@[m
 			placeholder="If leaved empty, default alias will be same as title with spaces replaced by dashes" />[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-textarea">[m
 		<label>Content:</label>[m
 		<textarea th:field="*{content}" id="articleContent" required></textarea>[m
 	</div>[m
[36m@@ -40,7 +40,7 @@[m
 		</select>[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Published:</label>[m
 		<input type="checkbox" th:field="*{published}" />[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/articles.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/articles.html[m
[1mindex 0fa7ee9..6b1955f 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/articles.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/articles/articles.html[m
[36m@@ -45,7 +45,7 @@[m
 			<td>[m
 				<div class="table-links">[m
 					<a class="fas fa-file-alt icon-aqua link-article"[m
[31m-						th:href="@{'/articles/detail/' + ${article.id}}"[m
[32m+[m						[32mth:href="@{'/articles/modal/detail/' + ${article.id}}"[m
 						title="View details of this article"></a>[m
 					<div th:replace="fragments :: edit('/articles/edit/' + ${article.id}, 'article')"></div>[m
 					<div th:replace="fragments :: delete('/articles/delete/' + ${article.id}, ${article.id}, 'article', 'none')"></div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/categories/category_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/categories/category_form.html[m
[1mindex 1cce71f..d0f0cda 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/categories/category_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/categories/category_form.html[m
[36m@@ -35,7 +35,7 @@[m
 			th:required="${category.imagePath == null}" />[m
 		<img th:src="@{${category.imagePath}}" alt="Image preview" id="thumbnail">[m
 	</div>[m
[31m-	<div class="enable">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Enabled:</label>[m
 		<input type="checkbox" th:field="*{enabled}"/>[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customer_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customer_form.html[m
[1mindex 6252dfe..737ea73 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customer_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customer_form.html[m
[36m@@ -72,7 +72,7 @@[m
 		<input type="text" th:field="*{postalCode}" required minlength="3" maxlength="10"/>[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Enabled</label>[m
 		<input type="checkbox" th:field="*{enabled}"/>[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customers.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customers.html[m
[1mindex 1e5d7d0..d8972c3 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customers.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/customers/customers.html[m
[36m@@ -88,7 +88,7 @@[m
 [m
 [m
 <!-- Pagination -->[m
[31m-<div th:replace="fragments :: pagination('customer')"></div>[m
[32m+[m[32m<div th:replace="fragments :: pagination('customers')"></div>[m
 </div>[m
 [m
 <!-- Confirm Modal-->[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/fragments.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/fragments.html[m
[1mindex 5a16099..36dfa64 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/fragments.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/fragments.html[m
[36m@@ -74,6 +74,15 @@[m
 	<div class="detail-modal-content"></div>[m
 </div>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:fragment="warning-modal" class="warning-modal">[m
[32m+[m	[32m<div class="warning-modal-content">[m
[32m+[m		[32m<h2>Warning</h2>[m
[32m+[m		[32m<p class="warning-modal-message">Message</p>[m
[32m+[m		[32m<a class="warning-modal-btn">Close</a>[m
[32m+[m	[32m</div>[m
[32m+[m[32m</div>[m
[32m+[m
 <!-- Pagination -->[m
 <a th:fragment="page_link(pageNumber, label)" [m
 	th:href="@{${moduleURL} + '/page/' + ${pageNumber} + '?sortField=' + ${sortField} + '&sortDir=' + ${sortDir} + ${keyword != null ? '&keyword=' + keyword : ''} + ${categoryId != null ? '&categoryId=' + categoryId : ''}}">[[${label}]]</a>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/login.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/login.html[m
[1mindex 8b6e5cc..f99ee12 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/login.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/login.html[m
[36m@@ -31,7 +31,6 @@[m
 </form>[m
 <!-- End of Login Form -->[m
 [m
[31m-<script type="text/javascript" th:src="@{/js/script.js}"></script>[m
 <script type="text/javascript" th:src="@{/js/common.js}"></script>[m
 </body>[m
 </html>[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_detail_modal.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_detail_modal.html[m
[1mdeleted file mode 100644[m
[1mindex e123d2a..0000000[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_detail_modal.html[m
[1m+++ /dev/null[m
[36m@@ -1,8 +0,0 @@[m
[31m-<h1 class="detail-modal-title">Menu Details</h1>[m
[31m-[m
[31m-<div class="detail-modal-input-wrapper">[m
[31m-	<div class="detail-modal-input">[m
[31m-		<label>Title:</label>[m
[31m-		<input type="text" th:value="${article.title}" readonly />[m
[31m-	</div>[m
[31m-</div>[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_form.html[m
[1mindex a598a41..2e26d4b 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menu_form.html[m
[36m@@ -40,7 +40,7 @@[m
 		</select>[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Enabled:</label>[m
 		<input type="checkbox" th:field="*{enabled}" />[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menus.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menus.html[m
[1mindex 52127a6..f50a733 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menus.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/menus/menus.html[m
[36m@@ -34,7 +34,7 @@[m
 	      		<td>[[${menu.title}]]</td>[m
 	      		<td>[[${menu.type}]]</td>[m
 	      		<td>[m
[31m-	      			<a th:href="'/articles/detail/' + ${menu.article.id}" class="link-article">[[${menu.article.title}]]</a>[m
[32m+[m	[41m      [m			[32m<a th:href="'/articles/modal/detail/' + ${menu.article.id}" class="link-article">[[${menu.article.title}]]</a>[m
 	      		</td>[m
 	      		<td>[m
 					<div th:replace="fragments :: status('/menus/' + ${menu.id}, 'menu', ${menu.enabled})"></div>[m
[36m@@ -72,7 +72,7 @@[m
 	            <div class="links">[m
 	            	<div>[m
 	            		<a th:href="@{'/menus/up/' + ${menu.id}}" class="fa-solid fa-angle-up icon-aqua"></a>[m
[31m-		      			[[${menu.position}]][m
[32m+[m		[41m      [m			[32m<span style="font-size: 1.6rem; margin-right: 1rem">[[${menu.position}]]</span>[m
 		      			<a th:href="@{'/menus/down/' + ${menu.id}}" class="fa-solid fa-angle-down icon-aqua"></a>[m
 	            	</div>[m
 	            	<div th:replace="fragments :: status('/menus/' + ${menu.id}, 'menu', ${menu.enabled})"></div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/navigation.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/navigation.html[m
[1mindex fc083a9..cb54621 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/navigation.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/navigation.html[m
[36m@@ -1,5 +1,6 @@[m
 <!DOCTYPE html>[m
[31m-<html xmlns:th="http://thymeleaf.org">[m
[32m+[m[32m<html xmlns:th="http://thymeleaf.org"[m
[32m+[m	[32m  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">[m
 <body>[m
 <!-- Navbar -->[m
 <div class="navbar" th:fragment="navbar">[m
[36m@@ -46,7 +47,7 @@[m
         [m
         <li sec:authorize="hasAnyAuthority('Admin', 'SalesPerson', 'Editor', 'Shipper')" class="dropdown">[m
        		<a th:href="@{/products}" class="nav-link">Products</a>[m
[31m-       		<div class="dropdown-content">[m
[32m+[m[41m       [m		[32m<div sec:authorize="hasAnyAuthority('Admin', 'Editor')" class="dropdown-content">[m
        			<a th:href="@{/products/new}" class="nav-link">New</a>[m
        			<a th:href="@{/products/export/csv}">Export CSV</a>[m
        		</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/order_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/order_form.html[m
[1mindex a9b7245..9b9ad3f 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/order_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/order_form.html[m
[36m@@ -46,6 +46,9 @@[m
 <!-- Add Product Modal -->[m
 <div th:replace="orders/add_product_modal :: content"></div>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:replace="fragments :: warning-modal"></div>[m
[32m+[m
 <script type="text/javascript">[m
 	contextPath = "[[@{/}]]";[m
 	moduleUrl = "[[@{/orders}]]";[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/orders.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/orders.html[m
[1mindex 7923c14..e59808b 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/orders.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/orders.html[m
[36m@@ -83,7 +83,7 @@[m
 </div>[m
 [m
 <!-- Pagination -->[m
[31m-<div th:replace="fragments :: pagination('order')"></div>[m
[32m+[m[32m<div th:replace="fragments :: pagination('orders')"></div>[m
 </div>[m
 [m
 <!-- Confirm Modal-->[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/search_product.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/search_product.html[m
[1mindex 0aa5089..97db5a6 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/search_product.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/orders/search_product.html[m
[36m@@ -10,7 +10,7 @@[m
 	<!-- Search Products-->[m
 	<form th:action="@{/orders/search_product}" method="post" class="search-form">[m
 		<input type="search" name="keyword" th:value="${keyword}" placeholder="product name" required />[m
[31m-		<input type="submit" value="Search" />[m
[32m+[m		[32m<input type="submit" value="Search" style="border-right: .1rem solid #00cccc;" />[m
 	</form>[m
 	[m
 	<!-- Products -->[m
[36m@@ -19,8 +19,9 @@[m
 		<div class="details">[m
 			<a href="" class="linkProduct" th:pid="${product.id}" title="Add this product">[m
 				<img th:src="@{${product.mainImagePath}}">[m
[32m+[m				[32m<span th:id="'pname' + ${product.id}"[m[41m [m
[32m+[m					[32mstyle="color: #00cccc;">[[${product.shortName}]]</span>[m
 			</a>[m
[31m-			<b th:id="'pname' + ${product.id}">[[${product.shortName}]]</b>[m
 		</div>[m
 	</div>[m
 	</th:block>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_form.html[m
[1mindex 866c99d..9dfdd0d 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_form.html[m
[36m@@ -78,6 +78,9 @@[m
 	</div>[m
 </form>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:replace="fragments :: warning-modal"></div>[m
[32m+[m
 <script type="text/javascript">[m
 	MAX_FILE_SIZE = 502400;[m
 	moduleUrl = "[[@{/products}]]";[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_overview.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_overview.html[m
[1mindex 476078a..f4334ce 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_overview.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/products/product_overview.html[m
[36m@@ -71,7 +71,7 @@[m
 		</th:block>[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Enabled:</label>[m
 		<th:block th:if="${isReadOnlyForSalesperson}">[m
 			<input type="checkbox" th:field="*{enabled}" disabled="disabled" />[m
[36m@@ -82,7 +82,7 @@[m
 		</th:block>[m
 	</div>[m
 	[m
[31m-	<div class="input-group">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>In-Stock:</label>[m
 		<th:block th:if="${isReadOnlyForSalesperson}">[m
 			<input type="checkbox" th:field="*{inStock}" disabled="disabled" />[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/questions/questions.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/questions/questions.html[m
[1mindex 330c044..8409f39 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/questions/questions.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/questions/questions.html[m
[36m@@ -103,7 +103,7 @@[m
 </div>[m
 [m
 <!-- Pagination -->[m
[31m-<div th:replace="fragments :: pagination('question')"></div>[m
[32m+[m[32m<div th:replace="fragments :: pagination('questions')"></div>[m
 </div>[m
 [m
 <!-- Confirm Modal-->[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/reviews/reviews.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/reviews/reviews.html[m
[1mindex 91804f9..5f81760 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/reviews/reviews.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/reviews/reviews.html[m
[36m@@ -91,7 +91,7 @@[m
 </div>[m
 [m
 <!-- Pagination -->[m
[31m-<div th:replace="fragments :: pagination('review')"></div>[m
[32m+[m[32m<div th:replace="fragments :: pagination('reviews')"></div>[m
 </div>[m
 [m
 <!-- Confirm Modal-->[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/shipping_rates/shipping_rates_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/shipping_rates/shipping_rates_form.html[m
[1mindex f56cca5..530966d 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/shipping_rates/shipping_rates_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/shipping_rates/shipping_rates_form.html[m
[36m@@ -37,7 +37,7 @@[m
 		<input type="number" th:field="*{days}" required="required" />[m
 	</div>[m
 	[m
[31m-	<div class="enable">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Cash On Deliver (COD):</label>[m
 		<input type="checkbox" th:field="*{codSupported}" />[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/users/user_form.html b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/users/user_form.html[m
[1mindex a583940..62a44e0 100644[m
[1m--- a/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/users/user_form.html[m
[1m+++ b/EcommerceWebParent/EcommerceBackEnd/src/main/resources/templates/users/user_form.html[m
[36m@@ -45,7 +45,7 @@[m
 		</div>[m
 	</div>[m
 	[m
[31m-	<div class="enable">[m
[32m+[m	[32m<div class="input-group-checkbox">[m
 		<label>Enabled:</label>[m
 		<input type="checkbox" th:field="*{enabled}"/>[m
 	</div>[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/modal.css b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/modal.css[m
[1mindex ded2b36..70fad1c 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/modal.css[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/modal.css[m
[36m@@ -613,8 +613,8 @@[m
 .return-order-modal {[m
     width: 100%;[m
     height: 100%;[m
[31m-    background: rgba(0, 0, 0, 0.8);[m
[31m-    position: absolute;[m
[32m+[m[32m    background: rgba(0, 0, 0, 0.6);[m
[32m+[m[32m    position: fixed;[m
     top: 0;[m
     right: 0;[m
     z-index: 100;[m
[36m@@ -627,7 +627,7 @@[m
     background-color: #474747;[m
     color: #00e5e5;[m
     position: absolute;[m
[31m-    box-shadow: .4rem .4rem 1rem #000;[m
[32m+[m[32m    box-shadow: .2rem .2rem .7rem #000;[m
     top: 50%;[m
     left: 50%;[m
     transform: translate(-50%, -50%);[m
[36m@@ -678,19 +678,19 @@[m
 }[m
 [m
 .return-modal-btn {[m
[31m-    font-size: 1.5rem;[m
[32m+[m[32m    font-size: 1.4rem;[m
     text-transform: uppercase;[m
[31m-    padding: .2rem 1rem;[m
[32m+[m[32m    padding: .2rem .5rem;[m
     border: none;[m
     outline: none;[m
     font-weight: bolder;[m
[32m+[m[32m    cursor: pointer;[m
 }[m
 [m
 .return-modal-btns .send-equest-btn {[m
     margin-right: 1rem;[m
     background-color: #00e5e5;[m
[31m-    color: #444;[m
[31m-    font-weight: bolder;[m
[32m+[m[32m    color: #555;[m
     letter-spacing: .4rem;[m
     transition: background .3s;[m
 }[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/product_detail.css b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/product_detail.css[m
[1mindex 2641284..0cb7d0d 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/product_detail.css[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/product_detail.css[m
[36m@@ -69,9 +69,9 @@[m
 [m
 .product-description,[m
 .product-details,[m
[31m-.product-questions,[m
[31m-.product-reviews {[m
[31m-    padding: 1rem;[m
[32m+[m[32m.product-questions {[m
[32m+[m	[32mpadding: 1rem;[m
[32m+[m[32m    border-bottom: .1rem solid #000;[m
 }[m
 [m
 .product-description h2,[m
[36m@@ -145,6 +145,10 @@[m [mtable {[m
 	background-color: #a6a6a6;[m
 }[m
 [m
[32m+[m[32m.product-reviews {[m
[32m+[m[32m    padding: 1rem;[m
[32m+[m[32m}[m
[32m+[m
 .review {[m
     margin-bottom: 1rem;[m
 }[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/style.css b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/style.css[m
[1mindex 8d62a08..ef91162 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/style.css[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/css/style.css[m
[36m@@ -43,7 +43,7 @@[m [mhtml {[m
 }[m
 [m
 .title:first-letter {[m
[31m-	color: #00ffff;[m
[32m+[m	[32mcolor: #00e5e5;[m
 	font-size: 5rem;[m
 }[m
 [m
[36m@@ -103,6 +103,19 @@[m [mhtml {[m
 	color: #00B2B2;[m
 }[m
 [m
[32m+[m[32m/* Scrollbar */[m
[32m+[m[32m::-webkit-scrollbar {[m
[32m+[m[32m  width: 1rem;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m::-webkit-scrollbar-track {[m
[32m+[m[32m  box-shadow: inset 0 0 5px grey;[m[41m [m
[32m+[m[32m}[m
[32m+[m[41m [m
[32m+[m[32m::-webkit-scrollbar-thumb {[m
[32m+[m[32m  background: #ccc;[m[41m [m
[32m+[m[32m}[m
[32m+[m
 /* Navbar */[m
 .navbar {[m
     width: 100%;[m
[36m@@ -327,10 +340,9 @@[m [mhtml {[m
     	position: absolute;[m
     	top: 1rem;[m
     	left: .5rem;[m
[31m-    	font-size: 1.6rem;[m
[32m+[m[41m    [m	[32mfont-size: 1.5rem;[m
     	color: #333;[m
     	padding: 0 .2rem;[m
[31m-    	letter-spacing: .4rem;[m
     }[m
     [m
     .nav-link-cart {[m
[36m@@ -889,7 +901,7 @@[m [mtd a {[m
 }[m
 [m
 .icon {[m
[31m-	font-size: 3rem;[m
[32m+[m	[32mfont-size: 2.5rem;[m
 }[m
 [m
 .table-cards-wrapper {[m
[36m@@ -898,7 +910,7 @@[m [mtd a {[m
 [m
 @media screen and (max-width: 1200px) {[m
 	.table-wrapper {[m
[31m-		padding: 0 2rem;[m
[32m+[m		[32mpadding: 0 1rem;[m
 	}[m
 	[m
 	.table-wrapper th, td {[m
[36m@@ -921,7 +933,6 @@[m [mtd a {[m
 	[m
 	.table-cards-wrapper {[m
 		width: 100%;[m
[31m-		padding: 0 2rem;[m
 		display: flex;[m
 		flex-direction: column;[m
 	}[m
[36m@@ -972,14 +983,6 @@[m [mtd a {[m
 		width: 100%;[m
 	}[m
 	[m
[31m-	.table-wrapper {[m
[31m-		padding: 0 1rem;[m
[31m-	}[m
[31m-	[m
[31m-	.table-cards-wrapper {[m
[31m-		padding: 0 1rem;[m
[31m-	}[m
[31m-	[m
 	.table-card-body {[m
 		font-size: 1.5rem;[m
 	}[m
[36m@@ -1126,6 +1129,10 @@[m [mtd a {[m
 }[m
 [m
 @media screen and (max-width: 500px) { [m
[32m+[m	[32m.common-form {[m
[32m+[m		[32mbox-shadow: .2rem .3rem .8rem #444;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
 	.common-form .form {[m
 		padding: .5rem;[m
 	}[m
[36m@@ -1172,7 +1179,7 @@[m [mtd a {[m
 [m
 .pagination-wrapper span {[m
 	color: #000;[m
[31m-	font-size: 2rem;[m
[32m+[m	[32mfont-size: 1.6rem;[m
 }[m
 [m
 .pagination {[m
[36m@@ -1189,11 +1196,11 @@[m [mtd a {[m
 }[m
 [m
 .pagination a {[m
[31m-	font-size: 2rem;[m
[32m+[m	[32mfont-size: 1.6rem;[m
 	color: #000;[m
 	text-transform: uppercase;[m
[31m-	padding: .2rem .5rem;[m
[31m-	border: .1rem solid #00b2b2;[m
[32m+[m	[32mpadding: .1rem .3rem;[m
[32m+[m	[32mborder: .1rem solid #000;[m
 	transition: background .3s;[m
 }[m
 [m
[36m@@ -1387,7 +1394,7 @@[m [mtd a {[m
 [m
 .linkMinus,[m
 .linkPlus {[m
[31m-    font-size: 2rem;[m
[32m+[m[32m    font-size: 1.8rem;[m
     color: #000;[m
     border: .2rem solid #000;[m
     padding: 0 .5rem;[m
[36m@@ -1513,7 +1520,9 @@[m [mtd a {[m
 [m
 .cart-message {[m
 	font-size: 2rem;[m
[32m+[m	[32mtext-transform: uppercase;[m
 	text-align: center;[m
[32m+[m	[32mmargin: 30rem auto;[m
 }[m
 [m
 @media screen and (max-width: 1300px) {[m
[36m@@ -1668,7 +1677,7 @@[m [mtd a {[m
 /* Checkout Page */[m
 .checkout-content {[m
 	width: 100%;[m
[31m-	padding: 0 5rem;[m
[32m+[m	[32mpadding: 0 15rem;[m
 	display: flex;[m
 	justify-content: space-between;[m
 	margin-bottom: 30rem;[m
[36m@@ -1932,13 +1941,36 @@[m [mtd a {[m
 [m
 /* Article */[m
 .article-wrapper {[m
[31m-	height: 100vh;[m
[31m-	padding: 0 10rem;[m
[32m+[m	[32mwidth: 110rem;[m
[32m+[m	[32mmargin: 0 auto 2rem auto;[m
 	font-size: 1.8rem;[m
 }[m
 [m
[32m+[m[32m.article-wrapper img {[m
[32m+[m	[32mmax-width: 100%;[m
[32m+[m[32m}[m
[32m+[m
 .article-title {[m
 	margin: 2rem 0;[m
[32m+[m	[32mtext-transform: uppercase;[m
[32m+[m	[32mletter-spacing: .2rem;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m@media screen and (max-width: 1200px) {[m
[32m+[m	[32m.article-wrapper {[m
[32m+[m		[32mwidth: 95%;[m
[32m+[m	[32m}[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m@media screen and (max-width: 500px) {[m
[32m+[m	[32m.article-wrapper {[m
[32m+[m		[32mwidth: 95%;[m
[32m+[m		[32mfont-size: 1.6rem;[m
[32m+[m	[32m}[m
[32m+[m[41m	[m
[32m+[m	[32m.article-title {[m
[32m+[m		[32mfont-size: 2.5rem;[m
[32m+[m	[32m}[m
 }[m
 /* End of Article */[m
 [m
[36m@@ -2025,4 +2057,65 @@[m [mtd a {[m
 		flex-direction: column;[m
 	}[m
 }[m
[31m-/* End of Questions Product */[m
\ No newline at end of file[m
[32m+[m[32m/* End of Questions Product */[m
[32m+[m
[32m+[m[32m/* Warning Modal */[m
[32m+[m[32m.warning-modal {[m
[32m+[m	[32mwidth: 100%;[m
[32m+[m	[32mheight: 100vh;[m
[32m+[m	[32mbackground: rgba(0, 0, 0, 0.4);[m
[32m+[m	[32mposition: fixed;[m
[32m+[m	[32mtop: 0;[m
[32m+[m	[32mleft: 0;[m
[32m+[m	[32mz-index: 1;[m
[32m+[m	[32mvisibility: hidden;[m
[32m+[m	[32mopacity: 0;[m
[32m+[m	[32mtransition: opacity .3s;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.change.warning-modal {[m
[32m+[m	[32mvisibility: visible;[m
[32m+[m	[32mopacity: 1;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-content {[m
[32m+[m	[32mwidth: 50rem;[m
[32m+[m	[32mbackground-color: #fff;[m
[32m+[m	[32mposition: absolute;[m
[32m+[m	[32mtop: 10rem;[m
[32m+[m	[32mleft: 50%;[m
[32m+[m	[32mtransform: translateX(-50%);[m
[32m+[m	[32mfont-size: 1.8rem;[m
[32m+[m	[32mpadding: .5rem;[m
[32m+[m	[32mbox-shadow: .2rem .4rem .8rem #000;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-content h2 {[m
[32m+[m	[32mtext-transform: uppercase;[m
[32m+[m	[32mfont-weight: 300;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-message {[m
[32m+[m	[32mpadding: 2rem 0;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m.warning-modal-btn {[m
[32m+[m	[32mfloat: right;[m
[32m+[m	[32mfont-size: 1.6rem;[m
[32m+[m	[32mbackground-color: #00cccc;[m
[32m+[m	[32mcolor: #fff;[m
[32m+[m	[32mletter-spacing: .2rem;[m
[32m+[m	[32mtext-shadow: .1rem .1rem .1rem #000;[m
[32m+[m	[32mbox-shadow: .1rem .1rem .1rem #000;[m
[32m+[m	[32mpadding: 0 .7rem;[m
[32m+[m	[32mfont-weight: bold;[m
[32m+[m	[32mcursor: pointer;[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32m@media screen and (max-width: 550px) {[m
[32m+[m	[32m.warning-modal-content {[m
[32m+[m		[32mwidth: 95%;[m
[32m+[m		[32mfont-size: 1.6rem;[m
[32m+[m	[32m}[m
[32m+[m[32m}[m
[32m+[m[32m/* End of Warning Modal */[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/common.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/common.js[m
[1mindex cc6ba40..6336f30 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/common.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/common.js[m
[36m@@ -6,6 +6,7 @@[m [m$(document).ready(function() {[m
 	navbar = $(".navbar");[m
 	[m
 	handleLinkViewNavList();[m
[32m+[m	[32mhandleLinkCloseWarningModal();[m
 });[m
 [m
 function handleLinkViewNavList() {[m
[36m@@ -13,3 +14,14 @@[m [mfunction handleLinkViewNavList() {[m
 		navbar.toggleClass("change");[m
 	});[m
 }[m
[32m+[m
[32m+[m[32mfunction showWarningModal(message) {[m
[32m+[m	[32m$(".warning-modal-message").text(message);[m
[32m+[m	[32m$(".warning-modal").addClass('change');[m
[32m+[m[32m}[m
[32m+[m
[32m+[m[32mfunction handleLinkCloseWarningModal() {[m
[32m+[m	[32m$(".warning-modal-btn").on("click", function() {[m
[32m+[m		[32m$(".warning-modal").removeClass('change');[m
[32m+[m	[32m})[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/post_question.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/post_question.js[m
[1mindex 3d0e41b..9aa62ea 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/post_question.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/post_question.js[m
[36m@@ -13,9 +13,9 @@[m [mfunction addQuestion() {[m
 		data: JSON.stringify(requestBody),[m
 		contentType: 'application/json'[m
 	}).done(function(response) {[m
[31m-		alert(response);[m
[32m+[m		[32mshowWarningModal(response);[m
 	}).fail(function() {[m
[31m-		alert("ERROR: Could not connect to server or server encountered an error");[m
[32m+[m		[32mshowWarningModal("ERROR: Could not connect to server or server encountered an error");[m
 	});[m
 	[m
 	return false;[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/quantity_control.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/quantity_control.js[m
[1mindex 2fc1b44..0b6de5b 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/quantity_control.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/quantity_control.js[m
[36m@@ -8,7 +8,7 @@[m [m$(document).ready(function() {[m
 		if (newQuantity > 0) {[m
 			quantityInput.val(newQuantity);[m
 		} else {[m
[31m-			alert('Minimum quantity is 1');[m
[32m+[m			[32mshowWarningModal('Minimum quantity is 1');[m
 		}[m
 	});[m
 	[m
[36m@@ -21,7 +21,7 @@[m [m$(document).ready(function() {[m
 		if (newQuantity <= 5) {[m
 			quantityInput.val(newQuantity);[m
 		} else {[m
[31m-			alert('Maximum quantity is 5');[m
[32m+[m			[32mshowWarningModal('Maximum quantity is 5');[m
 		}[m
 	});[m
 });[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/question_vote.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/question_vote.js[m
[1mindex dd8267b..3a9e423 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/question_vote.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/question_vote.js[m
[36m@@ -16,10 +16,9 @@[m [mfunction voteQuestion(currentLink) {[m
 		}[m
 	}).done(function(voteResult) {[m
 		updateVoteCountAndIcons(currentLink, voteResult);[m
[31m-		[m
[31m-		alert(voteResult.message);[m
[32m+[m		[32mshowWarningModal(voteResult.message);[m
 	}).fail(function() {[m
[31m-		alert("Error voting question.")[m
[32m+[m		[32mshowWarningModal("Error voting question.");[m
 	});[m
 }[m
 [m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/review_vote.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/review_vote.js[m
[1mindex 908bc33..b268420 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/review_vote.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/review_vote.js[m
[36m@@ -19,9 +19,9 @@[m [mfunction voteReview(currentLink) {[m
 			updateVoteCountAndIcons(currentLink, voteResult);[m
 		}[m
 		[m
[31m-		alert(voteResult.message);[m
[32m+[m		[32mshowWarningModal(voteResult.message);[m
 	}).fail(function() {[m
[31m-		alert("Error voting review.");[m
[32m+[m		[32mshowWarningModal("Error voting review.");[m
 	});[m
 }[m
 [m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/shopping_cart.js b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/shopping_cart.js[m
[1mindex e94377a..62c573a 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/shopping_cart.js[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/static/js/shopping_cart.js[m
[36m@@ -28,7 +28,7 @@[m [mfunction decreaseQuantity(link) {[m
 		quantityInput.val(newQuantity);[m
 		updateQuantity(productId, newQuantity);[m
 	} else {[m
[31m-		alert('Minimum quantity is 1');[m
[32m+[m		[32mshowWarningModal('Minimum quantity is 1');[m
 	}[m
 }[m
 [m
[36m@@ -41,7 +41,7 @@[m [mfunction increaseQuantity(link) {[m
 		quantityInput.val(newQuantity);[m
 		updateQuantity(productId, newQuantity);[m
 	} else {[m
[31m-		alert('Maximum quantity is 5');[m
[32m+[m		[32mshowWarningModal('Maximum quantity is 5');[m
 	}[m
 }[m
 [m
[36m@@ -58,7 +58,7 @@[m [mfunction updateQuantity(productId, quantity) {[m
 		updateSubtotal(updatedSubtotal, productId);[m
 		updateTotal();[m
 	}).fail(function() {[m
[31m-		alert("Error while updating product quantity.");[m
[32m+[m		[32mshowWarningModal("Error while updating product quantity.");[m
 	})[m
 }[m
 [m
[36m@@ -101,9 +101,9 @@[m [mfunction removeProduct(link) {[m
 		removeProductHTML(rowNumber);[m
 		updateTotal();[m
 		updateCountNumber();[m
[31m-		alert(response);[m
[32m+[m		[32mshowWarningModal(response);[m
 	}).fail(function() {[m
[31m-		alert("Error while removing product.");[m
[32m+[m		[32mshowWarningModal("Error while removing product.");[m
 	});[m
 }[m
 [m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/article/article_detail.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/article/article_detail.html[m
[1mindex c1fb870..3c3a9a4 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/article/article_detail.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/article/article_detail.html[m
[36m@@ -13,9 +13,6 @@[m
 	<div th:utext="${article.content}" class="article-content"></div>[m
 </div>[m
 [m
[31m-<!-- Footer -->[m
[31m-<div th:replace="fragments :: footer"></div>[m
[31m-[m
 <script type="text/javascript" th:src="@{/js/common.js}"></script>[m
 </body>[m
 </html>[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/quantity_control.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/quantity_control.html[m
[1mindex 77a0556..5b66dfb 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/quantity_control.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/quantity_control.html[m
[36m@@ -9,19 +9,5 @@[m
 	<a href="" class="addToCartBtn" id="buttonAdd2Cart">Add to cart</a>[m
 </div>[m
 [m
[31m-<!-- <div th:fragment="quantity_control(quantityValue, productId)" class="quantity-control">[m
[31m-	<div>[m
[31m-		<a href="" class="linkMinus" th:pid="${productId}">-</a>[m
[31m-	</div>[m
[31m-	[m
[31m-	<div>[m
[31m-		<input type="text" th:value="${quantityValue}" onkeydown="return false;" th:id="'quantity' + ${productId}">[m
[31m-	</div>[m
[31m-	[m
[31m-	<div>[m
[31m-		<a href="" class="linkPlus" th:pid="${productId}">+</a>[m
[31m-	</div>[m
[31m-</div> -->[m
[31m-[m
 </body>[m
 </html>[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/shopping_cart.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/shopping_cart.html[m
[1mindex 2bf89ff..e074440 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/shopping_cart.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/cart/shopping_cart.html[m
[36m@@ -50,8 +50,8 @@[m
 				</form>[m
 			</div>[m
 			[m
[31m-			<div th:unless="${shippingSupported}">[m
[31m-				<span style="color: red; font-size: 2rem">No shipping available for your location</span>[m
[32m+[m			[32m<div th:unless="${shippingSupported}" style="font-size: 1.8rem; text-transform: none; margin: 1rem 0;">[m
[32m+[m				[32m<span style="color: red;">No shipping available for your location!</span>[m
 				[m
 				<div th:if="${usePrimaryAddressAsDefault}">[m
 					<a th:href="@{/account_details(redirect=cart)}">Update your address</a>[m
[36m@@ -71,24 +71,10 @@[m
 </div>[m
 [m
 <!-- Footer -->[m
[31m-<div class="footer" th:if="${#lists.size(cartItems)}">[m
[31m-	<div class="footer-content">[m
[31m-		<div class="footer-links">[m
[31m-			<a href="#">About</a>[m
[31m-			<a href="#">Benefits</a>[m
[31m-			<a href="#">Career</a>[m
[31m-			<a href="#">Support</a>[m
[31m-			<p>[[${COPYRIGHT}]]</p>[m
[31m-		</div>[m
[31m-		<div class="footer-icons">[m
[31m-			<i class="fa-brands fa-linkedin"></i>[m
[31m-	    	<i class="fa-brands fa-google"></i>[m
[31m-	    	<i class="fa-brands fa-facebook-square"></i>[m
[31m-	    	<i class="fa-brands fa-instagram"></i>[m
[31m-	    	<p>Support: al_alx@yahoo.com</p>[m
[31m-		</div>[m
[31m-	</div>[m
[31m-</div>[m
[32m+[m[32m<div th:replace="fragments :: footer"></div>[m
[32m+[m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:replace="fragments :: warning-modal"></div>[m
 [m
 <script type="text/javascript">[m
 	contextPath = "[[@{/}]]";[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/checkout/checkout.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/checkout/checkout.html[m
[1mindex 419c65f..1c74de1 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/checkout/checkout.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/checkout/checkout.html[m
[36m@@ -7,9 +7,6 @@[m
 [m
 <h1 class="title">Checkout</h1>[m
 [m
[31m-<!-- <p th:text="${paypalClientId}"></p>[m
[31m-		<p th:text="${currencyCode}"></p> -->[m
[31m-[m
 <div class="checkout-content">[m
 	<div class="order-summary">[m
 		<h2>Order Summary</h2>[m
[36m@@ -82,25 +79,7 @@[m
 </div>[m
  [m
 <!-- Footer -->[m
[31m-<div class="footer">[m
[31m-	<div class="footer-content">[m
[31m-		<h5>Welcome</h5>[m
[31m-		<div class="footer-links">[m
[31m-			<a href="#">About</a>[m
[31m-			<a href="#">Benefits</a>[m
[31m-			<a href="#">Career</a>[m
[31m-			<a href="#">Support</a>[m
[31m-			<p>[[${COPYRIGHT}]]</p>[m
[31m-		</div>[m
[31m-		<div class="footer-icons">[m
[31m-			<i class="fa-brands fa-linkedin"></i>[m
[31m-	    	<i class="fa-brands fa-google"></i>[m
[31m-	    	<i class="fa-brands fa-facebook-square"></i>[m
[31m-	    	<i class="fa-brands fa-instagram"></i>[m
[31m-	    	<p>Support: al_alx@yahoo.com</p>[m
[31m-		</div>[m
[31m-	</div>[m
[31m-</div>[m
[32m+[m[32m<div th:replace="fragments :: footer"></div>[m
 [m
 <script th:src="@{https://www.paypal.com/sdk/js(client-id=${paypalClientId},currency=${currencyCode})}"></script>[m
 <script type="text/javascript">[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/fragments.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/fragments.html[m
[1mindex b8a8d3f..9da39dd 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/fragments.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/fragments.html[m
[36m@@ -81,6 +81,15 @@[m
 	</form>[m
 </div>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:fragment="warning-modal" class="warning-modal">[m
[32m+[m	[32m<div class="warning-modal-content">[m
[32m+[m		[32m<h2>Warning</h2>[m
[32m+[m		[32m<p class="warning-modal-message">Message</p>[m
[32m+[m		[32m<a class="warning-modal-btn">Close</a>[m
[32m+[m	[32m</div>[m
[32m+[m[32m</div>[m
[32m+[m
 <!-- Footer -->[m
 <div th:fragment="footer" class="footer">[m
 	<div class="footer-content">[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/orders/orders_customer.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/orders/orders_customer.html[m
[1mindex 9655b2c..eb77988 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/orders/orders_customer.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/orders/orders_customer.html[m
[36m@@ -75,10 +75,13 @@[m
 					<a class="fas fa-file-alt icon icon-aqua link-detail" [m
 						th:href="@{'/orders/detail/' + ${order.id}}"[m
 						title="View details of this order"></a>[m
[32m+[m[41m					[m
[32m+[m					[32m<th:block th:if="${order.returnRequestd == false && order.delivered == true && order.returned == false}">[m
 					<a href="" class="fas fa-undo icon icon-silver link-return"[m
[31m-							th:classappend="'linkReturn' + ${order.id}"[m
[31m-							th:orderID="${order.id}"[m
[31m-							title="Return this order"></a>[m
[32m+[m						[32mth:classappend="'linkReturn' + ${order.id}"[m
[32m+[m						[32mth:orderID="${order.id}"[m
[32m+[m						[32mtitle="Return this order"></a>[m
[32m+[m					[32m</th:block>[m
 				</div>[m
 			</div>[m
 			<div class="table-card-body">[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/product/product_detail.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/product/product_detail.html[m
[1mindex 340e0fa..da47723 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/product/product_detail.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/product/product_detail.html[m
[36m@@ -18,13 +18,13 @@[m
 <div class="product-wrapper">[m
 	<div class="product">[m
 		<div class="img-wrapper">[m
[31m-		<div class="product-small-images">[m
[31m-			<img th:src="@{${product.mainImagePath}}" class="image-thumbnail">[m
[31m-			<th:block th:each="extraImage : ${product.images}">[m
[31m-			<img th:src="@{${extraImage.imagePath}}" class="image-thumbnail">[m
[31m-			</th:block>[m
[31m-		</div>[m
[31m-		<img th:src="@{${product.mainImagePath}}" class="product-main-img" id="mainImage">[m
[32m+[m			[32m<div class="product-small-images">[m
[32m+[m				[32m<img th:src="@{${product.mainImagePath}}" class="image-thumbnail">[m
[32m+[m				[32m<th:block th:each="extraImage : ${product.images}">[m
[32m+[m				[32m<img th:src="@{${extraImage.imagePath}}" class="image-thumbnail">[m
[32m+[m				[32m</th:block>[m
[32m+[m			[32m</div>[m
[32m+[m			[32m<img th:src="@{${product.mainImagePath}}" class="product-main-img" id="mainImage">[m
 		</div>[m
 		[m
 		<div class="product-info">[m
[36m@@ -67,42 +67,39 @@[m
 			</th:block>[m
 		</div>[m
 	</div>[m
[32m+[m[32m</div>[m
 [m
[31m-	<hr>[m
[31m-	[m
[31m-	<div class="product-description">[m
[31m-		<h2>Product Description</h2>[m
[31m-		<div class="product-full-description" th:utext="${product.fullDescription}"></div>[m
[31m-	</div>[m
[31m-[m
[31m-	<hr>[m
[31m-[m
[31m-	<div class="product-details">[m
[31m-		<h2>Product Details</h2>[m
[31m-		<table class="product-detail-table">[m
[31m-			<th:block th:each="detail : ${product.details}">[m
[31m-			<tr>[m
[31m-				<td>[[${detail.name}]]: </td>[m
[31m-				<td>[[${detail.value}]]</td>[m
[31m-			</tr>[m
[31m-			<th:block>[m
[31m-		</table>[m
[31m-	</div>[m
[32m+[m[32m<!-- Product Description -->[m
[32m+[m[32m<div class="product-description">[m
[32m+[m	[32m<h2>Product Description</h2>[m
[32m+[m	[32m<div class="product-full-description" th:utext="${product.fullDescription}"></div>[m
[32m+[m[32m</div>[m
 [m
[31m-	<hr>[m
[31m-	[m
[31m-	<!-- Questions -->[m
[31m-	<div th:replace="product/product_top_questions :: content"></div>[m
[32m+[m[32m<!-- Product Details -->[m
[32m+[m[32m<div class="product-details">[m
[32m+[m	[32m<h2>Product Details</h2>[m
[32m+[m	[32m<table class="product-detail-table">[m
[32m+[m		[32m<th:block th:each="detail : ${product.details}">[m
[32m+[m		[32m<tr>[m
[32m+[m			[32m<td>[[${detail.name}]]: </td>[m
[32m+[m			[32m<td>[[${detail.value}]]</td>[m
[32m+[m		[32m</tr>[m
[32m+[m		[32m<th:block>[m
[32m+[m	[32m</table>[m
[32m+[m[32m</div>[m
 [m
[31m-	<hr>[m
[32m+[m[32m<!-- Questions -->[m
[32m+[m[32m<div th:replace="product/product_top_questions :: content"></div>[m
 [m
[31m-	<!-- Reviews -->[m
[31m-	<div th:replace="product/product_top_reviews :: content"></div>[m
[31m-</div>[m
[32m+[m[32m<!-- Reviews -->[m
[32m+[m[32m<div th:replace="product/product_top_reviews :: content"></div>[m
 [m
 <!-- Footer -->[m
 <div th:replace="fragments :: footer"></div>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:replace="fragments :: warning-modal"></div>[m
[32m+[m
 <script type="text/javascript">[m
 	contextPath = "[[@{/}]]";[m
 	productId = "[[${product.id}]]";[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_product.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_product.html[m
[1mindex e4d15db..023273e 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_product.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_product.html[m
[36m@@ -32,6 +32,9 @@[m
 <!-- Footer -->[m
 <div th:replace="fragments :: footer"></div>[m
 [m
[32m+[m[32m<!-- Warning Modal -->[m
[32m+[m[32m<div th:replace="fragments :: warning-modal"></div>[m
[32m+[m
 <script type="text/javascript">[m
 	csrfHeaderName = "[[${_csrf.headerName}]]";[m
 	csrfValue = "[[${_csrf.token}]]";[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_votes.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_votes.html[m
[1mindex 3df3855..d5f77dd 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_votes.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/questions/questions_votes.html[m
[36m@@ -48,49 +48,5 @@[m
 	</div>[m
 	</th:block>[m
 </div>[m
[31m-[m
[31m-<!-- [m
[31m-<div>[m
[31m-<h2>Questions & answers for this product:</h2>[m
[31m-<th:block th:each="question : ${listQuestions}">[m
[31m-	<div>[m
[31m-		<div style="display: flex">[m
[31m-			<span>Question:</span>[m
[31m-			<div>[m
[31m-			<a th:href="@{'/vote_question/' + ${question.id} + '/up'}" [m
[31m-				class="fa-solid fa-sort-up fa-2x icon-silver linkVoteQuestion"[m
[31m-				th:classappend="${question.upvotedByCurrentCustomer ? 'icon-green' : 'icon-silver'}"[m
[31m-				th:id="'linkVoteUp-' + ${question.id}"[m
[31m-				th:questionId="${question.id}"></a>[m
[31m-			[m
[31m-			<span th:id="'voteCountQuestion-' + ${question.id}">[[${question.votes}]] Votes</span>[m
[31m-		[m
[31m-			<a th:href="@{'/vote_question/' + ${question.id} + '/down'}" [m
[31m-				class="fa-solid fa-sort-down fa-2x icon-silver linkVoteQuestion"[m
[31m-				th:classappend="${question.downvotedByCurrentCustomer ? 'icon-green' : 'icon-silver'}"[m
[31m-				th:id="'linkVoteDown-' + ${question.id}"[m
[31m-				th:questionId="${question.id}"></a>[m
[31m-			</div>[m
[31m-			[m
[31m-			<div>[m
[31m-				[[${question.customer.fullName}]][m
[31m-				<div th:replace="fragments :: format_time(${question.askTime})"></div>[m
[31m-			</div>[m
[31m-		</div>[m
[31m-		<p>[[${question.questionContent}]]</p>[m
[31m-		[m
[31m-		<th:block th:if="${question.answer}">[m
[31m-		<span>Answer:</span>[m
[31m-		<p>[[${question.answerContent}]]</p>[m
[31m-		<div>[m
[31m-			[[${question.user.fullName}]][m
[31m-			<div th:replace="fragments :: format_time(${question.answerTime})"></div>[m
[31m-		</div>[m
[31m-		</th:block>[m
[31m-	</div>[m
[31m-	<hr>[m
[31m-</th:block>[m
[31m-</div>[m
[31m--->[m
 </body>[m
 </html>[m
\ No newline at end of file[m
[1mdiff --git a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/reviews/reviews_votes.html b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/reviews/reviews_votes.html[m
[1mindex 634e931..ac9fdf3 100644[m
[1m--- a/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/reviews/reviews_votes.html[m
[1m+++ b/EcommerceWebParent/EcommerceFrontEnd/src/main/resources/templates/reviews/reviews_votes.html[m
[36m@@ -20,25 +20,6 @@[m
 			th:reviewId="${review.id}"></a>[m
 		</div>[m
 	</div>[m
[31m-	[m
[31m-	<!-- <div style="display: flex;">[m
[31m-		<input type="text" class="product-detail-rating-star" dir="ltr" data-size="sm" th:value="${review.rating}"/>[m
[31m-		&nbsp;&nbsp;[m
[31m-		[m
[31m-		<a class="LinkVoteReview fa-thumbs-up" style="font-size: 2rem;"[m
[31m-			th:classappend="${review.upvotedByCurrentCustomer ? 'fas' : 'far'}"[m
[31m-			th:id="'linkVoteUp-' + ${review.id}"[m
[31m-			th:reviewId="${review.id}"[m
[31m-			th:href="@{'/vote_review/' + ${review.id} + '/up'}"></a>[m
[31m-			[m
[31m-		<span th:id="'voteCount-' + ${review.id}" style="font-size: 2rem;">[[${review.votes}]] Votes</span>[m
[31m-		[m
[31m-		<a class="LinkVoteReview far fa-thumbs-down" style="font-size: 2rem;"[m
[31m-			th:classappend="${review.downvotedByCurrentCustomer ? 'fas' : 'far'}"[m
[31m-			th:id="'linkVoteDown-' + ${review.id}"[m
[31m-			th:reviewId="${review.id}"[m
[31m-			th:href="@{'/vote_review/' + ${review.id} + '/down'}"></a>[m
[31m-	</div> -->[m
 		[m
 	<div class="review">[m
 		<h5 class="review-headline">[m
[36m@@ -49,36 +30,5 @@[m
 	</div>[m
 	</th:block>[m
 </div>[m
[31m-[m
[31m-<!-- <div th:fragment="content">[m
[31m-	<th:block th:each="review : ${listReviews}">[m
[31m-	<div style="margin-bottom: 2rem;">[m
[31m-		<hr>[m
[31m-		<div style="display: flex;">[m
[31m-			<input type="text" class="product-detail-rating-star" dir="ltr" data-size="md" th:value="${review.rating}"/>[m
[31m-			[m
[31m-			<a class="LinkVoteReview fa-thumbs-up" style="font-size: 2rem;"[m
[31m-				th:classappend="${review.upvotedByCurrentCustomer ? 'fas' : 'far'}"[m
[31m-				th:id="'linkVoteUp-' + ${review.id}"[m
[31m-				th:reviewId="${review.id}"[m
[31m-				th:href="@{'/vote_review/' + ${review.id} + '/up'}"></a>[m
[31m-				[m
[31m-			<span th:id="'voteCount-' + ${review.id}" style="font-size: 2rem;">[[${review.votes}]] Votes</span>[m
[31m-			[m
[31m-			<a class="LinkVoteReview far fa-thumbs-down" style="font-size: 2rem;"[m
[31m-				th:classappend="${review.downvotedByCurrentCustomer ? 'fas' : 'far'}"[m
[31m-				th:id="'linkVoteDown-' + ${review.id}"[m
[31m-				th:reviewId="${review.id}"[m
[31m-				th:href="@{'/vote_review/' + ${review.id} + '/down'}"></a>[m
[31m-		</div>[m
[31m-		[m
[31m-		<h2 style="font-size: 4rem;">[[${review.headline}]]</h2>[m
[31m-		<p style="font-size: 3rem;">[[${review.comment}]]</p>[m
[31m-		<div style="font-size: 2rem; font-weight: lighter; color: orange; background-color: #000;">[m
[31m-			[[${review.customer.fullName}]], <div th:replace="fragments :: format_time(${review.reviewTime})"></div>[m
[31m-		</div>[m
[31m-	</div>[m
[31m-	</th:block>[m
[31m-</div> -->[m
 </body>[m
 </html>[m
\ No newline at end of file[m
