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
		<a href="<c:url value="/"/>" class="navbar-brand">Pitchfork State
			Bank</a>
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
			<h3 class="header">Recent Transactions</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-4">

			<table class="striped">
				<thead>
					<tr>
						<th data-field="">Sender</th>
						<th data-field="">Receiver</th>
						<th data-field="">Transaction Type</th>
						<th data-field="">amount</th>
						<th data-field="">Date</th>
						<th data-field="">Status</th>

					</tr>
				</thead>

				<tbody>

					<c:forEach var="FundTransaction" items="${FundTransactions}">
						<tr>
							<td>${FundTransaction.getSourceAccount()}</td>
							<td>${FundTransaction.getDestinationAccount()}</td>
							<td>${FundTransaction.getTransactionType()}</td>
							<td>${FundTransaction.getAmount()}</td>
							<td>${FundTransaction.getTimeStamp()}</td>
							<td>${FundTransaction.getStatus()}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-4 ">
			<h5 class="header">E-mail Bank Statements(Bank statements are
				password protected with the user's SSN.)</h5>
		</div>
	</div>
	<div class="col-md-6 col-md-offset-4">
		<form class="col s6" method="post" action="/cse/CrecentTransaction">
			<label for="sel1">Month</label>
			<div class="row">

				<div class="input-field col s6">

					<select class="browser-default" name="month">
						<option value="Sep">September</option>
						<option value="Oct">October</option>
						<option value="Nov">November</option>
					</select>
				</div>
			</div>
			<div>
				<label for="sel0">Year</label>
				<div class="input-field col s6">

					<select class="browser-default">
						<option value="0">2015</option>
					</select>
				</div>
				<div class="input-field col s6">

					<div class="col s4 offset-s7 ">
						<button class="btn waves-effect waves-light" type="submit"
							name="action" value="Download">
							e-mail statement <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
			</div>
		</form>
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
