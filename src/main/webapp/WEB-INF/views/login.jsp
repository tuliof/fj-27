<%@ include file="header.jsp" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="container">
	<div class="row main">
		<div class="main-login main-center">
			<form:form servletRelativeAction="/login" cssClass="form-horizontal" method="post">
				
		<!-- 
				<div class="form-group">
					<label for="email" class="cols-sm-2 control-label">Your Email</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
							<input type="text" class="form-control" name="email" id="email"  placeholder="Enter your Email"/>
						</div>
					</div>
				</div>
		 -->
				<div class="form-group">
					<label for="username" class="cols-sm-2 control-label">Username</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
							<input type="text" class="form-control" name="username" id="username"  placeholder="Enter your Username"/>
						</div>
					</div>
				</div>
		
				<div class="form-group">
					<label for="password" class="cols-sm-2 control-label">Password</label>
					<div class="cols-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
							<input type="password" class="form-control" name="password" id="password"  placeholder="Enter your Password"/>
						</div>
					</div>
				</div>
		
				<div class="form-group ">
					<button type="submit" class="btn btn-primary btn-lg btn-block login-button">Login</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp" %>