package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory
import kotlin.reflect.KClass

object JvmDefaults {

  fun createDefaultLoggerFactory(): ILoggerFactory {
    if (isSlf4jOnClasspath) {
      // TODO: should we not use slf4j if it's LoggerFactory is org.slf4j.helpers.NOPLoggerFactory = no binding is available for slf4j?
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


  val isSlf4jOnClasspath = isClassAvailable("org.slf4j.Logger")

  private fun isClassAvailable(qualifiedClassName: String): Boolean {
    try {
      Class.forName(qualifiedClassName)

      return true
    } catch (ignored: Exception) { }

    return false
  }
}