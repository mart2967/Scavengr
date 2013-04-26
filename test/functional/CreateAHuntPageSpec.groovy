import pages.HomePage
import pages.ListOfHuntsPage
import pages.UserShowPage
import pages.CreateAHuntPage

import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class CreateAHuntPageSpec {
	
	private loginAs(user, password) {
		to HomePage
		loginUserNameBox.value(user)
		loginPasswordBox.value(password)
		loginButton.click()
	}
	
	private "Adding Promts and Deleting Prompts"() {
		when:
		to HomaePage
		login("Walter", "password")
		createAHuntButton.click()
		
		then:
		at CreateAHuntPage
		
	}
}
