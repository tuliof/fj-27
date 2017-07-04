<%@ include file="../header.jsp" %>

<c:url value='/products' var="url"/>

	<div class="container col-sm-5">
		<h1>Cadastro produto</h1>
		
		<form method="post" action='${url}' class="form-group">
			<div class="form-group">
				<label for="title">Título</label>
				<input type="text" name="title" id="title" class="form-control"/>
			</div>
			<div class="form-group">
				<label for="description">Descrição</label>
				<textarea rows="10" cols="20" type="text" name="description" id="description" class="form-control">
				</textarea>
			</div>
			<div class="form-group">
				<label for="numberOfPages">Número de páginas</label>
				<input type="text" name="numberOfPages" id="numberOfPages" class="form-control"/>
			</div>
			
			<h3>Preço por formato</h3>
			<c:forEach items="${types}" var="bookType" varStatus="status">
				<div class="row">
					<div class="input-group col-2">
						
						<label for="price_${bookType}" class="col-form-label">${bookType}</label>
					</div>
					<div class="input-group col-4">
						<span class="input-group-addon">R$</span>
						<input type="text" name="prices[${status.index}].value" id="price_${bookType}" class="form-control"/>
						<input type="hidden" name="prices[${status.index}].bookType" value="${bookType}" />
					</div>
				</div>
			</c:forEach>
			
			<button type="submit" value="enviar" class="btn btn-primary">Criar</button>
		</form>
	</div>
	

<%@ include file="../footer.jsp" %>