import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.HomePage
import pages.ListOfHuntsPage
import pages.UserShowPage
import pages.CreateHuntPage


class UserShowPageSpec extends GebReportingSpec {
    
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
    
//    def 'create user redirects to the user show page'() {
//        when:
//        createUser("userName", "email@email.com", "password")
//        
//        then:
//        at UserShowPage
//    }
 /*   
    def 'redirects to create a hunt page when create hunt is clicked'() {
        when:
        loginAs("userName","password")
        createAHuntButton.click()
        
        then:
        at CreateHuntPage  
    }
*/
}
