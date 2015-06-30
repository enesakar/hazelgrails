# Hazelcast plugin for grails

### How to Install Plugin

Run the command:

> install-plugin hazelgrails

### Configuration
You will see hazelcast.xml in conf directory under plugins directory.

You can configure hazelcast in details.

For available options have a look at:
[http://www.hazelcast.com/docs/2.0/manual/single_html/#Config](http://www.hazelcast.com/docs/2.0/manual/single_html/#Config)

To see hazelcast logs add following to Config.groovy:
> info 'com.hazelcast'

Use Hazelcast as Hibernate 2nd Level Cache
In DataSource.groovy replace the following line in hibernate configuration block.

cache.region.factory_class = 'com.hazelcast.hibernate.HazelcastCacheRegionFactory'

### For more documentation:
See:
[http://blog.hazelcast.com/2012/04/03/distribute-grails-with-hazelcast-in-this-article-i-will-try/](http://blog.hazelcast.com/2012/04/03/distribute-grails-with-hazelcast-in-this-article-i-will-try/)
