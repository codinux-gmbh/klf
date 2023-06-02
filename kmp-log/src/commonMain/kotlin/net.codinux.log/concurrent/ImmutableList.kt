package net.codinux.log.concurrent

open class ImmutableList<E>(protected open val wrappedList: List<E>) : ImmutableCollection<E>(wrappedList), List<E> {

    constructor() : this(emptyList())


    override fun get(index: Int) = wrappedList[index]

    override fun indexOf(element: E) = wrappedList.indexOf(element)

    override fun lastIndexOf(element: E) = wrappedList.lastIndexOf(element)

    override fun listIterator() = wrappedList.listIterator()

    override fun listIterator(index: Int) = wrappedList.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = wrappedList.subList(fromIndex, toIndex)

}

fun <E> List<E>.toImmutableList(): ImmutableList<E> =
    ImmutableList(this)