<script type="text/javascript">
	$(document).ready(function(){
		$('#mail').popover({
			title: 'Notifications <span class="pull-right label label-warning">Dismiss All</span>',
			content: $('#notifications').html(),
			html:true,
			template: '<div class="popover notification-window"><div class="arrow"></div><div class="popover-inner "><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'
		});
	});
</script>



<div id="notifications" hidden="true">
	<g:each in="${loggedInUser?.recieved}" var="msg">
		<div class="notification">
			<b>${msg?.subject}</b>
			
			<small class="pull-right">
			<g:formatDate date="${msg?.dateCreated}" format="MM/dd hh:mm a"/>
			</small>
			<br />
			<div class="pull-right" style="margin-left:10px;">
			<g:if test="${msg?.link}">
			<a href="${msg?.link}" class="pull-right" style="margin-bottom:3px;"><span class="label label-success">${msg?.action}</span></a>
			<br />
			</g:if>
			<a><span class="label label-info pull-right">Dismiss</span></a>
			</div>
			<small>${msg?.message}</small>
			
		</div>
	</g:each>
</div>