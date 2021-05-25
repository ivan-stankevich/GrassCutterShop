<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Product form</title>
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
<body>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${grassCutter != null}">
                 <form action="<%=request.getContextPath()%>/admin/updateProduct/submit" method="post">
                     </c:if>
            <c:if test="${grassCutter == null}">
                 <form action="<%=request.getContextPath()%>/admin/newProduct/submit" method="post">
                     </c:if>

                    <caption>
                        <h3>
                            <c:if test="${grassCutter != null}">
                                <fmt:message key="product.edit.grass.cutter"/>
                            </c:if>
                            <c:if test="${grassCutter == null}">
                                <fmt:message key="product.add.new.grass.cutter"/>
                            </c:if>
                        </h3>
                    </caption>
                    <c:if test="${grassCutter != null}">
                        <input type="hidden" name="id" value="<c:out value='${grassCutter.productId}' />" />
                    </c:if>

                    <tr>
                        <th><fmt:message key="product.title"/>: </th>
                        <td>
                            <input type="text" name="title" size="45"
                                   value="<c:out value='${grassCutter.title}' />"
                            />
                        </td>
                    </tr>
                        <br><br/>
                        <th><fmt:message key="product.manufacturer"/>:</th>
                        <p><select size="1" name="manufacturer">
                            <c:if test="${grassCutter != null}">
                            <option disabled><c:out value='${grassCutter.manufacturer}'/></option>
                            </c:if>
                            <option value="HUSQVARNA">HUSQVARNA</option>
                            <option value="KARCHER">KARCHER</option>
                            <option value="STIHL">STIHL</option>
                            <option value="MDT">MDT</option>
                        </select></p>
                         <th><fmt:message key="product.type"/>: </th>
                        <p><select size="1" name="type">
                            <c:if test="${grassCutter != null}">
                            <option disabled><c:out value='${grassCutter.type}'/></option>
                            </c:if>
                        <option value="ROBOT">ROBOT</option>
                        <option value="WHEELED">WHEELED</option>
                        <option value="RIDER">RIDER</option>
                        <option value="SPINDLE">SPINDLE</option>
                        <option value="TRIMMER">TRIMMER</option>
                        </select></p>
                        <th><fmt:message key="product.engine"/>: </th>
                    <p><select size="1" name="engine">
                        <c:if test="${grassCutter != null}">
                            <option disabled><c:out value='${grassCutter.engine}'/></option>
                        </c:if>
                        <option value="RECHARGEABLE">rechargeable</option>
                        <option value="PETROL">petrol</option>
                        <option value="DIESEL">diesel</option>
                        <option value="ELECTRIC">electric</option>
                        <option value="MECHANICAL">mechanical</option>
                    </select></p>
                    <tr>
                        <th><fmt:message key="product.price"/>: </th>
                        <td>
                            <input type="text" name="price" size="5"
                                   value="<c:out value='${grassCutter.price}' />"
                            />
                        </td>
                    </tr>
                    <br/>
                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>