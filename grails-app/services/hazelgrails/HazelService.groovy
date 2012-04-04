package hazelgrails

import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import java.util.concurrent.locks.Lock

import com.hazelcast.core.DistributedTask
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.ITopic
import com.hazelcast.core.Member
import com.hazelcast.core.MultiTask

import javax.annotation.PostConstruct

class HazelService {

    static transactional = false

    def grailsApplication

    HazelcastInstance instance

    private HazelService() {
    }

    @PostConstruct
    void init() {
        instance = Hazelcast.newHazelcastInstance(null)
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
        Hazelcast.getIdGenerator(name).newId()
    }

    Collection executeOnAllMembers(Callable callable) {
        Collection<Member> members = instance.cluster.members
        MultiTask multitask = new MultiTask(callable, members)
        Hazelcast.executorService.execute(multitask)
        return multitask.get()
    }

    Object executeOnSomewhere(Callable callable) {
        Hazelcast.executorService.submit(callable).get()
    }

    Object executeOnMemberOwningTheKey(Callable callable, Object key) {
        FutureTask task = new DistributedTask(callable, key)
        Hazelcast.executorService.execute(task)
        return task.get()
    }
}
