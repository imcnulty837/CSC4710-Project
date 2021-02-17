<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head><title>Registration</title></head>
<body>
	<div align="center">
		<form action="registration">
			<table border="1" cellpadding="5">
				<tr>
					<th>Username: </th>
					<td>
						<input type="text" name="username" size="45" autofocus>
					</td>
				</tr>
				<tr>
					<th>First Name: </th>
					<td>
						<input type="text" name="firstname" size="45">
					</td>
				</tr>
				<tr>
					<th>Last Name: </th>
					<td>
						<input type="text" name="lastname" size="45">
					</td>
				</tr>
				<tr>
					<th>Age: </th>
					<td>
						<input type="number" name="age" size="45">
					</td>
				</tr>
				<tr>
					<th>Password: </th>
					<td> 
						<input type="password" name="password" size="45">
					</td>
				</tr>
				<tr>
					<th>Password Confirmation: </th>
					<td colspan="2" align="center">
						<input type="text" name="confirmation" size="45">
					</td>
				</tr>
			</table>
			<table>
				<th>
					<a href="AccountView.jsp" target="_self">Register</a>
				</th>
				<th>
					<a href="login.jsp" target="_self">Return to Login Page</a>
				</th>
			</table>
		</form>
	</div>
</body>
</html>