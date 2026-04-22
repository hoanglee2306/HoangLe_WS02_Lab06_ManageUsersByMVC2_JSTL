<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Details</title>
</head>
<body>
    <c:set var="user" value="${requestScope.userDetails}" />
    <c:if test="${empty user}">
        <c:set var="user" value="${sessionScope.userLoggedIn}" />
    </c:if>

    <form action="LogoutController" method="post">
        <input type="submit" value="Logout"/>
    </form>

    <h1>User Details</h1>
    <div style="color:green">${requestScope.message}</div>
    <form action="UserController" method="post">
        <input type="hidden" name="action" value="Update"/>

        UserName <input type="text" name="txtUserName" value="${user.userName}" readonly="readonly"/>
        <font color="red">${requestScope.errorDetails.userNameError}</font><br/>

        Password <input type="password" name="txtPassword" value="${user.password}"/>
        <font color="red">${requestScope.errorDetails.passwordError}</font><br/>

        LastName <input type="text" name="txtLastName" value="${user.lastName}"/>
        <font color="red">${requestScope.errorDetails.lastNameError}</font><br/>

        isAdmin <input type="checkbox" name="chkIsAdmin" value="ON" ${user.isAdmin ? 'checked' : ''}/><br/>

        <input type="submit" value="Update"/>
    </form>

    <a href="UserController?action=Search">Back</a>
</body>
</html>
