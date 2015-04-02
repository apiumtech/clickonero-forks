package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.ProfileCommand
import com.winbits.api.clients.mis.MisClient
import com.winbits.domain.affiliation.Gender
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.User
import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ProfileService)
@Build([Profile, User])
class ProfileServiceSpec extends Specification {

  ProfileCommand command
  User user
  Profile profile

  def setup() {
    User.metaClass.encodePassword {->}
    user = User.build()
    profile = Profile.build(user: user)
    command = new ProfileCommand(name: 'Pedro', lastName: 'Paramo', birthdate: new LocalDate(), gender: Gender.male,
        zipCode: '12345', location: 'Mexico', phone: '5523456123')
    service.misClient = Mock(MisClient)
  }

  void "test change profile transfer cashback if profile is complete"() {
    setup:
    def bitsService = Mock(BitsService)
    service.bitsService = bitsService

    when:
    def (editedProfile, transformResult) = service.editProfile(user, command)

    then:
    1 * bitsService.transferBitsForCompleteRegister(user) >> [:]
    editedProfile
    editedProfile.completed
    command.name == editedProfile.name
    command.lastName == editedProfile.lastName
    command.birthdate == editedProfile.birthdate
    command.phone == editedProfile.phone
  }

  @Unroll
  void "test change profile do not transfer cashback if profile is #desc"() {
    setup:
    command.lastName = lastName
    profile.completed = completed

    when:
    def (editedProfile, transformResult) = service.editProfile(user, command)

    then:
    editedProfile
    editedProfile.completed == completed
    command.name == editedProfile.name
    editedProfile.lastName == lastName
    command.birthdate == editedProfile.birthdate
    command.phone == editedProfile.phone

    where:
    completed | lastName  | desc
    true      | 'Perez'   | 'already completed'
    false     | null      | 'not complete'
  }

  void "get bits account"() {
    setup:
    profile.bitsId = 567

    when:
    def bitsId = service.bitsAccount(user.id)

    then:
    profile.bitsId == bitsId
  }
}
