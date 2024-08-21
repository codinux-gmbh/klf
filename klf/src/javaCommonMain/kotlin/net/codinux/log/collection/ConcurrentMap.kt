package net.codinux.log.collection

import java.util.concurrent.ConcurrentHashMap

actual open class ConcurrentMap<K, V> {

    private val impl = ConcurrentHashMap<K, V>()

    actual fun get(key: K): V? = impl.get(key)

    actual fun put(key: K, value: V): V? =
        if (key is Any && value is Any) {
            impl.put(key, value)
        } else {
            null
        }

    actual fun remove(key: K): V? = impl.remove(key)

    actual fun clear() = impl.clear()

}