package functional.pages

import geb.Page

class HuntEditPage extends Page {
    static url = "hunt/edit/huntInstance?.id"

    static content = {
        huntTitleBox() { $("text", name: "title")}
        huntDescriptionBox() { $("text", name: "description")}
        huntPromptBox(to: "../../prompt/show}" + this.id) { $("document")}
        huntPromptPhotos(to: "photoInstance") { $("thumbnail")}

        huntStartDateBox() { $("text", name: "start")}
        huntEndDateBox() { $("text", name: "end")}
        huntPrivacyBox() { $("switch", name: "isPrivate")}
        //huntStartDateClickBox()
        //huntEndDateClickBox()

        huntCreatorLink(to: huntInstance?.myCreator) { $("user")}

        huntUpdateButton(to: HuntShowPage) { $("submit")}
        huntEditCancelButton(to: HuntShowPage) { $("submit", name: "_action_cancel")}

    }
}