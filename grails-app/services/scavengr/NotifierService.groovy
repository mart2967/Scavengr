package scavengr

//import java.util.ArrayList
//import java.util.HashSet
import org.codehaus.groovy.grails.web.mapping.LinkGenerator



class NotifierService {
    LinkGenerator grailsLinkGenerator
    boolean transactional = false
    def mailService
    def link
    def authenticationService
    
    def sendNotification(sender, recipient, subject, message){
//        def sender = User.findByLogin(auth.user())
//        def recipient = User.findByLogin(params.login)
//        def subject = params.subject
//        def message = params.message
        def notificationInstance = new Notification(subject:subject, sender:sender, recipient:recipient, message:message)
        recipient.addToRecieved(notificationInstance)
    }
    
    def sendPasswordReset(email, newPassword) {
        mailService.sendMail {
            to email
            from "Scavengr@scavengr.com"
            subject "Scavengr Password Reset"
            body "Your new password is ${newPassword}"
        }
    }
    
    def contactCreator(name, email, key, title) {
        link = grailsLinkGenerator.link(absolute: true, controller: 'hunt', action: 'show', params: [key: key])
	mailService.sendMail {
	    to email
	    from "Scavengr@scavengr.com"
	    subject "You have created the hunt ${title}"
	    body "Hello ${name}, you have created a scavenger hunt! You can share this link with others to invite them to your hunt: ${link}"
	}
    }


    def contactHunters(myCreator, emails, key, title){
        link = grailsLinkGenerator.link(absolute: true, controller: 'hunt', action: 'show', params: [key: key])
	mailService.sendMail {
	    to emails
	    from 'Scavengr@scavengr.com'
	    subject "${myCreator} has invited you to their hunt, ${title}"
	    body "Hello, you have been selcted by ${myCreator} to participate in a photography scavenger hunt at ${link}"
	}
    }

    def resendKeys(email, givenBody) {
	mailService.sendMail {
	    to email
	    from "Scavengr@scavengr.com"
	    subject "Your Hunt Keys"
	    body givenBody
	}
    }
    
}