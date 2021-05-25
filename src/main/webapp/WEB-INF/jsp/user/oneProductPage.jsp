<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Grass Cutters</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<c:if test="${sessionScope.user.role.ordinal() == 0}"> <jsp:include page="../common/header_user.jsp"></jsp:include> </c:if>
<c:if test="${sessionScope.user.role.ordinal() == 1}"> <jsp:include page="../common/header_admin.jsp"></jsp:include> </c:if>
<div >
    <%@include file="/WEB-INF/locale.jsp" %>
</div>
<div class="row">
    <div class="container">
        <h3 class="text-center"><fmt:message key="page.detail.detail.info"/></h3>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th ><fmt:message key="product.title"/></th>
                <th><fmt:message key="product.manufacturer"/></th>
                <th><fmt:message key="product.type"/></th>
                <th><fmt:message key="product.engine"/></th>
                <th><fmt:message key="product.price"/></th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value="${product.title}" /></td>
                    <td><c:out value="${product.manufacturer}" /></td>
                    <td><c:out value="${product.type}" /></td>
                    <td><c:out value="${product.engine}" /></td>
                    <td><c:out value="${product.price}" /></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>