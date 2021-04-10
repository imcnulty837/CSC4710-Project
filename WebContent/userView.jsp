<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<title>$(view)</title>
</head>
<body>
    <div align="center">
        <form action="feed" method="post"><input type="submit" value="return to feed"/></form>
        <form action="logout" method="post"><input type="submit" value="logout"></form>
        <table>
            <tr>
                <th>Name </th> <th> Email </th>
            </tr>
            <c:forEach items="${userList}" var="Account" varStatus="i">
                <tr>
                    <td>${Account.firstName} ${Account.lastName}</td>
                    <td>${Account.email}</td>
                </tr>
            </c:forEach>
        </table>
        </div>
</body>