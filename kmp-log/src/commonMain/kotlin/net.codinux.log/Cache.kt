package net.codinux.log

import net.codinux.log.collection.ConcurrentMap
import net.codinux.log.collection.getOrPut

open class Cache<T> {

    protected open val cache = ConcurrentMap<String, T>()

    open fun getOrPut(key: String, createObject: (String) -> T): T {
        return cache.getOrPut(key) { createObject(key) }
    }

}