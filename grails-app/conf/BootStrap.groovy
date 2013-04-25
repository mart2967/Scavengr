//import java.util.Map

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
        
        def burningImageService
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

        //def walterSecure = new AuthenticationUser(login: 'WalterSecure', email: 'walter@email.com', 
			//password: 'password'.encodeAsMD5(), status:AuthenticationService.STATUS_VALID).save(failOnError: true)
        //def edwardSecure = new AuthenticationUser(login: 'EdwardSecure', email: 'edward@email.com', 
			//password: 'password'.encodeAsMD5(), status:AuthenticationService.STATUS_VALID).save(failOnError: true)
        //def laurieSecure = new AuthenticationUser(login: 'LaurieSecure', email: 'laurie@email.com', 
			//password: 'password'.encodeAsMD5(), status:AuthenticationService.STATUS_VALID).save(failOnError: true)
		
        /*Hunts*/
        def trees = new Hunt(title: 'Trees', description: 'Look at these fresh young trees.', 
			myCreator: walter, isPrivate: false, startDate: new Date(), endDate: lateDate, key: "yfe5mejs2a").save(failOnError)
        def colors = new Hunt(title: 'Colors', description: 'I forgot how to see these, plz halp.', 
			myCreator: laurie, isPrivate: true, startDate: new Date(), endDate: lateDate).save(failOnError)
        def raccoons = new Hunt(title: 'Raccoons', description: 'Raccoons. What more do you need?', 
			myCreator: laurie, isPrivate: true, startDate: new Date(), endDate: lateDate).save(failOnError)
        def cells = new Hunt(title: 'Plant Cells', description: 'Microscope. Now.', 
			myCreator: edward, isPrivate: false, startDate: new Date(), endDate: lateDate).save(failOnError)
        def people = new Hunt(title: 'People', description: 'Stalking 101', 
			myCreator: walter, isPrivate: false, startDate: new Date(), endDate: lateDate).save(failOnError)
        def animals = new Hunt(title: 'Animals', description: 'Everyone Loves Them', 
			myCreator: walter, isPrivate: false, startDate: new Date(), endDate: lateDate).save(failOnError)

        /*Prompts*/
        def oak = new Prompt(title: 'Oak', description: 'Majestic Trees', myHunt: trees).save(failOnError)
        def maple = new Prompt(title: 'Maple', description: 'Bringers of Syrup', myHunt: trees).save(failOnError)
        def green = new Prompt(title: 'Green', description: '', myHunt: colors).save(failOnError)
        def anyTree = new Prompt(title: 'Any Tree', description: 'Anything Will Do', myHunt: trees).save(failOnError)
        def microscopic = new Prompt(title: 'Cells Under Microscope', description: '', myHunt: cells).save(failOnError)
        def cats = new Prompt(title: 'Cats', description: '', myHunt: animals).save(failOnError)
        def pigs = new Prompt(title: 'Guinea Pigs', description: '', myHunt: animals).save(failOnError)
        def dogs = new Prompt(title: 'Dogs', description: 'Mans Best Friend', myHunt: animals).save(failOnError)


        /*Photos*/
        /*SetUp*/
        def dirPath = './WEB-INF/testImages/'
        //def img = burningImageService.doWith(getMultipartFile('tree_canopy.jpg'), dirPath)
        /*
        
        def photoOne = burningImageService.getFile(dirPath + 'tumblr_mfzzsxGSKT1remtneo1_1280.jpg')
        def photoTwo = burningImageService.getFile(dirPath + 'tree_canopy.jpg')
        def photoThree = burningImageService.getFile(dirPath + 'tree26.jpg')
        def photoFour = burningImageService.getFile(dirPath + 'trees_mem_planes.jpg')
        def photoFive = burningImageService.getFile(dirPath + 'trees23.jpg')
        def plantOne = burningImageService.getFile(dirPath + 'onion_epidermal_cells.jpg')
        def plantTwo = burningImageService.getFile(dirPath + 'plant_cell_structures.jpg')
        def plantThree = burningImageService.getFile(dirPath + 'palm-plant-cells-microscope-photo1.jpg')
        def plantFour = burningImageService.getFile(dirPath + 'depositphotos_11630672-Plant-cells-under-microscope.jpg')

        def animalOne = burningImageService.getFile(dirPath + 'guinea_pig_pair.jpg')
        def animalTwo = burningImageService.getFile(dirPath + '22196-Grey-and-white-cat-white-background.jpg')
        def animalThree = burningImageService.getFile(dirPath + '99059361-choose-cat-litter-632x475.jpg')
        def anproductionimalFour = burningImageService.getFile(dirPath + 'earliest-dogs-660x433-130306-chow-chow-660x433.jpg')
        def animalFive = burningImageService.getFile(dirPath + 'i-6c133aff21f8e4f5f4d9f15170f3bff2-guinea_pig1.jpg')
        def animalSix = burningImageService.getFile(dirPath + 'dogs.jpg')
        def animalSeven = burningImageService.getFile(dirPath + 'Siberian-Husky-dogs-13788924-1024-768.jpg')
        

        new Photo(title: 'Oak Tree', description: 'A Large Tree', 
			myFile: photoOne, myUser: edward, myPrompt: oak).save(failOnError)
        new Photo(title: 'Tree from Below', description: "I'm Short", 
			myFile: photoTwo, myUser: laurie, myPrompt: oak).save(failOnError)
        new Photo(title: 'Big Tree', description: 'This is my favorite one!', 
			myFile: photoThree, myUser: walter, myPrompt: maple).save(failOnError)
        new Photo(title: 'Trees on a Boulevard', description: 'I hate winter.', 
			myFile: photoFour, myUser: laurie, myPrompt: anyTree).save(failOnError)
        new Photo(title: 'My Oak Tree', description: 'How wonderful!', 
			myFile:photoFive, myUser: walter, myPrompt: oak).save(failOnError)

        new Photo(title: 'Some Cells', description: 'Yup', 
			myFile: plantOne, myUser: edward, myPrompt: microscopic).save(failOnError)
        new Photo(title: 'More Cells', description: 'These', 
			myFile: plantTwo, myUser: laurie, myPrompt: microscopic).save(failOnError)
        new Photo(title: 'Even More Cells', description: 'Are', 
			myFile: plantThree, myUser: edward, myPrompt: microscopic).save(failOnError)
        new Photo(title: 'Hello Cells', description: 'Pictures', 
			myFile: plantFour, myUser: walter, myPrompt: microscopic).save(failOnError)

        new Photo(title: 'A Cat', description: 'Hooray.', 
			myFile: animalTwo, myUser: walter, myPrompt: cats).save(failOnError)
        new Photo(title: 'A Pig', description: 'Hooray.', 
			myFile: animalOne, myUser: laurie, myPrompt: pigs).save(failOnError)
        new Photo(title: 'Another Cat', description: 'Wow!!!!!!!', 
			myFile: animalThree, myUser: edward, myPrompt: cats).save(failOnError)
        new Photo(title: 'A Dawg', description: '', 
			myFile: animalFour, myUser: walter, myPrompt: dogs).save(failOnError)
        new Photo(title: 'Another Dog', description: '', 
			myFile: animalSix, myUser: walter, myPrompt: dogs).save(failOnError)
        new Photo(title: 'Another Guinea Pig', description: '', 
			myFile: animalFive, myUser: edward, myPrompt: pigs).save(failOnError)
        new Photo(title: 'Last Dog', description: 'Ever.', 
			myFile: animalSeven, myUser: laurie, myPrompt: dogs).save(failOnError)
	*/
        }
    }

    def destroy = {
    }
}
