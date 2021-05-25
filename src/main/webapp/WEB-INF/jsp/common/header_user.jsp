<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ? sessionScope.language : ru_RU}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="localization.translation" scope="session"/>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> Todo App</a>
        </div>
        <ul class="navbar-nav navbar-collapse justify-content-end">
            <li><a href="<%= request.getContextPath() %>/user/userOrders" class="nav-link"><fmt:message key="page.header.orders"/></a></li>
            <li><a href="<%= request.getContextPath() %>/user/listOfProducts" class="nav-link"><fmt:message key="page.header.grass.cutters"/></a></li>
            <li><a href="<%= request.getContextPath() %>/user/editUser" class="nav-link"><fmt:message key="page.header.edit.user"/></a></li>
            <li><a href="<%= request.getContextPath() %>/logout" class="nav-link"><fmt:message key="page.logout"/></a></li>
        </ul>
    </nav>
</header>