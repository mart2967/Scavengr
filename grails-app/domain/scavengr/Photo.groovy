package scavengr
import pl.burningice.plugins.image.ast.FileImageContainer
import pl.burningice.plugins.image.engines.scale.ScaleType

@FileImageContainer(field = 'myFile')
class Photo {
    byte[] original
//    byte[] large // 870x*
//    byte[] medium //300x300
//    byte[] thumbnail //100x100
    String fileType
    String title
    String description
    User myUser
    //Participant myParticipants
    Prompt myPrompt
    Date dateCreated
    static belongsTo = [myUser:User, myPrompt:Prompt] //,myParticipants:Participant]

    static constraints = {

        title blank:true, maxSize:22
        description blank:true, maxSize:250
        myUser nullable:true
        original nullable:false, maxSize:5000000
        myPrompt nullable:true
        original sqlType: 'blob'
    }
    
    
    

    String toString() {
        return "$title"
    }
}
