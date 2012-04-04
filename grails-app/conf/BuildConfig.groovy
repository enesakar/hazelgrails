grails.project.work.dir = 'target'
grails.project.source.level = 1.6
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        grailsRepo "http://grails.org/plugins"
    }

    dependencies {
        compile 'com.hazelcast:hazelcast:2.0.2'
        compile 'com.hazelcast:hazelcast-hibernate:2.0.2'
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:1.0.0") {
            export = false
        }
    }
}
