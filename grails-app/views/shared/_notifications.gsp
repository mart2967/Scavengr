<script type="text/javascript">
	$(document).ready(function(){
		$('#mail').popover({
			title: 'Notifications' + $('#dismissAll').html(),
			content: $('#notifications').html(),
			html:true,
			template: '<div class="popover notification-window"><div class="arrow"></div><div class="popover-inner "><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'
		});
		
		
	});
	function hideMsg(id){
		div = document.getElementById('notification'+id);
		alert(div);
		$(div).hide();
	}
</script>
<div id="dismissAll" hidden="true">
<g:remoteLink controller="notification" action="dismissAll" update="notifications"><span class="pull-right label label-warning">Dismiss All</span></g:remoteLink>
</div>
<div id="notifications" hidden="true">
	<g:each in="${messages}" var="msg">
		<div class="notification" id="notification${msg.id}">
			<b>${msg?.subject}</b>
			
			<small class="pull-right">
			<g:formatDate date="${msg?.dateCreated}" format="MM/dd hh:mm a"/>
			</small>
			<br />
			<div class="pull-right" style="margin-left:10px;">
			<g:remoteLink controller="notification" action="dismiss" id="${msg.id}" onSuccess="hideMsg(${msg.id});"><span class="label label-info pull-right">Dismiss</span></g:remoteLink>
			<g:if test="${msg?.link}">
			<br />
			<a href="${msg?.link}" class="pull-right" style="margin-bottom:3px;"><span class="label label-success">${msg?.action}</span></a>
			</g:if>
			</div>
			<small>${msg?.message}</small>
			
		</div>
	</g:each>
</div>