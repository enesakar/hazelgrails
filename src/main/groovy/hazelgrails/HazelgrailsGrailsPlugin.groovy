package hazelgrails

import grails.plugins.Plugin

class HazelgrailsGrailsPlugin extends Plugin {
    def grailsVersion = "3.0.2 > *"
    def title = "Hazelcast Plugin"
    def author = "Enes Akar"
    def authorEmail = "enesakar@gmail.com"
    def description = '''\
Distribute your data with Hazelcast in your Grails project.
See http://blog.codepoly.com/distribute-grails-with-hazelcast
'''
    def profiles = ['web']
    def documentation = "https://github.com/enesakar/hazelgrails"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    def developers = [[name: 'Rohit Bishnoi', email: 'rbdharnia@gmail.com']]
    def issueManagement = [system: "GITHUB", url: "https://github.com/enesakar/hazelgrails/issues"]
    def scm = [url: 'https://github.com/enesakar/hazelgrails']


    @Override
    void doWithDynamicMethods() {
        HazelService service = applicationContext.hazelService
        for (domainClass in grailsApplication.domainClasses) {
            domainClass.metaClass.saveHz = { ->
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
