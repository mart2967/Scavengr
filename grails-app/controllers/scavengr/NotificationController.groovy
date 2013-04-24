package scavengr
import scavengr.Notification
import grails.converters.JSON

class NotificationController {
    def authenticationService
    
    def dismiss(){
        def userInstance = User.findByLogin(auth.user())
        def notificationInstance = Notification.get(params.id)
        notificationInstance.read = true
        def messages = Notification.findAll("from Notification as n where n.recipient=:user",[user:userInstance],[sort:'dateCreated', order:'asc', max:10])
        render messages as JSON
    }
    
    def dismissAll(){
        def userInstance = User.findByLogin(auth.user())
        def messages = Notification.findAll("from Notification as n where n.recipient=:user and n.read=:f",
            [user:userInstance,f:false],[sort:'dateCreated', order:'desc', max:10])
        messages.each{ msg ->
            msg.read = true
        }
        render template:"/shared/notifications", model:[messages:messages]
        }
}
