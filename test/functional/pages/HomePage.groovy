package pages

import geb.Page
import pages.UserShowPage

class HomePage extends Page {
    
        static uri = " "
        
        static at = {
            title.endsWith("Scavengr")
        }
        
        static content = {
            
            findHuntButton() {$("a", text: "Go!")}
            viewPublicHunts(to: ListOfHuntsPage) {$("a", text: "View Public Hunts")}
            createAHunt() {$("a", text: "Create A Hunt")}
            
            
            //Fields for Log in
            loginUserNameBox() {$('input', id:"loginUserBox")}
            loginPasswordBox() {$('input', id:"loginPasswordBox")}
            loginButton() {$('input', id:"LoginButton")}
            createAccountButton() {$("a", text: "Create Account")}
            
            //Fields for Popup Sign up
            userNameBox() {$('input', id: 'modalLogin')}
            emailBox() {$('input', name: 'email')}
            passwordBox() {$('input', name: 'password')}
            confirmPasswordBox() {$('input', name: 'passwordConfirm')}
            
            createButton() {$('button', id: "popUpCreate")}
            closeButton() {$('a', text: "Close")}
            
            scavengrButton(to: HomePage) {$('a' , id:"scavengrButton")}
            helloButton(to: UserShowPage) {$("a", text: startsWith("Hello,")) }
            logoutButton() {$('a', text: "Log out")}
        }
}
