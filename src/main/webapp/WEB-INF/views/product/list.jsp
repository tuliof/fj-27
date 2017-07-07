<%@ include file="../header.jsp" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="container col-sm-5">
	<h1>Lista de produtos</h1>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="user"/>
		<p>Olá ${user.username}</p>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<p>Visão de ADM</p>
	</sec:authorize>
	
	
	<table>
		<tr>
			<th>Título</th>
			<th>Preços</th>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<c:url value="/products/${product.id}" var="linkDetail" />
				<td><a href="${linkDetail}">${product.title}</a></td>
				<c:forEach items="${product.prices}" var="price">
					<td>${price.value}</td>
					<td>${price.bookType}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	<c:forEach items="">
		<li></li>
	</c:forEach>
	</table>
</div>
<%@ include file="../footer.jsp" %>