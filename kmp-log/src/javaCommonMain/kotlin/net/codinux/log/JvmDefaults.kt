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


  fun <T : Any> getLoggerName(forClass: KClass<T>): String = getClassName(unwrapCompanionClass(forClass))

  fun <T : Any> getLoggerName(forClass: Class<T>): String = getLoggerName(forClass.kotlin)

  // unwrap companion class to enclosing class given a Java Class
  internal fun <T : Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
    return if (ofClass.isCompanion) {
      ofClass.java.enclosingClass?.kotlin // enclosingClass should never be null, just in case
        ?: ofClass
    } else {
      ofClass
    }
  }

  fun <T : Any> getClassName(forClass: KClass<T>): String =
    forClass.qualifiedName // os opposed to jvmName qualifiedName for inner classes already replaces '$' with '.'
      ?: forClass.jvmName.replace('$', '.')


  fun getCurrentThreadName(): String? =
    Thread.currentThread().name

}