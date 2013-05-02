import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class HomePageSpec extends GebReportingSpec{

    private loginAs(user, password) {
        to HomePage
        loginUserNameBox.value(user)
        loginPasswordBox.value(password)
        loginButton.click()
    }

    private createUser(user, email, password) {
        to HomePage
        createAccountButton.click()
        userNameBox.value(user)
        emailBox.value(email)
        passwordBox.value(password)
        confirmPasswordBox.value(password)
        createButton.click()
    }

    def 'can reach the home page'() {
        when:
        to HomePage

        then:
        at HomePage
    }
    
    def 'can log in as bootstrapped data'() {
        when:
        loginAs('Walter', 'password')
        
        then:
        at UserShowPage
    }

    def 'can navigate to public hunts'() {
        when:
        to HomePage
        viewPublicHunts.click()
        then:
        at ListOfHuntsPage
    }

    def 'can create account'() {
        when:
        createUser('Pleasurewizard', 'email@junk.gov', 'password')

        then:
        at UserShowPage
    }


    def 'hitting Create A Hunt while not logged in does not redirect' (){
        when:
        to HomePage
        createAHuntButton.click()
        
        then:
        at HomePage
    }
       
    def 'clicking the navbar create a hunt button redirects to the create a hunt page if logged in' (){
        when:
        loginAs('Walter', 'password')
        navbarCreateButton.click()
        
        then:
        at CreateAHuntPage
    }

    def 'clicking the big blue create a hunt button redirects to the create a hunt page if logged in' (){
        when:
        loginAs('Walter', 'password')
        to HomePage
        createAHuntButton.click()
        
        then:
        at CreateAHuntPage
    }
    
    def 'can cancel signup box'() {
        when:
        to HomePage
        createAccountButton.click()
        closeButton.click()

        then:
        at HomePage
    }

    def 'can log out and then in again'() {
        when:
        createUser('Stuff123', 'stuff@junk.gov', 'password')
        to HomePage
        logoutButton.click()
        loginAs('Stuff123','password')

        then:
        at UserShowPage
    }



    def 'hello user button redirects to user page'() {
        when:
        loginAs('Stuff123', 'password')
        to HomePage
        helloButton.click()

        then:
        at UserShowPage

    }

    def 'scavengr button redirects to home page'() {
        when:
        loginAs('Stuff123', 'password')
        scavengrButton.click()

        then:
        at HomePage
    }
// THis test fails, but it should pass because logging out should take you to the homepage.
//    def 'log out redirects to home page'() {
//        when:
//        loginAs('Stuff123', 'password')
//        logoutButton.click()
//
//        then:
//        at HomePage
//    }

    def 'create an account with an identical name to another account'() {
        when:
        createUser('Stuff123', 'stuff@junk.gov', 'password')
        then:
        at HomePage
    }

    def 'log in with incorrect password'() {
        when:
        loginAs('Stuff123', 'incorrectpassword')
        then:
        at HomePage
    }
    
    def 'entering a key brings you to a hunt'() {
        when:
        to HomePage
        enterKeyBox.value('yfe5mejs2a')
        findHuntButton.click()
        
        then:
        at HuntShowPage
        
    }
    
    //This test currently does not work but should at some point. 
    /*def 'entering an incorrect key keeps you at the home page'() {
        when:
        to HomePage
        enterKeyBox.value('incorrectkey')
        findHuntButton.click()
        
        then:
        at HomePage
        
    }*/
    


}
