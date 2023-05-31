package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.LogcatAppender
import kotlin.reflect.KClass

actual class Platform {

  actual companion object {

    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender = LogcatAppender()

    actual fun <T : Any> getLoggerName(forClass: KClass<T>): String = JvmDefaults.getLoggerName(forClass)

    actual fun getCurrentThreadName() =
      JvmDefaults.getCurrentThreadName()

  }
}