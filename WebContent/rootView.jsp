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
		
		<h1>Image Views!</h1>
		<form action="coolImages" method="post"><input type="submit" value="Cool Images" /></form>
		<form action="recentImages" method="post"><input type="submit" value="Recently Posted" /></form>
		<form action="viralImages" method="post"><input type="submit" value="Viral Images" /></form>
		<form action="poorImages" method="post"><input type="submit" value="Poor Images" /></form>
		
		<h1>User Views!</h1>
		<form action="topUsers" method="post"><input type="submit" value="Top Users" /></form>
		<form action="popularUsers" method="post"><input type="submit" value="Popular Users" /></form>
		<form action="positiveUsers" method="post"><input type="submit" value="Positive Users" /></form>
		<form action="inactiveUsers" method="post"><input type="submit" value="Inactive Users" /></form>
		<form action="commonUsers" method="post"><input type="submit" value="Common Users"></form>
		
		<form action="topTags" method="post"><input type="submit" value="Top Tags!"></form>
		
		<c:forEach items="${listImages}" var="Image">
			<h4>posted by ${Image.email} at <time>${Image.timestamp}</time></h4>
			<img src=${Image.url} alt="image">
			<p>Likes: ${Image.likeCount}, Description: ${Image.description}</p>
		</c:forEach>
	</div>
</body>
</html>