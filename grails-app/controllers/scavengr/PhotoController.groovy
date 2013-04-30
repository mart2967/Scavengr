package scavengr


//import pl.burningice.plugins.image.test.FileUploadUtils
import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.web.mapping.LinkGenerator



class PhotoController {
    LinkGenerator grailsLinkGenerator
    def imageUploadService
    def burningImageService
    Map codeDefaultPhoto = [code: 'photo.label', default: 'Photo']
    Map actionList = [action: 'list']
    Map flushTrue = [flush: true]

    static List getPost = ['GET', 'POST']

    def authenticationService
    static allowedMethods = [create: getPost, edit: getPost, delete: 'POST']

//    def index() {
//        redirect action: 'list', params: params
//    }
//
//    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [photoInstanceList: Photo.list(params), photoInstanceTotal: Photo.count()]
//    }
    def create() {
        switch (request.method) {
            case 'GET':
                [photoInstance: new Photo(params)]
                break
            case 'POST':
                def photoInstance = new Photo(params)
                def huntInstance = photoInstance.myPrompt.myHunt
                def userInstance = User.findByLogin(auth.user())
                if(!userInstance) {
                    flash.message = 'You must have an account to upload to a hunt. Please log in or create an account.'
                    redirect controller: 'prompt', action: 'show', id: params.myPrompt.id
                    return
                }
                def promptInstance = Prompt.get(params.myPrompt.id)
                if(promptInstance.myHunt.bannedUsers.contains(userInstance)){
                    flash.message = 'Upload failed: you have been banned from this hunt.'
                    redirect controller: 'prompt', action: 'show', id: params.myPrompt.id
                    return
                }
                photoInstance.myPrompt = promptInstance
                photoInstance.myUser = userInstance
                def now = new Date()
                def closedHunt = huntInstance.endDate < now || huntInstance.startDate > now 
                if(closedHunt){
                    flash.message = 'Upload failed: this hunt has closed.'
                    redirect controller: 'prompt', action: 'show', id: photoInstance.myPrompt.id
                    return
                }
                
                def image = request.getFile('myFile')
                def okcontents = ['image/png', 'image/jpeg', 'image/gif']
                if (!okcontents.contains(image.getContentType())) {
                    flash.message = "Photo must be one of: ${okcontents}"
                    redirect controller: 'prompt', action: 'show', id: photoInstance.myPrompt.id
                    return
                }
                photoInstance.original = image.getBytes()
                photoInstance.fileType = image.getContentType()
                if (!photoInstance.save(flushTrue)) {
                    render controller: 'prompt', view: 'show', model: [photoInstance: photoInstance, promptInstance: photoInstance.myPrompt]
                    return
                }
                imageUploadService.save(photoInstance)
                photoInstance.save()
                if (!huntInstance.myUsers.find {user -> user == userInstance}){
                    userInstance.addToMyHunts(huntInstance)
                    huntInstance.removeFromEmails(userInstance.login)
                    huntInstance.removeFromEmails(userInstance.email)
                    userInstance.save()
                }

                flash.message = message(code: 'scavengr.Photo.created.message',
                        args: [message(codeDefaultPhoto), photoInstance.id])
                redirect action: 'show', controller: 'prompt', id: params.myPrompt.id
                break
        }
    }
    
    def toggleFavorite(){
        def photoInstance = Photo.get(params.id)
        def userInstance = User.findByLogin(auth.user())
        if(!userInstance){
            return
        }
        if(userInstance.favorites.contains(photoInstance)){
            userInstance.removeFromFavorites(photoInstance)
            render '<i class="icon icon-star"></i> Favorite'
        }else{
            userInstance.addToFavorites(photoInstance)
            render '<i class="icon icon-star-empty"></i> Unfavorite'
        }
        
    }
    
    def show() {
	//def participantInstance = Participant.get(params.participantId)
        def photoInstance = Photo.get(params.id)
        def showHunt = false
        def isMyPhoto = false
        def userInstance = User.findByLogin(auth.user())
        def key
        if (authenticationService.isLoggedIn(request) && userInstance == photoInstance.myUser) {
            isMyPhoto = true
        }
        if (!photoInstance.myPrompt?.myHunt?.isPrivate) {
            showHunt = true
            key = photoInstance.myPrompt?.myHunt?.key
        }
        if (!photoInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultPhoto), params.id])
            redirect actionList
            return
        }
	
        def photoIdList = Photo.executeQuery("select p.id from Photo p where p.myUser = ?",
            [photoInstance.myUser],[order:'desc']).reverse()
        def index = photoIdList.indexOf(params.long('id'))
        def prevId
        def nextId
        if (index != 0){
            prevId = photoIdList.get(index - 1)
        }
        if (index != photoIdList.size()-1){
            nextId = photoIdList.get(index + 1)
        }
        def isFavorite = userInstance.favorites.contains(photoInstance)
        photoInstance.views++
        [isFavorite:isFavorite, photoInstance: photoInstance, isMyPhoto: isMyPhoto, showHunt: showHunt, key: key,
	    //,participantInstance:participantInstance
            prevId: prevId, nextId: nextId]
    }

    def viewImage() {
        def photoInstance = Photo.get( params.id )
        switch(params.size){
            case 'large':
                response.outputStream << photoInstance.large
                break
            case 'medium':
                response.outputStream << photoInstance.medium
                break
            case 'thumbnail':
                response.outputStream << photoInstance.thumbnail
                break
            default:
                response.outputStream << photoInstance.original
                break
        }
        response.outputStream.flush()
    }

    def cancel() {
        def photoInstance = Photo.get( params.id )
        redirect action: 'show', id: photoInstance.id
    }

    def edit() {
        switch (request.method) {
            case 'GET':
                def photoInstance = Photo.get(params.id)
                if (User.findByLogin(auth.user()) != photoInstance.myUser) {
                    redirect action: 'show', id: photoInstance.id
                    return
                }
                if (!photoInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultPhoto), params.id])
                    redirect actionList
                    return
                }

                [photoInstance: photoInstance]
                break
            case 'POST':
                def photoInstance = Photo.get(params.id)
                if (User.findByLogin(auth.user()) != photoInstance.myUser) {
                    redirect action: 'show', id: photoInstance.id
                    return
                }
                if (!photoInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultPhoto), params.id])
                    redirect actionList
                    return
                }


                if (params.version) {
                    def version = params.version.toLong()
                    if (photoInstance.version > version) {
                        photoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                [message(codeDefaultPhoto)] as Object[],
                                'Another user has updated this Photo while you were editing')
                        render view: 'edit', model: [photoInstance: photoInstance]
                        return
                    }
                }
                photoInstance.properties = params
                if (!photoInstance.save(flushTrue)) {
                    render view: 'edit', model: [photoInstance: photoInstance]
                    return
                }

                flash.message = message(code: 'scavengr.Photo.updated.message',
                        args: [message(codeDefaultPhoto), photoInstance.id])
                redirect action: 'show', id: photoInstance.id
                break
        }
    }

    def delete() {
        def photoInstance = Photo.get(params.id)
        if (User.findByLogin(auth.user()) != photoInstance.myUser) {
            redirect action: 'show', id: photoInstance.id
            return
        }
        if (!photoInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultPhoto), params.id])
            redirect actionList
            return
        }

        try {
            photoInstance.delete(flushTrue)
            flash.message = message(code: 'default.deleted.message',
                    args: [message(codeDefaultPhoto), params.id])
            redirect controller: 'User', action: 'myProfile'
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message',
                    args: [message(codeDefaultPhoto), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
