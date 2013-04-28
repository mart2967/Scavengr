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
    
</script>


<div id="prompt-info" class="well">
	<h3>Add Prompts</h3>
	<fieldset>
		<div class="control-group">
			<g:textField name="new-prompt-title" placeholder="Title" />
		</div>
		<div class="control-group">
			<g:textArea name="new-prompt-description" placeholder="Description"></g:textArea>

			<button id="addPromptButton" value="Add Prompt" type="button"
				class="btn btn-primary" onclick="addPrompt();">Add Prompt</button>
		</div>
	</fieldset>
</div>

<div id="prompt-div">
	<g:each var="prompt" in="${huntInstance.myPrompts}" status="i">
		<g:render template='prompt' model="['prompt':prompt,'i':i]" />
	</g:each>
</div>