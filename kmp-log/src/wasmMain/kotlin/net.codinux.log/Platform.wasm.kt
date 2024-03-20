package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.appender.ConsoleAppender
import kotlin.reflect.KClass

actual class Platform {

    actual companion object {

        actual fun createDefaultLoggerFactory(): ILoggerFactory = DefaultLoggerFactory()

        actual val systemDefaultAppender: Appender = ConsoleAppender.Default

        actual fun <T : Any> getLoggerName(forClass: KClass<T>): String {
            // unwrapping companion objects is not possible on JS. There as class / logger name "Companion" will be used
            // do not use forClass.qualifiedName on JS, it will produce an error
            return forClass.simpleName ?: forClass.toString()
        }

        actual fun getCurrentThreadName(): String? = "main"

    }

}