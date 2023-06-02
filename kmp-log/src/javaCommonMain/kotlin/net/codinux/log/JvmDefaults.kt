package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory
import net.codinux.log.slf4j.Slf4jUtil
import kotlin.reflect.KClass

object JvmDefaults {

  fun createDefaultLoggerFactory(): ILoggerFactory {
    if (Slf4jUtil.useSlf4j) {
      return Slf4jLoggerFactory()
    }

    return DefaultLoggerFactory()
  }


  fun <T : Any> getLoggerName(forClass: KClass<T>) = getLoggerName(forClass.java)

  fun <T : Any> getLoggerName(forClass: Class<T>): String = unwrapCompanionClass(forClass).name

  // unwrap companion class to enclosing class given a Java Class
  private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
      ofClass.enclosingClass.kotlin.isCompanion == false
    } ?: ofClass
  }


  fun getCurrentThreadName(): String? =
    Thread.currentThread().name

}