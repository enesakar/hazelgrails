class HazelgrailsGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Hazelcast Plugin" // Headline display name of the plugin
    def author = "Enes Akar"
    def authorEmail = "enesakar@gmail.com"
    def description = '''\
Distribute your data with Hazelcast in your Grails project.
See https://github.com/enesakar/hazelgrails
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/enesakar/hazelgrails"
//    def documentation = "http://grails.org/plugin/hazelgrails"

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
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        def service = ctx.getBean("hazelService")
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

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
