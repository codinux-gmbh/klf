package net.codinux.log

import kotlinx.browser.window
import net.codinux.log.appender.Appender
import net.codinux.log.appender.JsConsoleAppender
import kotlin.reflect.KClass

internal actual object Platform {

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender by lazy { JsConsoleAppender() }

  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual val appName: String? by lazy {
    try {
      window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'))
    } catch (e: Throwable) { // on Node.js window is not defined
      null
    }
  }

}