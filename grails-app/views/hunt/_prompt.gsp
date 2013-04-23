<div id="prompt${i}" class="control-group">
	<g:hiddenField name="expandablePromptList[${i}].id" value="${prompt.id}"/>
	<g:textField name="expandablePromptList[${i}].title" value="${prompt.title}" />
	<g:textArea name="expandablePromptList[${i}].description" value="${prompt.description}" />
	<input type="hidden" name="expandablePromptList[${i}]._deleted" id="expandablePromptList[${i}]._deleted" value="false">
	<button type="button" onClick="$('#expandablePromptList\\[${i}\\]\\._deleted').val('true'); $('#book${i}').hide()"><i class="icon-remove icon-black"></i></button>
</div>