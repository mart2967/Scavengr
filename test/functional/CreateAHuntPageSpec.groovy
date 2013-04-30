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
    
    private "can create hunt with a title, description, start date, end date"() {
        when:
        getToCreateHuntPage()
        huntTitleBox.value("The Batman")
        huntDescriptionBox.value("Take pictures inspired by the dark knight")
        huntStartDate.value("04/25/2013 02:37 PM")
        huntEndDate.value("04/25/2099 02:45 AM")
        createHuntButton.click()
        
        then:
        at HuntShowPage
    }
    
    private "can add emails to a hunt when you are creating one"() {
        when:
        getToCreateHuntPage()
        emailBox.value("anemail@email.com")
        emailButton.click()
        deleteEmailButton.click()
        
        then:
        at CreateAHuntPage
    }
    
    private "can create hunt with a title, description, start date, end date, prompt, and an email"() {
        when:
        getToCreateHuntPage()
        huntTitleBox.value("The Batman")
        huntDescriptionBox.value("Take pictures inspired by the dark knight")
        huntStartDate.value("04/25/2013 02:37 PM")
        huntEndDate.value("04/25/2099 02:45 AM")
        emailBox.value("anemail@email.com")
        emailButton.click()
        promptTitleBox.value("Animals")
        promptDescriptionBox.value("Take pictures of them.")
        addPromptButton.click()
        createHuntButton.click()
        
        then:
        at HuntShowPage
    }
    
}
