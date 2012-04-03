package hazelgrails

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.ITopic
import java.util.concurrent.locks.Lock
import com.hazelcast.core.IdGenerator
import java.util.concurrent.ExecutorService
import java.util.concurrent.Callable
import java.util.concurrent.Future
import com.hazelcast.core.MultiTask
import com.hazelcast.core.Member
import com.hazelcast.core.DistributedTask
import java.util.concurrent.FutureTask
import com.hazelcast.config.Config
import com.hazelcast.config.FileSystemXmlConfig
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.hazelcast.core.Cluster
import javax.annotation.PostConstruct

class HazelService {
    def grailsApplication
    static transactional = false
    HazelcastInstance instance

    private HazelService() {
    }

    @PostConstruct
    void init() {
        this.instance = Hazelcast.newHazelcastInstance(null)
    }

    Map map(String mapName) {
        instance.getMap(mapName)
    }

    Queue queue(String queueName) {
        instance.getQueue(queueName)
    }

    ITopic topic(String topicName) {
        instance.getTopic(topicName)
    }

    Set hashset(String setName) {
        instance.getSet(setName)
    }

    List list(String listName) {
        instance.getList(listName)
    }

    Lock lock(Object lock) {
        instance.getLock(lock)
    }

    long generateId(String name) {
        IdGenerator idGenerator = Hazelcast.getIdGenerator(name);
        idGenerator.newId();
    }

    Collection executeOnAllMembers(Callable callable) {
        Collection<Member> members = instance.getCluster().getMembers()
        MultiTask multitask = new MultiTask(callable, members);
        ExecutorService executorService = Hazelcast.getExecutorService();
        executorService.execute(multitask);
        return multitask.get();
    }

    Object executeOnSomewhere(Callable callable) {
        ExecutorService executorService = Hazelcast.getExecutorService();
        Future task = executorService.submit(callable);
        return task.get();
    }

    Object executeOnMemberOwningTheKey(Callable callable, Object key) {
        FutureTask task = new DistributedTask(callable, key);
        ExecutorService executorService = Hazelcast.getExecutorService();
        executorService.execute(task);
        return task.get();
    }


}
