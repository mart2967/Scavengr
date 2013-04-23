package scavengr

import grails.test.mixin.TestFor
//import scavengr.User
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

	@Override
	def setup() {
	}

	@Override
	def cleanup() {
	}
	
	def "testUserConstraints"() {
		when:
		def user1 = new User(login: 'testUser1', email: 'testUser1@email.com', password: 'testUserPassword1')
		def user2 = new User(login: 'testUser2', email: '', password: 'testUserPassword2')
		def user3 = new User(login: '', email: 'testUser3@email.com', password: 'testUserPassword3')
		def user4 = new User(login: '', email: '', password: 'testUserPassword4')
		def user5 = new User(login: 'testUser5', email: 'testUserEmail5', password: 'testUserPassword5')
		def user6 = new User(login: 'testUser6', email: '', password: 'testUserPassword6')
		def user7 = new User(login: '', email: 'testUserEmail7', password: 'testUserPassword7')
		def user8 = new User(login: '', email: '', password: 'testUserPassword8')
		mockForConstraintsTests(User, [user1, user2, user3, user4, user5, user6, user7, user8])
		
		then:
		user1.validate()
		!user2.validate()
		!user3.validate()
		!user4.validate()
		!user5.validate()
		!user6.validate()
		!user7.validate()
		!user8.validate()
	}
	
	def "testToString"() {
		when:
		def user9 = new User(login: 'testUser9', email: 'testUser9@email.com', password: 'testUserPassword9')
		def user10 = new User(login: 'testUser10', email: '', password: 'testUserPassword10')
		
		then:
		user9.toString() == 'testUser9'
		user10.toString() == 'testUser10'
	}
}