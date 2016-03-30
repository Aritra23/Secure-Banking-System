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

<body>
	<%
		final Integer type = (Integer) request.getAttribute("TypeOf");
	%>

	<!--- Page Header -->


	<div class="navbar-header">
		<a
			href="<c:url value="/"/>" class="navbar-brand">Pitchfork State Bank</a>
	</div>
<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
        <li><a href="/cse/aboutus/" >About Us</a></li>
		</ul>
	</nav>


	<jsp:include page="Arightsidebar.jsp" />

	<div class="container-fluid"></div>





	<!-- <div class="col-sm-9 col-sm-offset-2 col-md-8 col-md-offset-2 main"> -->

	<div class="row"></div>
	<div class="row"></div>

	<div class="row">

		<div class="col-md-6 col-md-offset-3 ">

			<h3 class="header">
				<%
					if (type == 1) {
						out.print("Internal User Request");
					} else if (type == 2) {
						out.print("Internal Update Profile");
					}
				%>
			</h3>
		</div>
	</div>

	
	<%
		if (type == 1) {
	%>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">

			<table class="striped">
				<c:out value="${username}" />
				<br>
				<c:choose>
					<c:when test="${username == null}">
					</c:when>
					<c:otherwise>
						<thead>
							<tr>
								<th data-field="">User Name</th>
								<th data-field="">Employee Name</th>
								<th data-field="">Employee Role</th>
								<th data-field="">Authorized</th>
								<th data-field="">Not Authorized</th>
							</tr>
						</thead>
					</c:otherwise>
				</c:choose>

			</table>

			<ul class="collapsible" data-collapsible="accordion">
				<li>
					<div class="collapsible-body">
						<th><c:out value="${username}" /><br> <c:choose>
								<c:when test="${username == null}">
								</c:when>
								<c:otherwise>
									<div class="collapsible-header">${userName}<span
											style="margin-left: 5pc">${empName}</span><span
											style="margin-left: 8pc">${empRole}</span><span
											style="margin-left: 7pc"><a href="#">${Approve}</a></span><span
											style="margin-left: 7pc"><a href="#">${Decline}</a></span>
									</div>
								</c:otherwise>
							</c:choose></th>

							<table class="striped">
								<thead>
									<tr>
										<th data-field="">Field Name</th>
										<th data-field="">Old Value</th>
										<th data-field="">Updated</th>
									</tr>

									<tr>
										<td>${fieldName}</td>
										<td>${oldValue}</td>
										<td>${updatedValue}</td>
									</tr>

								</thead>
							</table>

					</div>
				</li>

			</ul>

		</div>
	</div>
	<%
		} else if (type == 2) {
	%>
	<%@ include file="UpdateProfileTable.jsp"%>
	<%
		}
	%>

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
