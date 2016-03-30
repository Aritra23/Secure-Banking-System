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
		<a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State Bank</a>
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

			<h3 class="header">Critical Transations</h3>

		</div>
	</div>


	<div class="row">
		<div class="col-md-6 col-md-offset-3">

			<table class="striped">
				<thead>
					<tr>
						<th data-field="">user Name</th>
						<th data-field="">Employee Name</th>
						<th data-field="">Transaction Id</th>
						<th data-field="">Authorization</th>
						<th data-field="">View</th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<td>${userName}</td>
						<td>${customerName}</td>
						<td>${transactionId}</td>
						<td><a href="<c:url value="#" />">${status}</a></td>


						<td><a href="#">${view}</a></td>
					</tr>


				</tbody>

			</table>
		</div>
	</div>
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
</body>
</html>
