package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender

internal actual object Platform {

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender by lazy { ConsoleAppender.Default }

  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual val appName: String? = null

}