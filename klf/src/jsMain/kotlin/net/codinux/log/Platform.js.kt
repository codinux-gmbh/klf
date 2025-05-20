package net.codinux.log

import kotlinx.browser.window
import net.codinux.log.appender.Appender
import net.codinux.log.appender.JsConsoleAppender
import kotlin.reflect.KClass

internal actual object Platform {

  actual val type = PlatformType.Js

  actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

  actual val systemDefaultAppender: Appender by lazy { JsConsoleAppender() }

  actual fun <T : Any> getLoggerName(forClass: KClass<T>): String {
    // unwrapping companion objects is not possible on JS. There as class / logger name "Companion" will be used
    // do not use forClass.qualifiedName on JS, it will produce an error
    return forClass.simpleName ?: forClass.js.name
  }

  actual fun getLoggerNameFromCallingMethod(): String? = null

  actual val appName: String? by lazy {
    try {
      window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'))
    } catch (e: Throwable) { // on Node.js window is not defined
      null
    }
  }

}