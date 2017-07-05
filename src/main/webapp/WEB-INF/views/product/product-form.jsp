<%@ include file="../header.jsp" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="container col-sm-5">
	<h1>Cadastro produto</h1>
	
	<form:form action='${spring:mvcUrl("createProduct").build()}' 
	commandName="product" method="post" enctype="multipart/form-data"
	cssClass="form-group">
		<div class="form-group">
			<label for="title">Título</label>
			<form:input path="title" cssClass="form-control"/>
			<form:errors path="title" />
		</div>
		<div class="form-group">
			<label for="description">Descrição</label>
			<form:textarea path="description" cssClass="form-control" cols="20" rows="10"/>
			<form:errors path="description" />
		</div>
		<div class="form-group">
			<label for="numberOfPages">Número de páginas</label>
			<form:input path="numberOfPages" cssClass="form-control"/>
			<form:errors path="numberOfPages" />
		</div>
		<div class="form-group">
			<label for="releaseDate">Data de lançamento</label>
			<div class="input-group date">
				<form:input path="releaseDate" cssClass="form-control"/>
				<div class="input-group-addon">
					<span class="glyphicon glyphicon-th"></span>
				</div>
			</div>
			<form:errors path="releaseDate" />
		</div>
		<div class="form-group">
			<label for="sumario">Upload de sumário</label>
			<input type="file" name="sumario" id="sumario" class="form-control"/>
		</div>
		
		<h3>Preço por formato</h3>
		<c:forEach items="${types}" var="bookType" varStatus="status">
			<div class="row">
				<div class="input-group col-2">
					
					<label for="price_${bookType}" class="col-form-label">${bookType}</label>
				</div>
				<div class="input-group col-4">
					<span class="input-group-addon">R$</span>
					<form:input path="prices[${status.index}].value" cssClass="form-control"/>
					<form:input type="hidden" path="prices[${status.index}].bookType" value="${bookType}"/>
				</div>
			</div>
		</c:forEach>
		
		<button type="submit" value="enviar" class="btn btn-primary">Criar</button>
	</form:form>
</div>
	

<%@ include file="../footer.jsp" %>