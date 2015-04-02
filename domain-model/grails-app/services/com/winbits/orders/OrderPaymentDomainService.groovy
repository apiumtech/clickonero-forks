package com.winbits.orders
 
import com.winbits.domain.orders.OrderPayment
import com.winbits.domain.orders.OrderPaymentStatus
import com.winbits.domain.orders.OrderPaymentStatusEnum
 
import org.joda.time.DateTime
import grails.converters.JSON

class OrderPaymentDomainService {
    static transactional = false

    OrderPayment changeOrderPaymentStatus(OrderPayment orderPayment, OrderPaymentStatus status) {
        orderPayment.status = status
        orderPayment.save()
    }

    OrderPayment changeOrderPaymentStatusAndPaidDateAndReference(DateTime paidDate, String hsbcLine, OrderPayment orderPayment){
        orderPayment.status = OrderPaymentStatusEnum.PAID.domain    
        orderPayment.paidDate = paidDate
        orderPayment.paymentCapture = addLineCaptureToPaymentCapture(orderPayment.paymentCapture,
                hsbcLine)
        orderPayment.save(flush:true)
    }

    String addLineCaptureToPaymentCapture(String paymentCapture, String hsbcCaptureLine){
        Map json =  JSON.parse(paymentCapture?:"{}")
        json.hsbcPayment = hsbcCaptureLine
        json.encodeAsJSON()
    }
}
