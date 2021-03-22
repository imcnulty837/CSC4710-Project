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
			<p>Likes: ${Image.likeCount}, Description: ${Image.description}</p>
			<c:choose>
				<c:when test = "${Image.likeSwitch}">
					<form action="dislike?url=${Image.url}&id=${Image.imageId}" method="post"><input type="submit" value="Unlike"/></form>
					
				</c:when>
				<c:otherwise>
					<form action="like?url=${Image.url}&id=${Image.imageId}" method="post"><input type="submit" value="Like"/></form>
				</c:otherwise>
			</c:choose>
			<c:if test="${Image.email == username }">
				<form action="delete?url=${Image.url}" method="post"><input type="submit" value="Delete Image"/></form>
				<form action="updateImage?url=${Image.url}" method="post"><input type="text" name="description" size="45"><input type="submit" value="Update Image"/></form> 
			</c:if>
			
		</c:forEach>
	</div>
</body>
</html>