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
        <h3 class="text-center"><fmt:message key="page.orders"/> </h3>
        <hr>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><fmt:message key="page.orders.order.id"/></th>
                <th><fmt:message key="page.orders.user.id"/></th>
                <th><fmt:message key="page.order.product.id"/></th>
                <th><fmt:message key="page.order.product.title"/></th>
                <th><fmt:message key="page.order.order.time"/></th>
                <th><fmt:message key="page.order.order.cast"/></th>
                <th><fmt:message key="page.order.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orderList}">
                <tr>
                    <td><c:out value="${order.orderId}" /></td>
                    <td><c:out value="${order.userId}" /></td>
                    <td><c:out value="${order.productId}" /></td>
                    <td><c:out value="${order.productTitle}" /></td>
                    <td><c:out value="${order.orderTime}" /></td>
                    <td><c:out value="${order.orderCost}" /></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp <a href="/user/deleteOrder?orderId=<c:out value='${order.orderId}' />"><fmt:message key="product.delete"/></a>
                        &nbsp;&nbsp;&nbsp;&nbsp <a href="/user/detailOrder?productId=<c:out value='${order.productId}' />"><fmt:message key="page.detail.detail.info"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>