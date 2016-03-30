<!DOCTYPE html>
<html lang="en">
<jsp:include page="jsDisble.jsp" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Pitchfork State Bank- login</title>

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


<script src="https://www.google.com/recaptcha/api.js" async defer></script>

</head>

<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="footer.jsp" />
	<!-- header here-->




	<div class="container-fluid"></div>

	<!-- <div class="col-sm-9 col-sm-offset-2 col-md-8 col-md-offset-2 main"> -->

	<div class="row"></div>
	<div class="row"></div>
	<div class="row"></div>
	<div class="row"></div>
	<div class="row"></div>
	<div class="row"></div>

	<% final Boolean display = (Boolean)request.getAttribute ("display");
			if(display) {%>
	<%@ include file="forgotpassword1.jsp"%>
	<% }%>

	<% final String errorMessage  = (String)request.getAttribute ("errorMessage");
					if(!errorMessage.isEmpty()) {%>
	<div class="col-md-3 col-md-offset-4 col-xs-offset-1 ">
		<div class="col s10 offset-s3">
			<h5 class="header" style="color: red">${errorMessage}</h5>
		</div>
	</div>
	<%}%>
	<!-- Footer here-->

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
