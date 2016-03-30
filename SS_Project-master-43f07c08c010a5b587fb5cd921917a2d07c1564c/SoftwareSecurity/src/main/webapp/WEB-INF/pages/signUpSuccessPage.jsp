<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<html>
<jsp:include page="jsDisble.jsp" />
<head>
<title>${title}</title>
</head>
<body>
	<h1>${message}</h1>
	
	<% final Integer id = (Integer) request.getAttribute("Transid");
		if(id>0){
	%>
	<h2>Thank you for signing up. We received you request. We will send you an</h2>
	<h2>email at ${email}.Please check your spam folder.your transaction id is <%= id %></h2>
	<%} %>
	<h2><a href="/cse/">Back to Login</a></h2>
	
</body>
</html>