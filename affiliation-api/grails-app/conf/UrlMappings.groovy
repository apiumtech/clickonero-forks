class UrlMappings {

  static mappings = {

    "/"(view: "/index")

    "/shipping-addresses"(resource: 'shippingAddresses')
    "/shipping-addresses/$id"(resource: 'shippingAddress')

    confirm: "/confirm/$salt"(controller: 'affiliation', action: 'confirm')

    expressLogin: "/express-login"(controller: 'affiliation', action: 'loginApiToken', parseRequest: true)

    expressFacebookLogin: "/express-facebook-login"(controller: 'affiliation', action: 'loginFacebookToken', parseRequest: true)

    "/social-accounts"(controller: 'affiliation', parseRequest: true) {
      action = [GET: 'accounts']
    }

    "/social-account/$id"(controller: 'affiliation') {
      action = [DELETE: 'deleteAccount']
    }
    "/password/recover"(controller: 'affiliation', parseRequest: true) {
      action = [POST: 'recover']
    }
    "/password/reset"(controller: 'affiliation', parseRequest: true) {
      action = [POST: 'reset']
    }

    "/subscriptions"(controller: 'affiliation', parseRequest: true) {
      action = [GET: 'subscriptions', PUT: 'updateSubscriptions']
    }

    "/resend/$email"(controller: 'affiliation', action: 'resendConfirmation')

    "/bits/$action"(controller: 'bits')

    "/locations/$zipCode"(resource: "locations")

    "/waiting-list-items"(resource: "waitingListItems")
    "/waiting-list-item/$id"(resource: "waitingListItem")
    "/waiting-list-notifier"(resource: "waitingListNotifier")

    name connect: "/connect/$providerId"(controller: "SocialConnect", parseRequest: true) {
      action = [GET: "oauthCallback", DELETE: "removeConnection", POST: "connect"]
    }

    name facebookLogin: "/facebook-login/$action"(controller: 'FacebookLogin', parseRequest: true)

    "/facebookPublish/$action"(controller: 'facebookPublish', parseRequest: true)
    "/twitterPublish/$action"(controller: 'twitterPublish', parseRequest: true)

    "/wish-list-items"(resource: "wishListItems")
    "/wish-list-items/$brandId"(resource: "wishListItem")

    "/orders"(controller: 'orders', parseRequest: true) {
      action = [GET: 'show']
    }

    "/profile"(controller: 'affiliation', parseRequest: true) {
      action = [GET: 'getProfile', PUT: 'profile', POST: 'profile']
    }

    "/$action/$id?"(controller: 'affiliation', parseRequest: true) {
      constraints {
        // apply constraints here
      }
    }

    "/verticals"(resource: "verticalsResource")
    "/home-vertical"(controller: "verticalsResource", action: "showHome")

    "/coupon/$couponId"(controller: 'coupon', parseRequest: true) {
      action = [GET: 'show']
    }

    "/coupons/$orderDetailId"(controller: 'couponList', parseRequest: true) {
      action = [GET: 'show']
    }

    "/validate/$action"(controller: 'validate')

    "/send-sms"(controller: 'sms', parseRequest: true){
      action =[POST: 'send']
    }

    "/activate-mobile"(controller: 'sms', parseRequest: true){
      action =[POST : 'activate']
    }

    "/get-bebitos-orders"(controller: 'bebitos', parseRequest: true){
          action =[POST : 'orders']
    }

    "/partner/$action/$verticalId"(controller: 'partner', parseRequest: true)
    "/partners"(controller: 'partner'){
      action = [GET: 'list']
    }
      "/addbebitos-newsletters"(controller: 'newsletters', parseRequest: true){
          action =[POST : 'addBebitosNewsletter']
      }

  }
}
