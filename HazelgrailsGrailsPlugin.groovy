class HazelgrailsGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "1.3 > *"
    def title = "Hazelcast Plugin"
    def author = "Enes Akar"
    def authorEmail = "enesakar@gmail.com"
    def description = '''\
Distribute your data with Hazelcast in your Grails project.
See http://blog.codepoly.com/distribute-grails-with-hazelcast
'''
    def documentation = "https://github.com/enesakar/hazelgrails"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPHAZELGRAILS" ]
    def scm = [url: 'https://github.com/enesakar/hazelgrails']

    def doWithDynamicMethods = { ctx ->
        def service = ctx.hazelService
        for (domainClass in application.domainClasses) {

            domainClass.metaClass.saveHz = {->
                delegate.save()
                if (delegate.id)
                service.map("domain_" + domainClass.name).put(delegate.id, delegate)
            }
            domainClass.metaClass.static.getHz = { id ->
                def ss = service.map("domain_" + domainClass.name).get(id)
                if (!ss) {
                    ss = delegate.get(id)
                    if (ss) {
                        service.map("domain_" + domainClass.name).put(id, ss)
                    }
                }
                return ss
            }
        }
    }
}
