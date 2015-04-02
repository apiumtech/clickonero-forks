package domain.model

import com.winbits.domain.orders.Order
import groovy.util.slurpersupport.NodeChildren

class TestOrderMailController {

  def generateOrderMailService
    def index() {
      Map emailData = generateOrderMailService.generateOrderMail([orderId: 1])
      emailData.parameters = new XmlSlurper().parseText(emailData.xml)
      render(view: '/email/orderConfirmation', model: emailData)
    }

}
