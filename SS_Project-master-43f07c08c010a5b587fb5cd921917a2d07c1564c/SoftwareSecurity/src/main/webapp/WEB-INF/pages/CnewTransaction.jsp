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
	
	#showReqLink{
		display:none;
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
        <li><a href="/cse/aboutus/" >About Us</a></li>
		</ul>
	</nav>
	<div class="container-fluid"></div>

	<jsp:include page="Crightsidebar.jsp" />
	
	<div class="row"></div>
	<div class="row"></div>
	<div class="row"></div>
	
	

		<div class="row" style="margin-top:150px;">
  <div class="col-sm-6 col-md-5 col-md-offset-3">
 
        <h3><a href="<c:url value="/CdebitOrCreditTransaction" />">Debit/Credit
					Transaction</a></h3>
  </div>

  <div class="col-sm-6 col-md-4 col-md-offset-0">
 
      
      <div class="caption">
        <h3><a href="<c:url value="/CfundTransfer" />">Fund Transfer</a></h3>
    
             </div>

  </div>
  
    <div class="col-sm-6 col-md-4 col-md-offset-6" id="${showReqLink}" >
 
      
      <div class="caption">
        <h3><a  href="<c:url value="/MRequestMoney" />">Request Money</a></h3>
    
             </div>

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
