
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<%         %>
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


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


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
	<jsp:include page="Crightsidebar.jsp" />
	<!-- <div class="col-sm-9 col-sm-offset-2 col-md-8 col-md-offset-2 main"> -->



	<c:forEach var="Transaction" items="${transactionRecur}">
	</c:forEach>

	<div class="row"></div>
	<div class="row"></div>



	<div class="row">




		<div class="col-md-6 col-md-offset-4 ">

			<h3 class="header">
				<% final Integer type = (Integer)request.getAttribute ("TypeOf");
			if(type==1){out.print("User Approval Request");}
			else if(type==2){out.print("Edit Profile Request");}
			else if(type==3){out.print("Critical Transactions Approval");}
			else if(type==4){out.print("Delete Profile Request");}
%>			</h3>
		</div>
	</div>


	<div class="col-md-6 col-md-offset-3">
		<div class="dropdown">
			<button class="btn btn-primary dropdown-toggle" type="button"
				data-toggle="dropdown">
				Select Request Type <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="/cse/manager/">New User</a></li>
				<li><a href="/cse/manager/UPrequest">Update Profile</a></li>
				<li><a href="/cse/manager/CTrequest">Critical Transaction</a></li>
				<li><a href="/cse/manager/deleteProfileRequest">Delete User Account</a></li>
			</ul>
		</div>
	</div>

	<% 
		if(type==1) {%>
	<%@ include file="ProfileTransactionTable.jsp"%>
	<% }
		else if(type==2){%>
	<%@ include file="UpdateProfileTable.jsp"%>
	<%}
	  	else if(type==3){%>
		<%@ include file="TransferApprovalTable.jsp"%>
	<%} 
	  	else if(type==4){%>
		<%@ include file="DeleteProfileTable.jsp"%>
	<%}%>

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
