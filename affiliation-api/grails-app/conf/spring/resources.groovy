import com.winbits.api.affiliation.WinbitsSaltSource
import com.winbits.api.affiliation.WinbitsUserDetailsService
import com.winbits.domain.affiliation.ApiAuthenticationFilter
import grails.util.Environment
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import wslite.http.auth.HTTPBasicAuthorization

// Place your Spring DSL code here
beans = {
  xmlns context: "http://www.springframework.org/schema/context"
  def clientsPackage = Environment.current.name.toLowerCase() in ['test', 'ci'] ? 'dummyclients' : 'clients'
  context.'component-scan'('base-package': "com.winbits.api.$clientsPackage")
  context.'component-scan'('base-package': 'com.winbits.api.affiliation.config')
  validator(LocalValidatorFactoryBean)

  userDetailsService(WinbitsUserDetailsService) {
    grailsApplication = ref('grailsApplication')
  }

  saltSource(WinbitsSaltSource) {
    userPropertyToUse = application.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
  }

  apiAuthenticationFilter(ApiAuthenticationFilter) {
    authenticationService = ref('authenticationService')
  }

  def bitsApiAuth = grailsApplication.config.winbits.api.clients.bits.auth
  bitsClientAuthorization(HTTPBasicAuthorization) {
    username = bitsApiAuth.username
    password = bitsApiAuth.password
  }
}
