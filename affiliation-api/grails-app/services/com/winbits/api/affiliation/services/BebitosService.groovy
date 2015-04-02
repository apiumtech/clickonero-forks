package com.winbits.api.affiliation.services

import com.winbits.api.affiliation.controllers.CreateShippingAddressCommand
import com.winbits.api.clients.bebitos.BebitosClient
import com.winbits.domain.affiliation.MigrationStatusEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.affiliation.VerticalPartner
import groovy.json.JsonSlurper
import org.apache.commons.lang.RandomStringUtils
import groovy.json.JsonOutput

import java.text.SimpleDateFormat




class BebitosService {

    static String VERTICAL_NAME = "Bebitos"

    BebitosClient bebitosClient
    def codeGeneratorService
    def shippingAddressService
    def partnerService
    def mongoDbService

    def Vertical getVertical() {
        Vertical.findByName(VERTICAL_NAME)
    }

    def User migrateUser(String email, String password) {
        log.info("Migrate user: "+email)
        def result = bebitosClient.findUserByCredentials(java.net.URLEncoder.encode(email, "UTF-8"), password)
        if(result) {
            def statusCode = result.response?.statusCode
            if(statusCode && statusCode == 200) {
                User.withTransaction {
                    def vertical = getVertical()
                    def user = new User()
                    user.email = email
                    user.password = password
                    user.vertical = vertical
                    user.referrerCode = codeGeneratorService.generateReferredCode()
                    user.generateSalt()
                    user.save()
                    new VerticalPartner(user: user, vertical: vertical, status: MigrationStatusEnum.PENDING.domain).save()
                    user
                }
            }
        }
    }


    def User migrateUserByEmail(String email) {
        def result = bebitosClient.userDetail(email)
        if(result) {
            def statusCode = result.response?.statusCode
            if(statusCode && statusCode == 200) {
                User.withTransaction {
                    def vertical = getVertical()
                    def user = new User()
                    user.email = email
                    user.password = RandomStringUtils.randomAlphanumeric(10).toUpperCase()
                    user.vertical = vertical
                    user.referrerCode = codeGeneratorService.generateReferredCode()
                    user.generateSalt()
                    user.save()
                    new VerticalPartner(user: user, vertical: vertical, status: MigrationStatusEnum.PENDING.domain).save()
                    user
                }
            }
        }
    }


    def createShippingAddress(User user, addresses) {
        addresses?.each { bebitosAddress ->
            if(bebitosAddress.country_id == 1) {
                def addressCommand = createAddressCommand(bebitosAddress)
                shippingAddressService.createShippingAddres(addressCommand, user)
            }
        }
    }

    def createAddressCommand(Map bebitosAddress) {
        new CreateShippingAddressCommand(
                firstName: bebitosAddress.firstname,
                lastName: bebitosAddress.lastname,
                phone: bebitosAddress.phone,
                street: bebitosAddress.address1,
                internalNumber: bebitosAddress.int_num,
                externalNumber: bebitosAddress.ext_num,
                indications: bebitosAddress.between_streets?:'Sin indicaciones',
                zipCode: bebitosAddress.zipcode,
                county: bebitosAddress.city,
                location: bebitosAddress.address2,
                state: bebitosAddress.state.name,
                main: bebitosAddress.main)
    }

    def migrateUserData(VerticalPartner partner) {
        def userDetail = bebitosClient.userDetail(partner.user.email)
        def statusCode = userDetail?.response?.statusCode
        if(statusCode == 200) {
            def data = userDetail.response.parsedResponseContent?.json
            //saveInfoInWinbitsDb(partner.user, data)
            def objectId = partnerService.saveInfoInMongoDb(data, partner.vertical)
            partnerService.updateAsMigrationComplete(objectId, partner)
        }
    }

    def saveInfoInWinbitsDb(User user, Map userData) {
        def addresses = userData?.addresses
        if(addresses)
            createShippingAddress(user, addresses)
    }



    def getClickoneroOrders(String clickoneroId,String path){
        def response
        def contenido
        try{
            contenido = (path+"?id="+ java.net.URLEncoder.encode(clickoneroId, "UTF-8")).toURL().text

        }
        catch (e){
            log.error ("Error: ", e)
            e.printStackTrace()
        }
        new JsonSlurper().parseText(contenido)
    }


    def findHistoryByEmail(String collectionName, String eMail,String clickoneroId,String path)  {
        def documentBebitos = mongoDbService.findDocumentByEmail(collectionName,eMail)
        def documentClickonero=null
        def listaOrdenesBebitos
        def  listaOrdenesClickonero
        if(clickoneroId && clickoneroId.length()>0){
            documentClickonero=getClickoneroOrders(clickoneroId,path)
            if(documentClickonero){
                listaOrdenesClickonero= generaListaClickonero(documentClickonero)
                if(listaOrdenesClickonero.size()==0){
                    documentClickonero=null
                }
            }

        }
        if( documentBebitos ) {
            listaOrdenesBebitos= generaListaBebitos(documentBebitos)
        }

        if(documentBebitos || documentClickonero) {
            def respuesta
            if(documentBebitos && documentClickonero){
                respuesta=new envolventes()
                def listaOrdenes=mergeClickoneroBebitos(listaOrdenesClickonero,documentBebitos)
                respuesta.orders= listaOrdenes
                def jsonLista = JsonOutput.toJson(respuesta)
                new JsonSlurper().parseText(jsonLista)
            }else if(documentBebitos || !documentClickonero) {
                respuesta=new envolventes()
                respuesta.orders= listaOrdenesBebitos
                def jsonLista = JsonOutput.toJson(respuesta)
                new JsonSlurper().parseText(jsonLista)
            }else{
                documentClickonero
            }
        } else{
            [:]
        }
    }

    def generaListaBebitos(document){
        def documentP=new JsonSlurper().parseText(document)
        def OrdersP=new JsonSlurper().parseText(documentP.orders)
        def listaOrdenes=new ArrayList()
        def iCont=0
        OrdersP?.each {prop ->
            def MiOrden= new  clickoneroMatchOrder()
            MiOrden.orderNumber=prop.id;
            MiOrden.paidDate=prop.created_at.substring(0,10)
            MiOrden.total=prop.total
            MiOrden.shippingStatusMessage=prop.payment_state
            MiOrden.verticalclass="iconVertical-t vertical7"
            MiOrden.skus= new ArrayList()
            prop.line_items?.each {ln ->
                def skuActual= new sku()
                skuActual.name=ln.variant.name
                skuActual.dateDelivery=""
                skuActual.shortDescription=""
                skuActual.url= ln.product.images[0].attachment_url
                skuActual.ext=""
                if(ln.variant.options_text.length()>0){
                    skuActual.attributes=new attribute[1]
                    skuActual.attributes[0]=new attribute()
                    skuActual.attributes[0].attrName=ln.variant.options_text.substring((int)0,ln.variant.options_text.indexOf(':').intValue())
                    skuActual.attributes[0].label=ln.variant.options_text.substring((ln.variant.options_text.indexOf(':')+1).intValue())
                }
                MiOrden.skus.add(skuActual)
            }
            if(iCont==0){
                listaOrdenes.add(MiOrden)
            }else{
                agregaOrdenaLista(listaOrdenes,MiOrden)
            }
            iCont++
        }
        listaOrdenes
    }

    def  agregaOrdenaLista(listaOrdenes,MiOrden){
        def sFechaLista
        def FechaLista
        def sFechaOrden
        def FechaOrden
        def i=0
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        sFechaOrden=MiOrden.paidDate
        FechaOrden = dt.parse(sFechaOrden.substring(0,10));
        sFechaLista=((clickoneroMatchOrder)listaOrdenes.get(i)).paidDate
        FechaLista = dt.parse(sFechaLista.substring(0,10));
        while(FechaLista>FechaOrden && i<listaOrdenes.size()-1){
            i++
            sFechaLista=((clickoneroMatchOrder)listaOrdenes.get(i)).paidDate
            FechaLista = dt.parse(sFechaLista.substring(0,10));
        }

        listaOrdenes.add(i,MiOrden)



    }

    def generaListaClickonero(jsonCliconero){
        def listaOrdenes=new ArrayList()
        jsonCliconero?.orders.each {prop ->
            def sFechaClickonero=prop.paidDate
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            def dFechaClickonero = new Date(sFechaClickonero)
            def sDateDelivery=prop.dateDelivery
            def dDateDelivery = new Date()


            def MiOrden= new  clickoneroMatchOrder()
            MiOrden.orderNumber=prop.orderNumber;
            MiOrden.paidDate=sdf.format(dFechaClickonero)
            MiOrden.total=prop.total
            MiOrden.shippingStatusMessage=prop.shippingStatusMessage
            MiOrden.verticalclass="iconVertical-t vertical4"
            MiOrden.skus= new ArrayList()
            prop.skus.each {ln ->
                def skuActual= new sku()
                sDateDelivery=ln.dateDelivery
                dDateDelivery = new Date(sDateDelivery)
                skuActual.name=ln.name
                skuActual.dateDelivery=sdf.format(dDateDelivery)
                skuActual.shortDescription=ln.shortDescription
                skuActual.url= ln.url
                skuActual.ext=ln.ext
                skuActual.attributes=new ArrayList()
                ln.attributes.each{att->
                    def atributoActual=new attribute()
                    atributoActual.attrName=att.attrName
                    atributoActual.label=att.label
                    skuActual.attributes.add(atributoActual)
                }
                MiOrden.skus.add(skuActual)
            }
            listaOrdenes.add(MiOrden)
        }
        listaOrdenes
    }


    def mergeClickoneroBebitos(listaClikconero,jsonBebitos){
        def documentP=new JsonSlurper().parseText(jsonBebitos)
        def OrdersP=new JsonSlurper().parseText(documentP.orders)
        def contBebitos=0
        def contClickonero=0
        def sFechaBebitos
        def FechaBebitos
        def FechaClickonero
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        def sFechaClickonero
        def sdateDelivery
        def dDateDelivery
        OrdersP?.each {prop ->
            contClickonero=0
            sFechaClickonero=((clickoneroMatchOrder)listaClikconero.get(contClickonero)).paidDate
            sFechaBebitos=prop.created_at
            FechaBebitos = dt.parse(sFechaBebitos.substring(0,10));
            FechaClickonero = dt.parse(sFechaClickonero);
            sdateDelivery=prop.updated_at
            dDateDelivery=dt.parse(sdateDelivery.substring(0,10));

            def MiOrden= new  clickoneroMatchOrder()
            MiOrden.orderNumber=prop.id;
            MiOrden.paidDate=sFechaBebitos.substring(0,10)
            MiOrden.total=prop.total
            MiOrden.shippingStatusMessage=prop.payment_state
            MiOrden.verticalclass="iconVertical-t vertical7"
            MiOrden.skus= new ArrayList()
            prop.line_items?.each {ln ->
                def skuActual= new sku()
                skuActual.name=ln.variant.name
                skuActual.dateDelivery= ""
                skuActual.shortDescription=""
                skuActual.url= ln.product.images[0].attachment_url
                skuActual.ext=""
                if(ln.variant.options_text.length()>0){
                    skuActual.attributes=new attribute[1]
                    skuActual.attributes[0]=new attribute()
                    skuActual.attributes[0].attrName=ln.variant.options_text.substring((int)0,ln.variant.options_text.indexOf(':').intValue())
                    skuActual.attributes[0].label=ln.variant.options_text.substring((ln.variant.options_text.indexOf(':')+1).intValue())
                }
                MiOrden.skus.add(skuActual)
            }
            while (FechaClickonero>FechaBebitos && contClickonero<listaClikconero.size()-1){
                contClickonero++
                sFechaClickonero=((clickoneroMatchOrder)listaClikconero.get(contClickonero)).paidDate
                FechaClickonero = dt.parse(sFechaClickonero);
            }
            listaClikconero.add(contClickonero,MiOrden)
        }
        listaClikconero
    }
}
class envolventes{
    ArrayList orders
}

class attribute{
    String attrName
    String label
}

class sku{
    String name
    ArrayList attributes
    String shortDescription
    String dateDelivery
    String url
    String ext

}

class clickoneroMatchOrder{
    String orderNumber
    String paidDate
    String total
    String shippingStatusMessage
    String verticalclass
    ArrayList skus
}
