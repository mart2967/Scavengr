<script type="text/javascript">
	$(document).ready(function(){
		$('#mail').popover({
			title: 'Notifications',
			content: $('#notifications').html(),
			html:true
		});
	});
</script>



<div id="notifications" hidden="true">
	<g:each in="${loggedInUser?.recieved}" var="msg">
		<div class="notification">
			<b>${msg.subject}</b>
			<small class="pull-right">
			<g:formatDate date="${msg.dateCreated}" format="MM/dd hh:mm a"/>
			</small>
			<p>${msg.message}</p>
		</div>
	</g:each>
</div>