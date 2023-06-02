package net.codinux.log.collection

import java.util.concurrent.ConcurrentHashMap

actual open class ConcurrentMap<K, V> : ConcurrentHashMap<K, V>()