package net.codinux.log.concurrent

expect class ConcurrentSet<E> actual constructor() : Set<E> {

    /**
     * Adds the specified element to the set.
     *
     * @return `true` if the element has been added, `false` if the element is already contained in the set.
     */
    fun add(element: E): Boolean

    fun remove(element: E): Boolean

    fun clear()

}