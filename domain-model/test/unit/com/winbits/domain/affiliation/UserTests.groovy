package com.winbits.domain.affiliation

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.junit.Before

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
@Mock([Vertical, User, Profile])
class UserTests {

  @Before
  void setup() {
    User.metaClass.encodePassword = {->}
  }

  void testConfirmUrl() {
    def vertical = Vertical.findOrSaveWhere(baseUrl: 'http://www.clickonero.com', name: 'clickOnero')
    def user = new User(salt: 'aetrer', vertical: vertical)
    user.grailsApplication = grailsApplication
    def confirmUrl = user.confirmUrl()

    assert confirmUrl
    assert confirmUrl.contains(user.salt)
  }

  void testProfileMap() {
    def user = new User().save(false)
    assert !user.profileMap()

    def profile = new Profile(name: 'Ana', user: user).save(false)
    assert profile.toMap() == user.profileMap()
  }
}
