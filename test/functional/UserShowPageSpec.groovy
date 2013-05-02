import geb.spock.GebReportingSpec 
import spock.lang.*
import pages.*



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
	
//	private changePassword(currentPassword, newPassword){
//		changePassword.click()
//		currentPasswordBox.value(currentPassword) != newPasswordBox.value(newPassword)
//		newPasswordBox.value(newPassword) == confirmPasswordBox.value(newPassword)
//		confirmPasswordButton.click()
//	}
	
	def 'when you log in you will be at the user show page'() {
		when:
		loginAs('Walter', 'password')
		
		then:
		at UserShowPage
	}
	

//    def 'redirects to create a hunt page when create hunt is clicked'() {
//        when:
//        loginAs('Walter','password')
//        createAHuntButton.click()
//        
//        then:
//        at CreateAHuntPage  
//    }
//	
//	def 'after changing password you are on the user show page'() {
//		when:
//		loginAs('Walter','password')
//		changePassword('password', 'password1')
//		
//		then:
//		at UserShowPage
//	}

}