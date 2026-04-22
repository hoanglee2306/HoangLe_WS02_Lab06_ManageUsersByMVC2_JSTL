<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search User</title>
</head>
<body>
    <c:set var="user" value="${sessionScope.userLoggedIn}" />
    <c:if test="${user == null}">
        <c:redirect url="Login.jsp"/>
    </c:if>

    <h2>Welcome to <font color="red">${user.lastName}</font></h2>
    <form action="LogoutController" method="post">
        <input type="submit" value="Logout"/>
    </form>

    <h1>Search user by last name</h1>
    <form action="UserController" method="get">
        <input type="hidden" name="action" value="Search"/>
        Enter search value <input type="text" name="txtSearchValue" value="${param.txtSearchValue}"/>
        <input type="submit" value="Search"/>
    </form>

    <div style="color:green">${requestScope.message}</div>

    <c:if test="${not empty requestScope.searchResult}">
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>UserName</th>
                    <th>LastName</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${requestScope.searchResult}" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${item.userName}</td>
                        <td>${item.lastName}</td>
                        <td>
                            <input type="checkbox" disabled="disabled" ${item.isAdmin ? 'checked' : ''}/>
                        </td>
                        <td>
                            <form action="UserController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="Delete"/>
                                <input type="hidden" name="UserName" value="${item.userName}"/>
                                <input type="hidden" name="SearchValue" value="${param.txtSearchValue}"/>
                                <input type="submit" value="Delete"/>
                            </form>
                            <a href="UserController?action=Details&UserName=${item.userName}">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        Number of users: ${fn:length(requestScope.searchResult)}
    </c:if>

    <c:if test="${empty requestScope.searchResult and not empty param.txtSearchValue}">
        No user is matched.
    </c:if>
</body>
</html>
