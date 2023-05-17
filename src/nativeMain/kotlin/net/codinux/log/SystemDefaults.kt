package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender

actual class SystemDefaults {

  actual companion object {

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DelegateToAppenderLoggerFactory()

    actual fun getDefaultAppender(): Appender = ConsoleAppender.Default

  }

}