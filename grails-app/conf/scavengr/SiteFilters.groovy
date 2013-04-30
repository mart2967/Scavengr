package scavengr
import scavengr.Notification

class SiteFilters {
    def authenticationService
    def filters = {
        all(controller:'*', action:'*'){
            after = { model ->
                def user = authenticationService.getUserPrincipal()
                if(user != null && session.participant == null){
                    model?.messages = Notification.findAll("from Notification as n where n.recipient=:user and n.read=:f",[user:user,f:false],[sort:'dateCreated', order:'desc'])
                    model?.numMessages = Notification.findAll("from Notification as n where n.recipient=:user and n.read=:f",[user:user,f:false]).size()
                    }
                model?.currentDate = new Date()
            }
        }
    }
}
