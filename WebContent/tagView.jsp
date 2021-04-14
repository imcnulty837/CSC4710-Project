<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<title>Top Tags!</title>
</head>
<body>
    <div align="center">
        <form action="root" method="post"><input type="submit" value="Return to Root View!"/></form>
        <table>
            <tr>
                <th> Tag </th>
            </tr>
            <c:forEach items="${tagList}" var="Tags" varStatus="i">
                <tr>
                    <td>${Tags.tag}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>