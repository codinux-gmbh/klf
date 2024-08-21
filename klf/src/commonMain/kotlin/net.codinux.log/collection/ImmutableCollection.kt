package net.codinux.log.collection

open class ImmutableCollection<E>(protected open val wrappedCollection: Collection<E>) : Collection<E> {

    constructor() : this(emptyList())

    constructor(wrappedArray: Array<E>) : this(wrappedArray.toList())


    override val size: Int
        get() = wrappedCollection.size

    override fun isEmpty() = wrappedCollection.isEmpty()

    override fun iterator() = wrappedCollection.iterator()

    override fun contains(element: E) = wrappedCollection.contains(element)

    override fun containsAll(elements: Collection<E>) = wrappedCollection.containsAll(elements)

}

fun <E> Collection<E>.toImmutableCollection(): ImmutableCollection<E> =
    ImmutableCollection(this)