package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.reflect.KClass

internal expect object Platform {

    val type: PlatformType

    fun createDefaultLoggerFactory(): ILoggerFactory

    val systemDefaultAppender: Appender

    fun <T : Any> getLoggerName(forClass: KClass<T>): String

    fun getLoggerNameFromCallingMethod(): String?

    val appName: String?

}