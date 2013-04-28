package pages

import geb.Page
class CreateAHuntPage extends Page{
    static uri = "hunt/create"

    static at = {
        title.endsWith("Create Hunt")
    }

    static content = {
        promptTitleBox(){$('input', id:"promptTitle" ) }
        promptDescriptionBox() {$('textarea', id:"promptDescription") }
        addPromptButton(){ $('button', id:"addPromptButton") }
        removePromptButton() {$('button', id:"remove0") }

    }
}
