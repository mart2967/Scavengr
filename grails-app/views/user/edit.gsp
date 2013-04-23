<%@ page import="scavengr.User" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="container">
		<div class="row-fluid">

			
			
			<div class="span12">

				<div class="page-header">
					<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${userInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${userInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="edit" id="${userInstance?.id}" >
						<g:hiddenField name="version" value="${userInstance?.version}" />
						<fieldset>
							<label for="login">Username</label>
							<g:textField name="login" value="${userInstance.login}"/>
							<label for="email">Email Address</label>
							<g:textField name="email" value="${userInstance.email}" />
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.update.label" default="Update" />
								</button>
								<button type="submit" class="btn btn-secondary" name="_action_cancel">
									<i class="icon-remove icon-black"></i>
									<g:message code="default.button.cancel.label" default="Cancel" />
								</button>
<%--								<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>--%>
<%--									<i class="icon-trash icon-white"></i>--%>
<%--									<g:message code="default.button.delete.label" default="Delete" />--%>
<%--								</button>--%>
							</div>
						</fieldset>
					</g:form>
				</fieldset>

			</div>

		</div>
		</div>
	</body>
</html>
