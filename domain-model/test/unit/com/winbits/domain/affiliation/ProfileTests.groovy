package com.winbits.domain.affiliation

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.LocalDate

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class ProfileTests {

  void testToMap() {
    def profile = new Profile(name: 'Pedro', lastName: 'Paramo', birthdate: new LocalDate())
    def result = profile.toMap()

    assert result.name == profile.name
    assert result.lastName == profile.lastName
    assert result.birthdate == profile.birthdate
  }
}
