package scavengr

class Prompt {
    String title
    String description
    Hunt myHunt
    Date dateCreated
    boolean deleted

    static transients = ['deleted']

    static belongsTo = [myHunt:Hunt]
    static hasMany = [myPhotos:Photo]

    static constraints = {
        title blank:false, maxSize:23
        description blank:true, maxSize:250
    }

    static mapping = {
        myPhotos cascade:'save-update'
    }

    String toString() {
        return "$title"
    }
}
