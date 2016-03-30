<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<style>
.error {
	display: none;
	margin-left: 10px;
}

.error_show {
	color: red;
	margin-left: 10px;
}
</style>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>

<body>
	<div class="navbar-header">
		<a class="navbar-brand" href="<c:url value="/login"/> "> Pitchfork
			State Bank</a>
	</div>
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
			<li><a href="/cse">Home</a></li>
			<li><a href="/cse/aboutus/">About Us</a></li>

		</ul>
	</nav>
	

	<%
		final String type = (String) request.getAttribute("role");
		if (type.equals("Admin")) {
	%>


	<jsp:include page="Arightsidebar.jsp" />


	<%
		}
	%>


	<jsp:include page="footer.jsp" />
	<!-- header here-->


	<div class="row">
		<div class="col-md-6 col-md-offset-5 ">
			<h3 class="header">Profile Details</h3>
		</div>
	</div>
	<div>
		<div class="col-md-6 col-md-offset-3">
			<form:form class="col s12" commandName="ProfileTransaction"
				id="register-form" action="${postLink}" method="post">

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="firstname" id="icon_prefix" type="text" value="" />
						<label style="transform: translateY(-140%);" for="icon_prefix">First
							Name</label>

					</div>
					<div class="input-field col s6">
					
						<form:input path="lastname" id="icon_lastname" type="text"
							value="" />
						
						     <label style="transform: translateY(-140%);" for="icon_lastname">Last
							Name</label>

					</div>
				</div>
				<!-- Error Display start 1-->
				<div class="row" id="errorrow1" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorfirstName" for="icon_prefix"
							style="display: hidden; color: red">Incorrect First Name</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
					 	<label id="errorlastName" style="display: none; color: red;">Incorrect
							Last Name</label>
					</div>
				</div>
				<!-- Error Display end 1-->

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="userName" id="icon_username" type="text"
							value="" />
						<label style="transform: translateY(-140%);" for="icon_username">User
							Name</label>

					</div>
					<div class="input-field col s6">
						
						<form:input path="social" id="icon_ssn" type="text" value=""
							class="validate" />
						<label style="transform: translateY(-140%);" for="icon_ssn">SSN</label>


					</div>
				</div>

				<!-- Error Display start 2-->

				<div class="row" id="errorrow2" style="display: ${display}">
					<c:choose>
						<c:when test="${empty errorMessageUserName}">
							<div class="col s5" style="padding-left: 150px">
								 <label
									id="errorUserName" for="icon_prefix"
									style="display: none; color: red">Incorrect User Name</label>
							</div>
						</c:when>

						<c:otherwise>
							<div class="col s5" style="padding-left: 150px">
								 <label
									id="errorUserName" for="icon_prefix"
									style="display: ${display}; color: red">User Already Exist</label>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<c:choose>
							<c:when test="${empty errorMessageSocial}">
								<label id="errorssn" style="display: none; color: red">Incorrect
									SSN</label>
							</c:when>
							<c:otherwise>
								<label id="errorssn" style="display: ${display}; color: red">SSN
									Already Exist</label>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<!-- Error Display end  2-->

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="password" id="icon_password" type="password"
							value="" class="validate" />
						<label style="transform: translateY(-140%);" for="icon_password">Password</label>
					</div>
					<div class="input-field col s6">
						
						<form:input path="" id="icon_password2" type="password" value=""
							class="validate" />
						<label style="transform: translateY(-140%);" for="icon_password2">Confirm
							Password</label>
					</div>
				</div>

				<!-- Error Display start 3-->
				<div class="row" id="errorrow3" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="dummyError" style="display: none; color: red"></label>
					</div>
					<div class="col s5" style="padding-left: 150px">
						<label id="errorPassword" style="display: none; color: red">The
							Passwords do not match</label>
					</div>

					<div class="col s12" style="padding-left: 150px">
						<label id="errorPassword2" style="display: none; color: red">Passwords do not match. (Minimum password length should be 8 characters)</label>
					</div>

				</div>
				<!-- Error Display end 3-->


				<div class="row">
					<div class="input-field col s6">
					
						<form:input path="phoneNumber" id="icon_phone" type="tel" value=""
							class="validate" />
						<label style="transform: translateY(-140%);" for="icon_phone">Telephone</label>
					</div>

					<div class="input-field col s6">
						
						<form:input path="email" id="icon_email" type="email" value="" />
						<label style="transform: translateY(-140%);" for="icon_email">Email</label>
					</div>
				</div>

				<!-- Error Display start 4-->
				<div class="row" id="errorrow4" style="display:  ${display}">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorphoneNumber" for="icon_prefix"
							style="display: none; color: red">Incorrect Telephone No.</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
					<c:choose>
						<c:when test="${empty errorMessageEmail}">
						<label id="errorEmail" style="display: none; color: red">Incorrect
							Email</label>
						</c:when>
						<c:otherwise>
						<label id="errorEmail" style="display: ${display}; color: red">Email
									Already Exist</label>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<!-- Error Display end 4-->

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="address" id="icon_address" type="text" value="" />

						<label style="transform: translateY(-140%);" for="icon_address">Address</label>

					</div>
					<div class="input-field col s6">
					
						<form:input path="city" id="icon_city" type="text" value=""
							class="validate" />
						<label style="transform: translateY(-140%);" for="icon_city">City</label>

					</div>
				</div>

				<!-- Error Display start 5-->
				<div class="row" id="errorrow5" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="erroradd" for="icon_prefix"
							style="display: none; color: red">Incorrect Address</label>
					</div>
					<div class="col s5"	style="margin-left: 450px; padding-left: 150px">
						<label id="errorcity" style="display: none; color: red">Incorrect
							City</label>
					</div>
				</div>

				<!-- Error Display end 5-->

				<div class="row">
					<div class="input-field col s6">
						
						<form:input path="state" id="icon_state" type="text" value="" />
						<!-- class="validate" -->
						<label style="transform: translateY(-140%);" for="icon_state">State</label>
					</div>

					<div class="input-field col s6">
						
						<form:input path="zipcode" id="icon_zipcode" type="text" value=""
							class="validate" />
						<label style="transform: translateY(-140%);" for="icon_zipcode">Zipcode</label>


					</div>
				</div>
				<!-- Error Display start 6-->
				<div class="row" id="errorrow6" style="display: none">
					<div class="col s5" style="padding-left: 150px">
						<label id="errorstate" for="icon_prefix"
							style="display: none; color: red">Incorrect State</label>
					</div>
					<div class="col s5" style="margin-left: 450px; padding-left: 150px">
						<label id="errorzipcode" style="display: none; color: red">Incorrect
							Zipcode</label>
					</div>
				</div>

				<!-- Error Display end 6-->



				<div class="row">
					<div class="col s12">
						<form:errors path="*" style="display:inline; color:red"
							element="div"></form:errors>
					</div>
				</div>

				<div class="row">
					<div class="col  offset-s2  ">
						<div class="form-group">
							<form:select path="transactionType" class="form-control"
								id="sel1">
								<%
									if (type.equals("Admin")) {
								%>
								<option value="regular_employee">Regular Employee</option>
								<option value="manager">System Manager</option>
								<%
									} else {
								%>>
								
								<option value="custoumer">Customer</option>
								<option value="merchant">Merchant</option>
								<%
									}
								%>
							</form:select>
							<label for="sel1">Select Account type</label>
						</div>
					</div>
					<div class="col offset-s2  ">
						<div class="form-group" style="display:${hide};">
						
							<form:select path="savingsEnabled" class="form-control" id="sel2" >
							
								<option value="0">Checking</option>
                                <option value="1">Checking and Saving</option>
                          
							</form:select>
							<label for="sel1">Select Account type</label>
						</div>
					</div>

				</div>
				<div class="row">
					<div class="col s4 offset-s3 ">
						<div class="g-recaptcha " data-sitekey="6Ldllw8TAAAAADfxeC8RRVVK3ymSfMoUl5LYa2Kd"></div>
					</div>
				</div>
				<div class="row">
					<div class="col s4 offset-s4 ">
						<h5 style="color:blue"><% final String error = (String)request.getAttribute ("errorMessage");
												if(error==null || error.isEmpty()){
													
												} else{
												out.print(error);
												}%>
												</h5>
						<button class="btn" id="submit_btn" type="submit" name="action">
							Submit <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
			</form:form>

		</div>
	</div>


	<!-- Footer here-->



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




	<!-- Styles -->


	<!-- JavaScript Code -->

	<script>
		$(document)
				.ready(
						function() {

							$('#submit_btn').click(function(event) {
								var validation = 1;
								// A
								validation &= lastNameFunction();
								validation &= iconPrefixFunction();
								validation &= ssnFunction();
								validation &= userNameFunction();
								validation &= passwordFunction();
								validation &= emailFunction();
								validation &= telephoneFunction();
								validation &= cityFunction();
								validation &= addFunction();
								validation &= zipFunction();
								validation &= stateFunction();
								
								if (validation == 1) {
									$("#register-form").submit();
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
								var input = $('#icon_phone');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^0-9]/);

								if (length != 10 || $.trim(value).length == 0
										|| nameRegex.test(value)) {
									$('#errorphoneNumber').get(0).style.display = "inline";
									$('#errorrow4').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorphoneNumber').get(0).style.display = "none";
									if ($('#errorEmail').get(0).style.display != "inline") {
										$('#errorrow4').get(0).style.display = "none"
									}
									return true;
								}
							};

							//email
							var emailFunction = function() {
								var input = $('#icon_email');
								var value = input.val();
								var length = value.length;
								var emailRegex = new RegExp(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/);
									if (length > 45 || $.trim(value).length == 0
										|| !emailRegex.test(value)) {
									$('#errorEmail').get(0).style.display = "inline";
									$('#errorrow4').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorEmail').get(0).style.display = "none";
									if ($('#errorphoneNumber').get(0).style.display != "inline") {
										$('#errorrow4').get(0).style.display = "none"
									}
									return true;
								}
							};

							//username
							var userNameFunction = function() {
								var input = $('#icon_username');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^a-zA-Z0-9_]/);

								if (length < 8 || $.trim(value).length == 0
										|| nameRegex.test(value)) {
									$('#errorUserName').get(0).style.display = "inline";
									$('#errorrow2').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorUserName').get(0).style.display = "none";
									if ($('#errorssn').get(0).style.display != "inline") {
										$('#errorrow2').get(0).style.display = "none"
									}
									return true;
								}
							};

							$('#submit').click(userNameFunction);

							//paassword
							var passwordFunction = function() {

								var password = document
										.getElementById('icon_password');
								var reTypePassword = document
										.getElementById('icon_password2');
								var input = $('#icon_password');
								var value = input.val();
								var length = value.length;
								var input1 = $('#icon_password2');
								var value1 = input1.val();
								var length1 = value.length1;

								if (length < 8
										|| length1 < 8
										|| $.trim(password.value).length == 0
										|| $.trim(reTypePassword.value).length == 0) {
									$('#errorPassword2').get(0).style.display = "inline";
									$('#errorrow3').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorPassword2').get(0).style.display = "none";
									$('#errorrow3').get(0).style.display = "none";

									if (password.value != reTypePassword.value) {
										$('#errorPassword').get(0).style.display = "inline";
										$('#errorrow3').get(0).style.display = "inline";
										//$('#modal1').closeModal();
										return false;
									} else {
										$('#errorPassword').get(0).style.display = "none";
										$('#errorrow3').get(0).style.display = "none";
										return true;
									}

								}
							};

							//ssn
							var ssnFunction = function() {
								var input = $('#icon_ssn');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^0-9]/);

								if (length != 9 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorssn').get(0).style.display = "inline";
									$('#errorrow2').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorssn').get(0).style.display = "none";
									if ($('#errorUserName').get(0).style.display != "inline") {
										$('#errorrow2').get(0).style.display = "none"
									}
									return true;
								}
							};

							$('#submit').click(ssnFunction);

							//state
							var stateFunction = function() {
								var input = $('#icon_state');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorstate').get(0).style.display = "inline";
									$('#errorrow6').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorstate').get(0).style.display = "none";
									if ($('#errorzipcode').get(0).style.display != "inline") {
										$('#errorrow6').get(0).style.display = "none"
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
									$('#errorrow6').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorzipcode').get(0).style.display = "none";
									if ($('#errorstate').get(0).style.display != "inline") {
										$('#errorrow6').get(0).style.display = "none"
									}
									return true;
								}
							};

							//address
							var addFunction = function() {
								var input = $('#icon_address');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^A-Za-z0-9,.\s\#]/);

								if (length < 2 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#erroradd').get(0).style.display = "inline";
									$('#errorrow5').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									console.log("Address not validated");
									return false;
								} else {
									$('#erroradd').get(0).style.display = "none";
									if ($('#errorcity').get(0).style.display != "inline") {
										$('#errorrow5').get(0).style.display = "none"
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
									$('#errorrow5').get(0).style.display = "inline";
									//		$('#modal1').closeModal();
									return false;
								} else {
									$('#errorcity').get(0).style.display = "none";
									if ($('#erroradd').get(0).style.display != "inline") {
										$('#errorrow5').get(0).style.display = "none"
									}
									return true;
								}
							};

						});
	</script>
</body>
</html>