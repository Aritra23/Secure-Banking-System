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
<body>

	<!--- Page Header -->

<div class="navbar-header">
    <a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State Bank</a>
     </div>  
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
        <li><a href="/cse/aboutus/" >About Us</a></li>
		</ul>
	</nav>
	<div class="container-fluid"></div>

	<jsp:include page="Crightsidebar.jsp" />

	<div class="row">

		<div class="col-md-6 col-md-offset-4 ">

			<h3 class="header">Customer Account</h3>

		</div>
	</div>

	<div class="row">
		<div class="col-md-6 col-md-offset-4">

			<table>
				<thead>
					<tr>
					
					
						<th data-field="">Account No.</th>
						<th data-field="">Account Type</th>
						<th data-field="">Pending Balance</th>
						<th data-field="">Balance</th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<td>${CAccountNo}</td>
						<td>${CAccountType}</td>
						<td>${CpendingBalance}</td>
						<td>${CheckingBalance}</td>
						
					</tr>
					<tr>
						<td>${SAccountNo}</td>
						<td>${SAccountType}</td>
						<td>${SpendingBalance}</td>
						<td>${SavingsBalance}</td>
					</tr>
				</tbody>

			</table>

			

			<div class="row"></div>
		</div>
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

</body>
</html>
