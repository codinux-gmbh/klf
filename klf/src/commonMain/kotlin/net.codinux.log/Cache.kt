package net.codinux.log

import net.codinux.kotlin.concurrent.collections.ConcurrentMap

open class Cache<Key, Value> {

    protected open val cache = ConcurrentMap<Key, Value>()

    open fun getOrPut(key: Key, createObject: (Key) -> Value): Value {
        return cache.getOrPut(key) { createObject(key) }
    }

}