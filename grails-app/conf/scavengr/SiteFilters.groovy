package scavengr
//import Notification

class SiteFilters {
    def authenticationService
    def filters = {
        all(controller:'*', action:'*'){
            after = { model ->
                try{
                    def user = authenticationService.getUserPrincipal()
                    if(user != null && session.participant == null){
                        model?.messages = Notification.findAll("from Notification as n where n.recipient=:user and n.read=:f",[user:user,f:false],[sort:'dateCreated', order:'desc'])
                        model?.numMessages = Notification.findAll("from Notification as n where n.recipient=:user and n.read=:f",[user:user,f:false]).size()
                        }
                    model?.currentDate = new Date()
                }catch(Exception e){
                    log.error "Error occured running siteFilters: ${e.message}", e
                }
            }
        }
    }
}
