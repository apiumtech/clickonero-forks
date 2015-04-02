final isReleaseEnvironment = System.getProperty('winbits.release')?.toLowerCase() == 'yes'
if (isReleaseEnvironment) {
	println 'Plugin configured for Release'
}
final winbitsPluginsVersionSuffix = isReleaseEnvironment ? '' : '-SNAPSHOT'
final pluginPublishRepo = isReleaseEnvironment ? 'winbits-releases' : 'winbits-snapshots'

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.repos.'winbits-releases'.url = 'http://repo.clickonero.com/nexus/content/repositories/winbits-releases'
grails.project.repos.'winbits-releases'.type = 'maven'
grails.project.repos.'winbits-releases'.username = 'admin'
grails.project.repos.'winbits-releases'.password = 'chidowan12'

grails.project.repos.'winbits-snapshots'.url = 'http://repo.clickonero.com/nexus/content/repositories/winbits-snapshots'
grails.project.repos.'winbits-snapshots'.type = 'maven'
grails.project.repos.'winbits-snapshots'.username = 'admin'
grails.project.repos.'winbits-snapshots'.password = 'chidowan12'

grails.project.repos.default = pluginPublishRepo

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
	repositories {
		//grailsCentral()
		// uncomment the below to enable remote dependency resolution
		// from public Maven repositories
		//mavenLocal()
		mavenCentral()
		//mavenRepo "http://snapshots.repository.codehaus.org"
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
		mavenRepo 'http://repo.clickonero.com/nexus/content/repositories/winbits-releases/'
		mavenRepo 'http://repo.clickonero.com/nexus/content/repositories/winbits-snapshots/'
		mavenRepo 'http://repo.grails.org/grails/repo'
	}
	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		runtime 'mysql:mysql-connector-java:5.1.29'
		runtime 'org.codehaus.gpars:gpars:1.1.0'
		runtime 'org.codehaus.jsr166-mirror:jsr166y:1.7.0'

		compile("joda-time:joda-time-hibernate:1.3") { excludes "joda-time", "hibernate" }
		compile 'org.gmetrics:GMetrics:0.6'
		compile 'com.winbits.libs:exceptions:1.0.4-SNAPSHOT'
    
    compile "com.winbits.libs:payment-commons:1.0.0-SNAPSHOT"
    compile "com.winbits.libs:cybersource:1.1.0-SNAPSHOT", {
      excludes 'payment-commons'
    }

		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
	}

	plugins {
		build(":tomcat:$grailsVersion",
				":release:2.2.1",
				":rest-client-builder:1.0.3") { export = false }

		runtime(":hibernate:$grailsVersion") { export = false }
		runtime(":database-migration:1.3.8") { export = false }

		compile ':plugin-config:0.2.0'
		compile ":build-info:1.2.5"
		compile ":joda-time:1.4"
		compile ":spring-security-core-fix:1.2.7.3"
		compile ":hibernate-filter:0.3.2"
		compile ":codenarc:0.20"
		compile ":gmetrics:0.3.1"

		test(":spock:0.7") { exclude "spock-grails-support" }
		test ":code-coverage:1.2.7"

		compile ":grails-template-engine:0.2.1"
		compile ":build-test-data:2.0.5"
	 	compile ":redis:1.4.2"


	}
}

codenarc.reports = {
	MyXmlReport('xml') {
		// The report name "MyXmlReport" is user-defined; $
		outputFile = 'target/CodeNarcReport.xml'  // Set the 'outputFile' property of the (XML$
		title = 'Violations Report'             // Set the 'title' property of the (XML) Report
	}
	MyHtmlReport('html') {
		// Report type is 'html'
		outputFile = 'target/CodeNarcReport.html'
		title = 'Violations Report'
	}
}

codenarc.propertiesFile='grails-app/conf/codenarc.properties'
codenarc.ruleSetFiles = "rulesets/basic.xml,rulesets/exceptions.xml, rulesets/imports.xml,rulesets/grails.xml, rulesets/unused.xml, rulesets/size.xml, rulesets/concurrency.xml,rulesets/convention.xml,rulesets/design.xml,rulesets/groovyism.xml,rulesets/imports.xml,rulesets/logging.xml"

