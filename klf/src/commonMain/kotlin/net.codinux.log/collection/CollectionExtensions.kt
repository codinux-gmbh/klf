package net.codinux.log.collection


/**
 * Provides a faster, allocation-free iteration over Lists.
 *
 * For most application the speed and memory difference to [List.forEach] however should be neglectable.
 */
internal inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}