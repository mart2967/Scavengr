package pages

import geb.Page
class HuntShowPage extends Page {
    
    static at = {
        title.endsWith('Show Hunt')
    }
    
    static content = {
        editHuntButton(to: HuntEditPage){$('a', name:'editHunt')}
		deleteButton(){$('button', name:'_action_delete')}
		creatorLink(to: UserShowPage){$('a', name: 'creatorLink')}
		
		newPromptTitleField(){$('input', id:'title')}
		newPromptDescriptionField(){$('textarea', id:'description')}
		newPromptSubmitButton(){$('button', id:'new-prompt-submit')}
		
		addUserNameField(){$('input', id:'user')}
		addUserSubmitButton(){$('button', id: 'new-participant-submit')}
		
		addAdminNameField(){$('input', id: 'admin-login')}
		addAdminSubmitButton(){$('button', id: 'new-admin-submit')}
		
		downloadPhotosButton(){$('button', name: '_action_downloadPhotos')}
    }

}
