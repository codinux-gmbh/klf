package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.NSLogAppender
import kotlin.reflect.KClass

actual class Platform {

  actual companion object {

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual val systemDefaultAppender: Appender = NSLogAppender()

    actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
      forClass.qualifiedName ?: forClass.simpleName ?: forClass.toString().replace("class ", "")

  }

}