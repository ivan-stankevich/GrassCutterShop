<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script>history.forward();</script>
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
        <h3 class="text-center"><fmt:message key="product.list.of.grass.cutters"/></h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/admin/newProduct"
               class="btn btn-success"><fmt:message key="product.add.new.grass.cutter"/></a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th ><fmt:message key="product.title"/></th>
                <th><fmt:message key="product.manufacturer"/></th>
                <th><fmt:message key="product.type"/></th>
                <th><fmt:message key="product.engine"/></th>
                <th><fmt:message key="product.price"/></th>
                <th><fmt:message key="page.usersList.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="grassCutter" items="${listOfProducts}">
                <tr>
                <td><c:out value="${grassCutter.title}" /></td>
                <td><c:out value="${grassCutter.manufacturer}" /></td>
                <td><c:out value="${grassCutter.type}" /></td>
                <td><c:out value="${grassCutter.engine}" /></td>
                <td><c:out value="${grassCutter.price}" /></td>
                    <td><c:if test="${sessionScope.user.role.ordinal() == 1}"> <a href="/admin/updateProduct?id=<c:out value='${grassCutter.productId}' />"><fmt:message key="product.edit"/></a> &nbsp;&nbsp;&nbsp;&nbsp; <a href="<%= request.getContextPath() %>/admin/deleteProduct?id=<c:out value='${grassCutter.productId}' />"><fmt:message key="product.delete"/></a> </c:if>
                    <a href="/user/addOrder/submit?productId=<c:out value='${grassCutter.productId}' />">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="product.add.order"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
