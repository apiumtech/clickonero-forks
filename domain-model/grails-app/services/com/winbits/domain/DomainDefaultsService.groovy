package com.winbits.domain

import com.winbits.domain.admin.*
import com.winbits.domain.affiliation.Profile
import com.winbits.domain.affiliation.SalesAgent
import com.winbits.domain.affiliation.SocialProviderEnum
import com.winbits.domain.affiliation.User
import com.winbits.domain.affiliation.Vertical
import com.winbits.domain.auth.api.ApiRole
import com.winbits.domain.auth.api.ApiUser
import com.winbits.domain.auth.api.ApiUserApiRole
import com.winbits.domain.catalog.Country
import com.winbits.domain.catalog.PaymentMethod
import com.winbits.domain.catalog.SocialProvider
import com.winbits.domain.common.Department
import com.winbits.domain.common.DepartmentEnum
import com.winbits.domain.utils.DomainModelUtils
import grails.util.Environment

import java.lang.reflect.ParameterizedType

class DomainDefaultsService {

  private static final Locale DEFAULT_LOCALE = new Locale('es', 'MX')
  static final String TEST_USER_EMAIL = 'test@winbits.com'
  static final String CS_REVIEW_USER_EMAIL = 'review@cybersource.com'
  static final String CS_REJECT_USER_EMAIL = 'reject@cybersource.com'

  def grailsApplication

  void createDefaultModel() {
    log.info 'Creating default model...'
    createDefaultCountries()
    def vertical = defaultVertical()
    createDefaultSocialProviders()
    createDefaultUsers(vertical)
    createDefaultPersistentEnums()
    createDefaultPaymentMethods()
  }

  private void createDefaultSocialProviders() {
    def queryKeys = ['providerId']
    findOrSaveDomainWhere(SocialProvider, [name: 'Facebook', logo: 'facebook.png', providerId: SocialProviderEnum.FACEBOOK.provider], queryKeys)
    findOrSaveDomainWhere(SocialProvider, [name: 'Twitter', logo: 'twitter.png', providerId: SocialProviderEnum.TWITTER.provider], queryKeys)
  }

  Vertical defaultCountry() {
    createDefaultCountries().first()
  }

  private List<Country> createDefaultCountries() {
    [findOrSaveDomainWhere(Country, [name: 'México', isoCode: 'MX', currency: 'MXN', defaultLocale: DEFAULT_LOCALE], ['isoCode'])]
  }

  Vertical defaultVertical() {
    createDefaultVerticals().first()
  }

  private List<Vertical> createDefaultVerticals() {
    def queryKeys = ['name']
    def verticalsConfig = grailsApplication.config.winbits.verticals
    [
        findOrSaveDomainWhere(Vertical, [name: "_Test_", baseUrl: verticalsConfig.test.url], queryKeys),
        findOrSaveDomainWhere(Vertical, [name: "My Looq", baseUrl: verticalsConfig.mylooq.url], queryKeys),
        findOrSaveDomainWhere(Vertical, [name: "Panda Sports", baseUrl: verticalsConfig.pandasports.url], queryKeys),
        findOrSaveDomainWhere(Vertical, [name: "clickOnero", baseUrl: verticalsConfig.clickonero.url], queryKeys)
    ]
  }

  private void createDefaultUsers(def vertical) {
    log.info('Creating admin user...')
    def mxLocale = DEFAULT_LOCALE

    log.info('Creating test user...')
    def testUser = findOrSaveDomainWhere(User, [email: TEST_USER_EMAIL, password: 's3cr3t0', enabled: true, apiToken: 'W1nb1tsT3st',
        referrerCode: '4X5Y6Z', vertical: vertical], ['email'])
    findOrSaveDomainWhere(Profile, [bitsId: -2L, locale: mxLocale, user: testUser], ['user'])

    if (Environment.current != Environment.PRODUCTION) {
      log.info('Creating review user...')
      def reviewUser = findOrSaveDomainWhere(User, [email: CS_REVIEW_USER_EMAIL, password: 's3cr3t0', enabled: true, apiToken: 'reviewUser',
          referrerCode: '4X5Y6Z', vertical: vertical], ['email'])
      findOrSaveDomainWhere(Profile, [bitsId: -2L, locale: mxLocale, user: reviewUser], ['user'])

      log.info('Creating reject user...')
      def rejectUser = findOrSaveDomainWhere(User, [email: CS_REJECT_USER_EMAIL, password: 's3cr3t0', enabled: true, apiToken: 'rejectUser',
          referrerCode: '4X5Y6Z', vertical: vertical], ['email'])
      findOrSaveDomainWhere(Profile, [bitsId: -2L, locale: mxLocale, user: rejectUser], ['user'])
    }
  }

  public void createDefaultApiUsers() {
    log.info('Creating root api user...')
    def rootUser = findOrSaveDomainWhere(ApiUser, [email: 'root@winbits.com', password: 's3cr3t0', enabled: true,
        vertical: Vertical.load(1)], ['email'])
    def rootRole = ApiRole.findOrSaveWhere(authority: 'ROLE_ROOT')
    if (!ApiUserApiRole.get(rootUser.id, rootRole.id)) {
      ApiUserApiRole.create(rootUser, rootRole)
    }

    log.info('Creating test api user...')
    def testUser = findOrSaveDomainWhere(ApiUser, [email: TEST_USER_EMAIL, password: 's3cr3t0', enabled: true,
        vertical: Vertical.load(1)], ['email'])
    def role = ApiRole.findOrSaveWhere(authority: 'ROLE_VERTICAL')
    if (!ApiUserApiRole.get(testUser.id, role.id)) {
      ApiUserApiRole.create(testUser, role)
    }
  }

  public void createDefaultAdminUsers() {
    log.info('Creating root admin user...')
    def rootUser = findOrSaveDomainWhere(AdminUser, [username:'root_admin@winbits.com', password: "s3cr3t0",
        enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false], ['username'])
    def rootRole = AdminRole.findOrSaveWhere(authority: 'ROLE_ROOT', description: 'role root')
    if (!AdminUserAdminRole.get(rootUser.id, rootRole.id)) {
      AdminUserAdminRole.create(rootUser, rootRole)
    }
    log.info('Creating test admin user...')
    def testUser = findOrSaveDomainWhere(AdminUser, [username: 'test_admin@winbits.com', password: "s3cr3t0",
        enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false], ['username'])
    def testRole = AdminRole.findOrSaveWhere(authority: 'ROLE_VERTICAL',description: 'role test')
    if (!AdminUserAdminRole.get(testUser.id, testRole.id)) {
      AdminUserAdminRole.create(testUser, testRole)
    }
    log.info('Creating admin switchUser...')
    def switchUser = findOrSaveDomainWhere(AdminUser, [username: 'switchuser_admin@winbits.com', password: "s3cr3t0",
        enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false, name: 'user', lastName:'switch', department:DepartmentEnum.DEPTO_SALES.domain], ['username'])
    def switchRole = AdminRole.findOrSaveWhere(authority: 'ROLE_SWITCH_USER',description: 'role switchUser')
    findOrSaveDomainWhere(SalesAgent, [adminUser: switchUser, enabled: true], ['adminUser'])
    def switchUserRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/switchUser', configAttribute: 'ROLE_SWITCH_USER', description:'Acceso a switch user winbits'], ["url"] )
    if (!AdminUserAdminRole.get(switchUser.id, switchRole.id)) {
      AdminUserAdminRole.create(switchUser, switchRole)
    }
    if (!AdminRoleAdminRequestmap.get(switchUserRequestmap.id, switchRole.id)) {
      AdminRoleAdminRequestmap.create(switchUserRequestmap, switchRole)
    }
    log.info('Creating admin switch User Operator...')
    def switchUserAdmin = findOrSaveDomainWhere(AdminUser, [username: 'switch_user_admin@winbits.com', password: "s3cr3t0",
        enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false, name: 'user', lastName:'switch', department:DepartmentEnum.DEPTO_SALES.domain], ['username'])
    def switchAdminRole = AdminRole.findOrSaveWhere(authority: 'ROLE_SWITCH_USER_ADMIN',description: 'role switchUser Administrator')
    def switchUserAdminCreateRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/create', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a crear usuario switch user'], ["url"] )
    def switchUserAdminSaveRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/save', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a guardar switch user'], ["url"] )
    def switchUserAdminUpdateRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/update', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a actualizar switch user'], ["url"] )
    def switchUserAdminEditRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/edit', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a editar switch user'], ["url"] )
    def switchUserAdminViewRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/view', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a visualizar switch user'], ["url"] )
    def switchUserAdminSearchRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/search', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a buscar switch user'], ["url"] )
    def switchUserAdminDeleteRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/delete', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a eliminar switch user'], ["url"] )
    def switchUserAdminRemoveRequestmap =findOrSaveDomainWhere(AdminRequestmap, [url:'/switchuser/remove', configAttribute: 'ROLE_SWITCH_USER_ADMIN', description:'Acceso a eliminar varios switch user'], ["url"] )
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminCreateRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminSaveRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminUpdateRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminEditRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminViewRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminSearchRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminDeleteRequestmap)
    createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(switchAdminRole, switchUserAdminRemoveRequestmap)
    if (!AdminUserAdminRole.get(switchUserAdmin.id, switchAdminRole.id)) {
      AdminUserAdminRole.create(switchUserAdmin, switchAdminRole)
    }
  }

  def createAdminRoleAdminRequestmapByAdminRoleAndAdminRequestmap(AdminRole role, AdminRequestmap requestmap){
    if (!AdminRoleAdminRequestmap.get(requestmap.id, role.id)) {
      AdminRoleAdminRequestmap.create(requestmap, role)
    }
  }

  public void createDefaultAdminRequestmaps(){
    def queryKeys = ["url"]
    def roleForSpecialUrls = Environment.current.name.toLowerCase() in ['staging', 'production'] ? 'ROLE_ROOT' : 'IS_AUTHENTICATED_ANONYMOUSLY'

    findOrSaveDomainWhere(AdminRequestmap, [url:'/**', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso a raíz'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/login/auth', configAttribute: 'IS_AUTHENTICATED_ANONYMOUSLY', description:'Acceso a login'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/dbconsole/**', configAttribute: 'IS_AUTHENTICATED_REMEMBERED', description:'Acceso a la consola de base de datos'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/build-info/**', configAttribute: 'IS_AUTHENTICATED_REMEMBERED', description:'Acceso a información de construcción de paquete'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/monitoring/**', configAttribute: 'IS_AUTHENTICATED_REMEMBERED', description:'Acceso a monitoreo de la aplicación'], queryKeys )

    //reembolsos urls
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/**', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso completo a reembolsos'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/refundDetail/**', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso a el detalle del reembolso'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/list', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso a el listado de reembolsos'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/search', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso a la busqueda de reembolsos'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/refundTotal', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso para aplicar reembolsos completos'], queryKeys )
    findOrSaveDomainWhere(AdminRequestmap, [url:'/refund/refundPartial', configAttribute: 'IS_AUTHENTICATED_FULLY', description:'Acceso para aplicar reembolsos parciales'], queryKeys )
  }

  static <T> T findOrSaveDomainWhere(Class<T> domainClass, Map domainData, List<String> queryKeys) {
    def queryMap = domainData.subMap(queryKeys)
    def domain = domainClass.findWhere(queryMap)
    if (!domain) {
      domain = domainClass.newInstance()
      domain.properties = domainData
      domain.save(failOnError: true)
    }
    domain
  }

  private void createDefaultPersistentEnums() {
    try {

      for (domainClass in grailsApplication.domainClasses) {
        def enumable = domainClass.clazz.genericInterfaces.find { it instanceof ParameterizedType && it.rawType == Enumable }
        if (enumable) {
          log.debug "Creating Enumable Class ${domainClass.clazz}"
          def enumClass = (enumable as ParameterizedType).actualTypeArguments.first() as Class
          DomainModelUtils.createPersistentEnumModel(enumClass)
        }
      }
    } catch(e) {
      e.printStackTrace()
    }
  }

  private List<PaymentMethod> createDefaultPaymentMethods() {
    def queryKeys = ['identifier']
    [
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'user.bits', minAmount: 0.0, maxAmount: 0.0, displayOrder: 0,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.cc', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.token', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.msi.3', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.msi.6', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.msi.9', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.msi.12', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.token.msi.3', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.token.msi.6', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.token.msi.9', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'cybersource.token.msi.12', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'amex.cc', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'amex.msi.3', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'amex.msi.6', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'amex.msi.9', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'amex.msi.12', minAmount: 0.01, maxAmount: 100000.0, displayOrder: 1,
            expireMinutes: -1, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'paypal.latam', minAmount: 15.0, maxAmount: 40000.0, displayOrder: 3,
            expireMinutes: 30, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'paypal.msi', minAmount: 15.0, maxAmount: 40000.0, displayOrder: 3,
            expireMinutes: 30, offline: false], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'oxxo.bc', minAmount: 20.0, maxAmount: 5000.0, displayOrder: 4,
            expireMinutes: 7200, offline: true], queryKeys),
        findOrSaveDomainWhere(PaymentMethod, [identifier: 'reference.hsbc', minAmount: 20.0, maxAmount: 15000.0, displayOrder: 4,
            expireMinutes: 7200, offline: true], queryKeys)
    ]
  }
}
