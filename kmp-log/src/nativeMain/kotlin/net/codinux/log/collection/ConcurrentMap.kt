package net.codinux.log.collection

import kotlin.native.concurrent.AtomicReference

actual open class ConcurrentMap<K, V> {

    protected open val atomicMap = AtomicReference(mapOf<K, V>())

    actual open fun get(key: K): V? =
        atomicMap.value[key]

    actual open fun put(key: K, value: V): V? {
        val previousValue = get(key)

        do {
            val existing = atomicMap.value

            val updated = existing.toMutableMap()
            updated[key] = value
        } while (atomicMap.compareAndSet(existing, updated) == false)

        return previousValue
    }

    actual open fun remove(key: K): V? {
        var previousValue: V?

        do {
            val existing = atomicMap.value

            val updated = existing.toMutableMap()
            previousValue = updated.remove(key)
        } while (atomicMap.compareAndSet(existing, updated) == false)

        return previousValue
    }

    actual open fun clear() {
        @Suppress("ControlFlowWithEmptyBody")
        while (atomicMap.compareAndSet(atomicMap.value, mapOf()) == false) { }
    }

}