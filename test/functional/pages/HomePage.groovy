package functional.pages

import geb.Page
import functional.pages.*
import functional.pages.HuntShowPage


class HomePage extends Page {
    static uri = ' '

    static content = {

        //JoinHuntBoxButton() { $("big-button well")}
        //JoinHuntForm() { $("text", name: "key")}
        /* create a HuntListPage */
        //JoinPublicHuntButton(to: HuntListPage) { $("submit")}
        //JoinWithKeyButton(to: HuntShowPage) { $("submit", value: "Go!") }

//        if (ifLoggedIn == true) {
//            CreateAHuntBox(to: HuntCreatePage) { $("big-button well")}
//            ViewProfileButton(to: "view"){ $("big-button well")}
//        }
//        if (ifNotLoggedIn == true) {
            CreateAHuntBox(to: loginTooltip()) { $("big-button well")}
            LogInButton() { $("big-button well")}
            LogInUsername() { $("text", name: "login") }
            LogInPassword() { $("password", name: "password")}
            CreateAnAccountButton(to: "#signupModal") { $("button")}
            UsernameBox() { $("text", name: "login")}
            EmailBox() { $("email", name: "email")}
            PasswordBox() { $("text", name: "password")}
            ConfirmPasswordBox() { $("text", name: "passwordConfirm")}
            //CreateButton() (to UserPage)
            LogInButton("/user?.username") { $("submit", value: "Login")}
//        }
    }
}

