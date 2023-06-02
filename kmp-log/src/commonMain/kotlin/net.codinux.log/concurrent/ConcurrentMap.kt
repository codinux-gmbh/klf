package net.codinux.log.concurrent

expect class ConcurrentMap<K, V> actual constructor() {

    fun get(key: K): V?

    fun put(key: K, value: V): V?

    fun remove(key: K): V?

    fun clear()

}


/**
 * Returns the value for the given [key] if the value is present and not `null`.
 * Otherwise, calls the [defaultValue] function,
 * puts its result into the map under the given key and returns the call result.
 *
 * Note that the operation is not guaranteed to be atomic if the map is being modified concurrently.
 */
fun <K, V> ConcurrentMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
    get(key)?.let { value ->
        return value
    }

    val newValue = defaultValue()

    put(key, newValue)

    return newValue
}