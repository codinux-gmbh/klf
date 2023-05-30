package net.codinux.log

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified R : Any> logger() = LoggerDelegate<R>()

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
  override fun getValue(thisRef: R, property: KProperty<*>) = LoggerFactory.getLogger(thisRef::class)
}