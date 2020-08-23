<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<%
    String contextPath = request.getServletContext().getContextPath();
    String errorString = (String) request.getAttribute("errorString");
    errorString = errorString == null ? "" : errorString;
    Product product = (Product) request.getAttribute("product");
    String code = "";
    String name = "";
    Float price = 0f;
    if (product != null) {
        code = product.getCode();
        name = product.getName();
        price = product.getPrice();
    }
%>
<h3>Edit Product</h3>
<p style="color: red;"><%=errorString%></p>
    <form method="POST" action="<%=contextPath%>/editProduct">
        <input type="hidden" name="code" value="<%=code%>"/>
        <table border="0">
            <tr>
                <td>Code</td>
                <td style="color:red;"><%=code%></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" value="<%=name%>"/></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input type="text" name="price" value="<%=price%>"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit"/>
                    <a href="<%=contextPath%>/productList">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>