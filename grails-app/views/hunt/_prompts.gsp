<script type="text/javascript">
    var promptCount = ${huntInstance?.myPrompts.size()} + 0;

    function addPrompt() {
    	var title = $('#new-prompt-title').val();
		var description = $('#new-prompt-description').val();
        var htmlId = 'prompt' + promptCount;
        var templateHtml = '<div name="' + htmlId + '">\n';
        templateHtml += '<g:textField name="myPrompts[' + promptCount + '].title" value="' + title + '" />\n';
        templateHtml += '<g:textArea name="myPrompts[' + promptCount + '].description" value="' + description + '" />\n';
        templateHtml += '<g:hiddenField name="deleted" />';
        //templateHtml += '<g:hiddenField name="temp-prompt-hunt" class="prompt-item-hunt" />'
        templateHtml += '<button type="button" id="remove' + promptCount + '" class="remove-prompt-button btn btn-secondary" value="Remove"><i class="icon-remove icon-black"></i></button>';
        templateHtml += "</div>\n";
        $("#prompt-div").append(templateHtml);
        promptCount++;
    }

    function addPromptOld( {

    	var title = $('#new-prompt-title').val();
		var description = $('#new-prompt-description').val();
		$('#new-prompt-title').focus();
		if (title != '') {
			var newPromptField = '<div class="control-group" hidden="true" id="temp-prompt-div"> <g:textField name="temp-prompt-title" class="temp-prompt-title" /> <g:textArea name="temp-prompt-description" class="temp-prompt-description" /><g:hiddenField name="temp-prompt-hunt" class="prompt-item-hunt" /> <button type="button" id="temp-prompt-button" class="remove-prompt-button btn btn-secondary" value="Remove"><i class="icon-remove icon-black"></i></div>';
			$('#new-prompt-title').attr('value', '');
			$('#new-prompt-description').attr('value', '');

			$('#prompt-list').prepend(newPromptField);
			$('#temp-prompt-title').attr('id', 'myPrompts[' + addedPrompts + '].title');
			$('#temp-prompt-description').attr('id', 'myPrompts[' + addedPrompts + '].description');
			$('#temp-prompt-hunt').attr('id', 'myPrompts[' + addedPrompts + '].myHunt');
			$('#temp-prompt-hunt').attr('value', "'${huntInstance}'" );
			$('#temp-prompt-button').attr('id', 'remove-prompt-button-' + addedPrompts);
			$('#prompt-div').show();
			$('#temp-prompt-div').slideDown();
			$('#temp-prompt-div').attr('id', 'prompt-item-' + addedPrompts);
			$('.temp-prompt-title').attr('name', 'myPrompts[' + addedPrompts + '].title');
			$('.temp-prompt-title').attr('value', title);
			$('.temp-prompt-description').attr('name', 'myPrompts[' + addedPrompts + '].description');
			$('.temp-prompt-description').attr('value', description);
			$('.temp-prompt-title').attr('class', 'prompt-title-item');
			$('.temp-prompt-description').attr('class', 'prompt-description-item');
			addedPrompts++;
		}
        })
    
</script>


<div id="prompt-info" class="well">
	<h3>Add Prompts</h3>
	<fieldset>
		<div class="control-group">
			<g:textField name="new-prompt-title" placeholder="Title" />
		</div>
		<div class="control-group">
			<g:textArea name="new-prompt-description" placeholder="Description"></g:textArea>

			<button id="add-prompt-button" value="Add Prompt" type="button"
				class="btn btn-primary" onclick="addPrompt();">Add Prompt</button>
		</div>
	</fieldset>
</div>

<div id="prompt-div">
	<g:each var="prompt" in="${huntInstance.myPrompts}" status="i">
		<g:render template='prompt' model="['prompt':prompt,'i':i]" />
	</g:each>
</div>