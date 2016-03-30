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

<style>
.footer {
	position: absolute;
	bottom: 0px;
	width: 100%;
	height: 60px;
	background: #fff;
	box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
}

.sidebar {
	background-color: #f5f5f5;
	padding: 20px;
	position: fixed;
	bottom: 0;
	top: 53px;
}

.side-nav {
	top: 52px;
}

.navbar-collapse {
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
}
</style>
</head>

<body>

	<!--- Page Header -->

	<div class="container-fluid"></div>


	<div class="row">
		<div class="side-nav fixed">
			<ul class="nav nav-sidebar">

				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<li class="bold "><a href="#" class=" ">Welcome,
							${pageContext.request.userPrincipal.name} </a></li>
					<li class="bold "><a href="#" class=" ">Role:<b>
								${role}</b>
					</a></li>
					<c:if test="${role == 'Customer' || role == 'Merchant'}">
					<div id="customerdiv">
						<li class="bold ${activeAuthorize} "><a href="<c:url value="/authorize" />">Authorize </a></li>
						<li class="bold ${activeAccDetail}"><a href="<c:url value="/CaccountDetails" />">Account Details</a></li>
						<li class="bold ${activeCeditProf}"><a href="<c:url value="/editprofile" />">Update Profile</a></li>
						<li class="bold ${activeCdeleteProf}"><a href="<c:url value="/deleteProfile" />">Delete Profile</a></li>
						<li class="bold ${activeCnewTrans}"><a href="<c:url value="/CnewTransaction" />">New Transaction</a></li>
						<li class="bold ${activeCrecentTrans}"><a href="<c:url value="/CrecentTransaction" />" class=" ">Recent
								Transactions</a></li>
					</div>
					</c:if>
					<c:if test="${role == 'Manager'}">
					<div id="managerdiv">
						<li class="bold ${activeMUserApp}"><a href="<c:url value="/manager" />" class=" active">User Approval</a></li>
						<li class="bold ${activeMudateProf}"><a href="<c:url value="/editprofile" />" class=" ">Update Profile</a></li>
						<li class="bold ${activeMuserAcc}"><a href="<c:url value="/useraccounts" />" class=" ">External User accounts</a></li>
					</div>
					</c:if>
					
					<c:if test="${role == 'Employee'}">
					<div id="employeediv">
						<li class="bold active"><a href="<c:url value="/employee" />" class=" active">Transfer Approval</a></li>
						<li class="bold "><a href="<c:url value="/editprofile" />" class=" ">Update Profile</a></li>
						</div>
					</c:if>
					<li><a href="<c:url value="/logout" />">Sign Out</a></li>
					</c:if>
			</ul>
		</div>
	</div>
</body>
</html>
