<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
<c:if test="${sessionScope.user.role.ordinal() == 0}"> <jsp:include page="../common/header_admin.jsp"></jsp:include> </c:if>
<c:if test="${sessionScope.user.role.ordinal() == 1}"> <jsp:include page="../common/header_admin.jsp"></jsp:include> </c:if>
<div >
    <%@include file="/WEB-INF/locale.jsp" %>
</div>
<div class="row">
    <div class="container">
        <h3 class="text-center"><fmt:message key="page.usersList.list.of.user"/></h3>

        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/admin/newUser" class="btn btn-success"><fmt:message key="page.usersList.add.new.user"/></a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th><fmt:message key="page.registration.first.name"/></th>
                <th><fmt:message key="page.registration.last.name"/></th>
                <th><fmt:message key="page.login.login"/></th>
                <th><fmt:message key="page.login.password"/></th>
                <th><fmt:message key="page.usersList.role"/></th>
                <th><fmt:message key="page.usersList.action"/></th>
            </tr>
            </thead>
            <tbody>
            <!--   for (Todo todo: todos) {  -->
            <c:forEach var="user" items="${listOfUsers}">
                <tr>
                    <td>
                        <c:out value="${user.userId}" />
                    </td>
                    <td>
                        <c:out value="${user.firstName}" />
                    </td>
                    <td>
                        <c:out value="${user.lastName}" />
                    </td>
                    <td>
                        <c:out value="${user.login}" />
                    </td>
                    <td>
                        <c:out value="${user.password}" />
                    </td>
                    <td>
                        <c:out value="${user.role}" />
                    </td>
                    <td><a href="<%= request.getContextPath() %>/admin/editUser?id=<c:out value='${user.userId}' />"><fmt:message key="page.usersList.edit"/></a> &nbsp;&nbsp;&nbsp;&nbsp; <a href="<%= request.getContextPath() %>/admin/deleteUser/submit?id=<c:out value='${user.userId}' />"><fmt:message key="page.usersList.delete"/></a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>

        </table>
    </div>
</div>
</body>

</html>
