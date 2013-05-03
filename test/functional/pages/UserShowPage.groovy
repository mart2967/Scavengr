package pages

import geb.Page   
import pages.*


class UserShowPage extends Page {
    
        static at = {
            title.endsWith('Show User')
        }
        
        def a = 'a'
        
        static content = {
            //This button currently does not work
          //  createAHuntButton(to: CreateHuntPage) {$('li.g', text: endsWith('Create Hunt') )}
            
            
            //The navbar buttons
            scavengrButton(to: HomePage){$(a , id:'scavengrButton')}
            helloButton(to: UserShowPage){$(a, text: startsWith('Hello,')) }
            logoutButton() {$(a, text: 'Log out')}
            navbarCreateButton(to: CreateAHuntPage){$(a, text: 'Create A Hunt')}
            userCreateAHuntButton(){$(a, text:'Create Hunt')}
			
			changePassword{$(a, id='changePassword')}
			changeEmail{$(a, id='changeEmail')}
			downloadPhotos{$(a, id='downloadPhotos')}
			comfirmPasswordButton{$(a, id='submitChange')}
			currentPasswordBox(){$('input', id:'changePassword')}
			newPasswordBox(){$('input', id:'newPassword')}
			confirmNewPasswordBox(){$('input', id:'confirmPassword')}
        }
}
