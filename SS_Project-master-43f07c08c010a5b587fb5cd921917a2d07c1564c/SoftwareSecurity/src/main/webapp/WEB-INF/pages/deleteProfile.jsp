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
<head>
<title>${title}</title>
</head>

<body>

	<!--- Page Header -->
	<div class="navbar-header">
    	<a href="#" class="navbar-brand">Pitchfork State Bank</a>
     </div>  
	<nav id="navbar" class="navbar-collapse collapse">
		<ul class="nav navbar-nav navbar-right">
		</ul>
	</nav>
	<div class="container-fluid"></div>

	<jsp:include page="Crightsidebar.jsp" />

	<div class="row">
		<div class="col-md-6 col-md-offset-4 ">
			<h3 class="header">Delete Profile</h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6 col-md-offset-4">

		<h5 style="color: blue">
			${message}
		</h5>
		<h5 style="color: blue">
			${postMessage}
		</h5>
		
		<c:if test="${postMessage != ''}">
			<form:form class="col s12" action="/cse/deleteProfile" method="post" >
				<input type="submit" class="btn" name="action" value="Delete"/>
			</form:form>
		</c:if>
		
		<div class="row"></div>
		<h5 style="color: blue">
			<a href="/cse/editprofile" class="btn waves-effect waves-light">
			Back to profile
			<i class="material-icons right">send</i>
			</a>
		</h5>
	
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