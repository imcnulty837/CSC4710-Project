<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<title>Account Page</title>
</head>
<body>
	<div align="center">
		<form action="community" method="post"><input type="submit" value="Community Page"/></form>
		<form action="logout" method="post"><input type="submit" value="logout" /></form>
		<h1>Welcome ${username}</h1>
		
		<button onclick="postForm()">post an image</button>
		<div id="postImageForm">
		<br>
			<form id="postimage" method="post" action="postForm">
				<label for="urlTxt">image URL: </label>
				<input type="text" id="urlTxt" name="url" placeholder="URL">
				<label for="descTxt">Description: </label>
				<input type="text" id="descTxt" name="description" placeholder="description">
				<label for="tagTxt">Tags: </label>
				<input type="text" id="tagTxt" name="tags" placeholder="tags">
				<input type="submit" value="Post Image">
			</form>
		</div>
		<script>
		function postForm(){
			var x = document.getElementById("postImageForm");
			if(x.style.display === "none"){
				x.style.display = "block";
			}
			else{
				x.style.display = "none";
			}
		}
		</script>
		
		<c:forEach items="${listImages}" var="Image">
			<h4>posted by ${Image.email} at <time>${Image.timestamp}</time></h4>
			<img src=${Image.url} alt="image">
			<p>${Image.description}</p>
			<c:if test="${Image.email == username }">
				<form action="delete?url=${Image.url}" method="post"><input type="submit" value="delete"/></form>
				<p> ${Image.email} --- ${username} </p>
			</c:if>
			
		</c:forEach>
	</div>
</body>
</html>