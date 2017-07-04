<%@ include file="../header.jsp" %>

<c:url value='/product/create' var="url"/>

	<div class="container">
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
			<button type="submit" value="enviar" class="btn btn-primary">Criar</button>
		</form>
	</div>
	

<%@ include file="../footer.jsp" %>