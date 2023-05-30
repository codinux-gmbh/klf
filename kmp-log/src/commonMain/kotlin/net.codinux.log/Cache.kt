package net.codinux.log

open class Cache<T> {

    // TODO: this structure is not thread-safe
    protected open val cache = hashMapOf<String, T>()

    open fun getOrCreate(key: String, createObject: (String) -> T): T {
        return cache.getOrPut(key) { createObject(key) }
    }

}