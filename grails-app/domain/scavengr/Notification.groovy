package scavengr

class Notification {
    String subject
    String message
    String action
    String link
    User sender
    User recipient
    Date dateCreated
    boolean read = false
    
    static belongsTo = [recipient:User]
    static mapping = {
        read column:'`read'
    }
    static constraints = {
        subject maxSize:25
        message blank:true, nullable: true
        action blank:true, nullable: true
        link blank:true, nullable: true
    }
    
}
