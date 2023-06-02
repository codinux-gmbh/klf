package net.codinux.log.concurrent

import java.util.concurrent.ConcurrentHashMap

actual open class ConcurrentMap<K, V> : ConcurrentHashMap<K, V>()