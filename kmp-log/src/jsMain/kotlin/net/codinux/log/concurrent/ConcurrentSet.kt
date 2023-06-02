package net.codinux.log.concurrent

/**
 * JavaScript has only one thread, so no need to take care of any thread-safety structures
 */
actual open class ConcurrentSet<E> : LinkedHashSet<E>(), Set<E>