<%@ page import="scavengr.User"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="bootstrap">
<g:set var="entityName" value="${message(code: 'user.label', default: 'New Account')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
		

</head>
<body>
<div class="container">
	<div class="row-fluid">
		<div class="span12">
			<div class="row-fluid">
				<div class="span6 well">
					<div class="page-header">
						<h1>
							<g:message code="default.create.label" args="[entityName]" />
						</h1>
					</div>
		
					<g:if test="${flash.message}">
						<bootstrap:alert class="alert-info">
							${flash.message}
						</bootstrap:alert>
					</g:if>
					
					<g:hasErrors>
						<bootstrap:alert class="alert-info">
							<g:hasErrors bean="${flash.signupFormErrors}" field="login">
							<g:renderErrors bean="${flash.signupFormErrors}" as="list" field="login"/>
							</g:hasErrors>
							
							<g:hasErrors bean="${flash.signupFormErrors}" field="email">
							<g:renderErrors bean="${flash.signupFormErrors}" as="list" field="email"/>
							</g:hasErrors>
							
							<g:hasErrors bean="${flash.signupFormErrors}" field="password">
							<g:renderErrors bean="${flash.signupFormErrors}" as="list" field="password"/>
							</g:hasErrors>
							
							<g:hasErrors bean="${flash.signupFormErrors}" field="passwordConfirm">
							<g:renderErrors bean="${flash.signupFormErrors}" as="list" field="passwordConfirm"/>
							</g:hasErrors>
						</bootstrap:alert>
					</g:hasErrors>
		
					<fieldset>
						
								<g:if test="${flash.authenticationFailure}">
									Login failed: ${message(code:"authentication.failure."+flash.authenticationFailure.result).encodeAsHTML()}
								</g:if>
								<auth:form authAction="signup" success="[controller:'user', action:'myProfile']" error="[controller:'user', action:'create']">
									<label for="login">Username</label>
		   							<g:textField name="login" value="${flash.signupForm?.login?.encodeAsHTML()}"/>
		   							
		   							<label for="email">Email Address</label>
									<g:textField name="email" value="${flash.signupForm?.email?.encodeAsHTML()}"/>
									
									
									<label for="password">Password</label>
		    						<input type="password" name="password" />
		    						
		    						<label for="passwordConfirm">Confirm Password</label>
		   					 		<input type="password" name="passwordConfirm" />
		   					 		
		   					 		<g:hiddenField name="immediate" value="true"/>
								<div class="form-actions">
									<button type="submit" class="btn btn-primary">
										<i class="icon-ok icon-white"></i>
										<g:message code="default.button.create.label" default="Create Account" />
									</button>
								</div>
								</auth:form>
								
							
					</fieldset>
				</div>
				
<%--				<div class="span6 well">--%>
<%--					<div class="page-header">--%>
<%--						<h1>--%>
<%--							Log In--%>
<%--						</h1>--%>
<%--					</div>--%>
<%--					<auth:form authAction="login" success="[controller:'user', action:'myProfile']" error="[controller:'user', action:'create']">--%>
<%--						<div class="control-group">--%>
<%--							<label for="login">Username</label>--%>
<%--							<g:textField name="login" value="${flash.loginForm?.login?.encodeAsHTML()}"/>--%>
<%--							<label for="password">Password</label>--%>
<%--							<input type="password" name="password" />--%>
<%--						</div>--%>
<%--						--%>
<%--						<div class="form-actions">--%>
<%--							<button type="submit" class="btn btn-primary">--%>
<%--								<i class="icon-ok icon-white"></i>--%>
<%--								Log In--%>
<%--							</button>--%>
<%--						</div>--%>
<%--					</auth:form>--%>
<%--				--%>
<%--				--%>
<%--				--%>
<%--				</div>--%>
			</div>
		</div>

	</div>
	</div>
</body>
</html>
