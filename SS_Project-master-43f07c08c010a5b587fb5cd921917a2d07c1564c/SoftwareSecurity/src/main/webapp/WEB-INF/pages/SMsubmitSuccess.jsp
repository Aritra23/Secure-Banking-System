
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html lang="en">
<jsp:include page="jsDisble.jsp" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>PitchFork State Bank</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/materialize.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">





<style>
#modalPrep {
	z-index: 1003;
	display: block;
	opacity: 1;
	transform: scaleX(1);
	top: 10%;
}
</style>
</head>

<body>

	<!--- Page Header -->



	<div class="navbar-header">
		<a href="#" class="navbar-brand">Pitchfork State Bank</a>
	</div>
	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
			<li><a href="/cse/" >Home</a></li>
			<li><a href="/cse/aboutus/" >About Us</a></li>

		</ul>
	</nav>




	<div class="container-fluid"></div>

	<jsp:include page="Crightsidebar.jsp" />

	<div id="modalPrep" class="modal" style="">

		<!-- Modal Structure -->

		<div class="modal-content">
			<h5>${message1}</h5>
			<p></p>
			<h5>Transaction Id: ${transId}</h5>

		</div>
		<form action="/cse/" method="get">
		<button class="btn modal-trigger" type="submit" name="submit"
			style="float: right; margin-right: 20px;">OK</button>
	</form>

	</div>

	<div class="lean-overlay" id="materialize-lean-overlay-3"
		style="z-index: 1002; display: block; opacity: 0.5;"></div>

	<jsp:include page="footer.jsp" />
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
