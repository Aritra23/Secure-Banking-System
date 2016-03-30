<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="jsDisble.jsp" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Pitchfork State Bank</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/materialize.min.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<%
	final Boolean type = (Boolean) request.getAttribute("editPassword");
	final String role = (String) request.getAttribute("role");
%>
<body>

	<!-- header here-->
	<div class="navbar-header">
		<a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State
			Bank</a>
	</div>
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
			<li><a href="/cse/aboutus/" >About Us</a></li>
		</ul>
	</nav>
	<div class="container-fluid"></div>

	<%
		if (role.equals("Customer")) {
			final Integer Roletype = 1;
	%>
	<jsp:include page="Crightsidebar.jsp" />
	<%
		} else if (role.equals("Merchant")) {
			final Integer Roletype = 2;
	%>
	<jsp:include page="Crightsidebar.jsp" />
	<%
		} else if (role.equals("Admin")) {
			final Integer Roletype = 3;
	%>
	<jsp:include page="Arightsidebar.jsp" />
	<%
		} else if (role.equals("Manager")) {
			final Integer roletype = 4;
	%>

	<jsp:include page="Crightsidebar.jsp" />
	<%
		} else if (role.equals("Employee")) {
			final Integer roletype = 5;
	%>
	<jsp:include page="Crightsidebar.jsp" />

	<%
		} else {
	%>

	<%
		}
	%>
	<div class="row">

		<div class="col-md-6 col-md-offset-5">
			<%
				if (type) {
			%>
			<h3 class="header">Edit Password</h3>
			<%
				} else {
			%>
			<h3 class="header">Profile Details</h3>
			<%
				}
			%>

			<%
				final String errorMessage = (String) request.getAttribute("errorMessage");
				if (!errorMessage.equals("")) {
			%>
			<div class="row">
				<h5 class="header"><%=errorMessage%></h5>
			</div>
			<%
				}
			%>

		</div>
	</div>
	<div class="row">

		<%
			if (!type) {
		%>
		<div class="col-md-6 col-md-offset-4">
			<h5 style="color: blue">
				<%
					final Integer RequestTrue = (Integer) request.getAttribute("RequestTrue");
						if (RequestTrue == 1) {
							out.println(
									"An Edit profile request is already pending for approval.<br> Creating a new request will delete the previous one.");
						}
				%>
			</h5>
		</div>
		<div class="col-md-6 col-md-offset-3">

			<form:form commandName="user" action="${PostLink}" method="POST"
				id="form_edit_profile" modelAttribute="user" class="col s12">


				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="firstName" id="icon_prefix" type="text"
							value="${user.getFirstName()}" />
						<label for="icon_prefix">First Name</label>
					</div>
					<div class="input-field col s6">
						
						<form:input path="lastName" id="icon_lastname" type="text"
							value="${user.getLastName()}" />
						<label for="icon_lastname">Last Name</label>
					</div>
				</div>
				<!-- Error Display start 1-->
				<div class="row" id="errorrow1" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorfirstName" for="icon_prefix"
							style="display: none; color: red">Incorrect First Name</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<label id="errorlastName" style="display: none; color: red">Incorrect
							Last Name</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="phoneNumber" id="icon_telephone" type="tel"
							value="${user.getPhoneNumber()}" />
						<label for="icon_telephone">Telephone</label>
					</div>

					<div class="input-field col s6">
					<input disabled	id="icon_email" type="email" value="${user.getEmail()}"
							class="validate"> <label for="email">Email</label>
					</div>
				</div>
				<!-- Error Display start 2-->
				<div class="row" id="errorrow2" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorphoneNumber" for="icon_telephone"
							style="display: none; color: red">Incorrect Telephone No.</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s6">
					
						<form:input path="address" id="icon_address" type="tel"
							value="${user.getAddress()}" />

						<label style="transform: translateY(-140%);" for="address">Address</label>
					</div>
					<div class="input-field col s6">
					<input disabled	id="icon_userName" type="text" value="${user.getUserName()}"
							class="validate"> <label
							style="transform: translateY(-140%);" for="address">UserName</label>

					</div>
				</div>
				<!-- Error Display start 3-->
				<div class="row" id="errorrow3" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="erroradd" for="icon_address"
							style="display: none; color: red">Incorrect Address</label>
					</div>

				</div>


				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="city" id="icon_city" type="text"
							value="${user.getCity()}" />
						<label style="transform: translateY(-140%);" for="City">City</label>
					</div>

					<div class="input-field col s6">
					

						<form:input path="zipcode" id="icon_zipcode" size="5" type="text"
							value="${user.getZipcode()}" />
						<label style="transform: translateY(-140%);" for="ZipCode">ZipCode</label>
					</div>
				</div>

				<!-- Error Display start 4-->
				<div class="row" id="errorrow4" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorcity" style="display: none; color: red">Incorrect
							City</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<label id="errorzipcode" style="display: none; color: red">Incorrect
							Zipcode</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="state" id="icon_state" type="text"
							value="${user.getState()}" />
						<label style="transform: translateY(-140%);" for="State">State</label>
					</div>

					<div class="input-field col s6">
					
						<form:input path="country" id="icon_country" type="text"
							value="${user.getCountry()}" />
						<label style="transform: translateY(-140%);" for="Country">Country</label>
					</div>
				</div>
				<!-- Error Display start 5-->

				<div class="row" id="errorrow5" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorstate" for="icon_state"
							style="display: none; color: red">Incorrect State</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<label id="errorcountry" for="icon_country"
							style="display: none; color: red">Incorrect Country</label>
					</div>

				</div>
				<!--
				<div class="row" style="display: hidden">
					<div class="input-field col s12">
						<textarea id="privatekey" class="materialize-textarea" 
							path="privatekey"></textarea>
						<label for="textarea1" >Private Key</label>
					</div>
				</div>
				-->
				<div class="row">
					<div class="col s4 offset-s3 ">
						<div class="g-recaptcha "
							data-sitekey="6Ldllw8TAAAAADfxeC8RRVVK3ymSfMoUl5LYa2Kd"></div>
					</div>
				</div>
				<div class="row">
					<div class="col s4 offset-s3 ">
						<button class="btn waves-effect waves-light" id="btn_edit_profile"
							type="submit" name="action">
							Update <i class="material-icons right">send</i>
						</button>
					</div>
					<div class="col s4 offset-s0 ">
					<a href="/cse/editpassword"
							class="btn waves-effect waves-light">Change Password<i
							class="material-icons right">send</i>
						</a>
					</div>
				</div>
			</form:form>
			<%
				} else {
			%>
		</div>
		<div class="col-md-6 col-md-offset-4">
			<form class="col s6" commandName="user" action="${PostLink}"
				method="POST" id="form_edit_password">

				<div class="row">
					<div class="input-field col s6">
						<i class="material-icons prefix">password</i> <input id="password"
							name="password" type="password" value="" class="validate">
						<label for="password">Current Password</label>
					</div>

					<div class="input-field col s6">

						<i class="material-icons prefix">password</i> <input
							id="newpassword" name="newpassword" type="password" value=""
							class="validate"> <label for="Re-type password">New
							Password</label>
					</div>

					<div class="input-field col s6">

						<i class="material-icons prefix">password</i> <input
							id="confirmpassword" name="confirmpassword" type="password"
							value="" class="validate"> <label for="Re-type password">Confirm
							New Password</label>
					</div>
									<!-- Error Display start 5-->

				<div class="row" id="errorrow6" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errornewpassword" for="newpassword"
							style="display: none; color: red">Password does not satisfy constraints</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<label id="errorrepassword" for="confirmpassword"
							style="display: none; color: red">Password entered does not match</label>
					</div>

				</div>
					<div class="row">
						<div class="col s4 offset-s3 ">
							<div class="g-recaptcha "
								data-sitekey="6Ldllw8TAAAAADfxeC8RRVVK3ymSfMoUl5LYa2Kd"></div>
						</div>
					</div>
					<div class="row">
						<div class="col s4 offset-s3 ">
							<button class="btn waves-effect waves-light" id="btn_edit_password"
								type="submit" name="action">
								Submit <i class="material-icons right">Update</i>
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<%
			}
		%>

	</div>

	<div>
		<jsp:include page="footer.jsp" />
	</div>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

	<script type="text/javascript"
		src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/materialize.js"></script>




	<!-- JavaScript Code -->

	<script>
		$(document)
				.ready(
						function() {
							var debug = true;
							$('#btn_edit_profile').click(function(event) {
								var validation = 1;
								validation &= lastNameFunction();

								validation &= iconPrefixFunction();
								validation &= telephoneFunction();
								validation &= addFunction();
								validation &= cityFunction();
								validation &= zipFunction();
								validation &= countryFunction();
								validation &= stateFunction();
								if (validation == 1) {
									$("#form_edit_profile").submit();
								} else {
									event.preventDefault();
									return false;
								}

							});
							$('#btn_edit_password').click(function(event) {
								var validation = 1;
								validation &= passwordFunction();
								debug && console.log("edit password button clicked");
								if (validation == 1) {
									$("#form_edit_password").submit();
								} else {
									event.preventDefault();
									return false;
								}

							});

							var iconPrefixFunction = function() {
								var input = $('#icon_prefix');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^a-zA-Z]/);
								if (length > 45 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorfirstName').get(0).style.display = "inline";
									$('#errorrow1').get(0).style.display = "inline";
									// $('#modal1').closeModal();
									return false;
								} else {
									$('#errorfirstName').get(0).style.display = "none";
									if ($('#errorlastName').get(0).style.display != "inline") {
										$('#errorrow1').get(0).style.display = "none"
									}
									return true;
								}
							};
							//last name
							var lastNameFunction = function() {
								var input = $('#icon_lastname');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^a-zA-Z]/);

								if (length > 45 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorlastName').get(0).style.display = "inline";
									$('#errorrow1').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorlastName').get(0).style.display = "none";
									if ($('#errorfirstName').get(0).style.display != "inline") {
										$('#errorrow1').get(0).style.display = "none"
									}
									return true;
								}
							};

							//telephone
							var telephoneFunction = function() {
								var input = $('#icon_telephone');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^0-9]/);

								if (length != 10 || $.trim(value).length == 0
										|| nameRegex.test(value)) {
									$('#errorphoneNumber').get(0).style.display = "inline";
									$('#errorrow2').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorphoneNumber').get(0).style.display = "none";
									if ($('#errorEmail').get(0).style.display != "inline") {
										$('#errorrow2').get(0).style.display = "none"
									}
									return true;
								}
							};

							//state
							var stateFunction = function() {
								var input = $('#icon_state');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorstate').get(0).style.display = "inline";
									$('#errorrow5').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorstate').get(0).style.display = "none";
									if ($('#errorcountry').get(0).style.display != "inline") {
										$('#errorrow5').get(0).style.display = "none"
									}
									return true;
								}
							};

							//country
							var countryFunction = function() {
								var input = $('#icon_country');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorcountry').get(0).style.display = "inline";
									$('#errorrow5').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorcountry').get(0).style.display = "none";
									if ($('#errorstate').get(0).style.display != "inline") {
										$('#errorrow5').get(0).style.display = "none"
									}
									return true;
								}
							};

							//zipcode
							var zipFunction = function() {
								var input = $('#icon_zipcode');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^0-9]/);

								if (length != 5 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorzipcode').get(0).style.display = "inline";
									$('#errorrow4').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorzipcode').get(0).style.display = "none";
									if ($('#errorcity').get(0).style.display != "inline") {
										$('#errorrow4').get(0).style.display = "none"
									}
									return true;
								}
							};

							//address
							var addFunction = function() {
								var input = $('#icon_address');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z0-9\s]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#erroradd').get(0).style.display = "inline";
									$('#errorrow3').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#erroradd').get(0).style.display = "none";
									if ($('#erroradd').get(0).style.display != "inline") {
										$('#errorrow3').get(0).style.display = "none"
									}
									return true;
								}
							};

							//city
							var cityFunction = function() {
								var input = $('#icon_city');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorcity').get(0).style.display = "inline";
									$('#errorrow4').get(0).style.display = "inline";
									//		$('#modal1').closeModal();
									return false;
								} else {
									$('#errorcity').get(0).style.display = "none";
									if ($('#errorzipcode').get(0).style.display != "inline") {
										$('#errorrow4').get(0).style.display = "none"
									}
									return true;
								}
							};

						});
		
		var passwordFunction = function() {

			var password = document
					.getElementById('newpassword');
			var reTypePassword = document
					.getElementById('confirmpassword');
			var input = $('#newpassword');
			var value = input.val();
			var length = value.length;
			var input1 = $('#confirmpassword');
			var value1 = input1.val();
			var length1 = value.length1;

			if (length < 8
					|| length1 < 8
					|| $.trim(password.value).length == 0
					|| $.trim(reTypePassword.value).length == 0) {
				$('#errornewpassword').get(0).style.display = "inline";
				$('#errorrow6').get(0).style.display = "inline";
				//	$('#modal1').closeModal();
				return false;
			} else {
				$('#errorrepassword').get(0).style.display = "none";
				$('#errorrow6').get(0).style.display = "none";

				if (password.value != reTypePassword.value) {
					$('#errorrepassword').get(0).style.display = "inline";
					$('#errorrow6').get(0).style.display = "inline";
					//$('#modal1').closeModal();
					return false;
				} else {
					$('#errornewpassword').get(0).style.display = "none";
					$('#errorrow6').get(0).style.display = "none";
					return true;
				}

			}
		};

	</script>


</body>
</html>
