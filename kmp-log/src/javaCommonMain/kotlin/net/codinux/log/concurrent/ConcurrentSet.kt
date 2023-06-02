package net.codinux.log.concurrent

import java.util.concurrent.CopyOnWriteArraySet

actual open class ConcurrentSet<E> : CopyOnWriteArraySet<E>(), Set<E>