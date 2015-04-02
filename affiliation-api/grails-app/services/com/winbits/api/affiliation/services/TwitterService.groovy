package com.winbits.api.affiliation.services

import org.springframework.social.twitter.api.Twitter

class TwitterService {

  Twitter twitter

  def publishStatus(String message) {
     twitter.timelineOperations().updateStatus(message)
  }
}
