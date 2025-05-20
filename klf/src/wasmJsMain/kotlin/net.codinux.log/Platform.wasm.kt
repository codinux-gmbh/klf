package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.JsConsoleAppender
import kotlin.reflect.KClass

// calls to js() have to be declared on package level (https://kotlinlang.org/docs/wasm-js-interop.html#kotlin-functions-with-javascript-code)
internal fun getCurrentPathname(): String =
    js("window.location.pathname")

internal actual object Platform {

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { JsConsoleAppender() }

    actual fun getLoggerNameFromCallingMethod(): String? = null

    actual val appName: String?  = null

}