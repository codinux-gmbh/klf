package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.reflect.KClass

internal expect object Platform {

    fun createDefaultLoggerFactory(): ILoggerFactory

    val systemDefaultAppender: Appender

    fun <T : Any> getLoggerName(forClass: KClass<T>): String

    fun getCurrentThreadName(): String?

    fun lineSeparator(): String

    val isRunningInDebugMode: Boolean

    val appName: String?

}