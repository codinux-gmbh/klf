package net.codinux.log.collection

import kotlin.concurrent.AtomicReference

actual open class ConcurrentSet<E> : Set<E> {

    protected open val atomicSet = AtomicReference(mutableSetOf<E>())


    actual fun add(element: E): Boolean {
        if (contains(element)) {
            return false
        }

        do {
            val existing = atomicSet.value

            val updated = existing
            updated.add(element)
        } while (atomicSet.compareAndSet(existing, updated) == false)

        return true
    }

    actual override fun contains(element: E) =
        atomicSet.value.contains(element)

    actual fun remove(element: E): Boolean {
        var removeResult: Boolean

        do {
            val existing = atomicSet.value

            val updated = existing
            removeResult = updated.remove(element)
        } while (atomicSet.compareAndSet(existing, updated) == false)

        return removeResult
    }

    actual fun clear() {
        @Suppress("ControlFlowWithEmptyBody")
        while (atomicSet.compareAndSet(atomicSet.value, mutableSetOf()) == false) { }
    }

    actual override val size: Int
        get() = atomicSet.value.size

    actual override fun isEmpty() = atomicSet.value.isEmpty()

    actual override fun iterator() = atomicSet.value.iterator()

    actual override fun containsAll(elements: Collection<E>) = atomicSet.value.containsAll(elements)

}