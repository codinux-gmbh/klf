package net.codinux.log.collection

import kotlin.concurrent.AtomicReference

actual open class ConcurrentSet<E> : Set<E> {

    protected open val atomicSet = AtomicReference(setOf<E>())


    actual fun add(element: E): Boolean {
        if (contains(element)) {
            return false
        }

        do {
            val existing = atomicSet.value

            val updated = existing.toMutableSet()
            updated.add(element)
        } while (atomicSet.compareAndSet(existing, updated) == false)

        return true
    }

    override fun contains(element: E) =
        atomicSet.value.contains(element)

    actual fun remove(element: E): Boolean {
        var removeResult: Boolean

        do {
            val existing = atomicSet.value

            val updated = existing.toMutableSet()
            removeResult = updated.remove(element)
        } while (atomicSet.compareAndSet(existing, updated) == false)

        return removeResult
    }

    actual fun clear() {
        @Suppress("ControlFlowWithEmptyBody")
        while (atomicSet.compareAndSet(atomicSet.value, setOf()) == false) { }
    }

    override val size: Int
        get() = atomicSet.value.size

    override fun isEmpty() = atomicSet.value.isEmpty()

    override fun iterator() = atomicSet.value.iterator()

    override fun containsAll(elements: Collection<E>) = atomicSet.value.containsAll(elements)

}