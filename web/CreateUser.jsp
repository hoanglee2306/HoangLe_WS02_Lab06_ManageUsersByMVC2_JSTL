<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create User</title>
</head>
<body>
    <h1>Create User</h1>
    <form action="UserController" method="post">
        <input type="hidden" name="action" value="Create"/>
        UserName <input type="text" name="txtUserName" value="${param.txtUserName}" />
        <font color="red">${requestScope.errorDetails.userNameError}</font>
        <font color="red">${requestScope.errorDetails.duplicateUserName}</font><br/>

        Password <input type="password" name="txtPassword" />
        <font color="red">${requestScope.errorDetails.passwordError}</font><br/>

        LastName <input type="text" name="txtLastName" value="${param.txtLastName}" />
        <font color="red">${requestScope.errorDetails.lastNameError}</font><br/>

        isAdmin <input type="checkbox" name="chkIsAdmin" value="ON" ${param.chkIsAdmin != null ? 'checked' : ''}/><br/>

        <input type="submit" value="Create" />
    </form>
    <div style="color:green">${requestScope.message}</div>
    <a href="Login.jsp">Back</a>
</body>
</html>
