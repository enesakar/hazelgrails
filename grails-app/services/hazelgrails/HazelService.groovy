package hazelgrails

import com.hazelcast.config.Config
import com.hazelcast.core.IExecutorService
import com.hazelcast.core.Member
import grails.core.GrailsApplication

import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.locks.Lock

import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.ITopic

import javax.annotation.PostConstruct

class HazelService {

    static transactional = false

    GrailsApplication grailsApplication

    HazelcastInstance instance

    private HazelService() {
    }

    @PostConstruct
    void init() {
        instance = Hazelcast.newHazelcastInstance()
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

    IExecutorService executorService(String executorName) {
        instance.getExecutorService(executorName)
    }

    Lock lock(Object lock) {
        instance.getLock(lock)
    }

    long generateId(String name) {
        Hazelcast.getIdGenerator(name).newId()
    }

    Map<Member, Future> executeOnAllMembers(Callable callable) {
        Map<Member, Future> result = instance.getExecutorService("default").submitToAllMembers(callable)
        return result
    }

    Object executeOnSomewhere(Callable callable) {
        return instance.getExecutorService("default").submit(callable).get()
    }

    Object executeOnMemberOwningTheKey(Callable callable, Object key) {
        return instance.getExecutorService("default").submitToKeyOwner(callable, key).get()
    }
}
