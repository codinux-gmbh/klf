package net.codinux.log

import net.codinux.log.collection.ConcurrentMap
import net.codinux.log.collection.getOrPut

open class Cache<Key, Value> {

    protected open val cache = ConcurrentMap<Key, Value>()

    open fun getOrPut(key: Key, createObject: (Key) -> Value): Value {
        return cache.getOrPut(key) { createObject(key) }
    }

}