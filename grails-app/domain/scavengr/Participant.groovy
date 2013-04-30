package scavengr

class Participant {

    String name
    User myUser
    List myPhotos = []
    
    def belongsTo = [myUser:User]
   // def hasMany = {myPhotos:Photo}
    
    String toString(){
	return "$name"
    }
    
    static constraints = {
	name nullable:true, maxSize:20
	myUser nullable:true
    }
    
}
