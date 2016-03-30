<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>

<html lang="en">
<jsp:include page="jsDisble.jsp" />
<head>
<title></title>
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

			<h3 class="header">Fund Transaction</h3>

		</div>
	</div>


	<div class="row">
		<div class="col-md-4 col-md-offset-4 ">

			<form:form class="col s12" commandName="FundTransaction"
				action="/cse/MfundTransfer" method="post">

				<div class="row">
					<div class="form-group">
						<label> From Account:</label>
						<form:select path="sourceAccount" class="browser-default"
							id="accountType">
							<form:option value="1">Checking</form:option>
							<form:option value="2">Savings</form:option>
						</form:select>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<label> From Account:</label>
						<form:input path="destinationAccount" id="destinationAccount"
							type="text" />
					</div>
				</div>


				<div class="row" id="errorrow1" style="display: none">
					<div class="col s5">

						<label id="erroracc" for="icon_prefix"
							style="display: none; color: red">Incorrect Entry</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<label> Amount:</label>
						<form:input path="amount" id="amount" type="text" />
					</div>
				</div>

				<div class="row" id="errorrow2" style="display: none">
					<div class="col s5">

						<label id="erroramt" for="icon_prefix"
							style="display: none; color: red">Incorrect Entry</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<label> Comments:</label>
						<form:input path="comment" id="comment" type="text" />
					</div>
				</div>

				<div class="row">
					<div class="col s4 offset-s4 ">
						<button class="btn waves-effect waves-light" id="submit"
							type="submit" name="action">
							Submit <i class="material-icons right">send</i>
						</button>
					</div>
				</div>
			</form:form>
			<p>${message1}</p>
		</div>
	</div>

	<jsp:include page="footer.jsp" />


	<!-- JavaScript Code -->
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
				var input = $('#destinationAccount');
				var value = input.val();
				var length = value.length;
				var nameRegex = new RegExp(/[^0-9]/);

				if ($.trim(value).length == 0 || nameRegex.test(value)) {
					$('#erroracc').get(0).style.display = "inline";
					$('#errorrow1').get(0).style.display = "inline";
					return false;
				} else {
					$('#erroracc').get(0).style.display = "none";

				}
			};

			$('#destinationAccount').blur(accountFunction);
			$('#submit').click(accountFunction);

		});
	</script>
</body>
</html>