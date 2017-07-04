<%@ include file="../header.jsp" %>
<div class="container col-sm-5">
	<h1>Lista de produtos</h1>
	
	<table>
		<tr>
			<th>Título</th>
			<th>Preços</th>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<td>${product.title}</td>
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