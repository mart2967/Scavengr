import scavengr.User

class SiteFilters {
    def authenticationService
    def filters = {
        all(controller:'*', action:'*'){
            after = { model ->
                def user = authenticationService.getUserPrincipal()
                if(user != null && session.participant == null){
                    model?.loggedInUser = user
                }
                model?.currentDate = new Date()
            }
        }
    }
}
