
<%@page session="true"%>
<html lang="en">
<jsp:include page="jsDisble.jsp" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Arizona State Bank</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/materialize.min.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- REFERENCE: https://developers.google.com/virtual-keyboard/v1/getting_started?hl=en
<script type="text/javascript" src="https://www.google.com/jsapi"></script>  -->
<script
		src="${pageContext.request.contextPath}/resources/js/jspi.js"></script>

 <script type="text/javascript">
 
 
 
 
      // Load the Google Onscreen Keyboard API
      google.load("elements", "1", {
          packages: "keyboard"
      });

      function vk() {
    	 
        var kbd = new google.elements.keyboard.Keyboard(
          [google.elements.keyboard.LayoutCode.ENGLISH],
          ['otp_input']);
      }

      google.setOnLoadCallback(vk);
      
      
      document.getElementById('otp_input').onclick =function(){
    	  kbd.setVisible(true);
      }
    </script>


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
		<a href="/cse/signup/" class="navbar-brand">Pitchfork State Bank</a>
	</div>





	<div class="container-fluid"></div>
 

	<div id="modalPrep" class="modal" style="">

		<!-- Modal Structure -->

		<div class="modal-content">

			<form modelAttribute="cse" id="otp_form"
				action="/cse/validateSignUpOTP" method='POST'>
				<p>
					OTP Sent To:<b> ${email}</b>
				</p>
				<input type="hidden" name="email" value="${email}">
				<table>
					<tr>
						<td><h5>Enter OTP:</h5></td>
						<td><input id='otp_input' type='text' style="width: 80%"
							name='pin' value=''></td>
					</tr>
				</table>
				<div class="row" id="errorrow1" style="display:  ${display}">
					<div class="col s5">
						<label id="errorotp" for="icon_prefix"
							style="display: none; color: red">Incorrect OTP</label>
					</div>
				</div>
				<input class="btn modal-trigger"
					style="float: right; margin-right: 20px;" id="submit_btn"
					name="submit" type="submit" value="submit" />
			</form>
			<form modelAttribute="cse" id="otp_form" action="/cse/signup">

				<button class="btn modal-trigger"
					style="float: right; margin-right: 20px;">
					<a style="color: #fff;" href="/cse/signup/" /> Cancel</a>
				</button>
			</form>
			<form modelAttribute="cse" action="/cse/regenerateSignUpOTP"
				method='POST'>

				<input type="hidden" name="email" value="${email}">
				<button class=""
					style="float: left; background: none; border: none; padding: 0; font: inherit; cursor: pointer;">Regenerate
					OTP?</button>
			</form>
		</div>
	</div>

	<div class="lean-overlay" id="materialize-lean-overlay-3"
		style="z-index: 1002; display: block; opacity: 0.5;">
		<a href="#" style="color: #fff;">Cancel</a>
	</div>

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
	
	//$("#otp_input").keydown(false);
	
		$(document)
				.ready(
						function() {

							$('#submit_btn').click(function(event) {
								var validation = 1;
								validation &= otpFunction();
								if (validation == 1) {
									$("#otp-form").submit();
								} else {
									event.preventDefault();
									return false;
								}

							});

							//otp
							var otpFunction = function() {
								var input = $('#otp_input');
								/*$('#otp_input').keyboard({
									layout : 'num',
									restrictInput : true,
									preventPaste : true,
									autoAccept : true
								}).addTyping(); */
								
								
								
								var value = input.val();
								var length = value.length;
								var nameRegex = new RegExp(/[^0-9]/);
								if (length != 6 || $.trim(value).length == 0
										|| nameRegex.test(value)) { //(!value.match(/[a-zA-Z]/))
									$('#errorotp').get(0).style.display = "inline";
									$('#errorrow1').get(0).style.display = "inline";
									//	$('#modal1').closeModal();
									return false;
								} else {
									$('#errorotp').get(0).style.display = "none";
									if ($('#errorotp').get(0).style.display != "inline") {
										$('#errorrow1').get(0).style.display = "none"
									}
									return true;
								}
							};

							$('#submit_btn').click(otpFunction);

						});
	</script>

</body>
</html>
