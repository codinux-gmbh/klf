package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory
import net.codinux.log.slf4j.Slf4jUtil
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

object JvmDefaults {

  fun createDefaultLoggerFactory(): ILoggerFactory {
    if (Slf4jUtil.useSlf4j) {
      return Slf4jLoggerFactory()
    }

    return DefaultLoggerFactory()
  }


  fun <T : Any> getLoggerName(forClass: KClass<T>): String = getUnwrappedLoggerClassName(forClass)
    .replace('$', '.') // for inner classes replace '$' with '.'

  fun <T : Any> getLoggerName(forClass: Class<T>): String = getLoggerName(forClass.kotlin)

  fun <T : Any> getLoggerClass(ofClass: KClass<T>): KClass<*> {
    return unwrapCompanionClass(ofClass)
  }

  // so that it can be substituted in native image generation
  private fun <T : Any> getUnwrappedLoggerClassName(forClass: KClass<T>): String =
    getLoggerClass(forClass).jvmName

  // unwrap companion class to enclosing class given a Java Class
  private fun <T : Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
    return if (ofClass.isCompanion) {
      ofClass.java.enclosingClass?.kotlin // enclosingClass should never be null, just in case
        ?: ofClass
    } else {
      ofClass
    }
  }


  fun getCurrentThreadName(): String? =
    Thread.currentThread().name

}