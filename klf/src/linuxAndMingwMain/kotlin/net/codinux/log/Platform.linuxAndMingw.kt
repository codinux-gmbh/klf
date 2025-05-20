package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import net.codinux.log.util.LoggerNameResolver
import kotlin.reflect.KClass

internal actual object Platform {

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender by lazy { ConsoleAppender.Default }

  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual val appName: String? = null

}