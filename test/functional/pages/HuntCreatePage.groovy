package functional.pages;
import geb.Page

class HuntCreatePage extends Page {
    static url = "hunt/create"

    static content = { 
        huntTitleBox() { $("text", name: "title")}
        huntDescriptionBox() { $("text", name: "description")}
        huntStartDateBox() { $("text", name: "start")}
        huntEndDateBox() { $("text", name: "end")}
        huntPrivacyBox() { $("switch", name: "isPrivate")}
        //huntStartDateClickBox()
        //huntEndDateClickBox()
        
        huntPromptTitleBox() { $("text", name: "new-prompt-title")} 
        huntPromptDescription() { $("text", name: "new-prompt-description")}
        huntAddPromptButton(to: addPrompt()) { $("input", value: "Add Prompt")}
        
        huntParticipantsEmails() { $("text", name: "new-email")}
        huntEmailInviteButton(to: addEmail()) { $("input", value: "Add Participant")} 
        
        huntCreateButton(to: HuntShowPage) { $("input")}
    }
}
