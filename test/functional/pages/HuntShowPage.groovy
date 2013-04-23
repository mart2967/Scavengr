package functional.pages

import geb.Page

class HuntShowPage extends Page{
    static url = "hunt/huntInstance?.id"

    static content = {
        huntPromptBox(to: "../../prompt/show}" + this.id) { $("document")}
        huntPromptPhotos(to: "photoInstance") { $("thumbnail")}

        huntCreatorLink(to: huntInstance?.myCreator) { $("user")}

        huntEditButton(to: HuntEditPage) { $("submit")}
        huntDeleteButton(to: HuntListPage) { $("submit", name: "_action_delete")}
        
        huntPromptTitleBox() { $("text", name: "new-prompt-title")}
        huntPromptDescription() { $("text", name: "new-prompt-description")}
        huntAddPromptButton(to: addPrompt()) { $("input", value: "Add Prompt")}
    }
}
