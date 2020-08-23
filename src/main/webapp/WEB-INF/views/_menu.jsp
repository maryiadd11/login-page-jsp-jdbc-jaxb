<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div style="padding: 5px;">
    <%
        String contextPath = request.getServletContext().getContextPath();
    %>

    <a href="<%=contextPath%>/">Home</a>
    |
    <a href="<%=contextPath%>/productList">Product List</a>
    |
    <a href="<%=contextPath%>/userInfo">My Account Info</a>
    |
    <a href="<%=contextPath%>/login">Login</a>

</div>