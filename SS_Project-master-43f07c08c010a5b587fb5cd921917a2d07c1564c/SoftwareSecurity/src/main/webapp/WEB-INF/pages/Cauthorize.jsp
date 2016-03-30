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

			<h3 class="header">Authorize Transactions</h3>

		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-4">

			<ul class="collapsible" data-collapsible="accordion">
				<c:forEach var="PendingTransaction" items="${pendingTransactions}">
					<li>
						<div class="collapsible-header">
							<b>Transaction Id:
								${PendingTransaction.getFundTransactionId()}</b><span
								style="margin-left: 28pc"></span> <a href="#">&#8600;</a>
						</div>
						<div class="collapsible-body">



							<%--Dynamic page loading should be created so as to  --%>

							<table class="striped">

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Transaction
											Id:</b></td>
									<td>${PendingTransaction.getFundTransactionId()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>User Id:</b></td>
									<td>${PendingTransaction.getUserId()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Transaction
											Type:</b></td>
									<td>${PendingTransaction.getTransactionType()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span> <b>Source
											Account:</b></td>
									<td>$ ${PendingTransaction.getSourceAccount()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Destination
											Account:</b></td>
									<td>${PendingTransaction.getDestinationAccount()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Amount:</b></td>
									<td>$ ${PendingTransaction.getAmount()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Status:</b></td>
									<td>${PendingTransaction.getStatus()}</td>
								</tr>

								<tr>
									<td><span style="margin-left: 1pc"></span><b>Date and
											Time:</b></td>
									<td>${PendingTransaction.getTimeStamp()}</td>
								</tr>



							</table>
							<form action="/cse/authorize" method="POST"
								id="pendingTransaction${PendingTransaction.getFundTransactionId()}"
								commandName="updatedTransactionId"
								modelAttribute="updatedTransactionId">
								<input type="hidden" name="updatedTransactionId"
									value="${PendingTransaction.getFundTransactionId()}" />
								<table>
									<tr>

										<td><span style="margin-left: 2pc"></span></td>
										<td>
											<div class="dropdown">
												<select class="form-control" id="BoolVal" name="BoolVal">
													<option value=true>Approve</option>
													<option value=false>Decline</option>
												</select>
											</div>

										</td>
										<td>
											<button id="submit" class="btn waves-effect waves-light"
												type="submit" name="action">
												Submit <i class="material-icons right">send</i>
											</button>
										</td>


									</tr>
									<%--Script should be inserted here to check whether the text box is filled before approving or not --%>


								</table>
							</form>

						</div>
					</li>
				</c:forEach>
			</ul>

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
