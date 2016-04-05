# Hazelcast plugin for grails

### How to Install Plugin

Just add the following dependency under the dependencies block in your project's build.gradle:

> compile "org.grails.plugins:hazelgrails:1.0-SNAPSHOT"

### Configuration

You can configure hazelcast in details.

For available options have a look at:
[http://www.hazelcast.com/docs/2.0/manual/single_html/#Config](http://www.hazelcast.com/docs/2.0/manual/single_html/#Config)


To use Hazelcast as Hibernate 2nd Level Cache, add the following line to application.groovy:

cache.region.factory_class = 'com.hazelcast.hibernate.HazelcastCacheRegionFactory'

### For more documentation:
See:
[http://blog.hazelcast.com/distribute-grails-with-hazelcast/](http://blog.hazelcast.com/distribute-grails-with-hazelcast/)

### For Grails3 Test application:
See:
[https://github.com/rohitbishnoi/hazelcast-test] (https://github.com/rohitbishnoi/hazelcast-test)
