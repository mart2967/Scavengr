import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class HuntShowPageSpec extends GebReportingSpec {
	private loginAs(user, password) {
		to HomePage
		loginUserNameBox.value(user)
		loginPasswordBox.value(password)
		loginButton.click()
	}
	
	private goToHuntShowPage() {
		loginAs('Walter', 'password')
		viewPublicHunts.click()
	}
}
