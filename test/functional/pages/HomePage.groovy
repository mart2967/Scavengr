package pages

import geb.Page
import pages.UserShowPage
import pages.CreateAHuntPage

class HomePage extends Page {
    
        static uri = " "
        
        static at = {
            title.endsWith("Scavengr")
        }
        
        static content = {
            //Join a Hunt
            findHuntButton() {$("input", value:"Go!")}
            viewPublicHunts(to: ListOfHuntsPage) {$("a", text: "View Public Hunts")}
            enterKeyBox() {$("input", name:"key")}
            
            
            createAHuntButton() {$("div", id: "create")}
            
            
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
            
            //Buttons that appear in user session
            //this is the button at the top of the page when you are logged in
            navbarCreateButton() {$("a", text: "Create A Hunt")}
            
            scavengrButton(to: HomePage) {$('a' , text: 'Scavengr')}
            helloButton(to: UserShowPage) {$("a", text: startsWith("Hello,")) }
            logoutButton() {$('a', text: "Log out")}
        }
}
