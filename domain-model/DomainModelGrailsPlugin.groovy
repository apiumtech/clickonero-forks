import com.winbits.domain.DomainModelDynamicMethods
import com.winbits.domain.editors.CustomTypePropertyEditorRegistrar
import com.winbits.domain.editors.EnumablePropertyEditorRegistrar
import org.joda.time.DateTimeZone

class DomainModelGrailsPlugin {
  // the plugin version
  def version = "2.3.6_1"
  // the version or versions of Grails the plugin is designed for
  def grailsVersion = "2.2 > *"
  // resources that are excluded from plugin packaging
  def pluginExcludes = [
      "grails-app/views/error.gsp"
  ]
  def loadBefore = ['build-test-data']
  def loadAfter = ['hibernate', 'databasemigration']
  def observe = ['domainClass']

  // TODO Fill in these fields
  def title = "Domain Model Plugin" // Headline display name of the plugin
  def author = "Your name"
  def authorEmail = ""
  def description = '''\
Brief summary/description of the plugin.
'''

  // URL to the plugin's documentation
  def documentation = "http://grails.org/plugin/domain-model"

  // Extra (optional) plugin metadata

  // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

  // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

  // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

  // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

  // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

  def domainDefaultsService

  def doWithWebDescriptor = { xml ->
    // TODO Implement additions to web.xml (optional), this event occurs before
  }

  def doWithSpring = {
    enumableEditorRegistrar(EnumablePropertyEditorRegistrar)
    customEditorRegistrar(CustomTypePropertyEditorRegistrar)
  }

  def doWithDynamicMethods = { ctx ->
    ctx.domainDefaultsService.createDefaultModel()
    DomainModelDynamicMethods.decorate(application)
  }

  def doWithApplicationContext = { applicationContext ->
    Locale.default = new Locale('es', 'MX')
    def defaultTimeZone = TimeZone.getTimeZone('America/Mexico_City')
    TimeZone.default = defaultTimeZone
    DateTimeZone.default = DateTimeZone.forTimeZone(defaultTimeZone)
  }

  def onChange = { event ->
  }

  def onConfigChange = { event ->
    // TODO Implement code that is executed when the project configuration changes.
    // The event is the same as for 'onChange'.
  }

  def onShutdown = { event ->
    // TODO Implement code that is executed when the application shuts down (optional)
  }
}
