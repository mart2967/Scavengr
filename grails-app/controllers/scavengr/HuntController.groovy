package scavengr

import java.util.zip.ZipEntry
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import java.util.zip.ZipOutputStream
import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.validator.GenericValidator

class HuntController {

    Map codeDefaultHunt = [code: 'hunt.label', default: 'Hunt']
    Map actionList = [action: 'list']
    Map flushTrue = [flush: true]
    LinkGenerator grailsLinkGenerator
    static List getPost = ['GET', 'POST']
    def NotifierService
    def authenticationService
    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def huntInstanceList = Hunt.findAllByIsPrivate(false, [max:params.max, offset:params.offset, sort:params.sort, order:params.order])
        [huntInstanceList: huntInstanceList, huntInstanceTotal: Hunt.findAllByIsPrivate(false).size()]

    }

    def downloadPhotos(){
        def huntInstance = Hunt.get(params.id)
        def p = 0
        huntInstance.myPrompts.each {prompt ->
            prompt.myPhotos.each {photo ->
                p++
            }
        }
        if(p == 0) {
            flash.message = 'No photos to download!'
            redirect action: 'show', params: [key: huntInstance.key]
            return
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zipFile = new ZipOutputStream(baos)
        huntInstance.myPrompts.each { prompt ->
            prompt.myPhotos.each { photo ->
                zipFile.putNextEntry(new ZipEntry((photo.title ? photo.title + '-' + photo.id : 'Untitled-' + photo.id) + '.' + photo.fileType.split('/')[1]))
                zipFile << photo.original
                zipFile.closeEntry()
            }
        }

        zipFile.finish()
        response.setHeader("Content-disposition", "filename=\"${huntInstance.title}.zip\"")
        response.contentType = "application/zip"
        response.outputStream << baos.toByteArray()
        response.outputStream.flush()
    }

    def create() {
        switch (request.method) {
            case 'GET':
                if (!authenticationService.isLoggedIn(request)) {
                    redirect controller: 'index', action: 'index', params:[login:true]
                }
                [huntInstance: new Hunt(params), emails: [], prompts:[] ]
                break
            case 'POST':
                def dateParser = new java.text.SimpleDateFormat('MM/dd/yyyy hh:mm a')

                def huntInstance = new Hunt(params)
                def creator = User.findByLogin(auth.user())
                huntInstance.myCreator = creator

                def emailsToBeDeleted = []
                for (e in huntInstance.emails){
                    if (e == ''){
                        emailsToBeDeleted.add(e)
                    }
                }
                huntInstance.emails.removeAll(emailsToBeDeleted)

                def promptsToBeDeleted = huntInstance.myPrompts.findAll {it.deleted}
                if (promptsToBeDeleted) {
                    huntInstance.myPrompts.removeAll(promptsToBeDeleted)
                }


                def emails = huntInstance.emails as grails.converters.JSON
                def prompts = huntInstance.myPrompts as grails.converters.JSON
                try{
                    huntInstance.startDate = dateParser.parse(params.start)
                    huntInstance.endDate = dateParser.parse(params.end)
                } catch(java.text.ParseException e){
                    flash.message = 'Invalid date(s)'
                    render view: 'create', model: [huntInstance: huntInstance, emails: emails, prompts:prompts]
                    return
                }
                def emailArray = huntInstance.emails.toArray()


                for(email in emailArray){
                    if(!GenericValidator.isEmail(email)) {
                        flash.message = 'Invalid email address: ' + email
                        render view: 'create', model: [huntInstance: huntInstance, emails: emails, prompts:prompts]
                        return
                    }
                }
                creator.addToMyCreatedHunts(huntInstance)

                if (!creator.save(flushTrue)) {

                    render view: 'create', model: [huntInstance: huntInstance, emails: emails, prompts:prompts]
                    return
                }



                flash.message = message(code: 'scavengr.Hunt.created.label',
                        args: [message(codeDefaultHunt), huntInstance.id])
                if(emailArray.size() > 0){
                    NotifierService.contactHunters(creator, emailArray, huntInstance.key, huntInstance.title)
                }
                if (GenericValidator.isEmail(creator.email)) {
                    NotifierService.contactCreator(creator.login, creator.email, huntInstance.key, huntInstance.title)
                } else {
                    flash.message = message(code: 'scavengr.invalid.email.message')
                }


                redirect action: 'show', params: [key:huntInstance.key]
                break
        }
    }

    def invite() {
        def huntInstance = Hunt.get(params.id)
        def user = params.user.toString()
        if(huntInstance.emails.contains(user)){
            flash.message = user + ' has already been invited.'
            redirect controller: 'hunt', action: 'show', params: [key: huntInstance.key]
            return
        }
        def link = grailsLinkGenerator.link( controller: 'hunt', action: 'show', params: [key: huntInstance.key])
        if(GenericValidator.isEmail(user)){
            def userInstance = User.findByEmail(user)
            if(userInstance != null){
                
                NotifierService.sendNotification(huntInstance.myCreator, userInstance, 'Hunt Invitation',
                    "You have been invited to participate in the hunt \"$huntInstance.title\"", link, "Go to Hunt")
            }
            NotifierService.contactHunters(huntInstance.myCreator.login, user, huntInstance.key, huntInstance.title)
        }else{
            def userInstance = User.findByLogin(user)
            if(userInstance != null){
                NotifierService.sendNotification(huntInstance.myCreator, userInstance, 'Hunt Invitation',
                    "You have been invited to participate in the hunt \"$huntInstance.title\"", link, "Go to Hunt")
                
                NotifierService.contactHunters(huntInstance.myCreator.login, userInstance.email, huntInstance.key, huntInstance.title)
            }else{
                flash.message = 'User not found with email or username: ' + user
                redirect controller: 'hunt', action: 'show', params: [key: huntInstance.key]
                return
            }
        }
        huntInstance.addToEmails(user)
        flash.message = 'Invite sent to ' + user
        redirect controller: 'hunt', action: 'show', params: [key: huntInstance.key]
    }

    def inviteAdmin() {
        def huntInstance = Hunt.get(params.id)
        def newAdmin = User.findByLogin(params.login)
        def currentUser = User.findByLogin(auth.user())
        if(!newAdmin){
            flash.message = 'User not found with name ' + params.login
            redirect action: 'show', params:['key':huntInstance.key]
            return
        }
        if (newAdmin == currentUser){
            flash.message = 'You already have administrative powers!'
            redirect action: 'show', params:['key':huntInstance.key]
            return
        }
        if (!huntInstance.myAdmins.find {admin -> admin == newAdmin}){
            def link = grailsLinkGenerator.link( controller: 'hunt', action: 'show', params: [key: huntInstance.key])
            newAdmin.addToMyAdministratedHunts(huntInstance)
            NotifierService.sendNotification(currentUser, newAdmin,
                'You Are Now an Admin', "You have been made administrator of the hunt \"$huntInstance.title\"", link, "Go to Hunt")
            newAdmin.save()
            flash.message = 'Admin added: ' + newAdmin.login
            redirect action: 'show', params:['key':huntInstance.key]
            return
        }
        flash.message = newAdmin.login + ' is already an admin!'
        redirect action: 'show', params:['key':huntInstance.key]
    }

    def removeAdmin(){
        def huntInstance = Hunt.get(params.myHunt.id)
        def userInstance = User.findByLogin(auth.user())
        if(userInstance != huntInstance.myCreator){
            redirect action: 'show', params:['key':huntInstance.key]
            return
        }
        def admin = User.findByLogin(params.login)
        if(!admin || !huntInstance.myAdmins.contains(admin) ){
            flash.message = admin.login + ' is not an admin of this hunt.'
            redirect action: 'show', params:['key':huntInstance.key]
            return
        }
        huntInstance.removeFromMyAdmins(admin)
        NotifierService.sendNotification(userInstance, admin,
            "No Longer Admin", "You are no longer an administrator of the hunt \"$huntInstance.title\"")
        flash.message = admin.login + ' is no longer an admin'
        redirect action: 'show', params:['key':huntInstance.key]
        
    }
    
    def removeUser(){
        def huntInstance = Hunt.get(params.myHunt.id)
        def userInstance = User.findByLogin(auth.user())
        if (userInstance != huntInstance.myCreator && !huntInstance.myAdmins.contains(userInstance)){
            redirect action: 'show', params: [key:huntInstance.key]
            return
        }
        def userToRemove = User.findByLogin(params.login)
        if (userToRemove == huntInstance.myCreator || huntInstance.myAdmins.contains(userToRemove)){
            flash.message = 'Requested user was an admin, please revoke their power first.'
            redirect action: 'show', params: [key:huntInstance.key]
            return
        }
        huntInstance.myPrompts.each{prompt ->
            def photosToRemove = userToRemove.myPhotos.findAll {photo -> photo.myPrompt == prompt }
            photosToRemove.each {photo ->
                prompt.removeFromMyPhotos(photo)
            }
        }
        huntInstance.removeFromMyUsers(userToRemove)
        huntInstance.addToBannedUsers(userToRemove)
        NotifierService.sendNotification(userInstance, userToRemove,
            "Banned From Hunt", "You have been banned from uploading to the hunt \"$huntInstance.title\"")
        flash.message = userToRemove.login + ' and their photos have been removed from the hunt.'
        redirect action: 'show', params:['key':huntInstance.key]
    }
    
    def show() {
        def huntInstance = Hunt.findByKey(params.key)
        def userInstance = User.findByLogin(auth.user())
        if (!huntInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultHunt), params.key])
            redirect actionList
            return
        }

        def promptInstanceList = Prompt.findAllByMyHunt(huntInstance,[sort:'dateCreated', order:'asc'])
        def promptPhotoList = new ArrayList()
        for (promptInstance in promptInstanceList) {
            def userPhotoList = Photo.findAllByMyUserAndMyPrompt(userInstance, promptInstance,[sort:'dateCreated', order:'desc', max:6])
            def photoInstanceList = Photo.findAllByMyUserNotEqualAndMyPrompt(userInstance, promptInstance,[sort:'dateCreated', order:'desc', max:6-userPhotoList.size()])
            def promptFilled = userPhotoList.size() > 0
            def promptPhotoContainer = new ArrayList()
            promptPhotoContainer.add(promptInstance)
            promptPhotoContainer.add(promptFilled)
            promptPhotoContainer.addAll(userPhotoList)
            promptPhotoContainer.addAll(photoInstanceList)
            promptPhotoList.add(promptPhotoContainer)

        }
        def userLoginList = User.executeQuery("select u.login from User u")
        def isCreatorOrAdmin = (userInstance == huntInstance.myCreator || huntInstance.myAdmins.contains(userInstance))
        def isParticipating = huntInstance.myUsers.contains(userInstance)
        [huntInstance: huntInstance, userInstance:userInstance, promptInstanceList: promptInstanceList,
                    promptPhotoList: promptPhotoList, userInstance:userInstance, 
                    isCreatorOrAdmin:isCreatorOrAdmin, userLoginList:userLoginList as grails.converters.JSON,
                    now: new Date(), isParticipating:isParticipating ]
    }

    def closeHunt() {
        def huntInstance = Hunt.get(params.id)
        def userInstance = User.findByLogin(auth.user())
        if (userInstance != huntInstance.myCreator && !huntInstance.myAdmins.contains(userInstance)){
            redirect action: 'show', params: [key:huntInstance.key]
            return
        }
        huntInstance.endDate = new Date()
        flash.message = 'Hunt closed. To reopen, edit the end date.'
        redirect action: 'show', params: [key:huntInstance.key]
    }
    
    def cancel() {
        def huntInstance = Hunt.get(params.id)
        redirect action: 'show', params: [key:huntInstance.key]
    }

    def edit() {
        def dateParser = new java.text.SimpleDateFormat('MM/dd/yyyy hh:mm a')
        switch (request.method) {
            case 'GET':
                def huntInstance = Hunt.get(params.id)
                def userInstance = User.findByLogin(auth.user())
                if (!huntInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultHunt), params.id])
                    redirect actionList
                    return
                }
                params.start = dateParser.format(huntInstance.startDate)
                params.end = dateParser.format(huntInstance.endDate)
                if (userInstance != huntInstance.myCreator && !huntInstance.myAdmins.contains(userInstance)) {
                    redirect action: 'show', params:[key: huntInstance.key]
                    return
                }
                

                def promptInstanceList = Prompt.findAllByMyHunt(huntInstance)
                def promptPhotoList = new ArrayList()
                for (promptInstance in promptInstanceList) {
                    def userPhotoList = Photo.findAllByMyUserAndMyPrompt(userInstance, promptInstance)
                    def photoInstanceList = promptInstance ? Photo.findAllByMyPrompt(promptInstance,[max:6]) : []
                    def promptFilled = userPhotoList.size() > 0
                    def promptPhotoContainer = new ArrayList()
                    promptPhotoContainer.add(promptInstance)
                    promptPhotoContainer.add(promptFilled)
                    promptPhotoContainer.addAll(photoInstanceList)
                    promptPhotoList.add(promptPhotoContainer)

                }
                def isCreatorOrAdmin = (userInstance == huntInstance.myCreator || huntInstance.myAdmins.contains(userInstance))
                [huntInstance: huntInstance, userInstance:userInstance, promptInstanceList: promptInstanceList,
                            promptPhotoList: promptPhotoList, userInstance:userInstance, isCreatorOrAdmin:isCreatorOrAdmin ]


                break
            case 'POST':
                def huntInstance = Hunt.get(params.id)
                def userInstance = User.findByLogin(auth.user())
                if (userInstance != huntInstance.myCreator && !huntInstance.myAdmins.contains(userInstance)) {
                    redirect action: 'show', params:[key: huntInstance.key]
                    return
                }
                if (!huntInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(codeDefaultHunt), params.key])
                    redirect actionList
                    return
                }

                if (params.version) {
                    def version = params.version.toLong()
                    if (huntInstance.version > version) {
                        huntInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                [message(codeDefaultHunt)] as Object[],
                                'Another user has updated this Hunt while you were editing')
                        render view: 'edit', model: [huntInstance: huntInstance]
                        return
                    }
                }

                huntInstance.properties = params
                try{
                    huntInstance.startDate = dateParser.parse(params.start)
                    huntInstance.endDate = dateParser.parse(params.end)
                } catch(java.text.ParseException e){
                    flash.message = 'Invalid date(s)'
                    redirect action: 'edit', params:params
                    return
                }

                if (!huntInstance.save(flushTrue)) {
                    redirect action: 'edit', params:params
                    return
                }

                flash.message = 'Hunt: ' + huntInstance.title + ' updated'
                redirect action: 'show', params: [key: huntInstance.key]
                break
        }
    }

    def delete() {
        def huntInstance = Hunt.get(params.id)
        if (User.findByLogin(auth.user()) != huntInstance.myCreator) {
            redirect action: 'show', params:[key: huntInstance.key]
            return
        }
        if (!huntInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultHunt), params.id])
            redirect actionList
            return
        }

        try {
            huntInstance.myAdmins.clear()
            huntInstance.myUsers.clear()
            huntInstance.delete(flushTrue)
            flash.message = message(code: 'default.deleted.message',
                    args: [message(codeDefaultHunt), params.id])
            redirect actionList
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message',
                    args: [message(codeDefaultHunt), params.id])
            redirect action: 'show', params: [key: huntInstance.key]
        }
    }
}
