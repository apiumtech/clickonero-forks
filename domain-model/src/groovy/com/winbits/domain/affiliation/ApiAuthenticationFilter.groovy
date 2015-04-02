package com.winbits.domain.affiliation

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY

/**
 * Created with IntelliJ IDEA.
 * User: juan
 * Date: 25/04/13
 * Time: 05:19 PM
 * To change this template use File | Settings | File Templates.
 */
class ApiAuthenticationFilter extends GenericFilterBean {

  private static final Logger log = LoggerFactory.getLogger(ApiAuthenticationFilter)

  def authenticationService

  @Override
  void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    def httpRequest = (HttpServletRequest) request
    def apiToken = httpRequest.getHeader('WB-API-TOKEN')
    if (apiToken) {
      log.info('Authenticating with token')
      def result = authenticationService.loginApiToken(apiToken)
      if (result) {
        def context = result.first()
        httpRequest.getSession().setAttribute(SPRING_SECURITY_CONTEXT_KEY, context)
        User user = result.last()
        if (user.salesAgentId) {
          authenticationService.saveCurrentSalesAgentId(user.salesAgentId, httpRequest)
        }
      }
    }
    chain.doFilter(request, response)
  }
}
