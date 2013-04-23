package scavengr

class Notification {
    String subject
    String message
    User sender
    User recipient
    Date dateCreated
    
    static belongsTo = [recipient:User]
    
    static constraints = {
        subject maxSize:25
        message blank:true
        
    }
}
