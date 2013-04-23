<%@ page import="scavengr.Participant" %>



<div class="fieldcontain ${hasErrors(bean: participantInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="participant.name.label" default="Name" />
		
	</label>
	<g:textField name="name" maxlength="20" value="${participantInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: participantInstance, field: 'myUser', 'error')} ">
	<label for="myUser">
		<g:message code="participant.myUser.label" default="My User" />
		
	</label>
	<g:select id="myUser" name="myUser.id" from="${scavengr.User.list()}" optionKey="id" value="${participantInstance?.myUser?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

