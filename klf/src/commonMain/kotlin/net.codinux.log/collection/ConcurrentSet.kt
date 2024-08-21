package net.codinux.log.collection

expect class ConcurrentSet<E>() : Set<E> {

    override val size: Int

    override operator fun contains(element: E): Boolean

    override fun containsAll(elements: kotlin.collections.Collection<E>): kotlin.Boolean

    override fun isEmpty(): kotlin.Boolean

    override operator fun iterator(): MutableIterator<E>

    /**
     * Adds the specified element to the set.
     *
     * @return `true` if the element has been added, `false` if the element is already contained in the set.
     */
    fun add(element: E): Boolean

    fun remove(element: E): Boolean

    fun clear()

}