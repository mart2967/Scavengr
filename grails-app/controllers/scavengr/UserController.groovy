package scavengr

//import grails.gorm.DetachedCriteria
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
//import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.validator.GenericValidator


class UserController {

    Map codeDefaultUser = [code: 'user.label', default: 'User']
    Map actionList = [action: 'list']
    Map flushTrue = [flush: true]

    static List getPost = ['GET', 'POST']
    def NotifierService
    def authenticationService
    static allowedMethods = [create: getPost, edit: getPost, delete: 'POST']

    def randomPassword(length){
        String pass = ''
        String[] alphabet = ['a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z']
        String[] numbers = ['0','1','2','3','4','5','6','7','8','9']
        Random random = new Random()
        for (int i = 0; i < length; i++) {

            if (random.nextInt(2) == 0) {
                pass += numbers[random.nextInt(10)]
            } else {
                pass += alphabet[random.nextInt(26)]
            }
        }
        return pass
    }

    def changeEmail(){
        def userInstance = User.findByLogin(auth.user())
        if(!userInstance){
            redirect controller:'index', params:[login:true]
            return
        }
        if (params.email != params.confirmEmail){
            flash.message = 'The emails entered did not match.'
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        if(!GenericValidator.isEmail(params.email)) {
            flash.message = 'Invalid email address: ' + params.email
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        userInstance.email = params.email
        userInstance.save()
        flash.message = 'Email changed to ' + params.email
        redirect action: 'show', params: [login: auth.user()]
    }

    def resetPassword() {
        def userInstance = User.findByEmailAndLogin(params.email, params.login)
        if(!userInstance){
            redirect controller:'index', params:[login:true]
            return
        }
        if(!userInstance){
            flash.message = 'No user named ' + params.login + ' with email address ' + params.email
            redirect action:'list'
            return
        }
        def newPassword = randomPassword(8)
        userInstance.password = authenticationService.encodePassword(newPassword)
        if(!userInstance.save(flushTrue)){
            flash.message = 'Password reset failed'
            redirect action:'list'
            return
        }
        NotifierService.sendPasswordReset(userInstance.email, newPassword)
        flash.message = 'Password reset email sent to ' + userInstance.email
        redirect action:'list'
    }

    def changePassword() {
        def userInstance = User.findByLogin(auth.user())
        if (authenticationService.encodePassword(params.password) != userInstance.password){
            flash.message = 'Password change failed: entered wrong password!'
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        if (params.newPassword != params.confirmPassword){
            flash.message = 'Password change failed: passwords did not match!'
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        if (params.newPassword.size() < 6){
            flash.message = 'Password change failed: password must be at least 6 characters long.'
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        userInstance.password = authenticationService.encodePassword(params.newPassword)
        if(!userInstance.save(flushTrue)){
            flash.message = 'Password change failed: error saving new password.'
            redirect action: 'show', params: [login: auth.user()]
            return
        }
        flash.message = 'Password Changed!'
        redirect action: 'show', params: [login: auth.user()]
        return
    }

    def downloadPhotos(){
        def userInstance = User.findByLogin(auth.user())
        if(!userInstance?.myPhotos){
            flash.message = 'No photos to download!'
            redirect action: 'show', params: [login: userInstance.login]
            return
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zipFile = new ZipOutputStream(baos)
        userInstance.myPhotos.each { photo ->
            zipFile.putNextEntry(new ZipEntry(
                (photo.title ?: 'Untitled-' + photo.id) + '.' + photo.fileType.split('/')[1]))
            zipFile << photo.original
            zipFile.closeEntry()
        }

        zipFile.finish()
        response.setHeader("Content-disposition", "filename=\"${userInstance.login}-Scavengr.zip\"")
        response.contentType = "application/zip"
        response.outputStream << baos.toByteArray()
        response.outputStream.flush()
    }

    def index() {
        redirect action:'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def myProfile() {
        if (!authenticationService.isLoggedIn(request)) {
            redirect action: 'index'
        } else {
            redirect action: 'show', params: [login: auth.user()]
        }
    }

    def show() {

        def userInstance = User.findByLogin(params.login)
        def loggedInUser = User.findByLogin(auth.user())
        def isLoggedInUser = userInstance == loggedInUser
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message',
                    args: [message(codeDefaultUser), params.login])
            redirect actionList
            return
        }

        params.max = Math.min(params.max ? params.int('max') : 8, 100)
        //params.favmax = Math.min(params.favmax ? params.int('favmax') : 8, 100)
        def photoInstanceList = Photo.findAllByMyUser(
            userInstance, [sort:'dateCreated', order:'desc', max:params.max, offset:params.offset])
        def f = Photo.createCriteria()
        def favoriteInstanceList = f.list(max:params.max, offset:params.offset){
            likedBy{
                eq('login', userInstance.login)
            }
        }
        def publicCreatedHuntInstanceList = userInstance.myCreatedHunts.findAll 
        {hunt -> hunt.isPrivate == false}
        def privateCreatedHuntInstanceList = userInstance.myCreatedHunts.findAll 
        {hunt -> hunt.isPrivate == true}
        def publicAdministratedHuntInstanceList = userInstance.myAdministratedHunts.findAll 
        {hunt -> hunt.isPrivate == false}
        def privateAdministratedHuntInstanceList = userInstance.myAdministratedHunts.findAll 
        {hunt -> hunt.isPrivate == true}
        def publicHuntParticipationList = userInstance.myHunts.findAll 
        {hunt -> hunt.isPrivate == false}
        def privateHuntParticipationList = userInstance.myHunts.findAll 
        {hunt -> hunt.isPrivate == true}

        [userInstance: userInstance, photoInstanceList: photoInstanceList,
                    publicCreatedHuntInstanceList: publicCreatedHuntInstanceList,
                    privateCreatedHuntInstanceList: privateCreatedHuntInstanceList,
                    publicAdministratedHuntInstanceList:publicAdministratedHuntInstanceList,
                    privateAdministratedHuntInstanceList:privateAdministratedHuntInstanceList,
                    publicHuntParticipationList: publicHuntParticipationList,
                    privateHuntParticipationList: privateHuntParticipationList,
                    isLoggedInUser: isLoggedInUser, photoInstanceTotal: Photo.findAllByMyUser(userInstance).size(),
                    favoriteInstanceList:favoriteInstanceList, favoriteInstanceTotal:userInstance.favorites.size(),
                    myEmail:loggedInUser?.email]
    }

    def cancel() {
        def userInstance = User.get(params.id)
        redirect action: 'show', params: [login: userInstance.login]

    }

}
