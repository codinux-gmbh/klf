package net.codinux.log

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


// by class

inline fun <reified R : Any> logger() = LoggerDelegate<R>()

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
  override fun getValue(thisRef: R, property: KProperty<*>) = LoggerFactory.getLogger(thisRef::class)
}


// by String / loggerName

fun logger(loggerName: String) = LoggerDelegateForName(loggerName)

class LoggerDelegateForName(val loggerName: String) {
  inline operator fun getValue(thisRef: Any, property: KProperty<*>) = LoggerFactory.getLogger(loggerName)
}