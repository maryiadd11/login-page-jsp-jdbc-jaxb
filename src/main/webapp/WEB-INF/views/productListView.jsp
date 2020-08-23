<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<%
    String errorString = (String) request.getAttribute("errorString");
    errorString = errorString == null ? "" : errorString;
    String productListHtml = (String) request.getAttribute("productListHtml");
%>
<h3>Product List</h3>

<p style="color: red;"><%=errorString%></p>

<table border="1" cellpadding="5" cellspacing="1">
    <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Price</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <%=productListHtml%>
</table>
<a href="createProduct">Create Product</a>
<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>