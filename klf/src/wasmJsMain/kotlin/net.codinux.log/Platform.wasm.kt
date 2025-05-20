package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.JsConsoleAppender
import kotlin.reflect.KClass

// calls to js() have to be declared on package level (https://kotlinlang.org/docs/wasm-js-interop.html#kotlin-functions-with-javascript-code)
internal fun getCurrentPathname(): String =
    js("window.location.pathname")

internal actual object Platform {

    actual val type = PlatformType.Wasm

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { JsConsoleAppender() }

    actual fun <T : Any> getLoggerName(forClass: KClass<T>): String {
        // unwrapping companion objects is not possible on JS. There as class / logger name "Companion" will be used
        // do not use forClass.qualifiedName on JS, it will produce an error
        return forClass.simpleName ?: forClass.toString()
    }

    actual fun getLoggerNameFromCallingMethod(): String? = null

    actual val isRunningInDebugMode: Boolean = false // TODO: don't know how to do this in JS

    actual val appName: String?  = null

}