
import scavengr.Hunt
import scavengr.Photo
import scavengr.Prompt
import scavengr.User
import grails.util.Environment
import com.grailsrocks.authentication.*
import pl.burningice.plugins.image.test.FileUploadUtils

class BootStrap {
    def authenticationService
    def init = { servletContext ->
        if(Environment.getCurrent() != Environment.PRODUCTION ) {
        
	Map failOnError = [failOnError: true]

        def baseDate = new Date()
        def lateDate = new Date(baseDate.getTime() + 1000*60*60*24)

        /*Users*/
        def walter = new User(login: 'Walter', password:authenticationService.encodePassword('password'), 
			email: 'walter@email.com', status:AuthenticationService.STATUS_VALID).save(failOnError)
        def edward = new User(login: 'Edward', password:authenticationService.encodePassword('password'), 
			email: 'edward@email.com', status:AuthenticationService.STATUS_VALID).save(failOnError)
        def laurie = new User(login: 'Laurie', password:authenticationService.encodePassword('password'), 
			email: 'laurie@email.com', status:AuthenticationService.STATUS_VALID).save(failOnError)

        
        /*Hunts*/
        def trees = new Hunt(title: 'Trees', description: 'Look at these fresh young trees.', 
			myCreator: walter, isPrivate: false, startDate: new Date(), endDate: lateDate, key: "yfe5mejs2a").save(failOnError)
        def colors = new Hunt(title: 'Colors', description: 'I forgot how to see these, plz halp.', 
			myCreator: laurie, isPrivate: true, startDate: new Date(), endDate: lateDate).save(failOnError)
        def cells = new Hunt(title: 'Plant Cells', description: 'Microscope. Now.', 
			myCreator: edward, isPrivate: false, startDate: new Date(), endDate: lateDate).save(failOnError)
        def animals = new Hunt(title: 'Animals', description: 'Everyone Loves Them', 
			myCreator: walter, isPrivate: false, startDate: new Date(), endDate: lateDate).save(failOnError)

        /*Prompts*/
        def prompts = []
        prompts.add( new Prompt(title: 'Oak', description: 'Majestic Trees', myHunt: trees).save(failOnError) )
        prompts.add( new Prompt(title: 'Maple', description: 'Bringers of Syrup', myHunt: trees).save(failOnError) )
        prompts.add( new Prompt(title: 'Green', description: '', myHunt: colors).save(failOnError) )
        prompts.add( new Prompt(title: 'Any Tree', description: 'Anything Will Do', myHunt: trees).save(failOnError) )
        prompts.add( new Prompt(title: 'Cells Under Microscope', description: '', myHunt: cells).save(failOnError) )
        prompts.add( new Prompt(title: 'Cats', description: '', myHunt: animals).save(failOnError) )
        prompts.add( new Prompt(title: 'Guinea Pigs', description: '', myHunt: animals).save(failOnError) )
        prompts.add( new Prompt(title: 'Dogs', description: 'Mans Best Friend', myHunt: animals).save(failOnError) )


        
        }
    }

    def destroy = {
    }
}
