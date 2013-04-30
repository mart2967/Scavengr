package scavengr

import java.util.Map

import org.springframework.dao.DataIntegrityViolationException

class ParticipantController {
    
    Map codeDefaultHunt = [code: 'participant.label', default: 'Participant']
    Map actionList = [action: 'list']
    Map flushTrue = [flush: true]

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

//    def index() {
//        redirect action: 'list', params: params
//    }
//
//    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [participantInstanceList: Participant.list(params), participantInstanceTotal: Participant.count()]
//    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[participantInstance: new Participant(params)]
			break
		case 'POST':
	        def participantInstance = new Participant(params)
	        if (!participantInstance.save(flushTrue)) {
	            render view: 'create', model: [participantInstance: participantInstance]
	            return
	        }
			flash.message = message(code: 'default.not.found.message', args: [message(codeDefaultHunt), participantInstance.id])
	        redirect controller: 'prompt', action: 'show', params: ['participantId': participantInstance.id]
			break
		}
    }

    def show() {
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(codeDefaultHunt), participantInstance.id])
            redirect actionList
            return
        }

        [participantInstance: participantInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def participantInstance = Participant.get(params.id)
	        if (!participantInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(codeDefaultHunt), participantInstance.id])
	            redirect actionList
	            return
	        }

	        [participantInstance: participantInstance]
			break
		case 'POST':
	        def participantInstance = Participant.get(params.id)
	        if (!participantInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(codeDefaultHunt), participantInstance.id])
	            redirect actionList
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (participantInstance.version > version) {
	                participantInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(codeDefaultHunt)] as Object[],
	                          "Another user has updated this Participant while you were editing")
	                render view: 'edit', model: [participantInstance: participantInstance]
	                return
	            }
	        }

	        participantInstance.properties = params

	        if (!participantInstance.save(flushTrue)) {
	            render view: 'edit', model: [participantInstance: participantInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(codeDefaultHunt), participantInstance.id])
	        redirect action: 'show', id: participantInstance.id
			break
		}
    }

    def delete() {
        def participantInstance = Participant.get(params.id)
        if (!participantInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(codeDefaultHunt), params.id])
            redirect actionList
            return
        }

        try {
            participantInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(codeDefaultHunt), params.id])
            redirect actionList
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(codeDefaultHunt), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
