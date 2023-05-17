package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.JsConsoleAppender

actual class SystemDefaults {

  actual companion object {

    private val defaultAppender = JsConsoleAppender()

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual fun getDefaultAppender(): Appender = defaultAppender

  }

}