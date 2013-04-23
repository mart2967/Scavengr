package scavengr

class IndexController {
    def authenticationService
    def index() { 
        def userInstance = User.findByLogin(auth.user())
       
        if(flash.loginFormErrors || flash.authenticationFailure){
            params.login = true
        }
        if(flash.signupFormErrors){
            params.signup = true
        }
        render view:'index'
    }
    
}
