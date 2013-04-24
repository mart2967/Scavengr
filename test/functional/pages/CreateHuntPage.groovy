package pages

import geb.Page
import pages.UserShowPage
import pages.HomePage

class CreateHuntPage extends Page{

    static uri = "hunt/create"

    static at = {
        title.endsWith("Create Hunt")
    }

    static content = {



        //The navbar buttons
        scavengrButton(to: HomePage) {$('a' , id:"scavengrButton")}
        helloButton(to: UserShowPage) {$("a", text: startsWith("Hello,")) }
        logoutButton() {$('a', text: "Log out")}
    }

}
