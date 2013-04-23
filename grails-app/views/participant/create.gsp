<%@ page import="scavengr.Participant"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="bootstrap">
<g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
	<div class="container">
		<div class="row-fluid">
			<div class="span12">
				<div class="row-fluid">
					<div class="span6 well">
						<ul class="nav nav-list">
							<li class="nav-header">
								${entityName}
							</li>
						</ul>
					</div>
				</div>

				<div class="span9">

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

					<g:hasErrors bean="${participantInstance}">
						<bootstrap:alert class="alert-error">
							<ul>
								<g:eachError bean="${participantInstance}" var="error">
									<li
										<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
											error="${error}" /></li>
								</g:eachError>
							</ul>
						</bootstrap:alert>
					</g:hasErrors>

					<fieldset>
						<g:form class="form-horizontal" action="create">
							<fieldset>
								<f:all bean="participantInstance" />
								<div class="form-actions">
									<button type="submit" class="btn btn-primary">
										<i class="icon-ok icon-white"></i>
										<g:message code="default.button.create.label" default="Create" />
									</button>
								</div>
							</fieldset>
						</g:form>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
