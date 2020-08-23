<%@ page import="model.UserAccount" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<%
    UserAccount loginedUser = (UserAccount) request.getSession().getAttribute("loginedUser");
    String userName = "";
    String userGender = "";
    if(loginedUser != null) {
        userName = loginedUser.getUserName();
        userGender = loginedUser.getGender();
    }
%>
<h3>Hello: <%=userName%></h3>

User Name: <b><%=userName%></b>
<br />
Gender: <%=userGender%> <br />

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>