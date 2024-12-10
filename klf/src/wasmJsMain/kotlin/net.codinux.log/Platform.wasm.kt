package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import kotlin.reflect.KClass

// calls to js() have to be declared on package level (https://kotlinlang.org/docs/wasm-js-interop.html#kotlin-functions-with-javascript-code)
internal fun getCurrentPathname(): String =
    js("window.location.pathname")

internal actual object Platform {

    actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

    actual val systemDefaultAppender: Appender = ConsoleAppender.Default

    actual fun <T : Any> getLoggerName(forClass: KClass<T>): String {
        // unwrapping companion objects is not possible on JS. There as class / logger name "Companion" will be used
        // do not use forClass.qualifiedName on JS, it will produce an error
        return forClass.simpleName ?: forClass.toString()
    }

    actual fun getCurrentThreadName(): String? = "main" // TODO: how to get current thread in WebAssembly?

    actual fun lineSeparator(): String = "\n"

    actual val isRunningInDebugMode: Boolean = false // TODO: don't know how to do this in JS

    actual val appName: String?  = null

}