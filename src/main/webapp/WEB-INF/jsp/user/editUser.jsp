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
<c:if test="${sessionScope.user.role.ordinal() == 0}"> <jsp:include page="../common/header_user.jsp"></jsp:include> </c:if>
<c:if test="${sessionScope.user.role.ordinal() == 1}"> <jsp:include page="../common/header_admin.jsp"></jsp:include> </c:if>
<div >
    <%@include file="/WEB-INF/locale.jsp" %>
</div>
<li><a href="<%= request.getContextPath() %>/login" class="nav-link"><fmt:message key="page.login.login"/></a></li>
<div class="container">
    <div class="col-md-6 col-md-offset-3">
        <c:if test="${sessionScope.user.role.ordinal() == 0}">
        <form action="<%=request.getContextPath()%>/user/editUser/submit" method="post">
            <a class="navbar-brand"><fmt:message key="page.usersList.edit"/> </a>
            </c:if>
            <c:if test="${sessionScope.user.role.ordinal() == 1}">
            <form action="<%=request.getContextPath()%>/admin/editUser/submit" method="post">
                <a class="navbar-brand"><fmt:message key="page.usersList.add.new.user"/> </a>
                </c:if>
                <input type="hidden" name="id" value="<c:out value='${updateUser.userId}' />" />
                <div class="form-group">
                    <label for="uname"><fmt:message key="page.registration.first.name"/></label>
                    <input type="text" class="form-control" id="uname" value="<c:out value='${updateUser.firstName}' />" name="firstName" required>
                </div>

                <div class="form-group">
                    <label for="uname"><fmt:message key="page.registration.last.name"/></label>
                    <input type="text" class="form-control" id="fname" value="<c:out value='${updateUser.lastName}' />" name="lastName" required>
                </div>

                <div class="form-group">
                    <label for="uname"><fmt:message key="page.login.login"/></label>
                    <input type="text" class="form-control" id="username" value="<c:out value='${updateUser.login}' />" name="login" required>
                </div>

                <div class="form-group">
                    <label for="uname"><fmt:message key="page.login.password"/></label>
                    <input type="password" class="form-control" id="password" value="<c:out value='${updateUser.password}' />" name="password" required>
                </div>

                <c:if test="${sessionScope.user.role.ordinal() == 1}">
                <th><fmt:message key="page.usersList.role"/>:</th>
                <p><select size="1" name="role">
                    <option disabled><c:out value='${updateUser.role}'/></option>
                    <option value="ADMIN_ROLE">Admin</option>
                    <option selected value="USER_ROLE">User</option>
                </select></p>
                </c:if>
                <c:if test="${sessionScope.user.role.ordinal() == 0}">
                <button type="submit" class="btn btn-primary"><fmt:message key="page.usersList.edit"/></button>
                </c:if>
                <c:if test="${sessionScope.user.role.ordinal() == 1}">
                    <button type="submit" class="btn btn-primary"><fmt:message key="page.usersList.add.new.user"/></button>
                </c:if>
            </form>
    </div>
</div>
</body>

</html>