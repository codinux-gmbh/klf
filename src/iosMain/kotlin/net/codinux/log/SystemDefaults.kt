package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.NSLogAppender
import kotlin.reflect.KClass

actual class SystemDefaults {

  actual companion object {

    private val defaultAppender = NSLogAppender()

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual fun getDefaultAppender(): Appender = defaultAppender

    actual fun <T : Any> getLoggerName(forClass: KClass<T>) =
      forClass.qualifiedName ?: forClass.simpleName ?: forClass.toString().replace("class ", "")

  }

}