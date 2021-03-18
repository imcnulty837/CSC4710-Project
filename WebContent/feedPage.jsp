<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head><title>Account Page</title></head>
<body>
	<div align="center">
		<form action="community" method="post"><input type="submit" value="Community Page"/></form>
		<h1>Welcome ${username}</h1>
		<c:forEach items="${listImages}" var="Image">
			<h4>posted by ${Image.email} at <time>${Image.timestamp}</time></h4>
			<img src=${Image.url} alt="image">
			<p>${Image.description}</p>
		</c:forEach>
	</div>
</body>
</html>