package net.codinux.log.collection

import kotlin.collections.LinkedHashSet


// TODO: synchronize access
actual open class ConcurrentSet<E> : Set<E> {

    private val impl = LinkedHashSet<E>()


    actual fun add(element: E) = impl.add(element)

    actual override fun contains(element: E) = impl.contains(element)

    actual fun remove(element: E) = impl.remove(element)

    actual fun clear() = impl.clear()

    actual override val size = impl.size

    actual override fun isEmpty() = impl.isEmpty()

    actual override fun iterator() = impl.iterator()

    actual override fun containsAll(elements: Collection<E>) = impl.containsAll(elements)

}