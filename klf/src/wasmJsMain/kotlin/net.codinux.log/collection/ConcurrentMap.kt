package net.codinux.log.collection

import kotlin.collections.LinkedHashMap

// TODO: synchronize access
actual open class ConcurrentMap<K, V> {

    private val impl = LinkedHashMap<K, V>()

    actual fun get(key: K): V? = impl.get(key)

    actual fun put(key: K, value: V): V? = impl.put(key, value)

    actual fun remove(key: K): V? = impl.remove(key)

    actual fun clear() = impl.clear()

}