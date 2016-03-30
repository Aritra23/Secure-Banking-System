<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Pitchfork State Bank</title>

<!-- Bootstrap -->
<!-- <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/materialize.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"> -->

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

				</c:if>

				<li class="bold ${activeAdmin} "><a
					href="<c:url value="/admin" />"> Internal User Request</a></li>
				<li class="bold ${activePii}"><a
					href="<c:url value="/piiRequest" />">PII Request</a></li>
				<li class="bold ${activeeditprofile}"><a
					href="<c:url value="/editprofile"/>">Edit Profile</a></li>
				<li class="bold ${activeSysLog}"><a
					href="<c:url value="/sysLog" />">System Log</a></li>
				<li class="bold ${activeAccessUsers}"><a href="<c:url value="/admin/useraccount" />" class=" ">Show Users</a></li>
			    
			</ul>

			<ul class="nav nav-sidebar">

				<li><a href="<c:url value="/logout" />">Sign Out</a></li>

			</ul>

		</div>
	</div>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug 
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>-->


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- <script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed 
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> -->

	<!-- <script type="text/javascript"
		src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/materialize.js"></script>  -->

</body>
</html>
