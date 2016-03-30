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


	<jsp:include page="Arightsidebar.jsp" />


	<div class="row">

		<div class="col-md-6 col-md-offset-4 ">

			<h4 class="header">System logs will be e-mailed to you as a ZIP
				file.</h4>

		</div>
	</div>
	<div class="col-md-6 col-md-offset-4">
		<form class="col s6" method="post" action="/cse/sysLog">

			<div class="row">
				<div class="input-field col s6">

					<div class="col s4 offset-s7 ">
						<button class="btn waves-effect waves-light" type="submit"
							name="action" value="Download">
							E-mail Logs <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
			</div>
		</form>
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
