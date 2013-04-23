package scavengr

import org.springframework.dao.DataIntegrityViolationException
import scavengr.Hunt

class PromptController {

    Map codeDefaultPrompt = [code: 'prompt.label', default: 'Prompt']
    Map actionList = [action: 'list']
    Map flushTrue = [flush: true]

    static List getPost = ['GET', 'POST']

    def authenticationService
    static allowedMethods = [create: getPost, edit: getPost, delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [promptInstanceList: Prompt.list(params), promptInstanceTotal: Prompt.count()]
    }

    def create() {
        switch (request.method) {
            case 'GET':
                [promptInstance: new Prompt(params)]
                break
            case 'POST':
                def promptInstance = new Prompt(params)

                if (!promptInstance.save(flushTrue)) {
                    flash.message = 'Prompt title / description too long!'
                    redirect controller:'hunt', action: 'show', params: [key: promptInstance.myHunt.key]
                    return
                }

                flash.message = message(code: 'default.created.message',
                        args: [message(codeDefaultPrompt), promptInstance.id])
                redirect controller: 'hunt', action: 'show', params: [key: promptInstance.myHunt.key]
                break
        }
    }

    def show() {
	def userInstance = User.findByLogin(auth.user())
	def participantInstance = Participant.get(params.participantId)
        def promptInstance = Prompt.get(params.id)
//        if (!userInstance && !participantInstance) {
//        	redirect controller: 'participant', action: 'create', params: [participantId: promptInstance.myHunt.id]
//        	return
//         } 
        def now = new Date()
        def closedHunt = promptInstance.myHunt.endDate < now || promptInstance.myHunt.startDate > now
        def endDate = promptInstance.myHunt.endDate.getDateTimeString()
        def isCreatorOrAdmin = (userInstance == promptInstance.myHunt.myCreator || promptInstance.myHunt.myAdmins.contains(userInstance))
        params.max = Math.min(params.max ? params.int('max') : 8, 100)
        def photoInstanceList = Photo.findAllByMyPrompt(promptInstance, [sort:'dateCreated', order:'desc', max:params.max, offset:params.offset])


        [promptInstance: promptInstance, //photoInstance: photoInstance,
                    photoInstanceList: photoInstanceList, userInstance: userInstance,
                    photoInstanceTotal: Photo.findAllByMyPrompt(promptInstance).size(),
                    closedHunt:closedHunt, endDate:endDate, isCreatorOrAdmin:isCreatorOrAdmin, participantInstance:participantInstance]
    }

    def cancel() {
        def promptInstance = Prompt.get(params.id)
        redirect action: 'show', id: promptInstance.id

    }
    
    def removePhoto(){
        def promptInstance = Prompt.get(params.id)
        def userInstance = User.findByLogin(auth.user())
        if (userInstance != promptInstance.myHunt.myCreator && !promptInstance.myHunt.myAdmins.contains(userInstance)) {
            redirect action: 'show', id: promptInstance.id
            return
        }
        def photoInstance = Photo.get(params.photo)
        promptInstance.removeFromMyPhotos(photoInstance)
        flash.message = 'Photo removed from hunt'
        redirect action: 'edit', id: promptInstance.id, params:params
        return
    }
    def edit() {
        switch (request.method) {
            case 'GET':
                def promptInstance = Prompt.get(params.id)
                def userInstance = User.findByLogin(auth.user())
                
                if (userInstance != promptInstance.myHunt.myCreator && !promptInstance.myHunt.myAdmins.contains(userInstance)) {
                    redirect action: 'show', id: promptInstance.id
                    return
                }
                if (!promptInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultPrompt), params.id])
                    redirect actionList
                    return
                }
                params.max = Math.min(params.max ? params.int('max') : 8, 100)
                def photoInstanceList = Photo.findAllByMyPrompt(promptInstance, [max:params.max, offset:params.offset])
                
                [promptInstance: promptInstance, photoInstanceList: photoInstanceList,
                    photoInstanceTotal: Photo.findAllByMyPrompt(promptInstance).size()]
                break
            case 'POST':
                def promptInstance = Prompt.get(params.id)
                def userInstance = User.findByLogin(auth.user())
                if (userInstance != promptInstance.myHunt.myCreator && !promptInstance.myHunt.myAdmins.contains(userInstance)) {
                    redirect action: 'show', id: promptInstance.id
                    return
                }
                if (!promptInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultPrompt), params.id])
                    redirect actionList
                    return
                }

                if (params.version) {
                    def version = params.version.toLong()
                    if (promptInstance.version > version) {
                        promptInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                [message(codeDefaultPrompt)] as Object[],
                                'Another user has updated this Prompt while you were editing')
                        render view: 'edit', model: [promptInstance: promptInstance]
                        return
                    }
                }

                promptInstance.properties = params

                if (!promptInstance.save(flushTrue)) {
                    render view: 'edit', model: [promptInstance: promptInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message',
                        args: [message(codeDefaultPrompt), promptInstance.id])
                redirect action: 'show', id: promptInstance.id
                break
        }
    }

    def delete() {
        def promptInstance = Prompt.get(params.id)
        if (User.findByLogin(auth.user()) != promptInstance.myHunt.myCreator) {
            redirect action: 'show', id: promptInstance.id
            return
        }
        if (!promptInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultPrompt), params.id])
            redirect actionList
            return
        }
        def key = promptInstance.myHunt.key
        try {
            promptInstance.myPhotos.each { photo ->
                photo.myPrompt = null
            }
            promptInstance.delete(flushTrue)
            flash.message = message(code: 'default.deleted.message',
                    args: [message(codeDefaultPrompt), params.id])
            redirect controller: 'hunt', action: 'show', params:[key:key]
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message',
                    args: [message(codeDefaultPrompt), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
