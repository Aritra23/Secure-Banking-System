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
<title>PitchFork State Bank</title>

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

	<!--- Page Header -->


	<jsp:include page="header.jsp" />
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
			<li><a href="/cse/" >Home</a></li>
			<li><a href="/cse/aboutus/" >About Us</a></li>

		</ul>
	</nav>
	

	<div class="container-fluid"></div>


	<div class="row">


		<!--      <jsp:include page="SMrightsidebar.jsp" /> -->


		<div class="row">

			<div class="col-md-6 col-md-offset-4 ">

				<h3 class="header">Transactions Approval Request</h3>

			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-4">

				<table class="striped">
					<thead>
						<tr>
							<th data-field="">Transaction Id</th>
							<th data-field="">Transaction Name</th>
							<th data-field="">Transaction Type</th>
							<th data-field="">Requested By</th>
							<th data-field="">Authorized</th>


						</tr>
					</thead>

					<tbody>
						<tr>
							<td>${TransactionId}</td>
							<td>${TransactionName}</td>
							<td>${TransactionType}</td>
							<td>${TrasactionRequestedBy}</td>
							<td><a href="#">${TrasactionAuthorized}</a></td>
						</tr>


					</tbody>
				</table>



			</div>
		</div>

	</div>


	<jsp:include page="footer.jsp" />
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

</body>
</html>
