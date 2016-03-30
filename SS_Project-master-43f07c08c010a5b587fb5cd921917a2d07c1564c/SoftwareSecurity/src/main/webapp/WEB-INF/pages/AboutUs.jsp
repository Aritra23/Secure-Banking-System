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
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>

	<!--- Page Header -->
	<body>
	<div class="navbar-header">
		<a class="navbar-brand" href="<c:url value="/"/> ">Pitchfork
			State Bank</a>
	</div>
	<div>
	</div>
	
      <div class="container">
 	  <h4 class="card-panel teal lighten-2">SS GROUP 5</h4>
	  <h4 class="card-panel teal lighten-2">Project Members</h4>
	  <h5 class="blue-text text-darken-2">Neeraj ChinthiReddy</h5>
	  <h5 class="blue-text text-darken-2">Kannan Haridas</h5>	  
      <h5 class="blue-text text-darken-2">Aritra Lahiri</h5>
      <h5 class="blue-text text-darken-2">Shivam Prakash</h5>
      <h5 class="blue-text text-darken-2">Keyul Shah</h5>
      <h5 class="blue-text text-darken-2">Srilalitha Suryanarayanan</h5>
      <h5 class="blue-text text-darken-2">Kruthika Tanikella</h5>
      <h5 class="blue-text text-darken-2">Roja Meghana Thatishetti</h5>
      <h5 class="blue-text text-darken-2">Ashwin Vasani</h5>
                 
           </div>
	<jsp:include page="footer.jsp" />

</body>