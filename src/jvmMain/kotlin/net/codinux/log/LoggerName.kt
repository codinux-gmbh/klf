package net.codinux.log

import kotlin.reflect.KClass

actual fun <T : Any> getLoggerName(forClass: KClass<T>) = getLoggerName(forClass.java)

fun <T : Any> getLoggerName(forClass: Class<T>): String = unwrapCompanionClass(forClass).name

// unwrap companion class to enclosing class given a Java Class
private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
  return ofClass.enclosingClass?.takeIf {
    ofClass.enclosingClass.kotlin.isCompanion == false
  } ?: ofClass
}