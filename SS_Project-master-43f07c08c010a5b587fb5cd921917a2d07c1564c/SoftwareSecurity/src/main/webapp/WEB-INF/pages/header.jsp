<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<noscript>
	<style>
.row, .side-nav.fixed, form {
	display: none;
}
</style>
	<b>For full functionality of this page it is necessary to enable
		JavaScript.</b> Here are the <a href="http://www.enable-javascript.com"
		target="_blank"> instructions how to enable JavaScript in your web
		browser</a>
</noscript>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Pitchfork State Bank</title>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/materialize.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/materialize.js"></script>


<style>
.navbar-collapse {
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
}
</style>
</head>

<body>

	<!--- Page Header -->


	<div class="navbar-header">
		<a href="<c:url value="/login" />" class="navbar-brand">Pitchfork State Bank</a>
	</div>


	<nav id="navbar" class="navbar-collapse collapse">


		<ul class="nav navbar-nav navbar-right">
        <li><a href="/cse/aboutus/" >About Us</a></li>
			<li><a href="/cse/signup/" >SignUp</a></li>
			<li><a href="/cse/forgotpassword" >ForgotPassword</a></li>
		</ul>
	</nav>
</body>
</html>
