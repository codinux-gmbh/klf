package net.codinux.log

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class logger<in R : Any> : ReadOnlyProperty<R, Logger> {
  override fun getValue(thisRef: R, property: KProperty<*>)
    = LoggerFactory.logger(thisRef.javaClass)
}


fun <R : Any> R.lazyLogger(): Lazy<Logger> {
  return lazy { LoggerFactory.logger(this.javaClass) }
}