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
        <form action="root" method="post"><input type="submit" value="Return to Root View!"/></form>
        <table>
            <tr>
                <th> Email </th>
            </tr>
            <c:forEach items="${userList}" var="Account" varStatus="i">
                <tr>
                    <td>${Account.email}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>