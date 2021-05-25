<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
<div >
    <%@include file="/WEB-INF/locale.jsp" %>
</div>
<li><a href="<%= request.getContextPath() %>/login" class="nav-link"><fmt:message key="page.login.login"/></a></li>
<div class="container">
    <div class="col-md-6 col-md-offset-3">
        <c:if test="${sessionScope.user == null}">
        <form action="<%=request.getContextPath()%>/registration/submit" method="post">
            <a class="navbar-brand"><fmt:message key="page.registration.registration.title"/> </a>
            </c:if>
            <c:if test="${sessionScope.user != null}">
            <form action="<%=request.getContextPath()%>/admin/newUser/submit" method="post">
                <a class="navbar-brand"><fmt:message key="page.usersList.add.new.user"/> </a>
                </c:if>
            <div class="form-group">
                <label for="uname"><fmt:message key="page.registration.first.name"/></label> <input type="text" class="form-control" id="uname" placeholder=<fmt:message key="page.registration.first.name"/> name="firstName" required>
            </div>

            <div class="form-group">
                <label for="uname"><fmt:message key="page.registration.last.name"/></label> <input type="text" class="form-control" id="fname" placeholder=<fmt:message key="page.registration.last.name"/> name="lastName" required>
            </div>

            <div class="form-group">
                <label for="uname"><fmt:message key="page.login.login"/></label> <input type="text" class="form-control" id="username" placeholder=<fmt:message key="page.login.login"/> name="login" required>
            </div>

            <div class="form-group">
                <label for="uname"><fmt:message key="page.login.password"/></label> <input type="password" class="form-control" id="password" placeholder=<fmt:message key="page.login.password"/> name="password" required>
            </div>
                <c:if test="${sessionScope.user.role.ordinal() == 1}">
                    <th><fmt:message key="page.usersList.role"/>:</th>
                    <p><select size="1" name="role">
                        <option disabled><c:out value='${updateUser.role}'/></option>
                        <option value="ADMIN_ROLE">Admin</option>
                        <option selected value="USER_ROLE">User</option>
                    </select></p>
                </c:if>

            <button type="submit" class="btn btn-primary"><fmt:message key="page.usersList.add.new.user"/></button>

        </form>
    </div>
</div>
</body>

</html>