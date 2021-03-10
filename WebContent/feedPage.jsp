<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head><title>Account Page</title></head>
<body>
	<div align="center">
		<h1>Welcome ${username}</h1>
		<c:forEach items="${listImages}" var="Image">	
			<img src=${Image.url} alt="image" width="500" height="300">
		</c:forEach>
	</div>
</body>
</html>