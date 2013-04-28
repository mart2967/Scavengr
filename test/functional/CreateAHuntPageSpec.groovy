import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class CreateAHuntPageSpec extends GebReportingSpec {

    private loginAs(user, password) {
        to HomePage
        loginUserNameBox.value(user)
        loginPasswordBox.value(password)
        loginButton.click()
    }
    
    private getToCreateHuntPage() {
        loginAs("Walter", "password")
        navbarCreateButton.click()
    }
    
    private "can navigate to create a hunt page"() {
        when:
        getToCreateHuntPage()

        then:
        at CreateAHuntPage

    }
    
    private "can delete a prompt"() {
        when:
        getToCreateHuntPage()
        promptTitleBox.value("Animals")
        promptDescriptionBox.value("Take pictures of them.")
        addPromptButton.click()
        removePromptButton.click()
        
        then:
        at CreateAHuntPage
    }
    
}
