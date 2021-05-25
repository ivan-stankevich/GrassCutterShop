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
<li><a href="<%= request.getContextPath() %>/registration" class="nav-link"><fmt:message key="page.login.registration"/></a></li>
<div class="container col-md-8 col-md-offset-3" style="overflow: auto">
    <a class="navbar-brand"><fmt:message key="page.login.login.title"/> </a>
    <form action="<%=request.getContextPath()%>/login/submit" method="post">

        <c:if test="${user != null}">
            <input type="hidden" name="role" value="<c:out value='${user.role}' />" />
        </c:if>

        <div class="form-group">
            <label ><fmt:message key="page.login.login"/></label> <input type="text" class="form-control" id="username" placeholder=<fmt:message key="page.login.login"/> name="login" required>
        </div>

        <div class="form-group">
            <label><fmt:message key="page.login.password"/></label> <input type="password" class="form-control" id="password" placeholder=<fmt:message key="page.login.password"/> name="password" required>
        </div>

        <button type="submit" class="btn btn-primary" <fmt:message key="page.login.submit"/>><fmt:message key="page.login.submit"/></button>
    </form>
</div>
</body>

</html>