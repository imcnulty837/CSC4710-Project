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
            <c:choose>
            	<c:when test="${viewSwitch == true}">
            		<c:forEach items="${commonList}" var="email" varStatus="i">
		                <tr>
		                    <td>${email}</td>
		                </tr>
		            </c:forEach>
            	</c:when>
            	<c:otherwise>
            		<c:forEach items="${userList}" var="Account" varStatus="i">
		                <tr>
		                    <td>${Account.email}</td>
		                </tr>
		            </c:forEach>
            	</c:otherwise>
            </c:choose>
            
        </table>
    </div>
</body>