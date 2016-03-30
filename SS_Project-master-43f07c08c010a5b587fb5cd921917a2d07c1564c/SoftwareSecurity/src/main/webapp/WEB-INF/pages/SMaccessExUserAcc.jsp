<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

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
<%
	final Boolean type = (Boolean) request.getAttribute("editPassword");
	final String role = (String) request.getAttribute("role");
%>
<body>

	<!--- Page Header -->
	<div class="navbar-header">
		<a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State
			Bank</a>
	</div>
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
			<li><a href="/cse/" >Home</a></li>
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
		<div class="col-md-6 col-md-offset-4 ">
			<h3 class="header">Modify User Profile</h3>
		</div>
	</div>

	<div class="row">
		<form:form class="col s12" commandName="user"
			action="/cse/useraccounts" method="post" modelAttribute="user"
			id="register-form">
			<div class="col-md-4 col-md-offset-4 ">

				<ul>
					<li class="search">
						<div class="search-wrapper card focused">
							<form:input path="userName" id="icon_search" type="text"
								placeholder="Search by User Name" value="" />

						</div>
					</li>
					<li></li>
				</ul>

			</div>
			<div class="" style="padding: 10px; height: 45px;">
				<button class="btn waves-effect waves-light" type="submit"
					name="action">
					Search <i class="material-icons right" id="submit_btn">search</i>
				</button>
			</div>
			<div class="row" id="errorrow1" style="display: none">
				<div class="col s5" style="padding-left: 150px">
					<label id="errorsearch" for="icon_prefix"
						style="display: none; color: red">Incorrect entry</label>
				</div>
			</div>
		</form:form>
		<div></div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-4">

			<table class="striped">
				<thead>
					<%-- <c:choose>
						<c:when test="${display == null}">
						</c:when>
						<c:otherwise> --%>
					<tr>
						<th data-field="">User Name</th>
						<th data-field="">First Name</th>
						<th data-field="">Last Name</th>
					</tr>
					<%-- </c:otherwise>
					</c:choose> --%>
				</thead>
				<%-- <c:choose>
					<c:when test="${display == null}">
					</c:when>
					<c:otherwise> --%>
				<tbody>
					<h4>${UserProfilesError}</h4>
					<c:forEach var="UserProfile" items="${UserProfiles}">
						<tr>
							<td>${UserProfile.getUserName()}</td>
							<td>${UserProfile.getFirstName()}</td>
							<td>${UserProfile.getLastName()}</td>
						</tr>
					</c:forEach>

				</tbody>
				<%-- </c:otherwise>
				</c:choose> --%>
			</table>
		</div>
	</div>
	<div></div>
	<div>
		<jsp:include page="footer.jsp" />
	</div>
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
	<script>
		$(document)
				.ready(
						function() {

							$('#submit_btn').click(function(event) {
								var validation = 1;
								validation &= searchFunction();
								if (validation == 1) {
									$("#register-form").submit();
								} else {
									event.preventDefault();
									return false;
								}

							});

							//search function
							var searchFunction = function() {
								var input = $('#icon_search');
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^a-zA-Z0-9_]/);

								if (length < 8 || $.trim(value).length == 0
										|| nameRegex.test(value)) {
									$('#errorsearch').get(0).style.display = "inline";
									$('#errorrow1').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorsearch').get(0).style.display = "none";
									if ($('#errorsearch').get(0).style.display != "inline") {
										$('#errorrow1').get(0).style.display = "none"
									}
									return true;
								}
							};
							});
	</script>
</body>
</html>