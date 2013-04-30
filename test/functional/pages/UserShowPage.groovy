package pages

import geb.Page 

class UserShowPage extends Page {
    
        static at = {
            title.endsWith("Show User")
        }
        
        static content = {
            //This button currently does not work
          //  createAHuntButton(to: CreateHuntPage) {$('li.g', text: endsWith("Create Hunt") )}
            
            
            //The navbar buttons
            scavengrButton(to: HomePage) {$('a' , id:"scavengrButton")}
            helloButton(to: UserShowPage) {$("a", text: startsWith("Hello,")) }
            logoutButton() {$('a', text: "Log out")}
            navbarCreateButton(to: CreateAHuntPage) {$("a", text: "Create A Hunt")}
            createAHuntButton() {$('a', id:"CreateHuntButton")}
        }
}
