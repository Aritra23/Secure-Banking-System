<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.List"%>
<%@ page import="com.asu.cse.model.SSProfileTransaction"%>
<%@ page import="com.asu.cse.model.SSUser"%>
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

<body>
	<%
		final Integer type = (Integer) request.getAttribute("TypeOf");
	%>

	<!--- Page Header -->


	<div class="navbar-header">
		<a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State
			Bank</a>
	</div>

<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
        <li><a href="/cse/aboutus/">About Us</a></li>
		</ul>
	</nav>
	<nav>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/cse/admin/internalsignup" >Add
						New Internal User Account</a></li>
			</ul>
	</nav>
	<jsp:include page="Arightsidebar.jsp" />

	<div class="container-fluid"></div>





	<!-- <div class="col-sm-9 col-sm-offset-2 col-md-8 col-md-offset-2 main"> -->

	<div class="row"></div>
	<div class="row"></div>

	<div class="row">

		<div class="col-md-6 col-md-offset-4 ">

			<h3 class="header">
				<%
					if (type == 1) {
						out.print("Registered Users");
					} else if (type == 2) {
						out.print("Registered Merchants");
					} else if (type == 3) {
						out.print("Registered Employee");
					} else if (type == 4) {
						out.print("Registered Manager");
					}
				%>
			</h3>
		</div>
	</div>

	<div class="col-md-6 col-md-offset-3">
		<div class="dropdown">
			<button class="btn btn-primary dropdown-toggle" type="button"
				data-toggle="dropdown">
				Select User Type <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="/cse/admin/useraccount">Show Users</a></li>
				<li><a href="/cse/admin/merchantaccount">Show Merchant</a></li>
				<li><a href="/cse/admin/employeeaccount">Show Employees</a></li>
				<li><a href="/cse/admin/manageraccount">Show Managers</a></li>
			</ul>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="row">
				<c:if test="${error=='true'}">
					<div class="row"></div>
					<h5 class="text-center">${errorMessage}</h5>
					<div class="row"></div>
				</c:if>

				<ul class="collapsible" data-collapsible="accordion">
					<%
						List<SSUser> User = (List<SSUser>) request.getAttribute("UserList");
						if (User != null)
							for (SSUser currentUser : User) {
					%>
					<li>
						<div class="collapsible-header">
							<span style="margin-left: 2pc"></span>
							<%
								out.print(currentUser.getUserid());
							%><span style="margin-left: 10pc"></span>
							<%
								out.print(currentUser.getFirstName());
							%>
							<span style="margin-left: 10pc"></span>
							<%
								out.print(currentUser.getLastName());
							%>
						</div>
						<div class="collapsible-body">



							<%--Dynamic page loading should be created so as to  --%>
							<table class="striped">


								<tr>
									<td><span style="margin-left: 3pc"></span>Address:</td>
									<td>
										<%
											out.print(currentUser.getAddress());
										%>
									</td>

								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>City:</td>
									<td>
										<%
											out.print(currentUser.getCity());
										%>
									</td>

								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>Email</td>
									<td>
										<%
											out.print(currentUser.getEmail());
										%>
									</td>
								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>Phone number:</td>
									<td>
										<%
											out.print(currentUser.getPhoneNumber());
										%>
									</td>
								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>Address:</td>
									<td>
										<%
											out.print(currentUser.getAddress());
										%>
									</td>
								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>State:</td>
									<td>
										<%
											out.print(currentUser.getState());
										%>
									</td>
								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>User Name:</td>
									<td>
										<%
											out.print(currentUser.getUserName());
										%>
									</td>
								</tr>
								<tr>
									<td><span style="margin-left: 3pc"></span>Country:</td>
									<td>
										<%
											out.print(currentUser.getCountry());
										%>
									</td>
								</tr>
							</table>

						</div>
					</li>
					<%
						}
					%>
				</ul>
			</div>


		</div>
	</div>
	<jsp:include page="footer.jsp" />
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->



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
