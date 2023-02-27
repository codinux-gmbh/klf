package net.codinux.log

import kotlin.reflect.KClass

actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
  forClass.qualifiedName ?: forClass.simpleName ?: forClass.toString().replace("class ", "")