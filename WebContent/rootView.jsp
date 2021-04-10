<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head><title>Root Page</title></head>
<body>
	<div align="center">
		<form action="initialize">
			<input type="submit" value="Initialize Database"/>
		</form>
		<a href="login.jsp" target="_self">Logout</a><br><br>
		
		<form action="coolImages" method="post"><input type="submit" value="Cool Images" /></form>
		<form action="recentImages" method="post"><input type="submit" value="recently posted" /></form>
		<form action="viralImages" method="post"><input type="submit" value="viral Images" /></form>
		<form action="poorImages" method="post"><input type="submit" value="poor Images" /></form>
		
		<c:forEach items="${listImages}" var="Image">
			<h4>posted by ${Image.email} at <time>${Image.timestamp}</time></h4>
			<img src=${Image.url} alt="image">
			<p>Likes: ${Image.likeCount}, Description: ${Image.description}</p>
		</c:forEach>
	</div>
</body>
</html>