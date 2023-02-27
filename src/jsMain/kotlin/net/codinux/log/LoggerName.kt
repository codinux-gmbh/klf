package net.codinux.log

import kotlin.reflect.KClass

actual fun <T : Any> getLoggerName(forClass: KClass<T>): String {
  // unwrapping companion objects is not possible on JS. There as class / logger name "Companion" will be used
  // do not use forClass.qualifiedName on JS, it will produce an error
  return forClass.js.name
}
