<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pitchfork State Bank </title>
<script>
	// Disable back functionality
	// Ref: http://stackoverflow.com/questions/19926641/how-to-disable-back-button-in-browser-using-javascript
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
  // Disable right click
  // Ref : http://stackoverflow.com/questions/14657866/disable-right-click-in-website
  function clickIE4(){ 
	  if (event.button==2){ return false; } } 
	function clickNS4(e){ 
	  if (document.layers||document.getElementById&&!document.all){ 
	  if (e.which==2||e.which==3){return false; } } } 
	  if (document.layers){ document.captureEvents(Event.MOUSEDOWN); document.onmousedown=clickNS4; } 
	  else if (document.all&&!document.getElementById){ document.onmousedown=clickIE4; } document.oncontextmenu=new Function("return false")
</script>
</head>

<body>

</body>
</html>