<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>
    <h1>Login to website</h1>
    <form action="LoginController" method="post">
        Username <input type="text" name="txtUserName" value="${param.txtUserName}" /><br/>
        Password <input type="password" name="txtPassword" /><br/>
        <input type="submit" value="Login" />
        <input type="reset" value="Reset" />
    </form>
    <div style="color:red">${requestScope.message}</div>
    <a href="CreateUser.jsp">Click here to Sign up</a>
</body>
</html>
