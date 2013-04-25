package pages

import geb.Page
class CreateAHuntPage extends Page{
        static uri = "hunt/create"
        
        static at = {
            title.endsWith("Create Hunt")
        }
        
        static content = {
            
        }
}
