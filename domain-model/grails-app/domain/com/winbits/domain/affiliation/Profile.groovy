package com.winbits.domain.affiliation

import com.winbits.domain.catalog.ZipCodeInfo
import org.joda.time.DateTime
import org.joda.time.LocalDate

class Profile {
  String name
  String lastName
  LocalDate birthdate
  Gender gender
  String zipCode
  String location
  String phone
  Long bitsId
  Locale locale
  Long clickoneroId

  NewsletterFormat newsletterFormat = NewsletterFormat.defaultFormat()
  NewsletterPeriodicity newsletterPeriodicity = NewsletterPeriodicity.defaultPeriodicity()

  DateTime dateCreated
  DateTime lastUpdated
  Boolean newsletterActive = true
  Boolean completed = false

  ZipCodeInfo zipCodeInfo

  static belongsTo = [user: User]

  static hasMany = [subscriptions: Subscription]

  static transients = ['fullName', 'profileComplete']

  static constraints = {
    name nullable: true, maxSize: 75
    lastName nullable: true, maxSize: 75
    birthdate nullable: true
    gender nullable: true
    zipCode nullable: true, maxSize: 5
    location nullable: true, maxSize: 75
    phone nullable: true
    user unique: true
    newsletterActive defaultValue: 'true'
    zipCodeInfo nullable: true
    clickoneroId nullable: true
  }

  static mapping = {
    subscriptions cascade: "all-delete-orphan"
  }

  Map toMap() {
    [name: name, lastName: lastName, birthdate: birthdate, gender: gender]
  }

  String getFullName() {
    "$name $lastName"
  }

  Boolean isProfileComplete() {
    ['name', 'lastName', 'gender', 'birthdate', 'zipCode', 'location'].every { this."$it"?.toString()?.trim() }
  }
}
