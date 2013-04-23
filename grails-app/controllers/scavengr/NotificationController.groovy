package scavengr

class NotificationController {
    def authenticationService
    
    def send(){
        def sender = User.findByLogin(auth.user())
        def recipient = User.findByLogin(params.login)
        def subject = params.subject
        def message = params.message
        def notificationInstance = new Notification(subject:subject, sender:sender, recipient:recipient, message:message)
        recipient.addToMyNotifications(notificationInstance)
    }
}
