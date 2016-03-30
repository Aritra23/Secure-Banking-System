<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script
		src="${pageContext.request.contextPath}/resources/js/sha256.js"></script>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script>
	window.onload = function() {

		document.getElementById('passwordform').onsubmit = function() {
			var password = document.getElementById("password").value;
			var token = document.getElementById("token").value;
			var cryptohash = CryptoJS.SHA256(password + token);

			document.getElementById("password").value = cryptohash;
		};
	};

	function CheckPassword() {
		var password = document.getElementById("password").value;
		var cryptohash = CryptoJS.SHA256(password);

		document.getElementById("password").value = cryptohash;
		alert(cryptohash);
	}
</script>
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
	<div class="row">
		<div class="col-md-3 col-md-offset-5 col-xs-offset-1 ">
			<h3 class="header">LOGIN</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-4">

			<c:if test="${show == 'username'}">
				<form class="col s12" name='f' action="/cse/getusertoken"
					method='POST'>
					<div class="row">
						<div class="input-field col s6">
							<input type="text" name='userName' value='' id="username" class="validate">
							<label style="transform: translateY(-140%);" for="username">User
								Name</label>
						</div>
					</div>
				<c:if test="${not empty errorMessage}"> 
						<div class="row">
							<div class="col s6">
								<label id="dummyError" style=" color: red">"${errorMessage}"</label>
							</div>
						</div>
					</c:if>  
					<div class="row">
						<div class="col s4 offset-s2 ">
							<button class="btn waves-effect waves-light" type="submit"
								name="next" value="next" id="submit">
								Next <i class="material-icons right">send</i>
							</button>
						</div>
					</div>
					<div class="row" id="errorrow2" style="display: none">
					<div class="col s5">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label
							id="errorUserName" for="icon_prefix"
							style="display: none; color: red">Incorrect User Name</label>
					</div>
					
				</div>
					
				</form>
			</c:if>
			<c:if test="${show == 'password'}">
				<form class="col s12" name='f' id="passwordform"
					action="j_spring_security_check" method='POST'>
					<div class="row">
						<div class="input-field col s6">
							<input type="password" class="validate" name='password'
								id="password"> <label
								style="transform: translateY(-140%);" for="password">Password</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s6">
							<input type="hidden" name='username' id="username" 
								value="${username}" /> <input type="hidden" name='token'
								id="token" value="${token}" />
						</div>
					</div>
					<div class="row">
						<div class="col s4 offset-s2 ">
							<button class="btn waves-effect waves-light" type="submit"
								name="submit" value="submit" onSubmit="checkPassword()">
								Submit <i class="material-icons right">send</i>
							</button>
						</div>
					</div>
				</form>
			</c:if>
		</div>
	</div>
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
		
		
		<!-- JavaScript Code -->

<script> 
		$(document).ready(function() {

			var userNameFunction = function() {
				var input=$('#username');
				var value =input.val();
				var length = value.length;
				var nameRegex = new RegExp(/[^a-zA-Z0-9]/);
				
				if(length <1 || $.trim(value).length == 0 || nameRegex.test(value)){
					$('#errorUserName').get(0).style.display= "inline";
					$('#errorrow2').get(0).style.display= "inline";
				//	$('#modal1').closeModal();
					return false;
				}
				else{
					$('#errorUserName').get(0).style.display= "none";	
					if($('#errorssn').get(0).style.display != "inline"){
						$('#errorrow2').get(0).style.display= "none"
					}
				}};
			
				$('#submit').click(userNameFunction);
		});
		</script>
</body>
</html>
