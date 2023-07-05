package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.reflect.KClass


expect class Platform {

    companion object {

        fun createDefaultLoggerFactory(): ILoggerFactory

        val systemDefaultAppender: Appender

        fun <T : Any> getLoggerName(forClass: KClass<T>): String

        fun getCurrentThreadName(): String?

        val isRunningInDebugMode: Boolean

    }

}