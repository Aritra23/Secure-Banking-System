<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<html lang="en">

<jsp:include page="jsDisble.jsp" />
<head>
<title>Request money</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>${title}</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/materialize.min.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
	<script src="https://www.google.com/recaptcha/api.js" async defer></script>
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

			<h3 class="header">Request Money</h3>
		
		</div>
	</div>
	<div class="row">
		<div class="col-md-4 col-md-offset-4 ">
			<form:form class="col s12" commandName="FileUpload"
				action="/cse/MRequestMoney" method="post" modelAttribute="FileUpload" enctype="multipart/form-data">

				<div class="row">
					<div class="input-field col s12">
						<form:input path="sourceAccount" id="sourceAccount" type="text"
							class="validate" />
						<label style="transform: translateY(-140%);" for="account">Request
							from (account number)</label>
					</div>
				</div>


				<div class="row" id="errorrow1" style="display: none">
					<div class="col s5">

						<label id="erroracc" for="icon_prefix"
							style="display: none; color: red">Incorrect Account No.</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<form:input path="amount" id="amount" type="text" class="validate" />
						<label style="transform: translateY(-140%);" for="amount">Amount</label>
					</div>
				</div>
				
				<div class="row" id="errorrow2" style="display: none">
					<div class="col s5">

						<label id="erroramt" for="icon_prefix"
							style="display: none; color: red">Incorrect Amount</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<form:input path="comment" id="comment" type="text" class="validate" />
						<label style="transform: translateY(-140%);" for="comment">Comment</label>
					</div>
				</div>
				
				<div class="row" id="errorrow3" style="display: none">
					<div class="col s5">
						<label id="errorfrm" for="icon_prefix"
							style="display: none; color: red">Incorrect Comment</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						Upload Certificate: <form:input type="file" path="file" name="file"/>
					</div>
				</div>
				<div class="row">
					<div class="col s4 offset-s3 ">
						<div class="g-recaptcha " data-sitekey="6Ldllw8TAAAAADfxeC8RRVVK3ymSfMoUl5LYa2Kd"></div>
					</div>
				</div>
				
				<div class="row">
					<div class="col s4 offset-s5 ">
						<button class="btn waves-effect waves-light" id="submit"
							type="submit" name="action">
							Request <i class="material-icons right">Request</i>
						</button>
					</div>
				</div>

			</form:form>
			<div class="col s6 offset-s3 ">
			<h5 style="color:blue">${message1}</h5>
		</div></div>
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
	<script
		src="${pageContext.request.contextPath}/resources/js/materialize.js"></script>
		
		
			<script>
		$(document).ready(function() {
			
			

			var amtFunction = function() {
				var input = $('#amount');
				var value = input.val();
				var length = value.length;
				var nameRegex = new RegExp(/[^0-9]/);

				if ($.trim(value).length == 0 || nameRegex.test(value)) {
					$('#erroramt').get(0).style.display = "inline";
					$('#errorrow2').get(0).style.display = "inline";
					return false;
				} else {
					$('#erroramt').get(0).style.display = "none";

				}
			};

			$('#amount').blur(amtFunction);
			$('#submit').click(amtFunction);

			var accountFunction = function() {
				var input = $('#sourceAccount');
				var value = input.val();
				var length = value.length;
				var nameRegex = new RegExp(/[^0-9]/);

				if ( length!=10 || $.trim(value).length == 0 || nameRegex.test(value)) {
					$('#erroracc').get(0).style.display = "inline";
					$('#errorrow1').get(0).style.display = "inline";
					return false;
				} else {
					$('#erroracc').get(0).style.display = "none";

				}
			};

			$('#sourceAccount').blur(accountFunction);
			$('#submit').click(accountFunction);
			
			
			
			var commentFunction = function() {
				var input = $('#comment');
				var value = input.val();
				var length = value.length;
				var nameRegex = new Regex(/[^a-zA-Z0-9,.]/);
				
				if( $.trim(value).length == 0 || nameRegex.test(value)){
					$('#errorfrm').get(0).style.display= "inline";
					$('#errorrow3').get(0).style.display= "inline";
					return false;
				}
				else{
					$('#errorfrm').get(0).style.display= "none";	
					
				}
				};
			  $('#comment').blur(commentFunction);
			  $('#submit').click(commentFunction);
			

		});
	</script>
</body>
</html>