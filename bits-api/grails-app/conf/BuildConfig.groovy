import grails.util.Environment

final isReleaseEnvironment = System.getProperty('winbits.release')?.toLowerCase() == 'yes'
if (isReleaseEnvironment) {
  println "Using release version... ${appVersion}"
}
final isDevEnvironment = Environment.current.name.toLowerCase() in ['development', 'test']
final winbitsPluginsVersionSuffix = isReleaseEnvironment ? '' : '-SNAPSHOT'

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.server.port.http = 8001

grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits("global") {
    // specify dependency exclusions here; for example, uncomment this to disable ehcache:
    // excludes 'ehcache'
  }
  log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  checksums true // Whether to verify checksums on resolve
  legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

  repositories {
    inherits true // Whether to inherit repository definitions from plugins

    grailsPlugins()
    grailsHome()
    grailsCentral()

    mavenLocal()
    mavenCentral()

    // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
    //mavenRepo "http://snapshots.repository.codehaus.org"
    //mavenRepo "http://repository.codehaus.org"
    //mavenRepo "http://download.java.net/maven/2/"
    //mavenRepo "http://repository.jboss.com/maven2/"

    mavenRepo 'http://repo.clickonero.com/nexus/content/repositories/winbits-releases'
    mavenRepo 'http://repo.clickonero.com/nexus/content/repositories/winbits-snapshots'
    mavenRepo 'http://repo.grails.org/grails/repo'
  }

  dependencies {
    // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
    runtime 'mysql:mysql-connector-java:5.1.29'
    runtime 'org.apache.tomcat:tomcat-jdbc:7.0.50'

    compile("joda-time:joda-time-hibernate:1.3") {
      excludes "joda-time", "hibernate"
    }
    compile 'org.gmetrics:GMetrics:0.6'

    test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
  }

  plugins {
    build ":tomcat:$grailsVersion"

    runtime ":hibernate:$grailsVersion"
    runtime ":database-migration:1.3.8"
    runtime ":grails-melody:1.49.2"
    runtime ":jdbc-pool:7.0.47", {
      transitive = false
    }

    compile ':cache:1.0.1'
    compile ':spring-security-core-fix:1.2.7.3'
    compile ":joda-time:1.4"
    compile ":functional-test:2.0.RC1"
    compile ":quartz:1.0.1"
    compile ":codenarc:0.20"
    compile ":gmetrics:0.3.1"

    compile ':build-test-data:2.1.2'
    test(":spock:0.7") {
      exclude "spock-grails-support"
    }
    test ':functional-test:1.2.7'
    test ':code-coverage:1.2.7'
    test ":funky-spock:0.2.2"

    compile ":rabbitmq:1.0.0"

    /**********************************************************
     * Plugins de Winbits, nunca comentar                     *
     **********************************************************/
    compile ":grest:1.1.2$winbitsPluginsVersionSuffix"
  }
}

if (isDevEnvironment) {
/*********************************************************************
 * Descomentar plugin local si se usa para development               *
 * Evitar WARNING volviendo a comentar el plugin antes de hacer push *
 *********************************************************************/
  grails.plugin.location.'grest' = "../grails-grest"
}

codenarc.reports = {
  MyXmlReport('xml') {                    // The report name "MyXmlReport" is user-defined; $
    outputFile = 'target/CodeNarcReport.xml'  // Set the 'outputFile' property of the (XML$
    title = 'Violations Report'             // Set the 'title' property of the (XML) Report
  }
  MyHtmlReport('html') {                  // Report type is 'html'
    outputFile = 'target/CodeNarcReport.html'
    title = 'Violations Report'
  }
}
