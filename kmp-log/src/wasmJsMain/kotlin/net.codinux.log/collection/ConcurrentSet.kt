package net.codinux.log.collection

import kotlin.collections.LinkedHashSet


// TODO: synchronize access
actual open class ConcurrentSet<E> : Set<E> {

    private val impl = LinkedHashSet<E>()


    actual fun add(element: E) = impl.add(element)

    override fun contains(element: E) = impl.contains(element)

    actual fun remove(element: E) = impl.remove(element)

    actual fun clear() = impl.clear()

    override val size = impl.size

    override fun isEmpty() = impl.isEmpty()

    override fun iterator() = impl.iterator()

    override fun containsAll(elements: Collection<E>) = impl.containsAll(elements)

}