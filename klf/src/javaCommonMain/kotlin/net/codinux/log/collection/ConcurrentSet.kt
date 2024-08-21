package net.codinux.log.collection

import java.util.concurrent.CopyOnWriteArraySet

actual open class ConcurrentSet<E> : CopyOnWriteArraySet<E>(), Set<E>