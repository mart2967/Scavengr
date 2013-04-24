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
            loginUserNameBox() {$('input', id: "loginUsername")}
            loginPasswordBox() {$('input', id: "loginPassword")}
            loginButton() {$('input', id:"loginButton")}
            createAccountButton() {$("a", text: "Create Account")}
            
            //Fields for Popup Sign up
            userNameBox() {$('input', id: 'signupLogin')}
            emailBox() {$('input', id:"emailSignUp")}
            passwordBox() {$('input', id:"passwordSignup")}
            confirmPasswordBox() {$('input', id:"confirmSignup")}
            
            createButton() {$('button', id:"submitSignup")}
            closeButton() {$('button', text:'×')}
            
            scavengrButton(to: HomePage) {$('a' , text: 'Scavengr')}
            helloButton(to: UserShowPage) {$("a", text: startsWith("Hello,")) }
            logoutButton() {$('a', text: "Log out")}
        }
}
