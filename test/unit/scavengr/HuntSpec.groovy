package scavengr

import grails.test.mixin.TestFor
//import scavengr.Hunt
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Hunt)
class HuntSpec extends Specification {

	@Override
	def setup() {
	}

	@Override
	def cleanup() {
	}

	def "testHuntConstraints"() {
		when:
		def baseDate = new Date()
		def lateDate = new Date(baseDate.time + 1000*60*60*24)
		
		def hunt1 = new Hunt(title: 'testHunt1', description: 'testHuntDescription1', myCreator: new User(), 
			isPrivate: true, startDate: new Date(), endDate: lateDate, key:'6a0p0345ru', huntersEmail:'anEmail@email.fu')
		def hunt2 = new Hunt(title: 'testHunt2', description: 'testHuntDescription2', myCreator: new User(), 
			isPrivate: true, startDate: new Date(), endDate: lateDate, key:'aq831yf182', huntersEmail:'anEmail@email.fu')
		def hunt3 = new Hunt(title: 'testHunt3', description: '', myCreator: new User(), 
			isPrivate: true, startDate: new Date(), endDate: lateDate, key:'yk59is341b', huntersEmail:'anEmail@email.fu')
		def hunt4 = new Hunt(title: '', description: 'testHuntDescription4', myCreator: new User(), 
			isPrivate: true, startDate: new Date(), endDate: lateDate, key:'0q9z9r579t', huntersEmail:'anEmail@email.fu')
		def hunt5 = new Hunt(title: '', description: '', myCreator: new User(), 
			isPrivate: true, startDate: new Date(), endDate: lateDate, key:'610vh80w66', huntersEmail:'anemail@email.fu')
		mockForConstraintsTests(Hunt, [hunt1, hunt2, hunt3, hunt4, hunt5])
		
		then:
		hunt1.validate()
		hunt2.validate()
		hunt3.validate()
		!hunt4.validate()
		!hunt5.validate()
	}
	
	def "testToString"() {
		when:
		def hunt6 = new Hunt(title: 'testHunt6', description: 'testHuntDescription6', 
			myCreator: new User(), isPrivate: true, startDate: new Date(), endDate: new Date())
		def hunt7 = new Hunt(title: 'testHunt7', description: 'testHuntDescription7', 
			myCreator: new User(), isPrivate: true, startDate: new Date(), endDate: new Date())
		
		then:
		hunt6.toString() == 'testHunt6'
		hunt7.toString() == 'testHunt7'
	}
}