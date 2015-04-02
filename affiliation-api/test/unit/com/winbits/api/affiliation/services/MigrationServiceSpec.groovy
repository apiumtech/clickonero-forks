package com.winbits.api.affiliation.services

import com.winbits.api.clients.migration.MigrationClient
import com.winbits.domain.affiliation.*
import com.winbits.domain.utils.DomainModelUtils

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

import org.joda.time.LocalDate

import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MigrationService)
@TestMixin(GrailsUnitTestMixin)
@Mock ([User,Profile, MigrationStatus, VerticalPartner, Vertical])
@Build([User, ShippingAddress, MigrationStatus, Vertical])
class MigrationServiceSpec extends Specification {

	def setup() {
    DomainModelUtils.createPersistentEnumModel (MigrationStatusEnum)
    User.metaClass.encodePassword = {}
	}

	def cleanup() {
	}
	void "should obtain an user 4 migration client"() {
  setup:
    MigrationClient migrationClient = Mock ()
    migrationClient.obtainPersonByEmail (_) >>{
      def person = [firstName:'', lastName:'', gender:'F', referrerCode:null, 
       referredById:0, birthdate: 596268000000, phone: null, location:null, idClickonero:1]
       [response:[status:200, person:person]]
    }  

    service.migrationClient = migrationClient
    User user = new User(email:'diego.zarate@clickonero.com.mx').save(validate:false)
    Profile profile = new Profile(user:user).save(validate:false)
  when:
    User userResponse = service.obtainProfile(user)
  then:
    assert userResponse
    assert userResponse.profile() 
    assert userResponse.profile().birthdate == new LocalDate (1988,11,23)
    assert userResponse.profile().gender
    assert userResponse.profile().clickoneroId == 1
	}
	
  void "should obtain an user 4 migration facebook client"() {
  setup:
    MigrationClient migrationClient = Mock ()
    migrationClient.obtainPersonByEmail (_) >>{
      def person = [firstName:'facebook', lastName:'facebook', gender:null, referrerCode:null, 
       referredById:0, birthdate: 596268000000, phone: null, location:null]
       [response:[status:200, person:person]]
    }  

    service.migrationClient = migrationClient
    User user = new User(email:'diego.zarate@clickonero.com.mx').save(validate:false)
    Profile profile = new Profile(user:user,name:'name',lastName:'lastName', gender: Gender.male).save(validate:false)
  when:
    User userResponse = service.updateFacebookProfile(user)
  then:
    assert userResponse
    assert userResponse.profile() 
    assert userResponse.profile().birthdate == new LocalDate (1988,11,23)
    assert userResponse.profile().gender == Gender.male
    assert userResponse.profile().name == 'name'
    assert userResponse.profile().lastName == 'lastName'
	}

  void "should obtain gender"() {
  when:
  def response = service.obtainGenderFromPerson(gender)
  then:
  assert response == expectedResponse
  where:
  gender        |       expectedResponse
  'M'           |        Gender.male
  'F'           |        Gender.female
  null          |        null
  }
  
  void "should obtain birthdate"() {
  when:
  def response = service.obtainLocalDateFromLong(birthdate)
  then:
  assert response == expectedResponse
  where:
  birthdate      |       expectedResponse
  596268000000  |     new LocalDate (1988,11,23)
  null          |        null
  }


  void "should obtain an shipping Address for migration"() {
    setup:
    MigrationClient migrationClient = Mock ()
    migrationClient.getShippingAddressByEmail (_) >>{
      def shippingAddress = obtainShippingAddresses()
      [response:[status:200, shippingAddress:shippingAddress]]
    }

    service.migrationClient = migrationClient
    User user = new User(email:'diego.zarate@clickonero.com.mx').save(validate:false)
    Profile profile = new Profile(user:user,name:'name',lastName:'lastName', gender: Gender.male).save(validate:false)
    when:
    Map shippingAddressResponse = service.getShippingAddressByEmail(user.email)
    then:
    assert shippingAddressResponse.size() == 2
    assert shippingAddressResponse.containsKey('shippingAddress')
    assert shippingAddressResponse.shippingAddress.size() == 1
    assert shippingAddressResponse.shippingAddress[0].firstName == 'maria'
  }

  void 'create Shipping address'() {
    setup:
    def user = User.build(email: 'test@winbits.com')
    and:'setup migration client'
      List shippingAddresses = []
      shippingAddresses << [firstName:firstName,lastName:lastName,lastName2:lastName2,phone:phone,
          location:location,zipCode:zipCode,externalNumber:externalNumber,
          internalNumber:internalNumber, street:street,
          indications:""
      ]
    and:'setup to shipping address service'
    ShippingAddressService shippingAddressService = Mock()
    shippingAddressService.createShippingAddressMigration(_, _, _) >>{
      def commonAddress =  new CommonAddress(street: street,
            internalNumber: internalNumber,
            externalNumber: externalNumber,
            phone: phone,
            zipCode: zipCode,
            location: location,
            county: 'countyTest'
        )
      
      ShippingAddress.build(commonAddress: commonAddress, firstName: firstName,
          lastName: lastName, lastName2: lastName2, indications: indications, 
          user: user)
    }
    service.shippingAddressService = shippingAddressService
    when:
    def userResp = service.createShippingAddress(user, shippingAddresses)
    then:
    assert userResp.shippingAddresses
    assert !userResp.shippingAddresses.isEmpty()
    assert userResp.shippingAddresses.first().firstName == firstName
    assert userResp.shippingAddresses.first().lastName == lastName
    assert userResp.shippingAddresses.first().lastName2 == lastName2
    assert userResp.shippingAddresses.first().commonAddress.phone == phone
    assert userResp.shippingAddresses.first().indications == indications
    assert userResp.shippingAddresses.first().county == 'countyTest'
    where:
    firstName | lastName    | lastName2 | phone       | location      | zipCode | externalNumber | internalNumber | street  | indications
    "maria"   | "rodriguez" | "medina"  |"3929241011" | "Jamay Centro"|"47900"  | "# 76"         |     null       |"ortiz " | 'sin indicaciones'
  }
  
  void "should obtainInfoUser"() {
  given:
    def migrationClient = Mock(MigrationClient)
    def person = [firstName:'', lastName:'', gender:'F', referrerCode:null, 
    referredById:0, birthdate: 596268000000, phone: null, location:null]
    List<Map> creditos = []
    creditos.add ([balance: 50, expirationDate: null, description:'bonificacion test'])
    creditos.add([balance: 100, expirationDate: 1416635998000, description:'bonificacion test 2'])
    creditos.add([balance: 279, expirationDate: 1422856798000, description:'bonificacion test 3'])
    def responseService =  [response:[status:200, clickoneroCredits:creditos]]
    migrationClient.obtainUserInfo(_) >> responseService
    service.migrationClient = migrationClient
  when:
    def response = service.obtainUserInfo('diegoazd@gmail.com')
  then:
    assert response
    assert response.status == 200
    assert response.clickoneroCredits
  }

  void "should not deposit bits"() {
  given:
    User user = new User(email:'diego.zarate@clickonero.com.mx').save(validate:false)
    BitsService bitsService = Mock()
    service.bitsService = bitsService
  when:
    service.depositClickoneroCredit(user,[])
  then:
    0 * service.bitsService.transferBitsForMigration(_, _)
  }

  private User createUser(){
    User.build(email: 'test@winbits.com')
  }

  private List obtainCredits(){
    List creditos = []
    Map credito1 = [balance: 50, expirationDate: null, description:'bonificacion test']
    creditos << credito1
    Map credito2 = [balance: 100, expirationDate: 1416635998000, description:'bonificacion test 2']
    creditos << credito2
    Map credito3 = [balance: 279, expirationDate: 1422856798000, description:'bonificacion test 3']
    creditos << credito3
    creditos
  }
  
  private List obtainShippingAddresses(){
    [[firstName:"maria",lastName:"rodriguez",lastName2:"medina",phone:"3929241011",
    location:"Jamay Centro",zipCode:"47900",externalNumber:"# 76",internalNumber:null, street:"ortiz ",
    indications:"indications"
    ]] 
  }

  void "should deposit bits"() {
  given:
    BitsService bitsService = Mock()
    service.bitsService = bitsService
    User user = createUser()
    List creditos = obtainCredits()
  when:
    service.depositClickoneroCredit(user,creditos)
  then:
    3 * service.bitsService.transferBitsForMigration(_, _)
  }

  void "should migrate clickoner user"() {
  given:
    BitsService bitsService = Mock()
    service.bitsService = bitsService
    User user = createUser()
    List creditos = obtainCredits()
    def commonAddress =  new CommonAddress(street: 'callee',
      internalNumber: '31',
      externalNumber: 'a',
      phone: '0123456789',
      zipCode: '38210',
      location: 'location')
    def shippingAddress = ShippingAddress.build(commonAddress: commonAddress, firstName: 'diego',
    lastName: 'last', lastName2: 'name', indications: 'indications', user: user)
    List shippingAdresses = [] 
    shippingAdresses << shippingAddress
    
    def migrationClient = Mock(MigrationClient)
    def responseService =  [response:[status:200, clickoneroCredits:creditos, 
        shippingAddress: obtainShippingAddresses()]]
    migrationClient.obtainUserInfo(_) >> responseService
    service.migrationClient = migrationClient
  and:
    ShippingAddressService shippingAddressService = Mock()
    service.shippingAddressService = shippingAddressService
  when:
    service.migrateClickonerUser(user)
  then:
    3 * service.bitsService.transferBitsForMigration(_, _)
    1 * service.shippingAddressService.createShippingAddressMigration(_, _, _) >> shippingAddress
  }

  @Unroll
  void "should user needs migrate"(){
  given:
    User user = User.build(email: 'foo@foo.com', salt:salt)
  when:
    boolean response = service.isUserForMigration('foo@foo.com')
  then:
    response == expectedResponse
  where:
    salt        |       expectedResponse
    ''          |         true
    '12312'     |         false
  }

  void "migrating user info" () {
  given:
    User user = User.build(email: 'foo@foo.com', salt:'123121')
    Vertical vertical = Vertical.build()
    VerticalPartner verticalPartner = new VerticalPartner(user:user, status: MigrationStatusEnum.PENDING.domain, 
        vertical: vertical)
    verticalPartner.save()
  and:
    MigrationClient migrationClient = Mock()
    migrationClient.obtainUserInfo(_) >> [response: [status: 400]]
    service.migrationClient = migrationClient
  when:
    service.migrateUserInfo(verticalPartner) 
   then:
    verticalPartner.status == MigrationStatusEnum.COMPLETE
  }

  void "user only has salt" () {
  given:
    User user = new User(salt:'1234567890').save(validate:false)

  when:
    boolean response = service.isUserOnlyWithSalt(user)
  then:
    assert !response
  }

  void "valdate false with user null" () {
  when:
    boolean response = service.isUserOnlyWithSalt(null)
  then:
   assert !response
  }


  void "valdate false with user salt void and active user" () {
  given:
    User user = new User(enabled: true, salt: '').save(validate:false)
  when:
    boolean response = service.isUserOnlyWithSalt(user)
  then:
   assert response
  }
}
