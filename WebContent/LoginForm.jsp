<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login to Database</title>
</head>
<body>
	<div align="center">
		<form action="login" method="post">
			<table border="1" cellpadding="5">
				<tr>
					<th>Username: </th>
					<td>
						<input type="text" name="username" size="45" autofocus>
					</td>
				</tr>
				<tr>
					<th>Password: </th>
					<td> 
						<input type="password" name="password" size="45">
					</td>
				<tr>
					<td colspan="2" align="center">
						<input type="login" value="login"/>
					</td>
				</tr>
			</table>
			<table> 
				<th>
					<a href="AccountView.jsp" target="_self">Login</a>
				</th>
				<th>
					<a href="RegisterForm.jsp" target="_self">Register Here</a>
				</th>
			</table>
		</form>
	</div>
</body>
</html>