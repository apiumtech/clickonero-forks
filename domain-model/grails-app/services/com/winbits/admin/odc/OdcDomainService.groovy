package com.winbits.admin.odc
 
import com.winbits.domain.logistics.IncomeTypeEnum
import com.winbits.domain.logistics.Odc
import com.winbits.domain.logistics.OdcPaymentStatusEnum
import com.winbits.domain.logistics.OdcStatusEnum
import com.winbits.domain.logistics.OdcTypeEnum

class OdcDomainService {

    def createNewOdc( cmd, Map c = null, Closure detail) {
      def odc = new Odc(
           provider          : cmd.supplierId,
           type              : OdcTypeEnum.ODC.domain,
           status            : OdcStatusEnum.PENDING.domain,
           warehouse         : cmd.warehouseId,
           paymentStatus     : OdcPaymentStatusEnum.PENDING.domain,
           deliveryDate      : cmd?.deliveryDate?.toDateTimeAtStartOfDay()?.toLocalDateTime(),
           paymentConditions : cmd.paymentConditions,
           incomeType        : IncomeTypeEnum.FIRM.domain
      )


      odc.properties = c


      if (odc.validate()) {
        odc.save()
      } else {
        log.debug "ERRORRES ${odc.errors}  VALIDATE ${odc.validate()}"
      }
      
      def odcDetails = detail.call()
      odcDetails.each {
        it.odc = odc
        odc.addToOdcDetails(it)
      }
      
      if (!odcDetails*.validate().find {false}) {
        odcDetails*.save()
      }

      odc
    }


}
