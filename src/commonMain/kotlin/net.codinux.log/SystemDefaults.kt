package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.reflect.KClass


expect class SystemDefaults {

    companion object {

        fun createDefaultLoggerFactory(): ILoggerFactory

        fun getDefaultAppender(): Appender

        fun <T : Any> getLoggerName(forClass: KClass<T>): String

    }

}