package pages

import geb.Page

class HuntEditPage extends Page {

		
		static at = {
			title.endsWith('Edit List')
		}
		
		static content = {
			//The navbar buttons
			titleEditField(){$('input', id:'title')}
			descriptionEditField(){$('textarea', id:'description')}
			
			submitEditButton(){$('button', id: 'update-hunt')}
			cancelEditButton(){$('button', name: '_action_cancel')}
			
		}
	
}

