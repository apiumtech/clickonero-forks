package com.winbits.domain.catalog

import com.winbits.domain.utils.DomainModelUtils
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ItemStatus)
class ItemStatusSpec extends Specification {

  @Shared
  def itemStatuses

  def setup() {
    DomainModelUtils.createPersistentEnumModel(ItemStatusEnum)
    itemStatuses = ItemStatus.list()
  }

  @Unroll('get domain from enum: #enumConstant')
  def 'get domain from enum constant'() {
    when: 'call getDomain() on enum constant'
    def domain = enumConstant.domain

    then: 'must return a domainInstance instance with same id'
    domain instanceof ItemStatus
    domain.id == enumConstant.id

    where:
    enumConstant << ItemStatusEnum.values()
  }

  @Unroll('get enum constant from domain: #domainInstance')
  def 'get enum constant from domain'() {
    when: 'call getEnum() on enumable domain instance'
    def enumConstant = domainInstance.enum

    then: 'must return the corresponding enum constant'
    enumConstant instanceof ItemStatusEnum
    enumConstant.id == domainInstance.id

    where:
    domainInstance << itemStatuses
  }

  @Unroll('enum equality: #enumConstant == #domainInstance')
  def 'enum equality between enum constant & domain'() {
    expect: 'must be equal'
    enumConstant == domainInstance.enum

    where:
    enumConstant << ItemStatusEnum.values()
    domainInstance << itemStatuses
  }

  @Unroll('domain equality only works with id: #domainInstance == #enumConstant')
  def 'domain equality between enum constant & domain only works with id'() {
    expect: 'must be equal'
    enumConstant.domain instanceof ItemStatus
    enumConstant.domain.id == domainInstance.id

    where:
    enumConstant << ItemStatusEnum.values()
    domainInstance << itemStatuses
  }

  @Unroll('enum equality with loaded domain using enum: #enumConstant')
  def 'enum equality with loaded domain'() {
    expect: 'must be equal'
    ItemStatus.load(enumConstant.id).enum == enumConstant
    where:
    enumConstant << ItemStatusEnum.values()
  }

  @Unroll('getting enum from domain loaded by enum: #enumConstant')
  def 'getting enum from domain loaded by enum'() {
    expect: 'must be equal'
    enumConstant.domain.enum == enumConstant

    where:
    enumConstant << ItemStatusEnum.values()
  }

  @Unroll('equality from domain: #domainInstance == #enumConstant')
  def 'equality from domain'() {
    expect: 'must be equal'
    domainInstance == enumConstant

    where:
    enumConstant << ItemStatusEnum.values()
    domainInstance << itemStatuses
  }
}
